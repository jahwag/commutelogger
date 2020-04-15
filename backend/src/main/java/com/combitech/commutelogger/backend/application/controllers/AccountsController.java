package com.combitech.commutelogger.backend.application.controllers;

import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchAccountException;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchOfficeException;
import com.combitech.commutelogger.backend.domain.exceptions.OperationForbiddenException;
import com.combitech.commutelogger.backend.domain.requests.EditAccountRequest;
import com.combitech.commutelogger.backend.domain.responses.AccountDTO;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import com.combitech.commutelogger.backend.infrastructure.Offices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
@Secured(ApplicationRoles.USER)
public class AccountsController {

    private final Accounts accounts;

    private final Offices offices;

    public AccountsController(Accounts accounts, Offices offices) {
        this.accounts = accounts;
        this.offices = offices;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<AccountDTO> list() {
        return accounts.findAll()
                       .stream()
                       .map(AccountDTO::from)
                       .collect(toList());
    }

    @Transactional
    @PutMapping("/{email}")
    public AccountDTO edit(@PathVariable("email") String email, @RequestBody EditAccountRequest request) {
        Account account = accounts.findById(email)
                                  .orElseThrow(NoSuchAccountException::new);
        account.admin(request.admin());
        account.office(offices.findByOrganizationIdAndAndName(request.organizationId(), request.office())
                              .orElseThrow(NoSuchOfficeException::new));

        return AccountDTO.from(accounts.save(account));
    }

    @Transactional
    @DeleteMapping("/{email}")
    public void delete(PreAuthenticatedAuthenticationToken token, @PathVariable("email") String email) {
        Account user = accounts.of(token);
        String userEmail = user.email();

        if (!user.admin()) {
            throw new OperationForbiddenException();
        } else if (userEmail.equals(email)) {
            throw new OperationForbiddenException();
        }

        accounts.deleteById(email);
    }

}
