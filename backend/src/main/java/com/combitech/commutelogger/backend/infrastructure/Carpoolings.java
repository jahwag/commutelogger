package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.Carpooling;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Carpoolings extends JpaRepository<Carpooling, Long> {

    List<Carpooling> findByAuthorOfficeOrganizationId(long organizationId, Sort sort);

    Optional<Carpooling> findByIdAndAuthorEmail(long id, String authorEmail);

}
