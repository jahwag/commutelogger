package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchCarpoolingException extends ApiException {

    public NoSuchCarpoolingException() {
        super(HttpStatus.NOT_FOUND, "Carpooling does not exist.");
    }
}
