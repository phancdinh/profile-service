package org.ht.id.profile.data.model.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserName {
    private String nickName;
    private String displayName;
    private String fullName;
    private String firstName;
    private String lastName;
}
