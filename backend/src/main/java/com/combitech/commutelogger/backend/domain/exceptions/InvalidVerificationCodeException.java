package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidVerificationCodeException extends ApiException {

    public InvalidVerificationCodeException() {
        super(HttpStatus.BAD_REQUEST, "Invalid verification code.");
    }
}
