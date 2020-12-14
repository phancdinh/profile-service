package org.ht.account.bizservice;

import java.util.Optional;


import org.ht.account.dto.BitlyShortenLinkCreationResponse;
import org.ht.account.exception.ServiceUnavailableException;
import org.ht.account.external.service.bitly.BitlyLinkService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShortenLinkBizService {
    private final BitlyLinkService bitlyLinkService;

    public ShortenLinkBizService(BitlyLinkService bitlyLinkService) {
        this.bitlyLinkService = bitlyLinkService;
    }

    public Optional<BitlyShortenLinkCreationResponse> createShortLink(String domain, String groupId, String originalUrl) throws ServiceUnavailableException {
        return Optional.of(bitlyLinkService.create(domain, groupId, originalUrl));
    }
}
