package com.exemplo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarConfirmacao(String para, String assunto, String corpoHtml) throws MessagingException {
        MimeMessage mensagem = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(corpoHtml, true); // true = HTML

        mailSender.send(mensagem);
    }
}
