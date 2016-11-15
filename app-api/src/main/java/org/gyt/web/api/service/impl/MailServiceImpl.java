package org.gyt.web.api.service.impl;

import org.gyt.web.api.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendText(String to, String subject, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setFrom(username);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content);

        javaMailSender.send(mimeMessage);
    }
}
