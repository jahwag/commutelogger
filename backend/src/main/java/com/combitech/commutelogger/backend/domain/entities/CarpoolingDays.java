package com.combitech.commutelogger.backend.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@Builder(toBuilder = true)
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class CarpoolingDays {

    private boolean monday;

    private boolean tuesday;

    private boolean wednesday;

    private boolean thursday;

    private boolean friday;

}
