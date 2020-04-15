package com.combitech.commutelogger.backend.domain.events;

import com.combitech.commutelogger.backend.domain.entities.Trip;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class TripRemovedEvent extends TripEvent {

    public TripRemovedEvent(Object source, Trip trip) {
        super(source, trip);
    }
}
