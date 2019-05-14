import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.AfterSuite;

public class SendMailBySite {	
	

	
	public static void main(String[]args) throws IOException {

	    //	Calendar calendar = Calendar.getInstance();
		//	SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
	        final String username = "roshan_mankani@persistent.com";
	        final String password = "R@shan1234567890";
	              String attachmentPath = "D:\\Roshan Framework\\IBV\\src\\main\\java\\com\\test\\automation\\ibvAutomation\\report\\test14_05_2019_05_58_43.html";
	              String attachmentName = "test14_05_2019_05_58_43.html";
	              System.out.println("Roshan Mankani " + attachmentPath);
	              System.out.println("Roshan Mankani " + attachmentName);
	              String[] to={"roshan_mankani@persistent.com"};
	              String[] cc={"dilip_shrivastava@persistent.com"};
	              String[] bcc={};
	              String text = "Please find the reports attached.\n\n Regards\nWebMaster";
	              String subject = "Automation Test Reports";
	
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "outlook.office365.com");
	        props.put("mail.smtp.port", "587");
	
	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });        
	        
	
	        try {
	            Message message = new MimeMessage(session);	            
	            message.setFrom(new InternetAddress("roshan_mankani@persistent.com"));
	            
	            for(int i=0;i<to.length;i++){
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(to[i]));
	            }
	            
	            for(int i=0;i<cc.length;i++){
	                message.setRecipients(Message.RecipientType.CC,
	                    InternetAddress.parse(cc[i]));
	                }
	            message.setSubject(subject);
	         // Create the message part
	            BodyPart messageBodyPart = new MimeBodyPart();

	            // Now set the actual message
	            messageBodyPart.setText(text);

	            // Create a multipar message
	            Multipart multipart = new MimeMultipart();

	            // Set text message part
	            multipart.addBodyPart(messageBodyPart);

	            // Part two is attachment
	            messageBodyPart = new MimeBodyPart();	           
	            DataSource source = new FileDataSource(attachmentPath);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(attachmentName);
	            multipart.addBodyPart(messageBodyPart);        
	            message.setContent(multipart);
	            // Code
	            
	           
	            Transport.send(message);
	            System.out.println("Mail Sent Successfully");
	
	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
    }
}