package com.combitech.commutelogger.backend.infrastructure;

import com.combitech.commutelogger.backend.domain.entities.OfficeEntryPK;
import com.combitech.commutelogger.backend.domain.entities.StatisticsCo2;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsCo2s extends JpaRepository<StatisticsCo2, OfficeEntryPK> {

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO statistics_co2 (co2, date, number_of_sources, office_id, km)\n" +
            "    (SELECT sum(tx.gram_co2per_km * t.distance) as co2,\n" +
            "            t.date                              as date,\n" +
            "            count(distinct a)                   as number_of_sources,\n" +
            "            a.office_id                         as office_id,\n" +
            "            sum(t.distance)                     as km\n" +
            "     FROM trip t\n" +
            "              JOIN transport tx ON t.transport_name = tx.name\n" +
            "              JOIN account a on t.account_email = a.email\n" +
            "     WHERE a.office_id = ?\n" +
            "     GROUP BY t.date, a.office_id\n" +
            "     ORDER BY t.date DESC)")
    void updateEntriesByOfficeId(long officeId);

    void deleteAllByOfficeId(long officeId);

    List<StatisticsCo2> findAllByOfficeOrganizationIdInAndDateBetween(List<Long> organizationIds, LocalDate begin, LocalDate end, Sort sort);

    List<StatisticsCo2> findAllByOfficeIdInAndDateBetween(List<Long> officeIds, LocalDate begin, LocalDate end, Sort sort);

}
