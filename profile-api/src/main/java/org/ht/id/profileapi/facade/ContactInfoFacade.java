package org.ht.id.profileapi.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.common.exception.InactivatedAccountException;
import org.ht.id.profile.bizservice.ContactInfoBizService;
import org.ht.id.profile.bizservice.ProfileBizService;
import org.ht.id.profileapi.config.MessageApiProperties;
import org.ht.id.profileapi.dto.request.ContactInfoCreateRequest;
import org.ht.id.profileapi.dto.response.ContactInfoResponse;
import org.ht.id.profileapi.dto.response.internal.HierarchyContactResponse;
import org.ht.id.profileapi.facade.converter.ProfileInfoConverter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContactInfoFacade {

    private final ContactInfoBizService contactInfoBizService;
    private final ProfileInfoConverter profileInfoConverter;
    private final ProfileBizService profileBizService;
    private final MessageApiProperties messageApiProperties;

    public ContactInfoResponse create(String htId, ContactInfoCreateRequest createRequest) {
        return Optional.of(createRequest)
                .map(profileInfoConverter::convertToEntity)
                .map(contactInfoDto -> contactInfoBizService.create(htId, contactInfoDto))
                .map(contactInfoDto -> profileInfoConverter.convertToResponse(contactInfoDto, htId)).orElse(null);
    }

    public ContactInfoResponse findByHtId(String htId) {
        return profileInfoConverter.convertToResponse(contactInfoBizService.findByHtId(htId), htId);
    }

    public HierarchyContactResponse createContactEmail(String htId, String email) {
        return Optional.of(htId).map(profileBizService::findProfile)
                .filter(c -> !c.isInactivated())
                .map(c -> contactInfoBizService.createContactEmail(c.getHtCode(), email))
                .map(profileInfoConverter::convertToResponse)
                .orElseThrow(() -> {
                    throw new InactivatedAccountException(messageApiProperties.getMessage("validation.account.isNotActivated"));
                });
    }
}
