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
import java.time.LocalDate;

@IdClass(OfficeEntryPK.class)
@Entity
@Getter
@Builder
@Accessors(fluent = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"date", "office"})
public class StatisticsDistance {

    int km;

    @Id
    private LocalDate date;

    @Id
    @ManyToOne
    private Office office;


}
