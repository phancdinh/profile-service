package org.ht.profile.dto;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;

@Getter
@Setter
public class DemoGraphicsInfoDto extends BaseDto{
    private String value;
    private String attribute;
}
