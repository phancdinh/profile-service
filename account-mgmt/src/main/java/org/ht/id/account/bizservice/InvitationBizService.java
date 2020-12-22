package org.ht.id.account.bizservice;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import org.ht.id.account.config.AccountMgmtProperties;
import org.ht.id.account.config.AccountMgtMessageProperties;
import org.ht.id.account.config.BitlyApiProperties;
import org.ht.id.account.data.model.Invitation;
import org.ht.id.account.data.service.InvitationDataService;
import org.ht.id.account.dto.BitlyShortenLinkCreationResponse;
import org.ht.id.common.EncryptUtil;
import org.ht.id.common.exception.DataNotExistingException;
import org.ht.id.common.exception.EncryptFailureException;
import org.ht.id.common.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;
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
    private final AccountMgtMessageProperties accountMgtMessageProperties;

    public Invitation create(Invitation invitation) {
        invitation.setCreatedAt(new Date());
        return invitationDataService.create(invitation);
    }

    public Invitation update(Invitation invitation) {
        invitation.setLastModifiedDate(new Date());
        return invitationDataService.update(invitation);
    }

    public Invitation findById(String id) {
        return invitationDataService.findById(id).orElseThrow(() -> {
            String error = accountMgtMessageProperties.getMessage("validation.invitation.not.existed", id);
            log.error(error);
            throw new DataNotExistingException(error);
        });
    }

    public String generateInvitationLink(Invitation invitation) throws DataNotExistingException {
        //generate invitation link
        String url = Optional.of(invitation).map(i -> String.format(accountApiProperties.getAccountInvitationLink(), i.getId(), getMd5Invitation(i))).orElseThrow();

        try {
            url = shortenLinkBizService
                    .createShortLink(bitlyApiProperties.getServiceName(), bitlyApiProperties.getGroupName(), url)
                    .map(BitlyShortenLinkCreationResponse::getLink).orElse(url);
        } catch (ServiceUnavailableException e) {
            log.error(e.toString());//Original url will be return incase fail from external service
        }
        return url;
    }

    public boolean isMatchValueCheck(Invitation invitation, String valueCheck) {
        return Optional.of(valueCheck).filter(p -> p.equals(getMd5Invitation(invitation))).isPresent();
    }

    private String getMd5Invitation(Invitation invitation) {
        try {
            return EncryptUtil.md5(invitation.getId().toString(), invitation.getHtId(), invitation.getCreatedAt().toString());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new EncryptFailureException(accountMgtMessageProperties.getMessage("validation.activation.not.createdCode"));
        }
    }
}
