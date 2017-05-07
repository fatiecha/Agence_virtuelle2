package org.gestion.av.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gestion.av.entities.Client;
import org.gestion.av.entities.Echeance;
import org.gestion.av.entities.Facilite;
import org.gestion.av.entities.Reclamation;
import org.gestion.av.metier.ConsulterContratsMetier;
import org.gestion.av.metier.ConsulterEcheancesMetier;
import org.gestion.av.metier.ConsulterFacilitesMetier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@RequestMapping(value = "/Facilites")
@Controller
public class FaciliteController {
private ConsulterFacilitesMetier consulterFacilitesMetier;
private ConsulterContratsMetier consulterContratsMetier;
private ConsulterEcheancesMetier consulterEcheancesMetier;

	public void setConsulterEcheancesMetier(ConsulterEcheancesMetier consulterEcheancesMetier) {
	this.consulterEcheancesMetier = consulterEcheancesMetier;
}

	public void setConsulterContratsMetier(ConsulterContratsMetier consulterContratsMetier) {
	this.consulterContratsMetier = consulterContratsMetier;
}

	public void setConsulterFacilitesMetier(ConsulterFacilitesMetier consulterFacilitesMetier) {
		this.consulterFacilitesMetier = consulterFacilitesMetier;
	}


	@RequestMapping(value = "/listFacilites", method = RequestMethod.GET)
	public String listFacilites(HttpServletRequest pRequest,Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		model.addAttribute("facilite", new Facilite());
		return "Facilites/listFacilites";
	}

	@RequestMapping(value = "/FiltreListes", method = RequestMethod.POST)
	public String filtreListFacilite(HttpServletRequest pRequest,@ModelAttribute(value = "facilite") Facilite facilite, Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		List<Facilite> facilites = new ArrayList<>();
		facilites = consulterFacilitesMetier.consuterFacilite(Long.toString(facilite.getContrat().getId()));
		facilites = consulterFacilitesMetier.consuterFacilite(Long.toString(facilite.getContrat().getId()));
		model.addAttribute("facilites", facilites);
		model.addAttribute("emptyFacilite",facilites.isEmpty());
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		return "Facilites/listFacilites";
	}
	@RequestMapping(value = "/listEcheances", method = RequestMethod.POST)
	public ModelAndView listEcheances(long idFacilite) {
		List<Echeance> echeances = new ArrayList<>();
		echeances=consulterEcheancesMetier.consuterEcheance(Long.toString(idFacilite));
		return new ModelAndView("Facilites/listEcheancesAjax", "echeances", echeances);
	}
}
