package org.gestion.av.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gestion.av.entities.Client;
import org.gestion.av.entities.Devis;
import org.gestion.av.metier.ConsulterContratsMetier;
import org.gestion.av.metier.ConsulterDevisMetier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/Devis")
@Controller
public class DevisController {
	private ConsulterDevisMetier consulterDevisMetier;
	private ConsulterContratsMetier consulterContratsMetier;

	public void setConsulterContratsMetier(ConsulterContratsMetier consulterContratsMetier) {
		this.consulterContratsMetier = consulterContratsMetier;
	}

	public void setConsulterDevisMetier(ConsulterDevisMetier consulterDevisMetier) {
		this.consulterDevisMetier = consulterDevisMetier;
	}

	@RequestMapping(value = "/listDevis", method = RequestMethod.GET)
	public String listDevis(HttpServletRequest pRequest, Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		model.addAttribute("devis", new Devis());
		return "Devis/listDevis";
	}

	@RequestMapping(value = "/FiltreListes", method = RequestMethod.POST)
	public String filtreListDevis(HttpServletRequest pRequest, @ModelAttribute(value = "devis") Devis devis,
			Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		List<Devis> listDevis = new ArrayList<>();
		listDevis = consulterDevisMetier.consuterDevis(Long.toString(devis.getContrat().getId()));
		model.addAttribute("listDevis", listDevis);
		model.addAttribute("emptyDevis",listDevis.isEmpty());
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		return "Devis/listDevis";
	}
}
