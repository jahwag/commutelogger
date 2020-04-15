package com.combitech.commutelogger.backend.application.controllers;

import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.exceptions.OperationForbiddenException;
import com.combitech.commutelogger.backend.domain.logging.InMemoryAppenderAccess;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/logs")
@Secured(ApplicationRoles.USER)
public class LoggingController {

    private final Accounts accounts;

    public LoggingController(Accounts accounts) {
        this.accounts = accounts;
    }

    @GetMapping
    public List<String> logs(PreAuthenticatedAuthenticationToken token) {
        Account user = accounts.of(token);

        if (!user.admin()) {
            throw new OperationForbiddenException();
        }
        return InMemoryAppenderAccess.content();
    }

}
