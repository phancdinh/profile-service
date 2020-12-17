package org.ht.id.profile.data.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.ht.id.common.constant.UserStatus;
import org.ht.id.external.user.data.UserData;
import org.ht.id.external.user.service.ExternalUserService;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profile.data.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileDataService {

    private final ProfileRepository profileRepository;
    private final ExternalUserService externalUserService;

    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    public Optional<Profile> findByHtId(String htId) {
        return profileRepository.findByHtId(htId);
    }

    public boolean existsByHtId(String htId) {
        return profileRepository.existsByHtId(htId);
    }

    public Profile create(String htId, String leadSource, String email, String phone, String password) {
        Profile addedProfile = new Profile();
        addedProfile.setHtId(htId);
        addedProfile.setLeadSource(leadSource);
        if (StringUtils.isEmpty(password)) {
            addedProfile.setStatus(UserStatus.CREATED);
        } else {
            UserData user = externalUserService.create(htId, email, phone, password);
            addedProfile.setStatus(UserStatus.IN_ACTIVE);
            addedProfile.setScimId(user.getId());
        }
        return profileRepository.insert(addedProfile);
    }

    public void deleteProfileByHtId(String htId) {
        profileRepository.deleteByHtId(htId);
    }

    public boolean existsByHtCodesAndStatus(List<ObjectId> htCodes, UserStatus status) {
        return profileRepository.existsByHtCodeInAndStatus(htCodes, status);
    }

    public Profile updateStatus(String htId, UserStatus status, String email, String phone, String password) {
        return findByHtId(htId).map(p -> {
            if (p.getStatus().equals(status)) {
                return p;
            }
            // TODO should be refactor for clear code.
            if (UserStatus.CREATED.equals(p.getStatus()) && UserStatus.IN_ACTIVE.equals(status)) {
                UserData user = externalUserService.create(htId, email, phone, password);
                p.setScimId(user.getId());
            }
            if (UserStatus.IN_ACTIVE.equals(p.getStatus()) && UserStatus.ACTIVE.equals(status)) {
                externalUserService.updateAccountLock(p.getScimId(), false);
            }
            p.setStatus(status);
            return profileRepository.save(p);
        }).orElse(new Profile());
    }
}
