package org.ht.id.account.external.service.bitly;

import org.ht.id.account.dto.BitlyShortenLinkCreationRequest;
import org.ht.id.account.dto.BitlyShortenLinkCreationResponse;
import org.ht.id.common.exception.ServiceUnavailableException;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BitlyLinkService {
    private final BitlyLinkClient bitlyLinkClient;

    public BitlyLinkService(BitlyLinkClient bitlyLinkClient) {
        this.bitlyLinkClient = bitlyLinkClient;
    }

    public BitlyShortenLinkCreationResponse create(String domain, String group, String originalUrl) throws ServiceUnavailableException {
        BitlyShortenLinkCreationRequest shortLink = new BitlyShortenLinkCreationRequest();
        shortLink.setDomain(domain);
        shortLink.setGroup_guid(group);
        shortLink.setLong_url(originalUrl);
        BitlyShortenLinkCreationResponse result = null;
        try {
            result = bitlyLinkClient.create(shortLink);
        } catch (Exception he) {
            log.error(he.toString());
            throw new ServiceUnavailableException(he.toString());
        }
        return result;
    }
}
