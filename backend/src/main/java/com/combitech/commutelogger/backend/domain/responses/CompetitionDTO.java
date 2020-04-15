package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.entities.Competition;
import com.combitech.commutelogger.backend.domain.entities.Office;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Value
@Builder
public class CompetitionDTO {

    long id;

    String title;

    List<String> participants;

    LocalDate begins;

    LocalDate ends;

    String description;

    public static CompetitionDTO of(Competition competition) {
        return CompetitionDTO.builder()
                             .id(competition.id())
                             .title(competition.title())
                             .participants(competition.participants()
                                                      .stream()
                                                      .map(Office::name)
                                                      .collect(Collectors.toList()))
                             .begins(competition.begins())
                             .ends(competition.ends())
                             .description(competition.description())
                             .build();
    }

}
