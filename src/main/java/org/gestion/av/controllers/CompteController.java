package org.gestion.av.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gestion.av.entities.Client;
import org.gestion.av.entities.Compte;
import org.gestion.av.entities.Consommation;
import org.gestion.av.entities.Reclamation;
import org.gestion.av.metier.ConsulterCompteMetier;
import org.gestion.av.metier.ConsulterContratsMetier;
import org.gestion.av.service.IAgenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@RequestMapping(value="/Compte")
@Controller
public class CompteController {
private ConsulterCompteMetier consulterCompteMetier;
private ConsulterContratsMetier consulterContratsMetier;

public void setConsulterContratsMetier(ConsulterContratsMetier consulterContratsMetier) {
	this.consulterContratsMetier = consulterContratsMetier;
}

public void setConsulterCompteMetier(ConsulterCompteMetier consulterCompteMetier) {
	this.consulterCompteMetier = consulterCompteMetier;
}
@RequestMapping(value = "/consultation",method=RequestMethod.GET)
public String consulterCompte(HttpServletRequest pRequest,Model model) {
	Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
	model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
	model.addAttribute("compte", new Compte());
	model.addAttribute("afficherTab", false);
	return "Compte/consultation";
}
@RequestMapping(value = "/detailCompte", method = RequestMethod.POST)
public String detailCompte(HttpServletRequest pRequest,@ModelAttribute(value = "compte") Compte compte, Model model) {
	Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
	Compte cmp=new Compte();
	cmp = consulterCompteMetier.consuterCompte(Long.toString(compte.getIdContrat()));
	model.addAttribute("compte", cmp);
	model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
	model.addAttribute("afficherTab", true);
	return "Compte/consultation";
}





}
