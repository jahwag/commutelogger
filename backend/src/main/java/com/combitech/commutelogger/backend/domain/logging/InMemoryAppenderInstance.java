package com.combitech.commutelogger.backend.domain.logging;

public final class InMemoryAppenderInstance {

    private static InMemoryAppender instance;

    private InMemoryAppenderInstance() {
    }

    public static InMemoryAppender getInstance() {
        return instance;
    }

    static void setInstance(InMemoryAppender instance) {
        InMemoryAppenderInstance.instance = instance;
    }

}