package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class OperationNotAllowed extends ApiException {

    public OperationNotAllowed(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

}
