package org.gestion.av.serviceImpl;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
public class MailMail {
	private JavaMailSender mailSender;
	private SimpleMailMessage simpleMailMessage;

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMailAttachement(String email,long idFacture) {

	   MimeMessage message = mailSender.createMimeMessage();

	   try{
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		String from = "radeema.client@gmail.com";
		helper.setFrom(from);
		helper.setTo(email);
		helper.setSubject(simpleMailMessage.getSubject());
		helper.setText("Veuillez  trouver en pièces jointes votre facture :");

		FileSystemResource file = new FileSystemResource("C:\\Users\\Fatimzhra\\Desktop\\Files\\Facture" + idFacture + ".pdf");
		helper.addAttachment(file.getFilename(), file);

	     }catch (MessagingException e) {
		throw new MailParseException(e);
	     }
	     mailSender.send(message);
         }
}