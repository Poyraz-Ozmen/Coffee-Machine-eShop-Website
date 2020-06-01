package com.eshop.eshopManagerAPI.models;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailUtil {

    public static void sendDiscountMail(String recepient,String m) throws Exception {

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
        
        Message message = prepareMessage(session,username,recepient,m);
        Transport.send(message);
        System.out.println("message send succesfully");  
    }

	private static Message prepareMessage(Session session, String username, String recepient,String m) {
		
        try {
        	Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Discount Mail");
			// we can use setText method to send raw mail instead of using htmlCode and setContent method
            message.setText("Hello," + "\n\n" + m );
			//
            
            
            
            /*
             * Discount pdf  feature won't be used
             * 
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
    
            //attached 1 --------------------------------------------
            //Change for testing
            String file = "C:\\Users\\poyraz\\Desktop\\cs308text\\discount.pdf";
            String fileName = "discount.pdf";
            messageBodyPart = new MimeBodyPart();   
            DataSource source = new FileDataSource(file);      
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
        	//------------------------------------------------------   
            message.setContent(multipart);
            *
            *
             */
            
        
           
            
            return message;
		} 
        
		 catch (Exception ex) {
			 System.out.println("message could not sent, Failure");
		}
        return null;
	}
	
	
	
	//// send invoice pdf file via mail.
	
	public static void sendInvoiceMail(String recepient) throws Exception {

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

        Message message = prepareInvoice(session,username,recepient);
        Transport.send(message);
        System.out.println("message send succesfully");
    }
	private static Message prepareInvoice(Session session, String username, String recepient) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("CS 308");
            // we can use setText method to send raw mail instead of using htmlCode and setContent method
            message.setText("Hello," + "\n\n No spam to my email, please!");
            //

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();

            //attached 1 --------------------------------------------
            String file = "C:\\Users\\poyraz\\Desktop\\cs308text\\invoice.pdf";
            String fileName = "invoice.pdf";
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            //------------------------------------------------------
            message.setContent(multipart);

            return message;
        } 

         catch (Exception ex) {
             System.out.println("message could not sent, Failure");
        }
        return null;
    }
	
	///// Delivery part almost same as discount mail
	
	
    public static void sendDeliveryMail(String recepient,String m) throws Exception {

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
        
        Message message = prepareDelivery(session,username,recepient,m);
        Transport.send(message);
        System.out.println("message send succesfully");  
    }

	private static Message prepareDelivery(Session session, String username, String recepient,String m) {
		
        try {
        	Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Delivery Status Mail");
			// we can use setText method to send raw mail instead of using htmlCode and setContent method
            message.setText("Hello," + "\n\n" + m );
            
            return message;
		} 
        
		 catch (Exception ex) {
			 System.out.println("delivery message could not sent, Failure");
		}
        return null;
	}
}
