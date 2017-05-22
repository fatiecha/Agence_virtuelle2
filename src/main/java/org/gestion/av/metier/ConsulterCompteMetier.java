package org.gestion.av.metier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.gestion.av.entities.Compte;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Transactional
public class ConsulterCompteMetier {

	public Compte consuterCompte(String id_contrat) {
		Compte compte = new Compte();
//		String s = null;

		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = "http://localhost:9091/Agence_virtuelle_ws/services/ConsulterCompteWS";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(id_contrat), url);

			// Process the SOAP Response
			compte = printSOAPResponse(soapResponse);
			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
		return compte;
	}

	private static SOAPMessage createSOAPRequest(String id_contrat) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "http://projectWS.projectWS";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("a0", serverURI);

		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("consulterCompte", "a0");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("id", "a0");
		soapBodyElem1.addTextNode(id_contrat);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "consulterCompte");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	private Compte printSOAPResponse(SOAPMessage soapResponse) throws Exception {
		Compte dev = new Compte();
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Source sourceContent = soapResponse.getSOAPPart().getContent();
			StreamResult sr = new StreamResult(System.out);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			sr.setOutputStream(out);
			transformer.transform(sourceContent, sr);
			sr.setOutputStream(out);
			InputStream is = new ByteArrayInputStream(out.toByteArray());

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;

			dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();

			XPath xPath = XPathFactory.newInstance().newXPath();

			String expression = "/Envelope/Body/consulterCompteResponse/return";
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node nNode = nodeList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					dev.setCredit(
							Double.parseDouble(eElement.getElementsByTagName("ax21:credit").item(0).getTextContent()));
					dev.setMt_exigible(Double
							.parseDouble(eElement.getElementsByTagName("ax21:mt_exigible").item(0).getTextContent()));
					dev.setMt_impaye(Double
							.parseDouble(eElement.getElementsByTagName("ax21:mt_impayé").item(0).getTextContent()));
					dev.setProvision(Double
							.parseDouble(eElement.getElementsByTagName("ax21:provision").item(0).getTextContent()));
					dev.setIdContrat(
							Long.parseLong(eElement.getElementsByTagName("ax21:contrat_id").item(0).getTextContent()));
//					dev.set(
//							Long.parseLong(eElement.getElementsByTagName("ax21:contrat_id").item(0).getTextContent()));
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();

		} catch (SAXException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return dev;
	}

//	private  String printSOAPResponse33(SOAPMessage soapResponse) throws Exception {
//		String rslt = null;
//
//		TransformerFactory transformerFactory = TransformerFactory.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		Source sourceContent = soapResponse.getSOAPPart().getContent();
//		StreamResult sr = new StreamResult(System.out);
//
//		transformer.transform(sourceContent, sr);
//
//		return rslt;
//
//	}

}
