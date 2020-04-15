package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class TransportNotFoundException extends ApiException {

    public TransportNotFoundException(String name) {
        super(HttpStatus.BAD_REQUEST, "Transport '" + name + "' not found.");
    }
}
