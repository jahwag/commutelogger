package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class TripAccessForbiddenException extends ApiException {

    public TripAccessForbiddenException() {
        super(HttpStatus.FORBIDDEN, "Access to this trip is forbidden.");
    }
}
