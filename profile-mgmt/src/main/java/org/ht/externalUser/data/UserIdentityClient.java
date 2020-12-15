package org.ht.externalUser.data;

import org.ht.externalUser.config.UserIdentityClientConfiguration;
import org.ht.externalUser.dto.UserAccountUnlockRequest;
import org.ht.externalUser.dto.UserCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "is-client", url = "${profile-mgmt.identity.server}", configuration = UserIdentityClientConfiguration.class)
public interface UserIdentityClient {
    @RequestMapping(method = RequestMethod.POST, value = "${profile-mgmt.identity.scim2.users}")
    UserData createUser(@RequestBody UserCreationRequest body);

    @RequestMapping(method = RequestMethod.PATCH, value = "${profile-mgmt.identity.scim2.users}" + "/{id}")
    UserData updateAccountLocked(@PathVariable("id") String id, @RequestBody UserAccountUnlockRequest body);
}
