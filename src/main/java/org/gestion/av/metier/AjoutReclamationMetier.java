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
public class AjoutReclamationMetier {
	PSR r=new PSR();
	public  String ajouterReclamation(String codec,String origine ,String  type_reclamation,String comm) {
		String a = null;
		 try {
	            // Create SOAP Connection
	            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	            // Send SOAP Message to SOAP Server
	            String url = "http://localhost:9091/Agence_virtuelle_ws/services/AjouterReclamationWS";
	            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest( codec, origine ,type_reclamation, comm), url);

	            // Process the SOAP Response
	           a= r.printSOAPResponseString(soapResponse);

	            soapConnection.close();
	        } catch (Exception e) {
	            System.err.println("Error occurred while sending SOAP Request to Server");
	            e.printStackTrace();
	        }
		return a;
	}
	private  SOAPMessage createSOAPRequest(String codec,String origine ,String  type_reclamation,String comm) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String serverURI = "http://projectWS.projectWS";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("example", serverURI);

       
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("ajouterReclamation", "example");
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("codec", "example");
        soapBodyElem1.addTextNode(codec);
        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("origine", "example");
        soapBodyElem2.addTextNode(origine);
        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("type_reclamation", "example");
        soapBodyElem3.addTextNode(type_reclamation);
        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("comm", "example");
        soapBodyElem4.addTextNode(comm);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "ajouterReclamation");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message = ");
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }
	}
