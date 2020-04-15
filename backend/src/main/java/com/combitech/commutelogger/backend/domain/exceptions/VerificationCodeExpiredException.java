package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class VerificationCodeExpiredException extends ApiException {

    public VerificationCodeExpiredException() {
        super(HttpStatus.TOO_MANY_REQUESTS, "Verification code has expired, please request a new one.");
    }
}
