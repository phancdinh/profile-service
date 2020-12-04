package org.ht.account.data.service;

import org.ht.account.config.UserIdentityClientConfiguration;
import org.ht.account.dto.UserCreationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "is-client", url = "${account-mgmt.identity.server}", configuration = UserIdentityClientConfiguration.class)
interface UserIdentityClient {

    @RequestMapping(method = RequestMethod.POST, value = "${account-mgmt.identity.scim2.users}")
    String createUser(@RequestBody UserCreationRequest body);
}
