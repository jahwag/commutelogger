package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.entities.Account;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class AccountDTO {

    String email;

    String organization;

    String office;

    boolean admin;

    boolean enabled;

    LocalDate created;

    public static AccountDTO from(Account account) {
        return AccountDTO.builder()
                         .email(account.email())
                         .organization(account.office()
                                              .organization()
                                              .name())
                         .office(account.office()
                                        .name())
                         .admin(account.admin())
                         .enabled(account.enabled())
                         .created(account.created()
                                         .toLocalDateTime()
                                         .toLocalDate())
                         .build();
    }
}
