package org.gestion.av.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gestion.av.entities.Client;
import org.gestion.av.entities.Reclamation;
import org.gestion.av.metier.AjoutReclamationMetier;
import org.gestion.av.metier.ConsulterContratsMetier;
import org.gestion.av.metier.ConsulterReclamationsMetier;
import org.gestion.av.service.IAgenceService;
import org.gestion.av.serviceImpl.AudioReclamation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/Reclamation")
@Controller
public class ReclamationController {

	private ConsulterContratsMetier consulterContratsMetier;
	private AjoutReclamationMetier reclamationMetier;
	private ConsulterReclamationsMetier consulterReclamationsMetier;
	private IAgenceService agenceService;
	private final  AudioReclamation audioReclamation=new AudioReclamation();

	
	public void setReclamationMetier(AjoutReclamationMetier reclamationMetier) {
		this.reclamationMetier = reclamationMetier;
	}

	public void setConsulterReclamationsMetier(ConsulterReclamationsMetier consulterReclamationsMetier) {
		this.consulterReclamationsMetier = consulterReclamationsMetier;
	}

	public void setConsulterContratsMetier(ConsulterContratsMetier consulterContratsMetier) {
		this.consulterContratsMetier = consulterContratsMetier;
	}

	public void setAgenceService(IAgenceService agenceService) {
		this.agenceService = agenceService;
	}

	@RequestMapping(value = "/listReclamations", method = RequestMethod.GET)
	public String listReclamation(HttpServletRequest pRequest, Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		model.addAttribute("reclamation", new Reclamation());
		return "Reclamation/listReclamations";
	}

	@RequestMapping(value = "/FiltreListes", method = RequestMethod.POST)
	public String filtreListReclamation(HttpServletRequest pRequest,
			@ModelAttribute(value = "reclamation") Reclamation reclamation, Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		List<Reclamation> reclamations = new ArrayList<>();
		reclamations = consulterReclamationsMetier.consuterReclamations(Long.toString(reclamation.getIdcon()));
		model.addAttribute("reclamations", reclamations);
		model.addAttribute("emptyReclamation", reclamations.isEmpty());
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		return "Reclamation/listReclamations";
	}

	@RequestMapping(value = "/ajoutReclamation", method = RequestMethod.GET)
	public String ajoutReclamation(HttpServletRequest pRequest, Model model) {

		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		model.addAttribute("typesReclamation", agenceService.getlibelleTypeReclamation());
		model.addAttribute("reclamation", new Reclamation());
		return "Reclamation/ajoutReclamation";
	}

	@RequestMapping(value = "/creerReclamation", method = RequestMethod.POST)
	public String SaveReclamation(@ModelAttribute(value = "reclamation") Reclamation r, Model model) {
		String msg = null;
		boolean boolReclamation;

		msg = reclamationMetier.ajouterReclamation(Long.toString(r.getIdcon()), r.getOrigine(), r.getTypeR(),
				r.getCommentaire());
		if (msg.equals("oui")) {
			boolReclamation = true;
			model.addAttribute("checkRec", boolReclamation);
			// return "redirect:/Reclamation/listReclamations";
		} else {
			boolReclamation = false;
			model.addAttribute("checkRec", boolReclamation);
		}
		return "Reclamation/ajoutReclamation";

	}

	

	@RequestMapping(value = "/audioReclamation", method = RequestMethod.POST)
	public String envoyerAudioReclamation(HttpServletRequest pRequest, Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		 Thread t = new Thread(audioReclamation);
		  t.start();
		  audioReclamation.start();
		audioReclamation.sending(client);
		model.addAttribute("contrats", consulterContratsMetier.consulterContrats(Long.toString(client.getId())));
		model.addAttribute("typesReclamation", agenceService.getlibelleTypeReclamation());
		model.addAttribute("reclamation", new Reclamation());
		return "Reclamation/ajoutReclamation";
	}

}
