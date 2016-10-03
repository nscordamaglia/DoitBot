/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author u189299
 */
public class SendMail {
    
    // Recipient's email ID needs to be mentioned.
      String to = ConfigManager.getAppSetting("mailto");

      // Sender's email ID needs to be mentioned
      String from = ConfigManager.getAppSetting("mailfrom");

      // Assuming you are sending email from localhost
      String host = ConfigManager.getAppSetting("smtp");

    public void Ready (){
    
    
            // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.setProperty("mail.smtp.host", host);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try{
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject("Reporte IM");

         // Send the actual HTML message, as big as you like
         message.setContent("<h1>Reporte IM</h1>", "text/html" );
         
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
