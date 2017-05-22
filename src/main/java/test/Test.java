package test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	// pr faire l'injection des dependances /av/resources/login/img/98-1.jpg}" 
	static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });
//	static MailSender mailSender= (MailSender) context.getBean("mailSender");


//    
    
//    public static void main(String[] args) {
//    	boolean a=AudioReclamation.recording();
//    	Client c=new Client("a","a","a","a","a","a");
//    	c.setId(7);
//    	if(a){
//    		AudioReclamation.sending(c);
//    	}
////    	MailMail mm = (MailMail) context.getBean("mailMail");
////        mm.sendMailAudio();
//    }
   
}

