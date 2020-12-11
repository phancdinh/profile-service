package org.ht.account.data.service;

import static java.util.function.Predicate.not;

import java.util.Optional;

import org.ht.account.data.model.Account;
import org.ht.account.data.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountDataService {
    private final AccountRepository accountRepository;

    public AccountDataService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account account) {
        return Optional.ofNullable(account)
                .filter(not(o -> accountRepository.existsByEmail(o.getEmail())))
                .map(accountRepository::insert)
                .orElse(account);
    }

    public Account update(Account account) {
        return Optional.ofNullable(account)
        .map(accountRepository::save)
        .orElseThrow();
    }

    public Optional<Account> findByHtId(String htId) {
        return Optional.ofNullable(htId).flatMap(accountRepository::findByHtId);
    }

    public boolean existsByEmail(String email) {
        return Optional.ofNullable(email).map(accountRepository::existsByEmail).orElse(false);
    }

    public Optional<Account> findByEmailAndActive(String email, boolean active) {
        return accountRepository.findByEmailAndActive(email, active);
    }
}
