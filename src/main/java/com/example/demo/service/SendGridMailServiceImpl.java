package com.example.demo.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.propertyeditors.CustomBooleanEditor.VALUE_TRUE;

@Service
public class SendGridMailServiceImpl implements SendGridMailService {
    private static final String CONTENT_TYPE_TEXT_PLAIN = "text/plain";

    private static final String KEY_X_MOCK = "X-Mock";

    private static final String SEND_GRID_ENDPOINT_SEND_EMAIL = "mail/send";

    @Value("${send_grid.api_key}")
    private String sendGridApiKey;

    @Value("${send_grid.from_email}")
    private String sendGridFromEmail;

    @Value("${send_grid.from_name}")
    private String sendGridFromName;

    @Value("${mail.temp}")
    private String mailTemp;
    @Override
    public void sendMail(List<String> sendToEmails, List<String> ccEmails, List<String> bccEmails, String abc, String project) {
        Map<String, Object> mapTemplateData = new HashMap<>();
        mapTemplateData.put("name", abc);
        mapTemplateData.put("project", project);
        Mail mail = buildMailToSend(sendToEmails, ccEmails, bccEmails, mapTemplateData);
        send(mail);
    }

    private void send(Mail mail) {
        SendGrid sg = new SendGrid(sendGridApiKey);
        sg.addRequestHeader(KEY_X_MOCK, VALUE_TRUE);

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint(SEND_GRID_ENDPOINT_SEND_EMAIL);
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private Mail buildMailToSend(List<String> sendToEmails, List<String> ccEmails, List<String> bccEmails, Map<String, Object> mapData) {
        DateFormat dateFormatter = new SimpleDateFormat("ddMMyyyy");
        String currentDateTime = dateFormatter.format(new Date());
        System.out.println(currentDateTime);
        Mail mail = new Mail();
        mail.setTemplateId(mailTemp);
        Email fromEmail = new Email();
        fromEmail.setName(sendGridFromName);
        fromEmail.setEmail(sendGridFromEmail);

        mail.setFrom(fromEmail);

//        mail.setSubject(subject);

        Personalization personalization = new Personalization();
        mapData.forEach((k, v) -> personalization.addDynamicTemplateData(k, v));
        //Add sendToEmails
        if (sendToEmails != null) {
            for (String email : sendToEmails) {
                Email to = new Email();
                to.setEmail(email);
                personalization.addTo(to);
            }
        }

        //Add ccEmail
        if (ccEmails != null) {
            for (String email : ccEmails) {
                Email cc = new Email();
                cc.setEmail(email);
                personalization.addCc(cc);
            }
        }

        //Add bccEmail
        if (bccEmails != null) {
            for (String email : bccEmails) {
                Email bcc = new Email();
                bcc.setEmail(email);
                personalization.addBcc(bcc);
            }
        }
        mail.addPersonalization(personalization);

        Content content = new Content();
        content.setType(CONTENT_TYPE_TEXT_PLAIN);
//        content.setValue(contentStr);
//        mail.addContent(content);
        return mail;
    }
}
