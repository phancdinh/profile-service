package org.ht.id.external.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.ht.id.external.user.data.UserData;
import org.ht.id.external.user.data.UserIdentityClient;
import org.ht.id.external.user.dto.PatchUpdateData;
import org.ht.id.external.user.dto.UserAccountUnlockRequest;
import org.ht.id.external.user.dto.UserCreationRequest;
import org.ht.id.external.user.service.ExternalUserService;
import org.ht.id.external.user.contants.WSO2Params;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

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
        userCreation.setExtensions(Map.of(WSO2Params.VERIFY_EMAIL, true));
        return userIdentityClient.createUser(userCreation);
    }

    @Override
    public void updateAccountLock(String id, boolean accountLock) {
        UserAccountUnlockRequest unlockRequest = new UserAccountUnlockRequest();
        PatchUpdateData patchUpdateData = new PatchUpdateData();
        patchUpdateData.setOp(WSO2Params.OP_REPLACE);
        patchUpdateData.setValue(Map.of(WSO2Params.EXTENSIONS, Map.of(WSO2Params.ACCOUNT_LOCKED, accountLock)));
        unlockRequest.setOperations(Collections.singletonList(patchUpdateData));
        userIdentityClient.updateAccountLocked(id, unlockRequest);
    }
}
