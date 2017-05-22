package org.gestion.av.controllers;

import javax.servlet.http.HttpServletRequest;

import org.gestion.av.entities.Client;
import org.gestion.av.metier.AjoutConCliMetier;
import org.gestion.av.metier.ConsulterContratsMetier;
import org.gestion.av.models.ConCliModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/Contrat")

@Controller
public class ContratController {
	private ConsulterContratsMetier consulterContratsMetier;
	private AjoutConCliMetier concliMetier;

	public void setConcliMetier(AjoutConCliMetier concliMetier) {
		this.concliMetier = concliMetier;
	}

	public void setConsulterContratsMetier(ConsulterContratsMetier consulterContratsMetier) {
		this.consulterContratsMetier = consulterContratsMetier;
	}

	
	@RequestMapping(value = "/listContrats", method = RequestMethod.GET)
	public String listContrats(HttpServletRequest pRequest,Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		return "Contrat/listContrats";
	}

	@RequestMapping(value = "/association", method = RequestMethod.GET)
	public String ajoutContratClient(Model model) {

		model.addAttribute("concli", new ConCliModel());
		return "Contrat/association";
	}

	@RequestMapping(value = "/associerContratClient", method = RequestMethod.POST)
	public String SaveContratClient(HttpServletRequest pRequest, @ModelAttribute(value = "concli") ConCliModel c,
			Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		String msg = null;
		boolean boolAssociation;
		msg = concliMetier.ajouterConCli(c.getNumeroContrat(),Long.toString(client.getId()),  c.getService());
		if (msg.equals("oui")) {
			boolAssociation=true;
			model.addAttribute("checkAssociation",boolAssociation);
		}
		else{
			boolAssociation=false;
			model.addAttribute("checkAssociation",boolAssociation);
		}
		return "Contrat/association";
	}
}
