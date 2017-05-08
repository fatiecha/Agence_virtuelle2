package test;

import org.gestion.av.serviceImpl.AudioReclamation;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	// pr faire l'injection des dependances /av/resources/login/img/98-1.jpg}" 
	static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });
//	static MailSender mailSender= (MailSender) context.getBean("mailSender");


//    
    
    public static void main(String[] args) {
    	boolean a=AudioReclamation.recording();
    	if(a){
    		AudioReclamation.sending(4,5);
    	}
//    	MailMail mm = (MailMail) context.getBean("mailMail");
//        mm.sendMailAudio();
    }
   
}

