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
    
    public Account create(Account acct) {
    	log.info("Create " + acct);
        return accountRepository.insert(acct);
    }
    public Account update(Account acct) {
    	log.info("Update " + acct);
        return accountRepository.save(acct);
    }

	public void createOrUpdate(Account acct) {
    	
		if((findByHtId(acct.getHtId()).isEmpty())) {
			accountRepository.save(acct);
			log.info("Update " + acct);
		}else {
			accountRepository.insert(acct);
			log.info("Create " + acct);
		}
		return ;
	}

}
