package com.combitech.commutelogger.backend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

@Slf4j
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Stockholm"));
        Locale.setDefault(Locale.forLanguageTag("se"));
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster asyncEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(asyncExecutor());

        return eventMulticaster;
    }

    @Bean
    public Executor asyncExecutor() {
        return ForkJoinPool.commonPool();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setVisibility(mapper.getSerializationConfig()
                                   .getDefaultVisibilityChecker()
                                   .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                   .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
        return mapper;
    }

}
