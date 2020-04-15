package com.combitech.commutelogger.backend.domain.logging;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryAppenderAccess {

    public static List<String> content() {
        InMemoryAppender instance = InMemoryAppenderInstance.getInstance();
        if (instance == null) {
            return Collections.emptyList();
        }

        return instance.getEvents()
                       .stream()
                       .map(e -> Instant.ofEpochMilli(e.getTimeStamp())
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDateTime() + " " + e.toString())
                       .collect(Collectors.toList());
    }
}