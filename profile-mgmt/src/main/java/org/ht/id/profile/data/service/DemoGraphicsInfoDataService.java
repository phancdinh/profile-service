package org.ht.id.profile.data.service;

import org.bson.types.ObjectId;
import org.ht.id.common.constant.DemoGraphicsAttribute;
import org.ht.id.profile.data.model.DemoGraphicsInfo;
import org.ht.id.profile.data.repository.DemoGraphicsInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DemoGraphicsInfoDataService {
    private final DemoGraphicsInfoRepository demoGraphicsInfoRepository;

    public DemoGraphicsInfoDataService(DemoGraphicsInfoRepository demoGraphicsInfoRepository) {
        this.demoGraphicsInfoRepository = demoGraphicsInfoRepository;
    }

    public Optional<DemoGraphicsInfo> findByHtIdAndAttribute(ObjectId htCode,
                                                             DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        return demoGraphicsInfoRepository.findByHtCodeAndAttribute(htCode, demoGraphicsInfoAttribute);
    }

    public boolean existingDemoGraphicsInfo(ObjectId htCode,
                                            DemoGraphicsAttribute demoGraphicsInfoAttribute) {
        return demoGraphicsInfoRepository.existsByHtCodeAndAttribute(htCode, demoGraphicsInfoAttribute);
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
