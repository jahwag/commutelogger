package com.combitech.commutelogger.backend.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
@Getter
@Setter
@Builder(toBuilder = true)
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
public class CarpoolingParameters {

    @NotBlank
    private String detourFlexibility;

    @NotBlank
    private boolean tolls;

    @NotBlank
    private String costSharing;

    @NotBlank
    private int seats;

}
