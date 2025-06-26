package com.hrproject.hrwebsiteproject.util;

import com.hrproject.hrwebsiteproject.model.dto.request.MailSenderRequestDto;
import com.hrproject.hrwebsiteproject.model.dto.request.WelcomeMailRequestDto;
import com.hrproject.hrwebsiteproject.model.enums.EEmbezzlementDuration;
import com.hrproject.hrwebsiteproject.model.enums.EEmbezzlementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendMail(MailSenderRequestDto mailModel) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(mailModel.email());
        simpleMailMessage.setSubject("Aktivasyon İşlemleri");
        simpleMailMessage.setText("Aktivasyon Kodu: " + mailModel.activationCode());
        simpleMailMessage.setCc("hhrwebsiteproject@gmail.com");

        javaMailSender.send(simpleMailMessage);
    }

    @Async
    public void sendPasswordMail(MailSenderRequestDto mailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(mailModel.email());
        simpleMailMessage.setSubject("Şifre Yenileme");
        simpleMailMessage.setText("Şifre yenileme için Aktivasyon Kodu: " + mailModel.activationCode());
        simpleMailMessage.setCc("hhrwebsiteproject@gmail.com");


        javaMailSender.send(simpleMailMessage);
    }
    @Async
    public void sendInformationMail(WelcomeMailRequestDto mailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(mailModel.email());
        simpleMailMessage.setSubject(mailModel.subject());
        simpleMailMessage.setText(mailModel.body());
        simpleMailMessage.setCc("hhrwebsiteproject@gmail.com");

        javaMailSender.send(simpleMailMessage);
    }
}
