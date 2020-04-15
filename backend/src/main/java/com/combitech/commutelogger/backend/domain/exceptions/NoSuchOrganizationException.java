package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchOrganizationException extends ApiException {

    public NoSuchOrganizationException() {
        super(HttpStatus.NOT_FOUND, "Organization does not exist.");
    }
}
