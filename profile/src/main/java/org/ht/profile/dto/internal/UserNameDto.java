package org.ht.profile.dto.internal;

import lombok.Getter;
import lombok.Setter;
import org.ht.profile.dto.BaseDto;

@Getter
@Setter
public class UserNameDto extends BaseDto {
    private String nickName;
    private String displayName;
    private String fullName;
    private String firstName;
    private String lastName;
}
