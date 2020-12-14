package org.ht.account.data.repository;

import org.ht.account.data.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByHtId(String htId);

    boolean existsByEmail(String email);

    Optional<Account> findByEmailAndActive(String email, boolean active);

    boolean existsByHtId(String htId);
}
