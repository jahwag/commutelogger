package com.combitech.commutelogger.backend.domain.cache;

import com.combitech.commutelogger.backend.infrastructure.Accounts;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("OrganizationKeys")
public class OrganizationKeys implements KeyGenerator {

    private final Accounts accounts;

    public OrganizationKeys(Accounts accounts) {
        this.accounts = accounts;
    }

    @Override
    public Object generate(Object target, Method method, Object... params) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            PreAuthenticatedAuthenticationToken token = (PreAuthenticatedAuthenticationToken) authentication;

            return accounts.of(token)
                           .office()
                           .organization()
                           .id();
        }

        throw new RuntimeException(String.format("Expected but did not find PreAuthenticatedAuthenticationToken on method %s.%s", target.getClass(), method.getName()));
    }


}
