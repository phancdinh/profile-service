package org.ht.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.model.ContactInfo;
import org.ht.profile.data.repository.ContactInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
@Slf4j
public class ContactInfoDataService {

    private final ContactInfoRepository contactInfoRepository;

    public ContactInfoDataService(ContactInfoRepository contactInfoRepository) {
        this.contactInfoRepository = contactInfoRepository;
    }

    public Optional<ContactInfo> findByProfileId(ObjectId profileId) {
        return Optional.ofNullable(profileId)
                .flatMap(contactInfoRepository::findByProfileId);
    }

    public boolean existsByProfileId(ObjectId profileId) {
        return Optional.ofNullable(profileId)
                .map(contactInfoRepository::existsByProfileId)
                .orElse(false);
    }

    public ContactInfo create(ContactInfo contactInfo) {
        return Optional.ofNullable(contactInfo)
                .filter(not(o -> contactInfoRepository.existsByProfileId(o.getProfileId())))
                .map(contactInfoRepository::insert)
                .orElse(contactInfo);
    }
}

