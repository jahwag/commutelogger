package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

public interface Organizations extends JpaRepository<Organization, Long> {

    default Optional<Organization> findByToken(PreAuthenticatedAuthenticationToken token) {
        String email = Account.emailOf(token);
        String domain = email.substring(email.indexOf("@") + 1);

        return findByDomainWithOffices(domain);
    }

    @Transactional
    default Optional<Organization> findByDomainWithOffices(String domain) {
        Optional<Organization> optionalOrganization = findByDomains(domain);

        if (optionalOrganization.isPresent()) {
            Organization organization = optionalOrganization.get();

            // fetch offices
            Iterator<Office> it = organization.offices()
                                              .iterator();
            if (it.hasNext()) {
                it.next();
            }

            return Optional.of(organization);
        }

        return optionalOrganization;
    }

    Optional<Organization> findByDomains(String domain);
}
