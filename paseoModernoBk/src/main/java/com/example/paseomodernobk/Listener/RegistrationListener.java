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
        String confirmationUrl = mailConstants.getHostAddress() + "/api/v1/auth/account/registration/validate?token=" + event.getToken();
        String message = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <style type=\"text/css\">\n" +
                "        @media screen {\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: normal;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 400;\n" +
                "                src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');\n" +
                "            }\n" +
                "\n" +
                "            @font-face {\n" +
                "                font-family: 'Lato';\n" +
                "                font-style: italic;\n" +
                "                font-weight: 700;\n" +
                "                src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        /* CLIENT-SPECIFIC STYLES */\n" +
                "        body,\n" +
                "        table,\n" +
                "        td,\n" +
                "        a {\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "        }\n" +
                "\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-lspace: 0pt;\n" +
                "            mso-table-rspace: 0pt;\n" +
                "        }\n" +
                "\n" +
                "        img {\n" +
                "            -ms-interpolation-mode: bicubic;\n" +
                "        }\n" +
                "\n" +
                "        /* RESET STYLES */\n" +
                "        img {\n" +
                "            border: 0;\n" +
                "            height: auto;\n" +
                "            line-height: 100%;\n" +
                "            outline: none;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        table {\n" +
                "            border-collapse: collapse !important;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            height: 100% !important;\n" +
                "            margin: 0 !important;\n" +
                "            padding: 0 !important;\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        /* iOS BLUE LINKS */\n" +
                "        a[x-apple-data-detectors] {\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: none !important;\n" +
                "            font-size: inherit !important;\n" +
                "            font-family: inherit !important;\n" +
                "            font-weight: inherit !important;\n" +
                "            line-height: inherit !important;\n" +
                "        }\n" +
                "\n" +
                "        /* MOBILE STYLES */\n" +
                "        @media screen and (max-width:600px) {\n" +
                "            h1 {\n" +
                "                font-size: 32px !important;\n" +
                "                line-height: 32px !important;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        /* ANDROID CENTER FIX */\n" +
                "        div[style*=\"margin: 16px 0;\"] {\n" +
                "            margin: 0 !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">\n" +
                "  <!-- TEXTO OCULTO DE ENCABEZADO -->\n" +
                "  <div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\"> ¡Nos emociona tenerte aquí! Prepárate para sumergirte en tu nueva cuenta.\n" +
                "  </div>\n" +
                "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "      <!-- LOGOTIPO -->\n" +
                "      <tr>\n" +
                "          <td bgcolor=\"#dc3545\" align=\"center\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                  <tr>\n" +
                "                      <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"> </td>\n" +
                "                  </tr>\n" +
                "              </table>\n" +
                "          </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "          <td bgcolor=\"#dc3545\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">\n" +
                "                          <h1 style=\"font-size: 48px; font-weight: 400; margin: 2;\">¡Bienvenido/a!</h1> <img src=\" https://img.icons8.com/clouds/100/000000/handshake.png\" width=\"125\" height=\"120\" style=\"display: block; border: 0px;\" />\n" +
                "                      </td>\n" +
                "                  </tr>\n" +
                "              </table>\n" +
                "          </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "          <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                          <p style=\"margin: 0;\">Estamos emocionados de que comiences. Primero, necesitas confirmar tu cuenta. Solo presiona el botón a continuación.</p>\n" +
                "                      </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"left\">\n" +
                "                          <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                              <tr>\n" +
                "                                  <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">\n" +
                "                                      <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                "                                          <tr>\n" +
                "                                              <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#dc3545\"><a href=\""+ confirmationUrl + "\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #dc3545; display: inline-block;\">Confirmar Cuenta</a></td>\n" +
                "                                          </tr>\n" +
                "                                      </table>\n" +
                "                                  </td>\n" +
                "                              </tr>\n" +
                "                          </table>\n" +
                "                      </td>\n" +
                "                  </tr> <!-- COPIAR -->\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 0px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                          <p style=\"margin: 0;\">Si eso no funciona, copia y pega el siguiente enlace en tu navegador:</p>\n" +
                "                      </td>\n" +
                "                  </tr> <!-- COPIAR -->\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                          <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #dc3545;\">https://bit.li.utlddssdstueincx</a></p>\n" +
                "                      </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 20px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                          <p style=\"margin: 0;\">Si tienes alguna pregunta, simplemente responde a este correo electrónico; siempre estamos encantados de ayudar.</p>\n" +
                "                      </td>\n" +
                "                  </tr>\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                          <p style=\"margin: 0;\">Saludos,<br>Equipo de Paseo Moderno</p>\n" +
                "                      </td>\n" +
                "                  </tr>\n" +
                "              </table>\n" +
                "          </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "          <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 30px 10px 0px 10px;\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#dc3545\" align=\"center\" style=\"padding: 30px 30px 30px 30px; border-radius: 4px 4px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\">\n" +
                "                          <h2 style=\"font-size: 20px; font-weight: 400; color: #ffffff; margin: 0;\">¿Necesitas más ayuda?</h2>\n" +
                "                          <p style=\"margin: 0;\"><a href=\"#\" target=\"_blank\" style=\"color: #fff;\">Estamos aquí para ayudarte</a></p>\n" +
                "                      </td>\n" +
                "                  </tr>\n" +
                "              </table>\n" +
                "          </td>\n" +
                "      </tr>\n" +
                "      <tr>\n" +
                "          <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">\n" +
                "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "                  <tr>\n" +
                "                      <td bgcolor=\"#f4f4f4\" align=\"left\" style=\"padding: 0px 30px 30px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 18px;\"> <br>\n" +
                "                          <p style=\"margin: 0;\">Si estos correos son molestos, no dudes en <a href=\"#\" target=\"_blank\" style=\"color: #fff; font-weight: 700;\">darte de baja</a>.</p>\n" +
                "                      </td>\n" +
                "                  </tr>\n" +
                "              </table>\n" +
                "          </td>\n" +
                "      </tr>\n" +
                "  </table>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";

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
