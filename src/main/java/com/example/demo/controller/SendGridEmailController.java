package com.example.demo.controller;

import com.example.demo.service.SendGridMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class SendGridEmailController {
    @Autowired
    private SendGridMailService sendGridMailService;

    @GetMapping("/test-send-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void testSendEmail() {
        sendGridMailService.sendMail(
                Collections.singletonList("chiplove1821994@gmail.com"),
                null,
                null,
                "test name",
                "test project"
        );
    }
}