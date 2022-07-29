package com.example.demo.config;

import com.example.demo.service.SendMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    @Value("${send_grid.api_key}")
    private String sendGridApiKey;

    @Value("${send_grid.from_email}")
    private String sendGridFromEmail;

    @Value("${send_grid.from_name}")
    private String sendGridFromName;

    @Value("${mail.temp}")
    private String mailTemp;

    @Bean
    public SendMailService getSendMailService() {
        return new SendMailService(sendGridApiKey, sendGridFromName, sendGridFromEmail, mailTemp);
    }

    public String getSendGridApiKey() {
        return sendGridApiKey;
    }

    public void setSendGridApiKey(String sendGridApiKey) {
        this.sendGridApiKey = sendGridApiKey;
    }

    public String getSendGridFromEmail() {
        return sendGridFromEmail;
    }

    public void setSendGridFromEmail(String sendGridFromEmail) {
        this.sendGridFromEmail = sendGridFromEmail;
    }

    public String getSendGridFromName() {
        return sendGridFromName;
    }

    public void setSendGridFromName(String sendGridFromName) {
        this.sendGridFromName = sendGridFromName;
    }

    public String getMailTemp() {
        return mailTemp;
    }

    public void setMailTemp(String mailTemp) {
        this.mailTemp = mailTemp;
    }
}
