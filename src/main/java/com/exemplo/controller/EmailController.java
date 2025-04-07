package com.exemplo.controller;

import com.exemplo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teste-email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String enviarEmailTeste() {
        try {
            String destinatario = "saraefamilia303@gmail.com";
            String assunto = "Teste de E-mail - LavaFácil";
            String corpo = "Olá! Este é um teste de envio de e-mail pelo sistema LavaFácil. Se você recebeu esta mensagem, o envio está funcionando corretamente. ✅";

            emailService.enviarConfirmacao(destinatario, assunto, corpo);
            return "✅ E-mail de teste enviado com sucesso para " + destinatario;
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erro ao enviar e-mail: " + e.getMessage();
        }
    }
}
