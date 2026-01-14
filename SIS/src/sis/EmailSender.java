package sis;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import javax.activation.*;


public class EmailSender {
    
    
    public static boolean send(String to, String subject, String msg) {
        final String sender2 = "janinduac@gmail.com";
        final String appPassword2 = "glqxhedfgmtjbtqz"; // Gmail App Password
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");
            p.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session s = Session.getInstance(p,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(sender2, appPassword2);
                        }
                    });

            Message m = new MimeMessage(s);
            m.setFrom(new InternetAddress(sender2));
            m.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            m.setSubject(subject);
            m.setText(msg);

            Transport.send(m);
            return true;

        } catch (MessagingException e) {
            System.out.println(e);
        }
        return false;
    }
             }
