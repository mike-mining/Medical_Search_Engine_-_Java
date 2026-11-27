package Search_Engine;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class submitLink {
	Display display;
	
	public static void submitLinks(){
		Display.subSite = JOptionPane.showInputDialog("Enter the website: ");

		if(Display.subSite.length()>5){

			Properties props = new Properties();
			props.put("mail.smtp.host", "igor.gold.ac.uk");
			Session session = Session.getInstance(props, null);

			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("michaeladeniran@aol.com"));
				msg.setRecipients(Message.RecipientType.TO, "ma902ma@gold.ac.uk");
				msg.setSubject("JavaMail hello world example");
				msg.setSentDate(new Date());
				msg.setText(Display.subSite);
				Transport.send(msg);
			} catch (MessagingException e) {
				System.out.println();
				JOptionPane.showMessageDialog(null,"Send failed, exception: " + e,"Error",JOptionPane.PLAIN_MESSAGE);
			}
		} else { 
			JOptionPane.showInputDialog("Enter the website: ");
		}
	}
}
