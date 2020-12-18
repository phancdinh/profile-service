package org.ht.id.account.data.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ht.id.account.data.model.Activation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivationRepository extends MongoRepository<Activation, ObjectId> {

    Optional<Activation> findById(ObjectId id);

    List<Activation> findByHtId(String htId);

    List<Activation> findByEmail(String email);

}
