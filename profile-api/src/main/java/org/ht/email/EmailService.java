package org.ht.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

  private final JavaMailSender mailSender;
  private final MailTemplateReader mailTemplateReader;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
    this.mailTemplateReader = new MailTemplateReader();
  }

  public void send(
      String mailFrom,
      String mailTo,
      String subject,
      String content,
      String firstName,
      String link,
      EmailSenderType type)
      throws MessagingException, UnsupportedEncodingException {
    sendContent(mailFrom, mailTo, subject, content, firstName, link, type);
  }

  private void sendContent(
          String mailFrom,
          String mailTo,
          String subject,
          String content,
          String firstName,
          String link,
          EmailSenderType type)
      throws MessagingException, UnsupportedEncodingException {

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setFrom(new InternetAddress(mailFrom, "org.ht.profile"));
    mimeMessageHelper.setTo(mailTo);

    if (type == EmailSenderType.HTML) {
      mimeMessageHelper.setText("", mailTemplateReader.getTemplate(firstName, mailTo, link));
    } else {
      mimeMessageHelper.setText(content);
    }

    mailSender.send(mimeMessageHelper.getMimeMessage());
  }
}
