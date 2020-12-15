package org.ht.externalUser.service;


import org.ht.externalUser.data.UserData;

public interface ExternalUserService {
    UserData create(String username, String email, String phone, String password);

    void updateAccountLock(String id, boolean accountLock);
}
