package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class ResourseConflictException extends ApiException {

    public ResourseConflictException() {
        super(HttpStatus.CONFLICT, "Resource already exists.");
    }
}
