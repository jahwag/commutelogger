package com.combitech.commutelogger.backend.domain.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAppender extends AppenderBase<ILoggingEvent> {

    private final List<ILoggingEvent> events = new ArrayList<>();

    private int limit = 150;

    @Override
    protected void append(ILoggingEvent e) {
        synchronized (events) {
            events.add(e);
            if (events.size() > limit && limit > 0) {
                events.remove(0);
            }
        }
    }

    @Override
    public void start() {
        super.start();
        InMemoryAppenderInstance.setInstance(this);
    }

    @Override
    public void stop() {
        InMemoryAppenderInstance.setInstance(null);
        super.stop();
        events.clear();
    }

    public List<ILoggingEvent> getEvents() {
        synchronized (events) {
            return new ArrayList<>(events);
        }
    }

}