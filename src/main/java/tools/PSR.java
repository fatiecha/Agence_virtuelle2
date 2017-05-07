package tools;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class PSR {
	public  String printSOAPResponseString(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        result.setOutputStream(out);
        transformer.transform(sourceContent, result);
        String s =out.toString();
        s=s.substring(s.indexOf("<ns:return>")+11);
       s=s.substring(0,s.indexOf("</ns:return>") );
        
		return s;
    }
	public  int printSOAPResponseInt(SOAPMessage soapResponse) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source sourceContent = soapResponse.getSOAPPart().getContent();
        System.out.print("\nResponse SOAP Message = ");
        StreamResult result = new StreamResult(System.out);
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        result.setOutputStream(out);
        transformer.transform(sourceContent, result);
        String s =out.toString();
        s=s.substring(s.indexOf("<ns:return>")+11);
        s=s.substring(0,s.indexOf("</ns:return>") );
        int a=Integer.parseInt(s);
		return a;
    }
	
	
}
