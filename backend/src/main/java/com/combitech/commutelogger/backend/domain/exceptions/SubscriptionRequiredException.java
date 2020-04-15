package com.combitech.commutelogger.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class SubscriptionRequiredException extends ApiException {

    public SubscriptionRequiredException() {
        super(HttpStatus.FORBIDDEN, "Sorry, this service is not available to your organization.");
    }

}
