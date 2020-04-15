package com.combitech.commutelogger.backend.domain.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@Accessors(fluent = true)
public class SubmitTripRequest {

    @NotNull
    private String transport;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * Distance in kilometers.
     */
    @Min(1)
    @Max(10000)
    private int distance;

    @Min(1)
    @Max(6)
    private int travellers;

}
