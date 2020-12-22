package org.ht.id.email;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final MailTemplateReader mailTemplateReader;
    private final Environment environment;

    public void send(
            String mailTo,
            String subject,
            String content,
            String firstName,
            String link,
            EmailSenderType type, EmailTemplateType emailTemplateType)
            throws MessagingException, UnsupportedEncodingException {
        sendContent(mailTo, subject, content, firstName, link, type, emailTemplateType);
    }

    private void sendContent(
            String mailTo,
            String subject,
            String content,
            String firstName,
            String link,
            EmailSenderType type, EmailTemplateType emailTemplateType)
            throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom(new InternetAddress(environment.getProperty("MAIL_FROM"), environment.getProperty("MAIL_SET_SUBJECT")));
        mimeMessageHelper.setTo(mailTo);

        if (type == EmailSenderType.HTML) {
            mimeMessageHelper.setText("", mailTemplateReader.getTemplate(firstName, mailTo, link, emailTemplateType));
        } else {
            mimeMessageHelper.setText(content);
        }

        mailSender.send(mimeMessageHelper.getMimeMessage());
    }
}
