package com.combitech.commutelogger.backend.domain.requests;

import com.combitech.commutelogger.backend.domain.entities.CarpoolingDays;
import com.combitech.commutelogger.backend.domain.entities.CarpoolingJourney;
import com.combitech.commutelogger.backend.domain.entities.CarpoolingParameters;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Embedded;

@Value
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarpoolingRequest {

    @Embedded
    CarpoolingJourney where;

    @Embedded
    CarpoolingDays when;

    @Embedded
    CarpoolingParameters how;

    String comments;

}
