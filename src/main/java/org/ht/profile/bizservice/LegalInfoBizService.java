package org.ht.profile.bizservice;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.profile.data.model.LegalInfo;
import org.ht.profile.data.model.Profile;
import org.ht.profile.data.service.LegalInfoDataService;
import org.ht.profile.data.service.ProfileDataService;
import org.ht.profile.dto.request.LegalInfoCreateRequest;
import org.ht.profile.dto.response.BasicInfoResponse;
import org.ht.profile.dto.response.LegalInfoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Component
@Slf4j
public class LegalInfoBizService {
    private final ProfileDataService profileDataService;
    private final LegalInfoDataService legalInfoDataService;
    private final ModelMapper modelMapper;

    public LegalInfoBizService(ProfileDataService profileDataService, LegalInfoDataService legalInfoDataService, ModelMapper modelMapper) {
        this.profileDataService = profileDataService;
        this.legalInfoDataService = legalInfoDataService;
        this.modelMapper = modelMapper;
    }

    public LegalInfoResponse create(String htId, LegalInfoCreateRequest creationRequest) {
        ObjectId profileId = profileDataService.findByHtId(htId)
                .map(Profile::getId)
                .orElseThrow(() -> {
                    String error = String.format("Profile is not existed with htId: %s", htId);
                    log.error(error);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, error);
                });

        if (legalInfoDataService.existsByProfileId(profileId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Legal info is already existed with %s", htId));
        }

        LegalInfo creation = modelMapper.map(creationRequest, LegalInfo.class);
        creation.setProfileId(profileId);
        return Optional.of(creation)
                .map(legalInfoDataService::create)
                .map(legalInfo -> modelMapper.map(legalInfo, LegalInfoResponse.class))
                .orElseThrow();
    }

    public LegalInfoResponse findByHtId(String htId) {

        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile is not existed with htId: %s", htId));
        }

        return profileOptional
                .flatMap(existingProfile -> legalInfoDataService.findByProfileId(existingProfile.getId()))
                .map(legalInfo -> modelMapper.map(legalInfo, LegalInfoResponse.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Legal Info is not existed."));
    }
}
