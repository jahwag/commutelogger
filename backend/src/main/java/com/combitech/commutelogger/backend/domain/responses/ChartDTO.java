package com.combitech.commutelogger.backend.domain.responses;

import com.combitech.commutelogger.backend.domain.Pastels;
import com.combitech.commutelogger.backend.domain.entities.Office;
import com.combitech.commutelogger.backend.domain.entities.StatisticsCo2;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@Builder
public class ChartDTO {

    List<String> labels;

    List<ChartDatasetDTO> datasets;

    public static ChartDTO from(List<StatisticsCo2> statisticsCo2) {
        Pastels pastels = new Pastels();

        Map<Office, List<StatisticsCo2>> collect = statisticsCo2.stream()
                                                                .collect(Collectors.groupingBy(StatisticsCo2::office));
        List<ChartDatasetDTO> datasets = new LinkedList<>();
        for (Map.Entry<Office, List<StatisticsCo2>> entry : collect.entrySet()) {
            datasets.add(ChartDatasetDTO.builder()
                                        .label(entry.getKey()
                                                    .name())
                                        .backgroundColor(pastels.next())
                                        .data(entry.getValue()
                                                   .stream()
                                                   .map(StatisticsCo2::co2)
                                                   .map(i -> i / 1000)//g->kg
                                                   .collect(Collectors.toList()))
                                        .build());
        }

        return ChartDTO.builder()
                       .labels(statisticsCo2.stream()
                                            .map(StatisticsCo2::date)
                                            .distinct()
                                            .map(LocalDate::toString)
                                            .collect(Collectors.toList()))
                       .datasets(datasets)
                       .build();
    }


}
