package com.combitech.commutelogger.backend.application.controllers;

import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Transport;
import com.combitech.commutelogger.backend.domain.exceptions.TransportNotFoundException;
import com.combitech.commutelogger.backend.domain.requests.EditTransportRequest;
import com.combitech.commutelogger.backend.domain.responses.TransportDTO;
import com.combitech.commutelogger.backend.infrastructure.Transports;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/transports")
public class TransportsController {

    private final Transports transports;

    public TransportsController(Transports transports) {
        this.transports = transports;
    }

    @Cacheable(value = "transportsList")
    @Transactional(readOnly = true)
    @GetMapping
    public List<TransportDTO> transports() {
        return transports.findAll()
                         .stream()
                         .map(TransportDTO::from)
                         .collect(Collectors.toList());
    }

    @CacheEvict(value = "transportsList")
    @Secured(ApplicationRoles.ADMIN)
    @Transactional
    @PostMapping
    public TransportDTO edit(EditTransportRequest request) {
        Transport transport = transports.findById(request.name())
                                        .orElseThrow(() -> new TransportNotFoundException(request.name()))
                                        .toBuilder()
                                        .gramCo2PerKm(request.gramCo2PerKm())
                                        .perPersonKm(request.perPersonKm())
                                        .shareable(request.shareable())
                                        .build();
        return TransportDTO.from(transports.save(transport));
    }

    @CacheEvict(value = "transportsList")
    @Secured(ApplicationRoles.ADMIN)
    @Transactional
    @DeleteMapping
    public void delete(TransportDTO transportDTO) {
        transports.deleteById(transportDTO.getName());
    }

    @Cacheable(value = "transports", key = "#name")
    @Transactional(readOnly = true)
    @GetMapping("/:name")
    public TransportDTO findByName(@RequestParam("name") String name) {
        return TransportDTO.from(transports.findById(name)
                                           .orElseThrow(() -> new TransportNotFoundException(name)));
    }

}
