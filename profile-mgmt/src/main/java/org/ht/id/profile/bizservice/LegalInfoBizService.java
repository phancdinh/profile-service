package org.ht.id.profile.bizservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.profile.config.MessageApiProperties;
import org.ht.id.profile.data.exception.DataConflictingException;
import org.ht.id.profile.data.exception.DataNotExistingException;
import org.ht.id.profile.data.model.LegalInfo;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profile.data.service.LegalInfoDataService;
import org.ht.id.profile.data.service.ProfileDataService;
import org.ht.id.profile.helper.ProfileConverterHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class LegalInfoBizService {
    private final ProfileDataService profileDataService;
    private final LegalInfoDataService legalInfoDataService;
    private final ProfileConverterHelper profileConverterHelper;
    private final MessageApiProperties messageApiProperties;

    public LegalInfo create(String htId, LegalInfo createdInfo) throws DataNotExistingException, DataConflictingException {
        ObjectId htCode = profileDataService.findByHtId(htId)
                .map(Profile::getHtCode)
                .orElseThrow(() -> {
                    String error = messageApiProperties.getMessageWithArgs("validation.profile.isNotExisted", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });

        if (legalInfoDataService.existsByHtCode(htCode)) {
            throw new DataConflictingException(messageApiProperties.getMessageWithArgs("validation.legalInfo.isExisted", htId));
        }
        return Optional.of(profileConverterHelper.convert(createdInfo, htCode))
                .map(legalInfoDataService::create)
                .orElseThrow();
    }

    public LegalInfo findByHtId(String htId) throws DataNotExistingException {
        Optional<Profile> profileOptional = profileDataService.findByHtId(htId);
        if (profileOptional.isEmpty()) {
            throw new DataNotExistingException(messageApiProperties.getMessageWithArgs("validation.profile.isNotExisted", htId));
        }

        return profileOptional
                .flatMap(existingProfile -> legalInfoDataService.findByHtCode(existingProfile.getHtCode()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, messageApiProperties.getMessage("validation.legalInfo.isNotExisted")));
    }
}
