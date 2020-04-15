package com.combitech.commutelogger.backend.domain.requests;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class EditAccountRequest {

    boolean admin;

    long organizationId;

    String office;

}
