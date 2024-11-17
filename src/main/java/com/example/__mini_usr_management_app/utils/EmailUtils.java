package com.example.__mini_usr_management_app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to , String subject , String body){

        boolean isSend = false;

        try {
            /**
             * Two ways to send email via JavaMailSender mailSender
             * 1. Simple Message - only body will send
             *      - SimpleMessage msg = new SimpleMessage()
             * 2. MimeMessage - With body attachment will send also
             *     -  MimeMessage mimeMessage = mailSender.createMimeMessage();
             */
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body , true);

            mailSender.send(mimeMessage);

            isSend = true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return isSend;
    }
}
