package org.ht.id.profileapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import static org.ht.id.common.constant.DateTime.DATETIME_PATTERN;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DemoGraphicsInfoResponse {
    private String htId;
    private String value;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_PATTERN)
    private Date lastModifiedDate;
}
