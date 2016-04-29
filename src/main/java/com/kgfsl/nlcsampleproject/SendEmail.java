package com.kgfsl.nlcsampleproject;

import com.eda.rest.sendgrid.SendGrid;
import com.eda.rest.sendgrid.SendGridException;

public class SendEmail {

	public static void main(String[] args) throws SendGridException {
	   SendGrid sendgrid = new SendGrid("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 "); // recommended
	    // OR
	  //SendGrid sendgrid = new SendGrid("reka.v"," kgfsl@123");

	    SendGrid.Email email = new SendGrid.Email();

	    email.addTo("aravindgrand2@gmail.com");
	    email.setFrom("rekahoney.honey@gmail.com");
	    email.setSubject("Sending with SendGrid is Fun");
	    email.setHtml("and easy to do anywhere, even with Java");

	    SendGrid.Response response = sendgrid.send(email);
	    System.out.println("RESPONSE:::"+response.getMessage()+"STATUS::"+response.getStatus());
		
	/*String from = "noreply@kgfsl.com";
		

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.sendgrid.net");
		props.put("mail.smtp.port", 587);

		
		//Session session = Session.getDefaultInstance(props);
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("OIJ8cfMUS0","ggtrh01NkNqn4201");
					}
				});

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(from));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("reka.v@kgfsl.com"));

			message.setSubject("Hi.....");

			message.setText("Hi TEST");

			// Send message
			Transport.send(message);
			
			System.out.println("SEND SUCCESSFULLY....");


	}catch(Exception e)
		{
		e.printStackTrace();
		}*/
}
}
