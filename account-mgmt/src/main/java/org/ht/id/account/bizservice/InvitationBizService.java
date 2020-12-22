package org.ht.id.account.bizservice;

import java.util.Date;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ht.id.account.config.AccountMgmtProperties;
import org.ht.id.account.config.BitlyApiProperties;
import org.ht.id.account.data.model.Invitation;
import org.ht.id.account.data.service.InvitationDataService;
import org.ht.id.account.exception.DataNotExistingException;
import org.ht.id.account.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InvitationBizService {

    private final InvitationDataService invitationDataService;
    private final AccountMgmtProperties accountApiProperties;
    private final ShortenLinkBizService shortenLinkBizService;
    private final BitlyApiProperties bitlyApiProperties;

    public Invitation create(Invitation invitation) {
        invitation.setCreatedAt(new Date());
        return invitationDataService.create(invitation);
    }

    public Invitation update(Invitation invitation) {
        invitation.setLastModifiedDate(new Date());
        return invitationDataService.update(invitation);
    }

    public Invitation findById(ObjectId id) {
        return invitationDataService.findById(id).orElseThrow(() -> {
                String error = String.format("Invitation is not existed with id: %s", id);
                log.error(error);
                throw new DataNotExistingException(error);
                });
    }

    public String generateInvitationLink(Invitation invitation) throws DataNotExistingException {
        //generate invitation link
        String url = Optional.of(invitation).map(i -> {
            return String.format(accountApiProperties.getAccountInvitationLink(), i.getId(), getMd5Invitation(i));
        }).orElseThrow();

        try {
            url = shortenLinkBizService
                    .createShortLink(bitlyApiProperties.getServiceName(), bitlyApiProperties.getGroupName(), url)
                    .map(p -> p.getLink()).orElse(url);
        } catch (ServiceUnavailableException e) {
            log.error(e.toString());//Original url will be return incase fail from external service
        }
        return url;
    }

    public boolean isMatchValueCheck(Invitation invitation, String valueCheck) {
        return Optional.of(valueCheck).filter(p -> p.equals(getMd5Invitation(invitation))).isPresent();
    }

    private String getMd5Invitation(Invitation invitation) {
        return generateMD5(invitation.getId().toString(), invitation.getHtId(), invitation.getCreatedAt().toString());
    }

    private String generateMD5(String... data) {
        StringBuilder builder = new StringBuilder();
        for (String b : data) {
            builder.append(b);
        }
        return DigestUtils.md5DigestAsHex(builder.toString().getBytes());
    }
}