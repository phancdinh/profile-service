package org.ht.id.profile.data.model.internal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserName {

    public UserName(String fullName) {
        this.fullName = fullName;
    }

    private String nickName;
    private String displayName;
    private String fullName;
    private String firstName;
    private String lastName;
}
