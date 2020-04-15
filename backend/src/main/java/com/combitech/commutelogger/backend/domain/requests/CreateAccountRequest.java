package com.combitech.commutelogger.backend.domain.requests;

import lombok.Value;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(fluent = true)
@Value
public class CreateAccountRequest {

    @NotBlank
    String office;

}
