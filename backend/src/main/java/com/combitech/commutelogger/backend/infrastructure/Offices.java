package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Offices extends JpaRepository<Office, Long> {

    List<Office> findAllByNameIn(List<String> names);

    Optional<Office> findByOrganizationIdAndAndName(long organizationId, String name);

}
