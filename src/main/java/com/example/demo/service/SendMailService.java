package com.example.demo.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SendMailService {
    private String mailApiKey;

    private String fromName;

    private String fromEmail;

    private String tempMailBillExpiredInfo;

    public SendMailService() {
    }

    public SendMailService(String mailApiKey, String fromName, String fromEmail, String tempMailBillExpiredInfo) {
        this.mailApiKey = mailApiKey;
        this.fromName = fromName;
        this.fromEmail = fromEmail;
        this.tempMailBillExpiredInfo = tempMailBillExpiredInfo;
    }

    public boolean sendMail(String toEmail, String fullName, String projectName) {
        int statusCode = 400;
        Map<String, Object> mapTemplateData = new HashMap<>();
        mapTemplateData.put("email", toEmail);
        mapTemplateData.put("name", fullName);
        mapTemplateData.put("project", projectName);
        try {
            statusCode = this.sendMailTemplateBySendGrid(fromEmail, fromName, tempMailBillExpiredInfo, mapTemplateData, Arrays.asList(toEmail));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode == 202;
    }

    private int sendMailTemplateBySendGrid(String from, String fromName, String templateId, Map<String, Object> mapData, List<String> lstTo) throws IOException {
        Mail mail = new Mail();
        mail.setTemplateId(templateId);
        Email fromEmail = new Email();
        fromEmail.setName(fromName);
        fromEmail.setEmail(from);
        mail.setFrom(fromEmail);
        Personalization personalization = new Personalization();
        mapData.forEach((k, v) -> personalization.addDynamicTemplateData(k, v));
        lstTo.forEach(item -> personalization.addTo(new Email(item)));
        mail.addPersonalization(personalization);
        try {
            SendGrid sg = new SendGrid(mailApiKey);
            sg.addRequestHeader("X-Mock", "true");

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            return response.getStatusCode();
        } catch (IOException ex) {
            throw ex;
        }
    }
}
