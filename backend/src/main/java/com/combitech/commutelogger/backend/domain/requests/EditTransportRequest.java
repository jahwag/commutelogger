package com.combitech.commutelogger.backend.domain.requests;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class EditTransportRequest {

    String name;

    int gramCo2PerKm;

    boolean perPersonKm;

    boolean shareable;

}
