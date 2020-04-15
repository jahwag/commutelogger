package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class TripNotFoundException extends ApiException {

    private static final TripNotFoundException INSTANCE = new TripNotFoundException();

    public TripNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Trip not found.");
    }

    public static TripNotFoundException instance() {
        return INSTANCE;
    }
}
