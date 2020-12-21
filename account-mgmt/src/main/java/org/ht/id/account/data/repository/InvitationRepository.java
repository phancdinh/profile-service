package org.ht.id.account.data.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ht.id.account.data.model.Invitation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends MongoRepository<Invitation, ObjectId> {

    List<Invitation> findByHtId(String htId);

    Optional<Invitation> findById(ObjectId id);

}
