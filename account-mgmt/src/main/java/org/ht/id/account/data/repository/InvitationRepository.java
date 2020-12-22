package org.ht.id.account.data.repository;

import org.ht.id.account.data.model.Invitation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends MongoRepository<Invitation, String> {

    List<Invitation> findByHtId(String htId);

    Optional<Invitation> findById(String id);

}
