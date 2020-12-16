package org.ht.id.profile.data.model.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
@Builder
public class HierarchyDate {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fullDate;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer quarter;
}
