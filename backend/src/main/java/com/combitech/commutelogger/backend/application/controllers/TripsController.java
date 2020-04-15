package com.combitech.commutelogger.backend.application.controllers;

import com.combitech.commutelogger.backend.application.security.ApplicationRoles;
import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Trip;
import com.combitech.commutelogger.backend.domain.events.TripAddedEvent;
import com.combitech.commutelogger.backend.domain.events.TripChangedEvent;
import com.combitech.commutelogger.backend.domain.events.TripRemovedEvent;
import com.combitech.commutelogger.backend.domain.exceptions.TransportNotFoundException;
import com.combitech.commutelogger.backend.domain.exceptions.TripAccessForbiddenException;
import com.combitech.commutelogger.backend.domain.exceptions.TripNotFoundException;
import com.combitech.commutelogger.backend.domain.requests.EditTripRequest;
import com.combitech.commutelogger.backend.domain.requests.RemoveTripRequest;
import com.combitech.commutelogger.backend.domain.requests.SubmitTripRequest;
import com.combitech.commutelogger.backend.domain.responses.TripDTO;
import com.combitech.commutelogger.backend.infrastructure.Accounts;
import com.combitech.commutelogger.backend.infrastructure.Transports;
import com.combitech.commutelogger.backend.infrastructure.Trips;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.by;

@Slf4j
@RestController
@RequestMapping("/api/trips")
@Secured(ApplicationRoles.USER)
public class TripsController {

    private final Accounts accounts;

    private final Trips trips;

    private final Transports transports;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public TripsController(Trips trips, Accounts accounts, Transports transports, ApplicationEventPublisher applicationEventPublisher) {
        this.trips = trips;
        this.accounts = accounts;
        this.transports = transports;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @CacheEvict(value = "tripsList", keyGenerator = "OrganizationKeys")
    @Transactional
    @PostMapping
    public TripDTO add(PreAuthenticatedAuthenticationToken token, @Valid @RequestBody SubmitTripRequest request) {
        Trip trip = trips.save(Trip.builder()
                                   .account(accounts.of(token))
                                   .date(request.date())
                                   .transport(transports.findById(request.transport())
                                                        .orElseThrow(() -> new TransportNotFoundException(request.transport())))
                                   .distance(request.distance())
                                   .travellers(request.travellers())
                                   .build());

        applicationEventPublisher.publishEvent(new TripAddedEvent(this, trip));

        return TripDTO.from(trip);
    }

    @CacheEvict(value = "tripsList", keyGenerator = "OrganizationKeys")
    @Transactional
    @PutMapping
    public TripDTO edit(PreAuthenticatedAuthenticationToken token, @Valid @RequestBody EditTripRequest request) {
        Trip trip = trips.findById(request.id())
                         .orElseThrow(TripNotFoundException::new);
        Account owner = trip.account();

        checkOwnership(token, owner);

        trip = trips.save(Trip.builder()
                              .account(accounts.of(token))
                              .date(request.date())
                              .transport(request.transport())
                              .distance(request.distance())
                              .build());

        applicationEventPublisher.publishEvent(new TripChangedEvent(this, trip));

        return TripDTO.from(trip);
    }

    private void checkOwnership(PreAuthenticatedAuthenticationToken token, Account owner) {
        String ownerEmail = owner.email();
        String principalEmail = Account.emailOf(token);

        if (!ownerEmail.equals(principalEmail)) {
            throw new TripAccessForbiddenException();
        }
    }

    @Cacheable(value = "tripsList", keyGenerator = "OrganizationKeys")
    @Transactional(readOnly = true)
    @GetMapping
    public List<TripDTO> list(HttpServletResponse response, PreAuthenticatedAuthenticationToken token) {
        response.addHeader("X-Total-Count", String.valueOf(trips.countBy(token)));

        return trips.findBy(token, by(Direction.DESC, "date"))
                    .stream()
                    .map(TripDTO::from)
                    .collect(Collectors.toList());
    }

    @CacheEvict(value = "tripsList", keyGenerator = "OrganizationKeys")
    @Transactional
    @DeleteMapping
    public void remove(PreAuthenticatedAuthenticationToken token, @Valid @RequestBody RemoveTripRequest request) {
        Trip trip = trips.findById(request.id())
                         .orElseThrow(TripNotFoundException::new);
        Account owner = trip.account();

        checkOwnership(token, owner);

        trips.deleteById(request.id());

        applicationEventPublisher.publishEvent(new TripRemovedEvent(this, trip));
    }

}
