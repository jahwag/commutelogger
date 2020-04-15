package com.combitech.commutelogger.backend.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@IdClass(OfficeTransportEntryPK.class)
@Entity
@Getter
@Builder
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"date", "office", "transport"})
public class StatisticsTransport {

    @Min(0)
    @Max(100)
    int percentage;

    @Id
    private LocalDate date;

    @Id
    @ManyToOne
    private Transport transport;

    @Id
    @ManyToOne
    private Office office;

}
