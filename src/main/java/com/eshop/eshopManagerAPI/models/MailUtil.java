package com.eshop.eshopManagerAPI.models;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    public static void sendMail(String recepient) throws Exception {

        final String username = "cs308mailsender@gmail.com";
        final String password = "Cs3081q2w3e4r";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // port for gmail is 587

        Session session = Session.getInstance(props,new Authenticator() {
        	@Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });
        
        Message message = prepareMessage(session,username,recepient);
        Transport.send(message);
        System.out.println("message send succesfully");  
    }

	private static Message prepareMessage(Session session, String username, String recepient) {
		
        try {
        	Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("CS 308 Test");
			// we can use setText method to send raw mail instead of using htmlCode and setContent method
            message.setText("Hello," + "\n\n No spam to my email, please!");
			
            return message;
		} 
        
		 catch (Exception ex) {
			 System.out.println("message could not sent, Failure");
		}
        return null;
	}
}
