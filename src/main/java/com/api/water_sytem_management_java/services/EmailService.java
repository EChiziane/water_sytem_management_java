package com.api.water_sytem_management_java.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public String enviarEmailTexto(String destinatario, String assunto, String mensagem) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(remetente);
            simpleMailMessage.setTo(destinatario);
            simpleMailMessage.setSubject(assunto);
            simpleMailMessage.setText(mensagem);
            //System.out.println("Email enviado com sucesso!");
            javaMailSender.send(simpleMailMessage);

            return "Email enviado";
        } catch (Exception e) {
            return "Erro ao tentar enviar email " + e.getLocalizedMessage();
        }
    }
}