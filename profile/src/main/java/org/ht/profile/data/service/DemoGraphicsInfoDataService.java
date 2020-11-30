package org.ht.profile.data.service;

import org.bson.types.ObjectId;
import org.ht.profile.constants.DemoGraphicsInfoAttribute;
import org.ht.profile.data.model.DemoGraphicsInfo;
import org.ht.profile.data.repository.DemoGraphicsInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemoGraphicsInfoDataService {
    private final DemoGraphicsInfoRepository demoGraphicsInfoRepository;

    public DemoGraphicsInfoDataService(DemoGraphicsInfoRepository demoGraphicsInfoRepository) {
        this.demoGraphicsInfoRepository = demoGraphicsInfoRepository;
    }

    public Optional<DemoGraphicsInfo> findByHtIdAndAttribute(ObjectId profileId,
                                                             DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {
        return demoGraphicsInfoRepository.findByProfileIdAndAttribute(profileId, demoGraphicsInfoAttribute);
    }

    public boolean existingDemoGraphicsInfo(ObjectId profileId,
                                            DemoGraphicsInfoAttribute demoGraphicsInfoAttribute) {
        return demoGraphicsInfoRepository.existsByProfileIdAndAttribute(profileId, demoGraphicsInfoAttribute);
    }

    public DemoGraphicsInfo insert(DemoGraphicsInfo demoGraphicsInfo) {
        return Optional.ofNullable(demoGraphicsInfo)
                .map(demoGraphicsInfoRepository::insert)
                .orElse(demoGraphicsInfo);
    }

    public DemoGraphicsInfo save(DemoGraphicsInfo demoGraphicsInfo) {
        return Optional.ofNullable(demoGraphicsInfo)
                .map(demoGraphicsInfoRepository::save)
                .orElse(demoGraphicsInfo);
    }

    public void delete(DemoGraphicsInfo demoGraphicsInfo) {
        Optional.ofNullable(demoGraphicsInfo)
                .ifPresent(demoGraphicsInfoRepository::delete);
    }
}
