package org.ht.id.account.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.ht.id.account.data.model.Account;
import org.ht.id.account.data.service.AccountDataService;
import org.ht.id.account.exception.AccountRegisterFailureException;
import org.ht.id.account.exception.DataNotExistingException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AccountBizService {
    private final AccountDataService accountDataService;

    public AccountBizService(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    public Account createOrUpdate(String htId, Account creationAccount) throws AccountRegisterFailureException {

        creationAccount.setHtId(htId);

        try {
            // Fetch the account if existing
            return findAccount(htId);
        } catch (DataNotExistingException exc) {
            return Optional.of(creationAccount).map(accountDataService::update).orElseThrow();
        }
    }

    public Account findAccount(String htId) {
        return accountDataService.findByHtId(htId).orElseThrow(() -> {
            String error = String.format("Account is not existed with htId: %s", htId);
            log.error(error);
            throw new DataNotExistingException(error);
        });
    }
}
