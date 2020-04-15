package com.combitech.commutelogger.backend.domain.events;

import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Trip;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.context.ApplicationEvent;

@Getter
@Accessors(fluent = true)
public abstract class TripEvent extends ApplicationEvent {

    Office office;

    public TripEvent(Object source, Trip trip) {
        this(source, trip.account()
                         .office());
    }

    public TripEvent(Object source, Office office) {
        super(source);
        this.office = office;
    }
}
