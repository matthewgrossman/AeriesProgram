package aeriesrefresher;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


//class used to send email alerts. Mostly copied off the internet at...
// http://www.rgagnon.com/javadetails/java-0570.html
public class Email {
	
	public String alertEmail;
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final int SMTP_HOST_PORT = 465;
    private static final String SMTP_AUTH_USER = "aeriesgradechecker@gmail.com";
    private static final String SMTP_AUTH_PWD  = "KNUCKLES";
	
	public Email(String a){
		alertEmail = a;
	}
	
	public void sendMessage(String m) throws Exception{
	        Properties props = new Properties();

	        props.put("mail.transport.protocol", "smtps");
	        props.put("mail.smtps.host", SMTP_HOST_NAME);
	        props.put("mail.smtps.auth", "true");

	        Session mailSession = Session.getDefaultInstance(props);
	        mailSession.setDebug(true);
	        Transport transport = mailSession.getTransport();

	        MimeMessage message = new MimeMessage(mailSession);
	        message.setSubject("Aeries Grade Update");
	        message.setContent(m, "text/plain");

	        message.addRecipient(Message.RecipientType.TO,
	             new InternetAddress(alertEmail));

	        transport.connect
	          (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

	        transport.sendMessage(message,
	            message.getRecipients(Message.RecipientType.TO));
	        transport.close();
	 }

}
