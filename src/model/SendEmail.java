package model;

import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendEmail {
 
	public String recipient;
	public String message;
	public String subject;
	public SendEmail(){};
	public boolean sendMessage(){
		final String username = "thechopperrr@gmail.com";
		final String password = "ggxctclzjupmheyp";
		boolean status = false;
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("thechopperrr@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(this.recipient));
			if(subject == ""){
				subject = "Email validation";
			}
			message.setSubject(subject);
			message.setText(this.message);
 
			Transport.send(message);
			status = true;
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	return status;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}