package org.ht.profile.serviceFacade;

import org.ht.profile.dto.request.BasicInfoCreateRequest;
import org.ht.profile.dto.response.BasicInfoResponse;


public interface ProfileServiceFacade {

    BasicInfoResponse create(BasicInfoCreateRequest p);

    BasicInfoResponse find(String htId);
}
