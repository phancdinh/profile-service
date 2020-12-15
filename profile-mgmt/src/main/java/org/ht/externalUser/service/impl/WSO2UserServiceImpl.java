package org.ht.externalUser.service.impl;

import lombok.RequiredArgsConstructor;
import org.ht.externalUser.data.UserData;
import org.ht.externalUser.data.UserIdentityClient;
import org.ht.externalUser.dto.PatchUpdateData;
import org.ht.externalUser.dto.UserAccountUnlockRequest;
import org.ht.externalUser.dto.UserCreationRequest;
import org.ht.externalUser.service.ExternalUserService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

import static org.ht.externalUser.contants.WSO2Params.ACCOUNT_LOCKED;
import static org.ht.externalUser.contants.WSO2Params.EXTENSIONS;
import static org.ht.externalUser.contants.WSO2Params.OP_REPLACE;
import static org.ht.externalUser.contants.WSO2Params.VERIFY_EMAIL;

@Service
@RequiredArgsConstructor
public class WSO2UserServiceImpl implements ExternalUserService {

    private final UserIdentityClient userIdentityClient;

    @Override
    public UserData create(String username, String email, String phone, String password) {
        var userCreation = new UserCreationRequest();
        userCreation.setUserName(username);
        userCreation.setPassword(password);
        userCreation.setEmails(Collections.singletonList(email));
        userCreation.setExtensions(Map.of(VERIFY_EMAIL, true));
        return userIdentityClient.createUser(userCreation);
    }

    @Override
    public void updateAccountLock(String id, boolean accountLock) {
        UserAccountUnlockRequest unlockRequest = new UserAccountUnlockRequest();
        PatchUpdateData patchUpdateData = new PatchUpdateData();
        patchUpdateData.setOp(OP_REPLACE);
        patchUpdateData.setValue(Map.of(EXTENSIONS, Map.of(ACCOUNT_LOCKED, accountLock)));
        unlockRequest.setOperations(Collections.singletonList(patchUpdateData));
        userIdentityClient.updateAccountLocked(id, unlockRequest);
    }
}
