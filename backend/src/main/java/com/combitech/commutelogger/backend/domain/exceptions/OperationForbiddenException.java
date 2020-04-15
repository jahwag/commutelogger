package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class OperationForbiddenException extends ApiException {

    public OperationForbiddenException() {
        super(HttpStatus.FORBIDDEN, "Operation forbidden.");
    }

}
