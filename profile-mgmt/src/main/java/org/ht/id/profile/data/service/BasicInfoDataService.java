package org.ht.id.profile.data.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.profile.data.model.BasicInfo;
import org.ht.id.profile.data.repository.BasicInfoRepository;
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

    public Optional<BasicInfo> findByHtCode(ObjectId htCode) {
        return Optional.ofNullable(htCode)
                .flatMap(basicInfoRepository::findByHtCode);
    }

    public boolean existsByHtCode(ObjectId htCode) {
        return Optional.ofNullable(htCode)
                .map(basicInfoRepository::existsByHtCode)
                .orElse(false);
    }

    public BasicInfo create(BasicInfo basicInfo) {
        return Optional.ofNullable(basicInfo)
                .filter(not(o -> basicInfoRepository.existsByHtCode(o.getHtCode())))
                .map(basicInfoRepository::insert)
                .orElse(basicInfo);
    }
}

