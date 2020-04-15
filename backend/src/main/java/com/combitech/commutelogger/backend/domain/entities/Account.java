package com.combitech.commutelogger.backend.domain.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.microsoft.azure.spring.autoconfigure.aad.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "email")
public class Account implements Serializable {

    @Id
    @NotNull(message = "email must be non-null")
    private String email;

    @NotNull(message = "enabled must be non-null")
    private boolean enabled;

    @NotNull(message = "office must be non-null")
    @ManyToOne
    private Office office;

    @NotNull(message = "created must be non-null")
    private Timestamp created;

    private boolean admin;

    public static String emailOf(PreAuthenticatedAuthenticationToken token) {
        UserPrincipal userPrincipal = (UserPrincipal) token.getPrincipal();
        Object preferred_username = userPrincipal.getClaim("preferred_username");
        return preferred_username.toString();
    }

}
