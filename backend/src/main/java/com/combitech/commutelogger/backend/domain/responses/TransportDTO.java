package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.entities.Transport;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TransportDTO {

    String name;

    boolean shareable;

    public static TransportDTO from(Transport transport) {
        return TransportDTO.builder()
                           .name(transport.name())
                           .shareable(transport.shareable())
                           .build();
    }
}
