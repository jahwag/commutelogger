package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.OfficeEntryPK;
import com.combitech.commutelogger.backend.domain.entities.StatisticsDistance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsDistances extends JpaRepository<StatisticsDistance, OfficeEntryPK> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM statistics_distance")
    void updateEntriesByOfficeId(long officeId);

    void deleteAllByOffice_Id(long officeId);
}
