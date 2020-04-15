package com.combitech.commutelogger.backend.application.controllers;

import com.azure.core.annotation.QueryParam;
import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Organization;
import com.combitech.commutelogger.backend.domain.exceptions.OperationForbiddenException;
import com.combitech.commutelogger.backend.domain.exceptions.SubscriptionRequiredException;
import com.combitech.commutelogger.backend.domain.responses.ChartDTO;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import com.combitech.commutelogger.backend.infrastructure.Offices;
import com.combitech.commutelogger.backend.infrastructure.Organizations;
import com.combitech.commutelogger.backend.infrastructure.StatisticsCo2s;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@Slf4j
@RestController
@RequestMapping("/api/offices")
@Secured(ApplicationRoles.USER)
public class OfficesController {

    private final Organizations organizations;

    private final Offices offices;

    private final Accounts accounts;

    private final StatisticsCo2s statisticsCo2s;

    @Autowired
    public OfficesController(Organizations organizations, Offices offices, Accounts accounts, StatisticsCo2s statisticsCo2s) {
        this.organizations = organizations;
        this.offices = offices;
        this.accounts = accounts;
        this.statisticsCo2s = statisticsCo2s;
    }

    @Cacheable(value = "officesList", keyGenerator = "OrganizationKeys")
    @Transactional(readOnly = true)
    @GetMapping
    public List<String> offices(PreAuthenticatedAuthenticationToken token) {
        return organizations.findByToken(token)
                            .orElseThrow(SubscriptionRequiredException::new)
                            .offices()
                            .stream()
                            .map(Office::name)
                            .collect(Collectors.toList());
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void delete(PreAuthenticatedAuthenticationToken token, @PathVariable("id") long id) {
        Account user = accounts.of(token);

        if (!user.admin()) {
            throw new OperationForbiddenException();
        }

        offices.deleteById(id);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}/co2")
    public ChartDTO co2(PreAuthenticatedAuthenticationToken token,
                        @PathVariable("id") long officeId,
                        @QueryParam("start") LocalDate start,
                        @QueryParam("end") LocalDate end,
                        @QueryParam("sortBy") String... sortBy) {
        Account account = accounts.of(token);
        Office accountOffice = account.office();
        Organization accountOrganization = accountOffice.organization();
        Long accountOrganizationId = accountOrganization.id();

        if (!accountOrganizationId.equals(officeId)) {
            throw new OperationForbiddenException();
        }

        return ChartDTO.from(statisticsCo2s.findAllByOfficeIdInAndDateBetween(singletonList(officeId),
                start == null ? LocalDate.now()
                                         .minusMonths(1) : start,
                end == null ? LocalDate.now() : end,
                sortBy == null ? Sort.by("date") : Sort.by(sortBy)));
    }

}
