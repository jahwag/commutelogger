package com.combitech.commutelogger.backend.application.controllers;

import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Organization;
import com.combitech.commutelogger.backend.domain.events.UserChangedOfficeEvent;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchOfficeException;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchOrganizationException;
import com.combitech.commutelogger.backend.domain.exceptions.ResourseConflictException;
import com.combitech.commutelogger.backend.domain.exceptions.SubscriptionRequiredException;
import com.combitech.commutelogger.backend.domain.requests.CreateAccountRequest;
import com.combitech.commutelogger.backend.domain.requests.SwitchOfficeRequest;
import com.combitech.commutelogger.backend.domain.responses.AccountDTO;
import com.combitech.commutelogger.backend.domain.responses.ChartDTO;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import com.combitech.commutelogger.backend.infrastructure.Offices;
import com.combitech.commutelogger.backend.infrastructure.Organizations;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/me")
@Secured(ApplicationRoles.USER)
public class MeController {

    private final ObjectMapper mapper;

    private final Accounts accounts;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Organizations organizations;

    private final Offices offices;

    private final OfficesController officesController;

    private final OrganizationsController organizationsController;

    public MeController(ObjectMapper mapper, Accounts accounts, ApplicationEventPublisher applicationEventPublisher,
                        Organizations organizations, Offices offices, OfficesController officesController, OrganizationsController organizationsController) {
        this.mapper = mapper;
        this.accounts = accounts;
        this.applicationEventPublisher = applicationEventPublisher;
        this.organizations = organizations;
        this.offices = offices;
        this.officesController = officesController;
        this.organizationsController = organizationsController;
    }

    @Transactional
    @PostMapping
    public void register(PreAuthenticatedAuthenticationToken token, @RequestBody @Valid CreateAccountRequest request) {
        String email = Account.emailOf(token);
        if (accounts.existsById(email)) {
            throw new ResourseConflictException();
        }

        Organization organization = organizations.findByToken(token)
                                                 .orElseThrow(SubscriptionRequiredException::new);
        accounts.save(Account.builder()
                             .email(email)
                             .office(offices.findByOrganizationIdAndAndName(organization.id(), request.office())
                                            .orElseThrow(NoSuchOfficeException::new))
                             .created(Timestamp.valueOf(LocalDateTime.now()))
                             .admin(false)
                             .enabled(true)
                             .build());
    }

    @Transactional
    @DeleteMapping
    public void unregister(PreAuthenticatedAuthenticationToken token) {
        accounts.delete(accounts.of(token));
    }

    @Transactional(readOnly = true)
    @GetMapping
    public AccountDTO about(PreAuthenticatedAuthenticationToken token) {
        return AccountDTO.from(accounts.of(token));
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/download")
    public String download(PreAuthenticatedAuthenticationToken token) {
        Account account = accounts.of(token);
        account.office(Office.builder()
                             .name(account.office()
                                          .name())
                             .build()); // shallow office

        try {
            return mapper.writeValueAsString(account);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @PostMapping("/office")
    public void switchOffice(PreAuthenticatedAuthenticationToken token, @RequestBody SwitchOfficeRequest request) {
        Account account = accounts.of(token);
        Office oldOffice = account.office();
        Organization organization = oldOffice.organization();
        Office newOffice = organization.offices()
                                       .stream()
                                       .filter(o -> o.name()
                                                     .equals(request.office()))
                                       .findAny()
                                       .orElseThrow(NoSuchOrganizationException::new);
        account.office(newOffice);
        accounts.save(account);

        applicationEventPublisher.publishEvent(new UserChangedOfficeEvent(this, oldOffice, newOffice));
    }

    @Transactional(readOnly = true)
    @GetMapping("/office/co2")
    public ChartDTO officeCo2(PreAuthenticatedAuthenticationToken token, LocalDate start, LocalDate end, String... sortBy) {
        Account account = accounts.of(token);
        Office accountOffice = account.office();

        return officesController.co2(token, accountOffice.id(), start, end, sortBy);
    }

    @Transactional(readOnly = true)
    @GetMapping("/organization/co2")
    public ChartDTO organizationCo2(PreAuthenticatedAuthenticationToken token, LocalDate start, LocalDate end, String... sortBy) {
        Account account = accounts.of(token);
        Office accountOffice = account.office();
        Organization accountOrganization = accountOffice.organization();

        return organizationsController.co2(token, accountOrganization.id(), start, end, sortBy);
    }

}
