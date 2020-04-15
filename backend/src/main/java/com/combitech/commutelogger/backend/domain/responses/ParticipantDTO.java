package com.combitech.commutelogger.backend.domain.responses;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ParticipantDTO {

    String name;

    long co2;

    long km;

}
