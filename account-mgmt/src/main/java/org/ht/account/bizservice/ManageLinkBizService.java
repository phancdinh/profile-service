package org.ht.account.bizservice;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.apache.commons.lang3.time.DateUtils;
import org.ht.account.config.AccountMgmtProperties;
import org.ht.account.config.BitlyApiProperties;
import org.ht.account.data.model.Account;
import org.ht.account.data.model.internal.Activation;
import org.ht.account.data.model.internal.Invitation;
import org.ht.account.data.service.AccountDataService;
import org.ht.account.exception.DataNotExistingException;
import org.ht.account.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ManageLinkBizService {
    
    private final AccountMgmtProperties accountApiProperties;
    private final BitlyApiProperties bitlyApiProperties;
    private final AccountDataService actDataService;
    private final ShortenLinkBizService shortenLinkBizService;
    
    public ManageLinkBizService(AccountDataService actDataService, AccountMgmtProperties accountApiProperties,
            ShortenLinkBizService shortenLinkBizService, BitlyApiProperties bitlyApiProperties) {
        this.actDataService = actDataService;
        this.accountApiProperties = accountApiProperties;
        this.shortenLinkBizService = shortenLinkBizService;
        this.bitlyApiProperties = bitlyApiProperties;
    }
    
    public String generateActivationLink(String htId) throws DataNotExistingException {
        
        Account account = actDataService.findByHtId(htId).filter(p -> !p.isActive() && !p.isUserCreated())
                .orElseThrow(() -> {
                    String error = String.format("Account does not existed or actived with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });
        
        Activation actLink = new Activation();
        actLink.setCreatedAt(new Date());
        actLink.setExpiredAt(DateUtils.addDays(new Date(), accountApiProperties.getActivationExpirePeriodDays()));
        account.setActivation(actLink);
        actDataService.update(account);
        
        String url = initActivationLink(account);
        log.info("url: " + url);
        return url;
    }
    
    public Account validateActivationLink(String htId, String check) throws DataNotExistingException {
        // TODO we must remove active and userCreated field.
        Account account = actDataService.findByHtId(htId)
                .filter(p -> !p.isActive() && !p.isUserCreated() && p.getActivation() != null).orElseThrow(() -> {
                    String error = String.format("Account activation does not existed or actived with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });
        // Check activation date is still valid and correct link for this htid
        if (isLinkExpire(account) || !check.equals(getMd5Activation(account))) {
            String error = String.format("Account activation does not existed or actived with htId: %s", htId);
            log.error(error);
            throw new DataNotExistingException(error);
        }
        
        account.setActive(true);
        account.setUserCreated(true);
        account.getActivation().setConfirmedAt(new Date());
        return actDataService.update(account);
    }
    
    public String generateInvitationLink(String htId, String contact) throws DataNotExistingException {
        Account account = actDataService.findByHtId(htId).orElse(null);
        // TODO: this is around solution for current situation.
        if (account == null) {
            Account newAccount = new Account();
            newAccount.setHtId(htId);
            account = actDataService.update(newAccount);
        } else {
            Optional.of(account).filter(p -> !p.isActive() && !p.isUserCreated()).orElseThrow(() -> {
                String error = String.format("Account does not existed or actived with htId: %s", htId);
                log.error(error);
                return new DataNotExistingException(error);
            });
        }
        Date currentDate = new Date();
        // Incase resend email/phone to invitation link, will update Invitation,
        // other will create new
        Invitation invitation = new Invitation(contact, currentDate, null);
        
        if (account.getInvitations() != null) {
            account.getInvitations().removeIf(p -> contact.equalsIgnoreCase(p.getMainContact()));
            account.getInvitations().add(invitation);
        } else {
            List<Invitation> list = Stream.of(invitation).collect(Collectors.toList());
            account.setInvitations(list);
        }
        String url = initInvitationLink(account, invitation);
        actDataService.update(account);
        
        try {
            url = shortenLinkBizService
                    .createShortLink(bitlyApiProperties.getServiceName(), bitlyApiProperties.getGroupName(), url)
                    .map(p -> p.getLink()).orElse(url);
        } catch (ServiceUnavailableException e) {
            log.error(e.toString());//Original url will be return incase fail from external service
        }
        return url;
    }
    
    public Invitation getInvitationLink(String htId, String valid) throws DataNotExistingException {
        Account data = actDataService.findByHtId(htId)
                .filter(p -> !p.isActive() && !p.isUserCreated() && p.getInvitations().stream().count() > 0)
                .orElseThrow(() -> {
                    String error = String.format("Invitation does not existed or actived with htId: %s", htId);
                    log.error(error);
                    return new DataNotExistingException(error);
                });
        
        return getInvitation(valid, data);
    }
    
    private String initActivationLink(Account account) {
        StringBuilder builder = new StringBuilder();
        builder.append(accountApiProperties.getAccountActivationLink());
        builder.append("?htId=").append(account.getHtId());
        builder.append("&valid=").append(getMd5Activation(account));
        return builder.toString();
    }
    
    private String getMd5Invitation(Account account, Invitation invitation) {
        return new String((DigestUtils.md5DigestAsHex(
                (account.getHtId() + invitation.getMainContact() + invitation.getCreatedAt()).getBytes())));
    }
    
    private String initInvitationLink(Account account, Invitation invitation) {
        StringBuilder builder = new StringBuilder();
        builder.append(accountApiProperties.getAccountInvitationLink());
        builder.append("?htId=").append(account.getHtId());
        builder.append("&valid=").append(getMd5Invitation(account, invitation));
        return builder.toString();
    }
    
    private Invitation getInvitation(String check, Account account) {
        return account.getInvitations().stream().filter(p -> check.equals(getMd5Invitation(account, p))).findFirst()
                .orElseThrow(() -> {
                    String error = String.format("Invitation does not match with htId: %s", account.getHtId());
                    log.error(error);
                    return new DataNotExistingException(error);
                });
    }
    
    private boolean isLinkExpire(Account data) {
        Date currentDate = new Date();
        return currentDate.compareTo(data.getActivation().getExpiredAt()) > 0;
    }
    
    private String getMd5Activation(Account account) {
        return new String(
                (DigestUtils.md5DigestAsHex((account.getHtId() + account.getActivation().getCreatedAt()).getBytes())));
    }
}
