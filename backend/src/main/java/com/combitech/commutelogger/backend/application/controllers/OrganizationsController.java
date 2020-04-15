package com.combitech.commutelogger.backend.application.controllers;

import com.azure.core.annotation.QueryParam;
import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Organization;
import com.combitech.commutelogger.backend.domain.exceptions.OperationForbiddenException;
import com.combitech.commutelogger.backend.domain.responses.ChartDTO;
import com.combitech.commutelogger.backend.domain.responses.OrganizationDTO;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import com.combitech.commutelogger.backend.infrastructure.Organizations;
import com.combitech.commutelogger.backend.infrastructure.StatisticsCo2s;
import lombok.extern.slf4j.Slf4j;
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

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/api/organizations")
@Secured(ApplicationRoles.USER)
public class OrganizationsController {

    private final Organizations organizations;

    private final Accounts accounts;

    private final StatisticsCo2s statisticsCo2s;

    public OrganizationsController(Organizations organizations, Accounts accounts, StatisticsCo2s statisticsCo2s) {
        this.organizations = organizations;
        this.accounts = accounts;
        this.statisticsCo2s = statisticsCo2s;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<OrganizationDTO> list() {
        return organizations.findAll()
                            .stream()
                            .map(OrganizationDTO::of)
                            .collect(toList());
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void delete(PreAuthenticatedAuthenticationToken token, @PathVariable("id") long id) {
        Account user = accounts.of(token);

        if (!user.admin()) {
            throw new OperationForbiddenException();
        }

        organizations.deleteById(id);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}/co2")
    public ChartDTO co2(PreAuthenticatedAuthenticationToken token,
                        @PathVariable("id") long organizationId,
                        @QueryParam("start") LocalDate start,
                        @QueryParam("end") LocalDate end,
                        @QueryParam("sortBy") String... sortBy) {
        Account account = accounts.of(token);
        Office accountOffice = account.office();
        Organization accountOrganization = accountOffice.organization();
        Long accountOrganizationId = accountOrganization.id();

        if (!accountOrganizationId.equals(organizationId)) {
            throw new OperationForbiddenException();
        }

        return ChartDTO.from(statisticsCo2s.findAllByOfficeOrganizationIdInAndDateBetween(singletonList(organizationId),
                start == null ? LocalDate.now()
                                         .minusMonths(1) : start,
                end == null ? LocalDate.now() : end,
                sortBy == null ? Sort.by("date") : Sort.by(sortBy)));
    }

}
