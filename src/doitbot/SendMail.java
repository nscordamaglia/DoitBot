/*
 * Maquetado para el mail
 */
package doitbot;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Nicolas Scordamaglia
 */
public class SendMail {

    // Destinatario
      String to = ConfigManager.getAppSetting("mailto");

      // Origen
      String from = ConfigManager.getAppSetting("mailfrom");

      // SMTP
      String host = ConfigManager.getAppSetting("smtp");

    public void Ready (){


            // Propiedades del sistema
      Properties properties = System.getProperties();

      // Configuranción del servidor
      properties.setProperty("mail.smtp.host", host);

      // Inicio de sesion con los valores por defecto
      Session session = Session.getDefaultInstance(properties);

      try{Creación del mensajeCreate a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Configura origen
         message.setFrom(new InternetAddress(from));

         // Configura destino
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // configura asunto
         message.setSubject("Reporte IM");

         // Envio del mail
         message.setContent("<h1>Reporting</h1>", "text/html" );

         MimeBodyPart messageBodyPart = new MimeBodyPart();
         String filename = "logs/ReporteIM.csv";
         DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(filename);
         Multipart multipart = new MimeMultipart();
         multipart.addBodyPart(messageBodyPart);
         message.setContent(multipart);

         // Send message
         Transport.send(message);
         System.out.println("Sent message successfully....");
      }catch (MessagingException mex) {
         mex.printStackTrace();

      }

    }

}
