package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendPasswordResetEmail(String toEmail, String name, String resetUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Restablecer contrasena");

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <h2>Hola %s,</h2>
                    <p>Recibimos una solicitud para restablecer tu contrasena.</p>
                    <p>Haz clic en el siguiente enlace (valido por 1 hora):</p>
                    <a href="%s"
                       style="display:inline-block; padding:12px 24px; background:#007bff;
                              color:white; text-decoration:none; border-radius:4px;">
                        Restablecer contrasena
                    </a>
                    <p style="margin-top:16px;">Si no solicitaste este cambio, ignora este email.</p>
                </body>
                </html>
                """.formatted(name, resetUrl);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar email: " + e.getMessage());
        }
    }
}