package org.ht.profile.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.helper.ProfileConverterHelper;
import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.model.BasicInfo;
import org.ht.profile.repository.BasicInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class BasicInfoService {

    private final BasicInfoRepository basicInfoRepository;

    public BasicInfoService(BasicInfoRepository basicInfoRepository) {
        this.basicInfoRepository = basicInfoRepository;
    }

    public BasicInfo findByProfileId(ObjectId profileId) {
        BasicInfo basicInfo = basicInfoRepository.findByProfileId(profileId);
        if (basicInfo == null) {
            log.error("Hung thinh Id is not found with {}", profileId.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile is not existed.");
        }
        return basicInfo;
    }

    public BasicInfo create(ObjectId profileId, BasicInfoCreateRequest profileRequest) {
        if (basicInfoRepository.existsByProfileId(profileId)) {
            log.error("profileId {} is already existed.", profileId.toString());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Profile is already existed.");
        }
        BasicInfo addedInfo = ProfileConverterHelper.convert(profileRequest);
        addedInfo.setProfileId(profileId);
        addedInfo.setPob(ProfileConverterHelper.convert(profileRequest.getPob()));
        addedInfo.setPermanentAddress(ProfileConverterHelper.convert(profileRequest.getPermanentAddress()));
        addedInfo.setHometown(ProfileConverterHelper.convert(profileRequest.getHometown()));
        addedInfo.setDob(ProfileConverterHelper.convert(profileRequest.getDob()));
        addedInfo.setUserName(ProfileConverterHelper.convertUserName(profileRequest.getFullName()));
        return basicInfoRepository.insert(addedInfo);
    }
}

