package Email;

import base.EnumWarningType;
import base.LogManager;
import base.Main;
import base.StringUtilities;
import database.UserManufacturer;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Bailey Sostek on 4/27/17.
 */
public class EmailManager {

    public static void sendEmail(String address, String subject, String[] body){
        String to = address;//change accordingly

        //refrence the config file
        String from = Main.getConfigData("ResponceEmailAddress")+"";
        final String username = Main.getConfigData("ResponceEmailAddress")+"";
        final String password = Main.getConfigData("ResponceEmailPassword")+"";

        //This is the gmail server
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(StringUtilities.mergeStringArrayWithDelimiter(body, "\n"));

            // Send message
            Transport.send(message);

            LogManager.println("Sent message successfully...");

        } catch (MessagingException e) {
            LogManager.println(address+" is not a valid email address.", EnumWarningType.WARNING);
        }
    }
}
