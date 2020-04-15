package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Trip;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.List;

public interface Trips extends JpaRepository<Trip, Long> {

    default int countBy(PreAuthenticatedAuthenticationToken token) {
        return countByAccountEmail(Account.emailOf(token));
    }

    int countByAccountEmail(String email);

    default List<Trip> findBy(PreAuthenticatedAuthenticationToken token, Sort sort) {
        return findByAccountEmail(Account.emailOf(token), sort);
    }

    List<Trip> findByAccountEmail(String email, Sort sort);
}
