package org.ht.account.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserCreationRequest {
    private List<Object> schemas = new ArrayList<>();
    private List<UserEmail> emails = new ArrayList<>();
    private String userName;
    private String password;
    private String mail;
    private boolean accountLock = true;

    public void addEmail(boolean primary, String type, String value) {
        UserEmail userEmail = new UserEmail(primary, type, value);
        if (CollectionUtils.isEmpty(emails)) {
            emails = new ArrayList<>();
        }
        emails.add(userEmail);
    }
}

