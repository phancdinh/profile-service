package org.ht.id.account.data.service;

import lombok.RequiredArgsConstructor;
import org.ht.id.account.data.model.Activation;
import org.ht.id.account.data.repository.ActivationRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivationDataService {
    private final ActivationRepository activationRepository;

    public Activation create(Activation activation) {
        return Optional.ofNullable(activation).map(activationRepository::insert).orElseThrow();
    }

    public Activation update(Activation activation) {
        return Optional.ofNullable(activation).filter(o -> activationRepository.existsById(activation.getId()))
                .map(activationRepository::save).orElseThrow();
    }

    public List<Activation> findByHtId(String htId) {
        return activationRepository.findByHtId(htId);
    }

    public Optional<Activation> findById(String id) {
        return activationRepository.findById(id);
    }

    public List<Activation> findByEmail(String email) {
        return activationRepository.findByEmail(email);
    }

    public boolean findByEmailAndActive(String email) {
        return this.findByEmail(email).stream().anyMatch(p -> p.getExpiredAt().compareTo(new Date()) > 0);
    }
}
