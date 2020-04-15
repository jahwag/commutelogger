package com.combitech.commutelogger.backend.domain.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@IdClass(OfficeEntryPK.class)
@Entity
@Getter
@Builder
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"date", "office"})
public class StatisticsCo2 {

    private long co2;

    private long km;

    private int numberOfSources;

    @Id
    private LocalDate date;

    @Id
    @ManyToOne
    private Office office;

}
