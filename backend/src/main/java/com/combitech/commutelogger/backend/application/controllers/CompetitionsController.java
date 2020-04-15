package com.combitech.commutelogger.backend.application.controllers;

import com.azure.core.annotation.QueryParam;
import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Competition;
import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Organization;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchCompetitionException;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchOrganizationException;
import com.combitech.commutelogger.backend.domain.exceptions.OperationForbiddenException;
import com.combitech.commutelogger.backend.domain.requests.CompetitionRequest;
import com.combitech.commutelogger.backend.domain.responses.ChartDTO;
import com.combitech.commutelogger.backend.domain.responses.CompetitionDTO;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import com.combitech.commutelogger.backend.infrastructure.Competitions;
import com.combitech.commutelogger.backend.infrastructure.Offices;
import com.combitech.commutelogger.backend.infrastructure.StatisticsCo2s;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/api/competitions")
@Secured(ApplicationRoles.USER)
public class CompetitionsController {

    private final Accounts accounts;

    private final Offices offices;

    private final Competitions competitions;

    private final StatisticsCo2s statisticsCo2s;

    public CompetitionsController(Accounts accounts, Offices offices, Competitions competitions, StatisticsCo2s statisticsCo2s) {
        this.accounts = accounts;
        this.offices = offices;
        this.competitions = competitions;
        this.statisticsCo2s = statisticsCo2s;
    }

    @Cacheable(value = "competitionsList", keyGenerator = "OrganizationKeys")
    @Transactional(readOnly = true)
    @GetMapping
    public List<CompetitionDTO> competitions(PreAuthenticatedAuthenticationToken token, @QueryParam("full") boolean full) {
        Account account = accounts.of(token);
        Office office = account.office();
        List<Competition> competitions = !full ? office.competitions() : office.organization()
                                                                               .offices()
                                                                               .stream()
                                                                               .map(Office::competitions)
                                                                               .flatMap(Collection::stream)
                                                                               .distinct()
                                                                               .collect(toList());

        return competitions.stream()
                           .map(CompetitionDTO::of)
                           .sorted(Comparator.comparing(CompetitionDTO::getEnds)
                                             .reversed())
                           .collect(toList());
    }

    @Cacheable(value = "competitions", key = "#id")
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public CompetitionDTO find(PreAuthenticatedAuthenticationToken token, @PathVariable("id") long id) {
        Account account = accounts.of(token);
        Office userOffice = account.office();
        Organization userOrganization = userOffice.organization();

        Competition competition = competitions.findById(id)
                                              .orElseThrow(NoSuchCompetitionException::new);
        Organization competitionOrganization = competition.participants()
                                                          .stream()
                                                          .findFirst()
                                                          .map(Office::organization)
                                                          .orElseThrow(NoSuchOrganizationException::new);

        if (!userOrganization.equals(competitionOrganization)) {
            throw new OperationForbiddenException();
        }

        return CompetitionDTO.of(competition);
    }

    @CacheEvict(value = "competitionsList", keyGenerator = "OrganizationKeys")
    @Transactional
    @PostMapping
    public void add(@Valid @RequestBody CompetitionRequest request) {
        edit(null, request);
    }

    @CacheEvict(value = "competitionsList", keyGenerator = "OrganizationKeys")
    @Transactional
    @PutMapping("/{id}")
    public void edit(@PathVariable("id") Long id, @Valid @RequestBody CompetitionRequest request) {
        Competition competition = Competition.builder()
                                             .id(id)
                                             .title(request.title())
                                             .participants(offices.findAllByNameIn(request.participants()))
                                             .begins(request.begins())
                                             .ends(request.ends())
                                             .description(request.description())
                                             .build();
        competitions.save(competition);
    }

    @CacheEvict(value = "competitionsList", keyGenerator = "OrganizationKeys")
    @Transactional
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        competitions.deleteById(id);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}/co2")
    public ChartDTO co2(PreAuthenticatedAuthenticationToken token,
                        @PathVariable("id") long competitionId) {
        Account account = accounts.of(token);
        Office accountOffice = account.office();
        Competition competition = competitions.findById(competitionId)
                                              .orElseThrow(NoSuchCompetitionException::new);
        List<Office> participants = competition.participants();

        if (!participants.contains(accountOffice)) {
            throw new OperationForbiddenException();
        }

        return ChartDTO.from(statisticsCo2s.findAllByOfficeIdInAndDateBetween(participants.stream()
                                                                                          .map(Office::id)
                                                                                          .collect(Collectors.toList()),
                competition.begins(),
                competition.ends(),
                Sort.by("date")));
    }

}
