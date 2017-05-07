package org.gestion.av.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gestion.av.entities.Client;
import org.gestion.av.entities.Consommation;
import org.gestion.av.metier.ConsulterConsommationsMetier;
import org.gestion.av.metier.ConsulterContratsMetier;
import org.gestion.av.service.IAgenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@RequestMapping(value = "/Consommation")
@Controller
public class ConsommationController {
private ConsulterConsommationsMetier consulterConsommationsMetier;
private ConsulterContratsMetier consulterContratsMetier;




	public void setConsulterContratsMetier(ConsulterContratsMetier consulterContratsMetier) {
	this.consulterContratsMetier = consulterContratsMetier;
}

	public void setConsulterConsommationsMetier(ConsulterConsommationsMetier consulterConsommationsMetier) {
		this.consulterConsommationsMetier = consulterConsommationsMetier;
	}

	@RequestMapping(value = "/listConsommations", method = RequestMethod.GET)
	public String listConsommations(HttpServletRequest pRequest,Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		model.addAttribute("consommation", new Consommation());
		return "Consommation/listConsommations";
	}
	

	@RequestMapping(value = "/FiltreListes", method = RequestMethod.POST)
	public String filtreListConsommation(HttpServletRequest pRequest,@ModelAttribute(value = "consommation") Consommation consommation, Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		List<Consommation> consommations = new ArrayList<>();
		consommations = consulterConsommationsMetier.consuterConsommations(Long.toString(consommation.getContrat().getId()));
		model.addAttribute("consommations", consommations);
		model.addAttribute("emptyConsommation",consommations.isEmpty());
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		return "Consommation/listConsommations";
	}
}
