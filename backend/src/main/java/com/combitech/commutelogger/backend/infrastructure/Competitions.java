package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.Competition;
import com.combitech.commutelogger.backend.domain.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Competitions extends JpaRepository<Competition, Long> {

    List<Competition> findAllByParticipantsContains(Office office);

}
