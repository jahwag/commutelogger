package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.OfficeTransportEntryPK;
import com.combitech.commutelogger.backend.domain.entities.StatisticsTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsTransports extends JpaRepository<StatisticsTransport, OfficeTransportEntryPK> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM statistics_transport")
    void updateEntriesByOfficeId(long officeId);

    void deleteAllByOffice_Id(long officeId);

}
