package org.ht.id.account.data.service;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ht.id.account.data.model.Invitation;
import org.ht.id.account.data.repository.InvitationRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationDataService {
    private final InvitationRepository invitationRepository;

    public Invitation create(Invitation invitation) {
        return Optional.ofNullable(invitation)
                .map(invitationRepository::insert).orElseThrow();
    }

    public Invitation update(Invitation invitation) {
        return Optional.ofNullable(invitation).filter(o -> invitationRepository.existsById(invitation.getId()))
                .map(invitationRepository::save).orElseThrow();
    }

    public List<Invitation> findByHtId(String htId) {
        return invitationRepository.findByHtId(htId);
    }

    public Optional<Invitation> findById(ObjectId id) {
        return invitationRepository.findById(id);
    }
}
