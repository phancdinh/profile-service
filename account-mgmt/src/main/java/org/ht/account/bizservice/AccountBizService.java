package org.ht.account.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.ht.account.data.model.Account;
import org.ht.account.data.service.AccountDataService;
import org.ht.account.exception.AccountRegisterFailureException;
import org.springframework.stereotype.Component;

import java.util.Date;
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

        return Optional.of(creationAccount).map(accountDataService::create).orElseThrow();
    }

    public boolean checkEmailValidForRegister(String email) {
        Optional<Account> account = accountDataService.findByEmailAndActive(email, false);
        if (account.isEmpty()) {
            String errorMessage = String.format("Account %s was not found ", email);
            log.info(errorMessage);
            return true;
        }
        // TODO Need be refactor
        Date currentDate = new Date();
        return account.filter(a -> currentDate.compareTo(a.getActivation().getExpiredAt()) > 0).isPresent();
    }
}
