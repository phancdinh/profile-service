package org.ht.account.data.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ht.account.data.model.Account;
import org.ht.account.data.model.internal.Activation;
import org.ht.account.data.model.internal.Invitation;
import org.ht.account.dto.response.ResponseData;
import org.ht.account.dto.response.ResponseStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ManageLinkBizService {
    
    
	private static int EXPIRE_PERIOD_DAYS = 10;
    
    private final AccountService actDataService;

    public ManageLinkBizService(AccountService actDataService) {
		this.actDataService = actDataService;
    }

	public ResponseData getActLink(String htId, String prefixUrl) {
      	
    	ResponseData result = new ResponseData();
    	Optional<Account> oData = actDataService.findByHtId(htId);
    	if(oData.isEmpty()) {
    		result.setStatus(ResponseStatus.FAILURE);
    		result.setMessage("Account does not existed!");
    		return result;
    	}
    	Account data = oData.get();
    	Activation actLink = new Activation();
	    LocalDateTime currentTime = LocalDateTime.now();
		String url =  prefixUrl + "/" + htId + "?" + currentTime.getNano();
		
		actLink.setCreatedAt(currentTime);
		actLink.setExpiredAt(currentTime.plusDays(EXPIRE_PERIOD_DAYS));
		actLink.setUrl(url);
		
		log.info("url: " + url);
		data.setActivation(actLink);
		
    	//Store Activation link to DB and return url back user
		actDataService.createOrUpdate(data);
		result.setStatus(ResponseStatus.SUCCESS);
		result.setMessage(url);
    	return result;
    }

	public ResponseData verifyActLink(String htId, String url) {
		ResponseData result = new ResponseData();
		//Does not existed account or account is register
    	Optional<Account> oData = actDataService.findByHtId(htId);
    	
		if(oData.isEmpty()
				|| oData.get().getActivation().getUrl() == null || oData.get().getActivation().getUrl() == ""
				|| oData.get().getActivation().getUrl().equalsIgnoreCase(url)
				|| oData.get().isActive()) {
			log.info("HtId: " + htId + " does not existed!");
			result.setMessage("Activation link does not existed!");
			result.setStatus(ResponseStatus.FAILURE);
			result.setCode("");// define later
			return result;
		}
				
		Account data = oData.get();

		//check expire date from create
		if(LocalDateTime.now().isAfter(data.getActivation().getExpiredAt())){
			log.info("HtId: " + htId + " active link expired already!");
			result.setMessage("HTID active link expired already!!");
			result.setStatus(ResponseStatus.FAILURE);
			result.setCode("");// define later
			
			//data.getActivation().setConfirmedAt(LocalDateTime.now());
			data.getActivation().setUrl("");//Remove active Link
			actDataService.update(data);
			return result;
		}
		
		data.setActive(true);
		data.setLastModifiedDate(LocalDateTime.now());
		data.getActivation().setConfirmedAt(LocalDateTime.now());
		data.getActivation().setUrl("");
		
		actDataService.update(data);
		result.setMessage("HTID active link valid!");
		result.setStatus(ResponseStatus.SUCCESS);
		result.setCode("");// define later
		return result;
	}

  
	  public ResponseData getInvtLink(String htId, String prefixUrl, String contact) {
		  
		  
			ResponseData result = new ResponseData();
		    LocalDateTime currentTime = LocalDateTime.now();
		    //Does not create Profile this account
			Optional<Account> oData = actDataService.findByHtId(htId);
			if (oData.isEmpty()) {
				result.setStatus(ResponseStatus.FAILURE);
				result.setMessage("Account does not existed!");
				return result;
			}

			//Account is existed or active already 
			Account data = oData.get();
			if(data.isActive() || data.isUserCreated()) {
				result.setStatus(ResponseStatus.FAILURE);
				result.setMessage("Account is created already!");
				return result;
			}
			
			//Incase resend email/phone to invitation link, will update Invitation, other will create new
			data.getInvitations().removeIf(p -> contact.equalsIgnoreCase(p.getMainContact()));
			Invitation invtData = null;
			String url = prefixUrl + "/" + htId + "?" + currentTime.getNano() + "&" + contact ;
			
			invtData = new Invitation();
			invtData.setCreatedAt(currentTime);
			invtData.setMainContact(contact);
			invtData.setUrl(url);
			invtData.setShortUrl(url);//will update later after short link will be applied
			
			data.getInvitations().add(invtData);
			//Store Activation link to DB and return url back user
			actDataService.update(data);
			result.setStatus(ResponseStatus.SUCCESS);
			result.setMessage(url);
			return result;
	    	
	  }
	  
	  public ResponseData verifyInvtLink(String htId, String url, String contact) {
		  
		  	ResponseData result = new ResponseData();
		    //Does not create Profile this account
			Optional<Account> oData = actDataService.findByHtId(htId);
			if (oData.isEmpty()) {
				result.setStatus(ResponseStatus.FAILURE);
				result.setMessage("Account does not existed!");
				return result;
			}

			//Account is existed or active already 
			Account data = oData.get();
			if(data.isActive() || data.isUserCreated()) {
				result.setStatus(ResponseStatus.FAILURE);
				result.setMessage("Account is created already!");
				return result;
			}
			
			//Incase resend email/phone to invitation link, will update Invitation, other will create new
			Invitation invtData = data.getInvitations().stream().filter(p -> contact.equalsIgnoreCase(p.getMainContact())).findAny().orElse(null);

			if(invtData == null) {
				result.setStatus(ResponseStatus.FAILURE);
				result.setMessage("Invitation Link does not existed!");
				return result;
			}
			invtData.setLastModifiedDate(LocalDateTime.now());
			invtData.setUrl("");
			invtData.setShortUrl("");
			
			List<Invitation> newList = data.getInvitations().stream().filter(p -> !contact.equalsIgnoreCase(p.getMainContact())).collect(Collectors.toList());
			newList.add(invtData);
			data.setInvitations(newList);
			//Store Activation link to DB and return url back user
			actDataService.update(data);
			result.setStatus(ResponseStatus.SUCCESS);
			result.setMessage(url);
			return result;
	  }
	  
	  
}
