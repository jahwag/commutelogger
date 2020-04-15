package com.combitech.commutelogger.backend.domain.events;

import com.combitech.commutelogger.backend.domain.entities.Office;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class UserChangedOfficeEvent extends TripEvent {

    private final Office oldOffice;

    public UserChangedOfficeEvent(Object source, Office oldOffice, Office newOffice) {
        super(source, newOffice);
        this.oldOffice = oldOffice;
    }

}
