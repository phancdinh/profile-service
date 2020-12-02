package org.ht.profileapi.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.ht.profile.data.model.Email;
import org.ht.profileapi.service.ThymeleafService;

@Service
public class EmailDataService {

    private final JavaMailSender mailSender;
    private final ThymeleafService thymeleafService;

    public EmailDataService(JavaMailSender mailSender, ThymeleafService thymeleafService) {
        this.mailSender = mailSender;
        this.thymeleafService = thymeleafService;
    }

    public void send(Email mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "org.ht.profile"));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent(), thymeleafService.getContent(mail.getFirstName(), mail.getMailTo(), mail.getLink()));

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
