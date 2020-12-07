package org.ht.account.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.ht.account.data.service.UserIdentityService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserIdentityBizService {
    private final UserIdentityService userIdentityService;

    public UserIdentityBizService(UserIdentityService userIdentityService) {
        this.userIdentityService = userIdentityService;
    }

    public void createUser(String username, String password, String email) {
        userIdentityService.create(username, password, email);
    }
}
