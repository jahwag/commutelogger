package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class NoSuchCompetitionException extends ApiException {

    public NoSuchCompetitionException() {
        super(HttpStatus.NOT_FOUND, "Competition does not exist.");
    }
}
