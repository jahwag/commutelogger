package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class AuthorizationFailedException extends ApiException {

    private static final AuthorizationFailedException INSTANCE = new AuthorizationFailedException();

    private AuthorizationFailedException() {
        super(HttpStatus.FORBIDDEN, "You do not have permission to perform this action.");
    }

    public static AuthorizationFailedException instance() {
        return INSTANCE;
    }
}
