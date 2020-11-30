package org.ht.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profile.data.repository.LegalInfoRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
public class LegalInfoDataService {
    private final LegalInfoRepository legalInfoRepository;

    public LegalInfoDataService(LegalInfoRepository legalInfoRepository) {
        this.legalInfoRepository = legalInfoRepository;
    }

    public Optional<LegalInfo> findByProfileId(ObjectId profileId) {
        return Optional.ofNullable(profileId)
                .flatMap(legalInfoRepository::findByProfileId);
    }

    public LegalInfo create(LegalInfo legalInfo) {
        return Optional.ofNullable(legalInfo)
                .map(legalInfoRepository::insert)
                .orElse(legalInfo);
    }

    public boolean existsByProfileId(ObjectId profileId) {
        return Optional.ofNullable(profileId)
                .map(legalInfoRepository::existsByProfileId)
                .orElse(false);
    }
}
