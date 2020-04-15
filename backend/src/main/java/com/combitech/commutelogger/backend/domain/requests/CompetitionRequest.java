package com.combitech.commutelogger.backend.domain.requests;

import lombok.Value;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Accessors(fluent = true)
@Value
public class CompetitionRequest {

    @Size(min = 2, message = "must be at least two participants")
    List<String> participants;

    @NotNull(message = "title must be non-null")
    String title;

    @NotNull(message = "begins must be non-null")
    LocalDate begins;

    @NotNull(message = "ends must be non-null")
    LocalDate ends;

    String description;
}
