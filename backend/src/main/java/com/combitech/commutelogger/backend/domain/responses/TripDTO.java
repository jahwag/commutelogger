package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.entities.Trip;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
@Builder
public class TripDTO {

    long id;

    String transport;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    int distance;

    int travellers;

    int co2pp;

    float climateIndex;

    boolean shareable;

    public static TripDTO from(Trip trip) {
        int totalCo2 = totalCo2(trip);
        int totalCo2PerTraveller = co2PerTraveller(trip, totalCo2);

        return TripDTO.builder()
                      .id(trip.id())
                      .transport(trip.transport()
                                     .name())
                      .date(trip.date())
                      .distance(trip.distance())
                      .travellers(Math.max(1, trip.travellers()))
                      .shareable(trip.transport()
                                     .shareable())
                      .co2pp(totalCo2PerTraveller)
                      .climateIndex(climateIndex(totalCo2PerTraveller))
                      .build();
    }

    private static int totalCo2(Trip trip) {
        return trip.transport()
                   .gramCo2PerKm() * trip.distance();
    }

    private static int co2PerTraveller(Trip trip, int totalCo2) {
        return totalCo2 / Math.max(1, trip.travellers());
    }

    private static float climateIndex(int totalCo2PerTraveller) {
        // Assume 27 km avg commute
        int avgCommute = 27;

        // By bus
        int co2pkm = 15;

        // Let this be Index 5 (405)
        int referenceGramCo2 = avgCommute * co2pkm;

        return 5 * ((float) referenceGramCo2 / (float) Math.max(1, totalCo2PerTraveller));
    }

}
