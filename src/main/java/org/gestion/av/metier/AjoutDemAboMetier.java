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
public class AjoutDemAboMetier {
	static PSR r=new PSR();

	public   String ajouterAbo(String tournee, String code_client,String service,String tarif) {
		String a = null;
		 try {
	            // Create SOAP Connection
	            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	            // Send SOAP Message to SOAP Server
	            String url = "http://localhost:9091/Agence_virtuelle_ws/services/AjouterDemAboWS";
	            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(tournee,code_client,service,tarif), url);

	            // Process the SOAP Response
	           a= r.printSOAPResponseString(soapResponse);

	            soapConnection.close();
	        } catch (Exception e) {
	            System.err.println("Error occurred while sending SOAP Request to Server");
	            e.printStackTrace();
	        }
		return a;
	}
	private   SOAPMessage createSOAPRequest(String st_tournee, String st_code_client,String st_service,String st_tarif) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://projectWS.projectWS";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

       
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("ajouterDem_Abo", "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("tournee", "example");
        soapBodyElem1.addTextNode(st_tournee);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("code_client", "example");
        soapBodyElem2.addTextNode(st_code_client);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("service", "example");
        soapBodyElem3.addTextNode(st_service);
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("tarif", "example");
        soapBodyElem4.addTextNode(st_tarif);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "ajouterDem_Abo");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
	
}
