package org.ht.id.account.data.service;

import org.ht.id.account.data.model.Account;
import org.ht.id.account.data.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountDataService {
    private final AccountRepository accountRepository;

    public AccountDataService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account account) {
        return Optional.ofNullable(account)
                .map(accountRepository::insert)
                .orElseThrow();
    }

    public Account update(Account account) {
        return Optional.ofNullable(account)
                .map(accountRepository::save)
                .orElseThrow();
    }

    public Optional<Account> findByHtId(String htId) {
        return Optional.ofNullable(htId).flatMap(accountRepository::findByHtId);
    }

    public boolean existsByHtId(String htId) {
        return Optional.ofNullable(htId).map(accountRepository::existsByHtId).orElse(false);
    }
}
