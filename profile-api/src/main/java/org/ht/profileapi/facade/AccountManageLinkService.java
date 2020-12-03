package org.ht.profileapi.facade;

import org.ht.account.data.service.ManageLinkBizService;
import org.ht.account.dto.response.ResponseData;
import org.ht.account.dto.response.ResponseStatus;
import org.ht.profile.bizservice.ProfileBizService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AccountManageLinkService {

    private final ManageLinkBizService manageLinkBizService;
    private final ProfileBizService profileBizService;


    public AccountManageLinkService(ManageLinkBizService manageLinkBizService, ProfileBizService profileBizService) {
        this.manageLinkBizService = manageLinkBizService;
        this.profileBizService = profileBizService;
    }
    
    public ResponseData getActLink(String htId, String prefixUrl) {
    	return manageLinkBizService.getActLink(htId,prefixUrl);
    }
    
    public ResponseData verifyActLink(String htId, String url) {
    	return manageLinkBizService.verifyActLink(htId, url);
    }
    
    public ResponseData getInvtLink(String htId, String prefixUrl, String contact) {
    	ResponseData response = new ResponseData();
    	
		if (profileBizService.existsByHtId(htId)) {
			response.setStatus(ResponseStatus.FAILURE);
			response.setMessage("Doest not existed Profile!");
			return response;
		}

    	return manageLinkBizService.getInvtLink(htId,prefixUrl,contact);
    }

    public ResponseData verifyInvtLink(String htId, String url, String contact) {
    	return manageLinkBizService.verifyInvtLink(htId, url, contact);
    }
    
}
