package com.example.demo.controller;

import com.example.demo.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Component
@EnableScheduling
public class SendGridEmailController {
    @Autowired
    private SendMailService sendGridMailService;
   @Scheduled(cron = "${cron.time.sendgrid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendMail () {
        sendGridMailService.sendMail(
             "chienth@timebird.org",
                "test name",
                "test project"
        );
    }
}