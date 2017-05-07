package org.gestion.av.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.gestion.av.entities.Client;
import org.gestion.av.metier.AjoutDemAboMetier;
import org.gestion.av.models.AboModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/Abonnement")
@Controller
public class AboController {

	@Autowired
	private AjoutDemAboMetier aboMetier;

	public void setAboMetier(AjoutDemAboMetier pAboMetier) {
		this.aboMetier = pAboMetier;
	}

	@RequestMapping(value = "/ajoutAbo", method = RequestMethod.GET)
	public String ajoutContratClient(Model pModel) {

		pModel.addAttribute("services", Arrays.asList("Choisir service","Eau", "Basse Tension"));
		pModel.addAttribute("abonnement", new AboModel());
		return "Abonnement/ajoutAbo";
	}

	@RequestMapping(value = "/chargerTarif", method = RequestMethod.POST)
	public ModelAndView chargerTarif(String service) {
		List<String> tarifs = new ArrayList<>();
		if (service.equals("Eau")) {
			 tarifs = chargerMapTarif().get("Eau");
		} else {
			tarifs = chargerMapTarif().get("Basse Tension");

		} 
		return new ModelAndView("Abonnement/tarifAjax", "tarifs", tarifs);
	}

	@RequestMapping(value = "/creerAbonnement", method = RequestMethod.POST)
	public String SaveAbo(HttpServletRequest pRequest, @ModelAttribute(value = "abonnement") AboModel a, Model model) {
boolean bool;
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		String msg = null;
		msg = aboMetier.ajouterAbo(a.getTournee(), Long.toString(client.getId()), a.getService(), a.getTarif());
		if (msg !=null && msg.equals("oui")) {
			
			bool=true;
			model.addAttribute("checkAbo",bool);
			//return "redirect:/Reclamation/listReclamations";
		}
		else{
			bool=false;
			model.addAttribute("checkAbo",bool);
		}
		
		return "Abonnement/ajoutAbo";
	}

	private Map<String, List<String>> chargerMapTarif() {

		Map<String, List<String>> mapServiceTarifs = new HashMap<String, List<String>>();
		List<String> tarifEau = new ArrayList<String>();
		tarifEau.add("Eau domestique");
		mapServiceTarifs.put("Eau", tarifEau);

		List<String> tarifBasseTension = new ArrayList<String>();
		tarifBasseTension.add("BT domestique");
		tarifBasseTension.add("BT éclairage");
		tarifBasseTension.add("BT patente (Commerce)");
		mapServiceTarifs.put("Basse Tension", tarifBasseTension);
		return mapServiceTarifs;
	}

}
