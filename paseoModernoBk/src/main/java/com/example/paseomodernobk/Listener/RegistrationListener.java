package com.example.paseomodernobk.Listener;

import com.example.paseomodernobk.Events.OnRegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstants mailConstants;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        String recipientAddress = event.getUser().getEmail();
        String subject = "\uD83D\uDFE2 Paseo Moderno - Confirmación de Registro";
        String confirmationUrl = mailConstants.getHostAddress() + "/account/registration/validate?token=" + event.getToken();
        String message = "<div style='text-align: center;'>" +
                "<p style='font-size: 16px; line-height: 1.5em; font-weight: 600;'>¡Hola!</p>" +
                "<p style='font-size: 16px; line-height: 1.5em;'>Bienvenido/a a Paseo Moderno. Estamos muy contentos de que te hayas registrado con nosotros. " +
                "Ahora, para confirmar tu cuenta, por favor haz clic en el siguiente enlace:</p>" +
                "<a href='" + confirmationUrl + "' style='display: inline-block; margin: 20px 0; padding: 10px 20px; background-color: #007bff; color: #fff; font-size: 16px; text-decoration: none; border-radius: 5px;'>Confirmar cuenta</a>" +
                "<p style='font-size: 16px; line-height: 1.5em;'>Si no puedes hacer clic en el enlace, cópialo y pégalo en la barra de direcciones de tu navegador.</p>" +
                "<p style='font-size: 16px; line-height: 1.5em;'>¡Gracias por unirte a Paseo Moderno!</p>" +
                "<p style='font-size: 16px; line-height: 1.5em;'>Atentamente,<br>El equipo de Paseo Moderno</p>" +
                "</div>";

        // Construir el correo electrónico
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(recipientAddress);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }

        // Enviar el correo electrónico
        mailSender.send(mimeMessage);
    }

}
