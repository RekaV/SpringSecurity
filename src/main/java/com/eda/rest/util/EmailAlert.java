package com.eda.rest.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Service;

import com.eda.rest.sendgrid.SendGrid;
import com.eda.rest.sendgrid.SendGridException;

@PropertySource("classpath:db.properties")
@Service
public class EmailAlert {
	@Value("${smtp.host}")
	String host;
	@Value("${smtp.port}")
	String port;
	private Logger logger = Logger.getLogger(EmailAlert.class);
	String from = "noreply@kgfsl.com";
	public void sendEmailAlert(String to_address) {
		
		
		String msg_content="Hi,"
				+ "Your travel insurance form was successfully submitted to travel insurance agent.";
		
		try {
			sendEmail(to_address,"TRAVEL INSURANCE",msg_content);
		} catch (SendGridException e) {
			
			e.printStackTrace();
		}
		/*Properties props = new Properties();
		// props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		
		Session session = Session.getDefaultInstance(props);
		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to_address));

			message.setSubject("Testing Subject");

			message.setText("Hi,"
					+ "Your travel insurance form was successfully submitted to travel insurance agent.");

			// Send message
			Transport.send(message);

			logger.info("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}*/

	}
	
	public void sendFloralAlert(String to,String subject,String msg)
	{
		
		/*String from = "noreply@kgfsl.com";
	

		Properties props = new Properties();
		// props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		
		Session session = Session.getDefaultInstance(props);
		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));

			message.setSubject(subject);

			message.setText(msg);

			// Send message
			Transport.send(message);

			logger.info("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}*/
		
		try {
			sendEmail(to,subject,msg);
		} catch (SendGridException e) {
			
			e.printStackTrace();
		}
	}
	
	public void sendEmail(String to_address,String subject,String msg_content) throws SendGridException
	{
		 SendGrid sendgrid = new SendGrid("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 "); // recommended
		    // OR
		  //SendGrid sendgrid = new SendGrid("reka.v"," kgfsl@123");

		    SendGrid.Email email = new SendGrid.Email();

		    email.addTo(to_address);
		    email.setFrom("noreply@kgfsl.com");
		    email.setSubject(subject);
		    email.setHtml(msg_content);

		    SendGrid.Response response = sendgrid.send(email);
		    logger.info("RESPONSE:::"+response.getMessage()+"STATUS::"+response.getStatus());
	}
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
