package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.entities.Carpooling;
import com.combitech.commutelogger.backend.domain.entities.CarpoolingDays;
import com.combitech.commutelogger.backend.domain.entities.CarpoolingJourney;
import com.combitech.commutelogger.backend.domain.entities.CarpoolingParameters;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Embedded;
import java.time.format.DateTimeFormatter;

@Value
@Builder
public class CarpoolingDTO {

    Long id;

    String authorEmail;

    String authorOffice;

    @Embedded
    CarpoolingJourney where;

    @Embedded
    CarpoolingDays when;

    @Embedded
    CarpoolingParameters how;

    String comments;

    String lastModified;

    public static CarpoolingDTO of(Carpooling carpooling) {
        return CarpoolingDTO.builder()
                            .id(carpooling.id())
                            .authorEmail(carpooling.author()
                                                   .email())
                            .authorOffice(carpooling.author()
                                                    .office()
                                                    .name())
                            .where(carpooling.where())
                            .when(carpooling.when())
                            .how(carpooling.how())
                            .comments(carpooling.comments())
                            .lastModified(carpooling.lastModified() != null ? carpooling.lastModified()
                                                                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : null)
                            .build();
    }
}
