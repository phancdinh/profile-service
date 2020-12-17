package org.ht.id.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.LegalInfo;
import org.ht.id.profile.data.repository.LegalInfoRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
public class LegalInfoDataService {
    private final LegalInfoRepository legalInfoRepository;

    public LegalInfoDataService(LegalInfoRepository legalInfoRepository) {
        this.legalInfoRepository = legalInfoRepository;
    }

    public Optional<LegalInfo> findByHtCode(ObjectId htCode) {
        return Optional.ofNullable(htCode)
                .flatMap(legalInfoRepository::findByHtCode);
    }

    public LegalInfo create(LegalInfo legalInfo) {
        return Optional.ofNullable(legalInfo)
                .map(legalInfoRepository::insert)
                .orElse(legalInfo);
    }

    public boolean existsByHtCode(ObjectId htCode) {
        return Optional.ofNullable(htCode)
                .map(legalInfoRepository::existsByHtCode)
                .orElse(false);
    }
}
