package com.combitech.commutelogger.backend.application.bootstrap;

import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Organization;
import com.combitech.commutelogger.backend.domain.entities.Transport;
import com.combitech.commutelogger.backend.infrastructure.Offices;
import com.combitech.commutelogger.backend.infrastructure.Organizations;
import com.combitech.commutelogger.backend.infrastructure.Transports;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Getter
@Component
@Accessors(fluent = true)
public class Datasets implements InitializingBean {

    private final ObjectMapper mapper;

    public Datasets(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional
    @Autowired
    public void saveOrganizations(@Value("classpath:/organizations.json") Resource organizationResource,
                                  Organizations organizations, Offices offices) {
        if (organizations.count() == 0) {
            List<Organization> organizationsList;

            try (InputStream is = organizationResource.getInputStream()) {
                organizationsList = mapper.readValue(is, new TypeReference<List<Organization>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (Organization organization : organizationsList) {
                for (Office office : organization.offices()) {
                    office.organization(organization);
                }
            }
            organizations.saveAll(organizationsList);
        }
    }

    @Transactional
    @Autowired
    public void saveTransports(@Value("classpath:/transports.json") Resource transportsResource,
                               Transports transports) {
        if (transports.count() == 0) {
            try (InputStream is = transportsResource.getInputStream()) {
                List<Transport> transportsList = mapper.readValue(is, new TypeReference<List<Transport>>() {
                });

                transports.saveAll(transportsList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
