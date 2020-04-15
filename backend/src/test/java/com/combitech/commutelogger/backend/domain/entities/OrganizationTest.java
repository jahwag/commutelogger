package com.combitech.commutelogger.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class OrganizationTest {

    private final ObjectMapper mapper;

    public OrganizationTest() {
        mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig()
                                   .getDefaultVisibilityChecker()
                                   .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                   .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withCreatorVisibility(JsonAutoDetect.Visibility.ANY));
    }

    @Test
    void generatePreset() throws IOException {
        List<Organization> organizations = new LinkedList<>();
        organizations.addAll(combitechSe());
        organizations.addAll(combitechDk());
        organizations.addAll(combitechFi());

        mapper.writerWithDefaultPrettyPrinter()
              .writeValue(new File("src/main/generated/organizations.json"), organizations);
    }

    private List<Organization> combitechSe() {
        Office headQuarters = Office.builder()
                                    .name("Växjö")
                                    .build();

        List<Office> offices = new LinkedList<>();
        offices.add(headQuarters);

        for (String name : Stream.of("Arboga", "Borlänge", "Enköping", "Göteborg",
                "Helsingborg", "Hässleholm", "Jönköping", "Karlskoga",
                "Karlstad", "Kristianstad", "Linköping", "Luleå", "Lund", "Malmö",
                "Norrköping", "Skövde", "Sollefteå", "Stockholm", "Sundsvall",
                "Södertälje", "Trollhättan", "Umeå", "Uppsala", "Västerås",
                "Örnsköldsvik", "Östersund")
                                 .distinct()
                                 .collect(Collectors.toList())) {
            offices.add(Office.builder()
                              .name(name)
                              .build());
        }

        List<Organization> organizations = new LinkedList<>();
        organizations.add(Organization.builder()
                                      .name("Combitech AB")
                                      .domain("combitech.se")
                                      .offices(offices)
                                      .build());
        return organizations;
    }

    private List<Organization> combitechDk() {
        Office headQuarters = Office.builder()
                                    .name("Ballerup")
                                    .build();

        List<Organization> organizations = new LinkedList<>();
        organizations.add(Organization.builder()
                                      .name("Combitech A/S")
                                      .domain("combitech.dk")
                                      .offices(Collections.singletonList(headQuarters))
                                      .build());
        return organizations;
    }

    private List<Organization> combitechFi() {
        Office headQuarters = Office.builder()
                                    .name("Tampere")
                                    .build();

        List<Organization> organizations = new LinkedList<>();
        organizations.add(Organization.builder()
                                      .name("Combitech Oy")
                                      .domain("combitech.fi")
                                      .offices(Collections.singletonList(headQuarters))
                                      .build());
        return organizations;
    }
}