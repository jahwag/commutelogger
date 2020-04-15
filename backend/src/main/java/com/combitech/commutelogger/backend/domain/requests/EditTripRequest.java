package com.combitech.commutelogger.backend.domain.requests;

import com.combitech.commutelogger.backend.domain.entities.Transport;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Accessors(fluent = true)
public class EditTripRequest {

    long id;

    @NotNull
    private Transport transport;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * Distance in kilometers.
     */
    @Min(1)
    private int distance;

}
