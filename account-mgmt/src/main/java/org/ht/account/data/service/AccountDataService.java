package org.ht.account.data.service;

import lombok.extern.slf4j.Slf4j;
import org.ht.account.data.model.Account;
import org.ht.account.data.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static java.util.function.Predicate.not;

@Service
@Slf4j
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

    public Account update(Account acct) {
        return accountRepository.save(acct);
    }

    public void createOrUpdate(Account acct) {

        if ((findByHtId(acct.getHtId()).isEmpty())) {
            accountRepository.save(acct);
        } else {
            accountRepository.insert(acct);
        }
    }

    public Optional<Account> findByHtId(String htId) {
        return Optional.ofNullable(htId)
                .flatMap(accountRepository::findByHtId);
    }

    public boolean existsByEmail(String email) {
        return Optional.ofNullable(email)
                .map(accountRepository::existsByEmail)
                .orElse(false);
    }
}