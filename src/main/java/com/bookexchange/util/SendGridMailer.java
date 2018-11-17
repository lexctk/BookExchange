package com.bookexchange.util;

import com.sendgrid.*;
import java.io.IOException;

public class SendGridMailer {
	
	public static void sendEmail (String emailSender, String emailRecepient, String emailSubject, String emailContent) throws IOException {
	    Email from = new Email(emailSender);
	    String subject = emailSubject;
	    Email to = new Email(emailRecepient);
	    Content content = new Content("text/plain", emailContent);
	    
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    
	    Request request = new Request();
	    try {
	    	request.setMethod(Method.POST);
	    	request.setEndpoint("mail/send");
	    	request.setBody(mail.build());
	    	sg.api(request);
	    } catch (IOException ex) {
	    	throw ex;
	    }
	}

}
