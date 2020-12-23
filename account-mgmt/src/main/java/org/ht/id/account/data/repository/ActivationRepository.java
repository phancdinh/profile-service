package org.ht.id.account.data.repository;

import org.ht.id.account.data.model.Activation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivationRepository extends MongoRepository<Activation, String> {

    Optional<Activation> findById(String id);

    List<Activation> findByHtId(String htId);

    List<Activation> findByEmail(String email);

}
