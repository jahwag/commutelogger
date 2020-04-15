package com.combitech.commutelogger.backend.domain.responses;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ChartDatasetDTO {

    String label;

    String backgroundColor;

    List<Long> data;

}
