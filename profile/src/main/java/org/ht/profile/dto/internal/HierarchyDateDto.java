package org.ht.profile.dto.internal;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.dto.BaseDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Getter
@Setter
public class HierarchyDateDto extends BaseDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fullDate;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer quarter;
}
