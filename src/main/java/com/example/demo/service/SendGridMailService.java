package com.example.demo.service;

import java.util.List;

public interface SendGridMailService {
    void sendMail(List<String> sendToEmails, List<String> ccEmails, List<String> bccEmails, String name, String project);
}