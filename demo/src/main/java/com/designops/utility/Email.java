package com.designops.utility;

import java.util.Properties;
import javax.mail.*;
import javax.mail. internet. *;
public class Email {
public static void sendEmail(String From, String To, String host, String randomPassword) {
Properties properties = System.getProperties();
properties.put("mail. smtp.starttis.enable", "true");
properties.put("mail.smtp.auth", "true");
properties.put("mail.smtp.host", "smtp.gmail.com");
properties.put("mail.smtp.port", "587");
Session session = Session.getInstance (properties, new javax.mail. Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(From, Constant.password);
}
});
try {
MimeMessage message = new MimeMessage (session);
message. setFrom(new InternetAddress(From));
message.addRecipient(Message. RecipientType.TO, new InternetAddress(To));
message.setSubject("Marketplace : User Registered Successfully");
message.setText("\nRandom Password : " + randomPassword
+ "\nPlease Reset your Password using this Random Password \n\n\nAdmin\nMarketplaceTeam");
Transport.send(message);
} catch (MessagingException mex) {
mex.printStackTrace();
}
}
}
