package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchAccountException extends ApiException {

    public NoSuchAccountException() {
        super(HttpStatus.NOT_FOUND, "Account does not exist.");
    }
}
