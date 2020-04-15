package com.combitech.commutelogger.backend.application.services;

import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.events.TripEvent;
import com.combitech.commutelogger.backend.domain.events.UserChangedOfficeEvent;
import com.combitech.commutelogger.backend.infrastructure.StatisticsCo2s;
import com.combitech.commutelogger.backend.infrastructure.StatisticsDistances;
import com.combitech.commutelogger.backend.infrastructure.StatisticsRidesharings;
import com.combitech.commutelogger.backend.infrastructure.StatisticsTransports;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link StatisticsService} is responsible for collecting and processing instances of
 * {@link com.combitech.commutelogger.backend.domain.entities.Trip} into global statistics.
 *
 */
@Slf4j
@Component
public class StatisticsService implements ApplicationListener<TripEvent> {

    private final StatisticsCo2s statisticsCo2s;

    private final StatisticsDistances statisticsDistances;

    private final StatisticsRidesharings statisticsRidesharings;

    private final StatisticsTransports statisticsTransports;

    @Autowired
    public StatisticsService(StatisticsCo2s statisticsCo2s, StatisticsDistances statisticsDistances, StatisticsRidesharings statisticsRidesharings, StatisticsTransports statisticsTransports) {
        this.statisticsCo2s = statisticsCo2s;
        this.statisticsDistances = statisticsDistances;
        this.statisticsRidesharings = statisticsRidesharings;
        this.statisticsTransports = statisticsTransports;
    }

    @Transactional
    @Override
    public void onApplicationEvent(TripEvent event) {
        long start = System.currentTimeMillis();

        if (event instanceof UserChangedOfficeEvent) {
            UserChangedOfficeEvent userChangedOfficeEvent = (UserChangedOfficeEvent) event;
            update(userChangedOfficeEvent.oldOffice());
        }

        Office office = event.office();
        update(office);

        log.info("Finished {} in {} ms", event.office()
                                              .name(), System.currentTimeMillis() - start);
    }

    public void update(Office office) {
        statisticsCo2s.deleteAllByOfficeId(office.id());
        statisticsCo2s.updateEntriesByOfficeId(office.id());

        statisticsDistances.deleteAllByOffice_Id(office.id());
        statisticsDistances.updateEntriesByOfficeId(office.id());

        statisticsRidesharings.deleteAllByOffice_Id(office.id());
        statisticsRidesharings.updateEntriesByOfficeId(office.id());

        statisticsTransports.deleteAllByOffice_Id(office.id());
        statisticsTransports.updateEntriesByOfficeId(office.id());
    }

}
