package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.Account;
import com.combitech.commutelogger.backend.domain.exceptions.NoSuchAccountException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public interface Accounts extends JpaRepository<Account, String> {

    default Account of(PreAuthenticatedAuthenticationToken token) {
        return findById(Account.emailOf(token)).orElseThrow(NoSuchAccountException::new);
    }

}
