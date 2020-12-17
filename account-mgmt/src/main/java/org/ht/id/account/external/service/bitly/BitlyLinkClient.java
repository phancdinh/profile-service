package org.ht.id.account.external.service.bitly;

import org.ht.id.account.config.ShortenLinkClientConfiguration;
import org.ht.id.account.dto.BitlyShortenLinkCreationRequest;
import org.ht.id.account.dto.BitlyShortenLinkCreationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(contextId = "bitlyContextId", name = "bitly", url = "${account-mgmt.bitly.server}", configuration = ShortenLinkClientConfiguration.class)
interface BitlyLinkClient {

    @RequestMapping(method = RequestMethod.POST, path ="${account-mgmt.bitly.shorten-link}")
    BitlyShortenLinkCreationResponse create(@RequestBody BitlyShortenLinkCreationRequest body);
}
