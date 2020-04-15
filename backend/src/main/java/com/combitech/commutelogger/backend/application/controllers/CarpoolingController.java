package com.combitech.commutelogger.backend.application.controllers;

import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Carpooling;
import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Organization;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchCarpoolingException;
import com.combitech.commutelogger.backend.domain.requests.CarpoolingRequest;
import com.combitech.commutelogger.backend.domain.responses.CarpoolingDTO;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import com.combitech.commutelogger.backend.infrastructure.Carpoolings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/api/carpoolings")
@Secured(ApplicationRoles.USER)
public class CarpoolingController {

    private final Accounts accounts;

    private final Carpoolings carPoolings;

    public CarpoolingController(Accounts accounts, Carpoolings carPoolings) {
        this.accounts = accounts;
        this.carPoolings = carPoolings;
    }

    @GetMapping
    public List<CarpoolingDTO> list(PreAuthenticatedAuthenticationToken token) {
        Account account = accounts.of(token);
        Office office = account.office();
        Organization organization = office.organization();

        return carPoolings.findByAuthorOfficeOrganizationId(organization.id(), Sort.by(Sort.Direction.DESC, "lastModified"))
                          .stream()
                          .map(CarpoolingDTO::of)
                          .collect(toList());
    }

    @GetMapping("/{id}")
    public CarpoolingDTO findById(PreAuthenticatedAuthenticationToken token, @PathVariable long id) {
        Account account = accounts.of(token);
        Carpooling carpooling = carPoolings.findByIdAndAuthorEmail(id, account.email())
                                           .orElseThrow(NoSuchCarpoolingException::new);

        return CarpoolingDTO.of(carpooling);
    }

    @PostMapping
    public void add(PreAuthenticatedAuthenticationToken token,
                    @Valid @RequestBody CarpoolingRequest carpoolingRequest) {
        edit(token, carpoolingRequest, null);
    }

    @PutMapping("/{id}")
    public void edit(PreAuthenticatedAuthenticationToken token,
                     @Valid @RequestBody CarpoolingRequest carpoolingRequest, @PathVariable Long id) {
        carPoolings.save(Carpooling.builder()
                                   .id(id)
                                   .author(accounts.of(token))
                                   .where(carpoolingRequest.where())
                                   .when(carpoolingRequest.when())
                                   .how(carpoolingRequest.how())
                                   .comments(carpoolingRequest.comments())
                                   .lastModified(LocalDateTime.now())
                                   .build());
    }
}
