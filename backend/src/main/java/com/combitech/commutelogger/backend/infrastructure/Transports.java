package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Transports extends JpaRepository<Transport, String> {

}
