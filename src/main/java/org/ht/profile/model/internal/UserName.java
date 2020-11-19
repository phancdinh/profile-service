package org.ht.profile.model.internal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserName {
    private String nickName;
    private String displayName;
    private String fullName;
    private String firstName;
    private String lastName;
}
