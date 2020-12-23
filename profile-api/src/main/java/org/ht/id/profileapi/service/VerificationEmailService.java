package org.ht.id.profileapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.ht.id.common.EncryptUtil;
import org.ht.id.common.exception.EncryptFailureException;
import org.ht.id.profile.bizservice.ProfileBizService;
import org.ht.id.profile.data.model.Profile;
import org.ht.id.profileapi.config.MessageApiProperties;
import org.ht.id.profileapi.config.ProfileApiProperties;
import org.ht.id.profileapi.dto.response.internal.HierarchyContactResponse;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Aspect
@Service
@RequiredArgsConstructor
public class VerificationEmailService {

    private final AppEmailService appEmailService;
    private final ProfileApiProperties profileApiProperties;
    private final ProfileBizService profileBizService;
    private final MessageApiProperties messageApiProperties;

    @AfterReturning(value = "execution(* org.ht.id.profileapi.facade.ContactInfoFacade.createContactEmail(..)) && args(htId, email, ..)", returning = "result", argNames = "htId,email,result")
    public void sendEmail(String htId, String email, HierarchyContactResponse result) {
        if (result.isVerified()) {
            log.info(messageApiProperties.getMessage("mail.verification.success", email));
            return;
        }

        Profile profile = profileBizService.findProfile(htId);
        String verificationLink = generateVerificationLink(htId, email, profile.getHtCode().toString());
        appEmailService.sendVerificationEmail(email, verificationLink);
    }

    private String generateVerificationLink(String htId, String email, String htCode) {
        String code = generateVerificationCode(htId, email, htCode);
        String urlPattern = profileApiProperties.getEmailVerificationUrlPattern();
        return String.format(urlPattern, profileApiProperties.getWebClient(), htId, email, code);
    }

    private String generateVerificationCode(String htId, String email, String htCode) {
        try {
            return EncryptUtil.md5(htId, email, htCode);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new EncryptFailureException(messageApiProperties.getMessage("validation.verifyCode.failed"));
        }
    }
}
