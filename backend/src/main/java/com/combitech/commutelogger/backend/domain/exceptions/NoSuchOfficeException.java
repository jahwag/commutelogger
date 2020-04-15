package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchOfficeException extends ApiException {

    public NoSuchOfficeException() {
        super(HttpStatus.NOT_FOUND, "Office does not exist.");
    }
}
