package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.entities.Organization;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Value
@Builder
public class OrganizationDTO {

    long id;

    String name;

    String domain;

    List<OfficeDTO> offices;

    public static OrganizationDTO of(Organization organization) {
        return OrganizationDTO.builder()
                              .id(organization.id())
                              .name(organization.name())
                              .domain(organization.domain())
                              .offices(organization.offices()
                                                   .stream()
                                                   .map(OfficeDTO::of)
                                                   .collect(toList()))
                              .build();
    }
}
