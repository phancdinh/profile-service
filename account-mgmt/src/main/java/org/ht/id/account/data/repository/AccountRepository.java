package org.ht.id.account.data.repository;

import org.ht.id.account.data.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByHtId(String htId);

    boolean existsByHtId(String htId);
}
