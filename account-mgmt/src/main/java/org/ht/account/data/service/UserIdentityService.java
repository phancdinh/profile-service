package org.ht.account.data.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.account.dto.UserCreationRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserIdentityService {
    private final UserIdentityClient userIdentityClient;

    public UserIdentityService(UserIdentityClient userIdentityClient) {
        this.userIdentityClient = userIdentityClient;
    }

    public void create(String username, String password, String email) {
        final String DEFAULT_EMAIL_TYPE = "home";
        var userCreation = new UserCreationRequest();
        userCreation.setUserName(username);
        userCreation.setPassword(password);
        userCreation.addEmail(true, DEFAULT_EMAIL_TYPE, email);
        userCreation.setMail(email);
        userCreation.setAccountLock(true);
        userIdentityClient.createUser(userCreation);
    }
}
