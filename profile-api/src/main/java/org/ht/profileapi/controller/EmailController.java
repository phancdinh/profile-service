package org.ht.profileapi.controller;

import org.ht.profile.data.model.Email;
import org.ht.profileapi.service.EmailDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailController {

    @Autowired
    private Environment env;

    private final EmailDataService emailDataService;

    public EmailController(EmailDataService emailDataService) {
        this.emailDataService = emailDataService;
    }

    @ResponseBody
    @GetMapping("/sendEmail")
    public String sendEmail() {

        Email mail = new Email();

        mail.setMailFrom(env.getProperty("MAIL_FROM"));
        mail.setMailTo(env.getProperty("MAIL_RECIPIENT"));
        mail.setFirstName(env.getProperty("MAIL_FIRST_NAME"));
        mail.setMailSubject(env.getProperty("MAIL_SET_SUBJECT"));
        mail.setMailContent(env.getProperty("MAIL_SET_CONTENT"));
        mail.setLink(env.getProperty("MAIL_ACTIVATION_LINK"));

        try {
            this.emailDataService.send(mail);
            return new String("Email sent");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
