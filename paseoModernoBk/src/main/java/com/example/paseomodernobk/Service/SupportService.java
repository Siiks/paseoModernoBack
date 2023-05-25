package com.example.paseomodernobk.Service;

import com.example.paseomodernobk.Entity.SupportEntity;
import com.example.paseomodernobk.Repository.SupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class SupportService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SupportRepository supportRepository;

    public void sendConfirmationEmail(SupportEntity supportEntity) {
        this.supportRepository.save(supportEntity);

        SimpleMailMessage messageToSender = new SimpleMailMessage();
        messageToSender.setTo(supportEntity.getEmail());
        messageToSender.setSubject("Confirmación de mensaje recibido");
        messageToSender.setText("¡Hola " + supportEntity.getNombre() + "!\n\nGracias por tu mensaje. Lo hemos recibido correctamente.");

        mailSender.send(messageToSender);

        // Enviar copia del mensaje a tu dirección de correo electrónico
        String yourEmail = "daw09.2022@gmail.com";
        SimpleMailMessage messageToYou = new SimpleMailMessage();
        messageToYou.setTo(yourEmail);
        messageToYou.setSubject("Nuevo mensaje recibido");
        messageToYou.setText("Has recibido un nuevo mensaje de:\n\nNombre: " + supportEntity.getNombre() + "\nEmail: " + supportEntity.getEmail() + "\nMensaje: " + supportEntity.getMensaje());

        mailSender.send(messageToYou);
    }
}
