package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.entities.Office;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OfficeDTO {

    long id;

    String name;

    public static OfficeDTO of(Office office) {
        return OfficeDTO.builder()
                        .id(office.id())
                        .name(office.name())
                        .build();
    }

}
