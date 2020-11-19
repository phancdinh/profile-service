package org.ht.profile.model.internal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HierarchyDate {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fullDate;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer quarter;
}
