package org.gestion.av.metier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

import org.gestion.av.entities.Devis;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Transactional
public class ConsulterDevisMetier {

	public List<Devis> consuterDevis(String id_contrat) {
		List<Devis> Devis = new ArrayList<Devis>();
//		String s = null;
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			// Send SOAP Message to SOAP Server
			String url = "http://localhost:9091/Agence_virtuelle_ws/services/ConsulterDevisWS";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(id_contrat), url);

			// Process the SOAP Response
			Devis = printSOAPResponse(soapResponse);
			soapConnection.close();
		} catch (Exception e) {
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
		return Devis;
	}

	private SOAPMessage createSOAPRequest(String id_contrat) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "http://projectWS.projectWS";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("a0", serverURI);

		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("consulterDevis", "a0");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("id", "a0");
		soapBodyElem1.addTextNode(id_contrat);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", serverURI + "consulterDevis");

		soapMessage.saveChanges();

		/* Print the request message */
		System.out.print("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();

		return soapMessage;
	}

	private static ArrayList<Devis> printSOAPResponse(SOAPMessage soapResponse) throws Exception {
		ArrayList<Devis> rslt = new ArrayList<Devis>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
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

			String expression = "/Envelope/Body/consulterDevisResponse/return";
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < nodeList.getLength(); i++) {

				Devis dev = new Devis();
				Node nNode = nodeList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					dev.setCommentaire(eElement.getElementsByTagName("ax27:commentaire").item(0).getTextContent());

					dev.setDateS(formater.format(
							formatter.parse(eElement.getElementsByTagName("ax27:date").item(0).getTextContent())));

					dev.setMontant(
							Double.parseDouble(eElement.getElementsByTagName("ax27:montant").item(0).getTextContent()));

					dev.setId(Long.parseLong(eElement.getElementsByTagName("ax27:id").item(0).getTextContent()));
					dev.setEtat(
							eElement.getElementsByTagName("ax27:etat").item(0).getTextContent());

					rslt.add(dev);

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

		return rslt;
	}

//	private String printSOAPResponse33(SOAPMessage soapResponse) throws Exception {
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
