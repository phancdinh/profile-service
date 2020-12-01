package org.ht.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.model.BasicInfo;
import org.ht.profile.data.repository.BasicInfoRepository;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
@Slf4j
public class BasicInfoDataService {

    private final BasicInfoRepository basicInfoRepository;

    public BasicInfoDataService(BasicInfoRepository basicInfoRepository) {
        this.basicInfoRepository = basicInfoRepository;
    }

    public Optional<BasicInfo> findByProfileId(ObjectId profileId) {
        return Optional.ofNullable(profileId)
                .flatMap(basicInfoRepository::findByProfileId);
    }

    public boolean existsByProfileId(ObjectId profileId) {
        return Optional.ofNullable(profileId)
                .map(basicInfoRepository::existsByProfileId)
                .orElse(false);
    }

    public BasicInfo create(BasicInfo basicInfo) {
        return Optional.ofNullable(basicInfo)
                .filter(not(o -> basicInfoRepository.existsByProfileId(o.getProfileId())))
                .map(basicInfoRepository::insert)
                .orElse(basicInfo);
    }
}

