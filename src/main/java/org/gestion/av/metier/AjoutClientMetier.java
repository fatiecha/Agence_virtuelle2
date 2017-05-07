package org.gestion.av.metier;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.springframework.transaction.annotation.Transactional;

import tools.PSR;
@Transactional
public class AjoutClientMetier {
	static PSR r=new PSR();
	public   String ajoutClient(String s, String s1, String s2, String s3, String s4, String s5) {
		String a = null;
		 try {
	            // Create SOAP Connection
	            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	            // Send SOAP Message to SOAP Server
	            String url = "http://localhost:9091/Agence_virtuelle_ws/services/AjouterClientWS";
	            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest2(s,s1,s2,s3,s4,s5), url);

	            // Process the SOAP Response
	           a= r.printSOAPResponseString(soapResponse);

	            soapConnection.close();
	        } catch (Exception e) {
	            System.err.println("Error occurred while sending SOAP Request to Server");
	            e.printStackTrace();
	        }
		return a;
	}
	
	private   SOAPMessage createSOAPRequest2(String s, String s1, String s2, String s3, String s4, String s5) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://projectWS.projectWS";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);
      
       
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("add", "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("s", "example");
        soapBodyElem1.addTextNode(s);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("s1", "example");
        soapBodyElem2.addTextNode(s1);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("s2", "example");
        soapBodyElem3.addTextNode(s2);
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("s3", "example");
        soapBodyElem4.addTextNode(s3);
        SOAPElement soapBodyElem5 = soapBodyElem.addChildElement("s4", "example");
        soapBodyElem5.addTextNode(s4);
        SOAPElement soapBodyElem6 = soapBodyElem.addChildElement("s5", "example");
        soapBodyElem6.addTextNode(s5);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "add");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
	

}
