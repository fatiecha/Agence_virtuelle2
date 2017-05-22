package org.gestion.av.controllers;




import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gestion.av.entities.Client;
import org.gestion.av.entities.Facture;
import org.gestion.av.metier.ConsulterContratsMetier;
import org.gestion.av.metier.ConsulterFacturesMetier;
import org.gestion.av.serviceImpl.GeneratePDF;
import org.gestion.av.serviceImpl.MailMail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/Facture")
@Controller
public class FactureController {
	private ConsulterFacturesMetier consulterFacturesMetier;
	private ConsulterContratsMetier consulterContratsMetier;
	private GeneratePDF generatePDF;
	private MailMail mailMail;
	
//	private static final String INTERNAL_FILE="irregular-verbs-list.pdf";
	public void setMailMail(MailMail mailMail) {
		this.mailMail = mailMail;
	}

	public void setConsulterContratsMetier(ConsulterContratsMetier consulterContratsMetier) {
		this.consulterContratsMetier = consulterContratsMetier;
	}

	public void setGeneratePDF(GeneratePDF generatePDF) {
		this.generatePDF = generatePDF;
	}

	public void setConsulterFacturesMetier(ConsulterFacturesMetier consulterFacturesMetier) {
		this.consulterFacturesMetier = consulterFacturesMetier;
	}

	@RequestMapping(value = "/simulerFacture", method = RequestMethod.GET)
	public String simulerFacture(HttpServletRequest pRequest, Model model) {
		Client c = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(c.getId())));
		model.addAttribute("facture", new Facture());
		return "Facture/simulerFacture";
	}

	@RequestMapping(value = "/simulation", method = RequestMethod.POST)
	public String simulation(HttpServletRequest pRequest, @ModelAttribute(value = "facture") Facture facture,
			Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		List<Facture> factures = new ArrayList<>();
		factures = consulterFacturesMetier.consuterFacture(Long.toString(facture.getContrat().getId()));
		model.addAttribute("factures", factures);
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		return "Facture/simulerFacture";
	}

	@RequestMapping(value = "/listFactures", method = RequestMethod.GET)
	public String listFactures(HttpServletRequest pRequest, Model model) {
		Client c = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(c.getId())));
		model.addAttribute("facture", new Facture());
		return "Facture/listFactures";
	}

	@RequestMapping(value = "/FiltreListes", method = RequestMethod.POST)
	public String filtreListFacture(HttpServletRequest pRequest, @ModelAttribute(value = "facture") Facture facture,
			Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		List<Facture> factures = new ArrayList<>();
		factures = consulterFacturesMetier.consuterFacture(Long.toString(facture.getContrat().getId()));
		model.addAttribute("factures", factures);
		model.addAttribute("emptyFacture", factures.isEmpty());
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		return "Facture/listFactures";
	}

	@RequestMapping(value = "/genererPDFEnvoyer", method = RequestMethod.POST)
	public String genererPDF(HttpServletRequest pRequest, @ModelAttribute(value = "facture") Facture facture,
			Model model,long idFacture) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		generatePDF.genererPdf(idFacture,client.getId());
		mailMail.sendMailAttachement(client.getEmail(), idFacture);
		return "Facture/listFactures";
	}
	

	@RequestMapping(value="/download/{idFacture}", method = RequestMethod.GET)
	public void download(HttpServletRequest pRequest,HttpServletResponse response,@PathVariable("idFacture") long idFacture) throws IOException {
		File file = null;
		 String EXTERNAL_FILE_PATH="C:/Users/Fatimzhra/workspace/Agence_virtuelle2/src/main/java/Pdf/Facture" + idFacture + ".pdf";
		 Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
			generatePDF.genererPdf(idFacture,client.getId());
			file = new File(EXTERNAL_FILE_PATH);
		if(!file.exists()){
			String errorMessage = "Sorry. The file you are looking for does not exist";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}
		
		String mimeType= URLConnection.guessContentTypeFromName(file.getName());
		if(mimeType==null){
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}
		
		System.out.println("mimetype : "+mimeType);
		
        response.setContentType(mimeType);
        
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() +"\""));

        
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        
        response.setContentLength((int)file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
}


