package org.ht.id.account.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.config.AccountMgmtProperties;
import org.ht.id.account.config.BitlyApiProperties;
import org.ht.id.account.data.service.AccountDataService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ManageLinkBizService {

    private final AccountMgmtProperties accountApiProperties;
    private final BitlyApiProperties bitlyApiProperties;
    private final AccountDataService actDataService;
    private final ShortenLinkBizService shortenLinkBizService;

    public ManageLinkBizService(AccountDataService actDataService, AccountMgmtProperties accountApiProperties,
                                ShortenLinkBizService shortenLinkBizService, BitlyApiProperties bitlyApiProperties) {
        this.actDataService = actDataService;
        this.accountApiProperties = accountApiProperties;
        this.shortenLinkBizService = shortenLinkBizService;
        this.bitlyApiProperties = bitlyApiProperties;
    }


}
