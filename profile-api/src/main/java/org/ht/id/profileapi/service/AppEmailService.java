package org.ht.id.profileapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ht.id.email.EmailSenderType;
import org.ht.id.email.EmailService;
import org.ht.id.email.EmailTemplateType;
import org.ht.id.profileapi.config.MessageApiProperties;
import org.ht.id.profileapi.config.ProfileApiProperties;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppEmailService {
    private final EmailService emailService;
    private final ProfileApiProperties profileApiProperties;
    private final MessageApiProperties messageApiProperties;

    public void sendActivationEmail(String activationLink, String customerEmail) {
        CompletableFuture.runAsync(() -> {
            try {
                emailService.send(customerEmail,
                        profileApiProperties.getActivationEmailSubject(), "", profileApiProperties.getMailFrom(),
                        activationLink, EmailSenderType.HTML, EmailTemplateType.ACTIVATION);
            } catch (MessagingException | UnsupportedEncodingException e) {
                log.error(messageApiProperties.getMessage("mail.activation.emailSentFailed", customerEmail));
            }
        });
    }

    public void sendResetPasswordEmail(String email, String resetPasswordLink) {
        CompletableFuture.runAsync(() -> {
            try {
                emailService.send(email,
                        messageApiProperties.getMessage("mail.resetPassword.emailSubject"), "",
                        profileApiProperties.getMailFrom(), resetPasswordLink, EmailSenderType.HTML, EmailTemplateType.RESET_PASSWORD);
            } catch (MessagingException | UnsupportedEncodingException e) {
                log.error(String.format(messageApiProperties.getMessage("mail.resetPassword.emailSentFailed"), e.getMessage()));
            }
        });
    }

    public void sendVerificationEmail(String email, String verificationLink) {
        CompletableFuture.runAsync(() -> {
            try {
                emailService.send(email,
                        messageApiProperties.getMessage("mail.verification.emailSubject"), "",
                        profileApiProperties.getMailFrom(), verificationLink, EmailSenderType.HTML, EmailTemplateType.EMAIL_VERIFICATION);
            } catch (MessagingException | UnsupportedEncodingException e) {
                log.error(String.format(messageApiProperties.getMessage("mail.verification.failure"), e.getMessage()));
            }
        });
    }
}
