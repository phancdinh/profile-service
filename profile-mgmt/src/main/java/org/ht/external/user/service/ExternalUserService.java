package org.ht.external.user.service;


import org.ht.external.user.data.UserData;

public interface ExternalUserService {
    UserData create(String username, String email, String phone, String password);

    void updateAccountLock(String id, boolean accountLock);
}
