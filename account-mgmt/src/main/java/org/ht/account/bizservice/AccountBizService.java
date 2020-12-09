package org.ht.account.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.ht.account.data.model.Account;
import org.ht.account.data.service.AccountDataService;
import org.ht.account.exception.AccountRegisterFailureException;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@Slf4j
public class AccountBizService {
    private final AccountDataService accountDataService;

    public AccountBizService(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    public Account create(String htId, Account creationAccount) throws AccountRegisterFailureException {

        creationAccount.setHtId(htId);
        if (accountDataService.existsByEmail(creationAccount.getEmail())) {
            String errorMessage = String.format("Email %s has been registered ", creationAccount.getEmail());
            log.error(errorMessage);
            throw new AccountRegisterFailureException(errorMessage); 
        }

        return Optional.of(creationAccount)
                .map(accountDataService::create)
                .orElseThrow();
    }
}
