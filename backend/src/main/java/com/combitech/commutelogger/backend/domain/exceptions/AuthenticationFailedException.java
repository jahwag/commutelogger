package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends ApiException {

    private static final AuthenticationFailedException INSTANCE = new AuthenticationFailedException();

    private AuthenticationFailedException() {
        super(HttpStatus.UNAUTHORIZED, "The email and password you entered did not match our records.\nPlease double-check and try again.");
    }

    public static AuthenticationFailedException instance() {
        return INSTANCE;
    }

}
