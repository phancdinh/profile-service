package org.ht.account.data.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.account.data.model.Account;
import org.ht.account.data.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findByHtId(String htId) {
        return Optional.ofNullable(htId)
                .flatMap(accountRepository::findByHtId);
    }
}
