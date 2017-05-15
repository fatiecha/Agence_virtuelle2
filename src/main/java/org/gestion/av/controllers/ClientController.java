package org.gestion.av.controllers;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.gestion.av.entities.Client;
import org.gestion.av.metier.AjoutClientMetier;
import org.gestion.av.metier.ConnexionMetier;
import org.gestion.av.metier.CountFIMetier;
import org.gestion.av.service.IAgenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClientController {
	@Autowired
	private CountFIMetier countFIMetier;
	private AjoutClientMetier clientMetier;
	private ConnexionMetier connexionMetier;
	private IAgenceService agenceService;
	private MailSender mailSender;


	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setConnexionMetier(ConnexionMetier connexionMetier) {
		this.connexionMetier = connexionMetier;
	}

	public void setCountFIMetier(CountFIMetier countFIMetier) {
		this.countFIMetier = countFIMetier;
	}

	public void setClientMetier(AjoutClientMetier clientMetier) {
		this.clientMetier = clientMetier;
	}

	public void setAgenceService(IAgenceService agenceService) {
		this.agenceService = agenceService;
	}

	@RequestMapping(value = "/inscriptionClient", method = RequestMethod.GET)
	public String ajoutClient(Model model) {
		model.addAttribute("client", new Client());
		return "inscriptionClient";
	}

	@RequestMapping(value = "/sinscrire", method = RequestMethod.POST)
	public String Save(@ModelAttribute(value = "client") Client c, Model model) {
		String msg;
		msg = clientMetier.ajoutClient(c.getNom(), c.getPrenom(), c.getCIN(), c.getEmail(), c.getTel(), c.getMDP());
		sendMailInscription(c);
		model.addAttribute("checkClt", msg);
		return "inscriptionClient";
	}

	@RequestMapping(value = "/seConnecter", method = RequestMethod.POST)
	public String login(HttpServletRequest pRequest, Model model, @ModelAttribute(value = "client") Client cli) {

		HttpSession pSession = pRequest.getSession();
		int idClientConnecte;

		idClientConnecte = connexionMetier.seConnecter(cli.getEmail(), cli.getMDP());
		if (idClientConnecte != 0) {
			pSession.setAttribute("nbrFacture", countFIMetier.countFactureImpayees(Long.toString(idClientConnecte)));
			pSession.setAttribute("clientConnecte", agenceService.getClient(idClientConnecte));
			return "redirect:/Contrat/listContrats";
		} else {
			model.addAttribute("checkInfo", true);
		}

		return "connexionClient";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String seConnecter(Model model) {
		model.addAttribute("client", new Client());
		return "connexionClient";
	}

	@RequestMapping(value = "/login_error", method = RequestMethod.GET)
	public String erreurConnexion(Model model) {
		model.addAttribute("client", new Client());
		model.addAttribute("checkInfo", true);
		return "connexionClient";
	}
	
	@RequestMapping(value = "/updateClient", method = RequestMethod.GET)
	public String updateClient(HttpServletRequest pRequest, Model model) {
		Client client = (Client) pRequest.getSession().getAttribute("clientConnecte");
		model.addAttribute("client", client);
		model.addAttribute("MDP", client.getMDP());
		return "updateClient";
	}

	@RequestMapping(value = "/majClient", method = RequestMethod.POST)
	public String majClient(@ModelAttribute(value = "client") Client client, Model model) {
		boolean bool;
		bool = agenceService.updateClient(new Client(client.getId(), client.getNom(), client.getPrenom(),
				client.getCIN(), client.getEmail(), client.getTel(), client.getMDP()));
		model.addAttribute("checkUpdateClt", bool);
		return "updateClient";
	}

	public void sendMailInscription(Client c) {
		SimpleMailMessage message = new SimpleMailMessage();
		String from = "radeema.client@gmail.com";
		String subject = "BIENVENU " + c.getPrenom() + " DANS VOTRE ESPACE CLIENT RADEEMA";
		message.setFrom(from);
		message.setTo(c.getEmail());
		message.setSubject(subject);

		message.setText("Votre informations ===> CIN : " + c.getCIN() + " , MOT DE PASSE : " + c.getMDP());

		mailSender.send(message);
	}

	@RequestMapping(value = "/mdpOublie", method = RequestMethod.GET)
	public String mdpoublie(Model model) {
		model.addAttribute("client", new Client());
		return "mdpOublie";
	}

	@RequestMapping(value = "/envoyerMDP", method = RequestMethod.POST)
	public String envoyerMDP(@ModelAttribute(value = "client") Client client, Model model) {
		Client cli = new Client();
		try {
			cli = agenceService.getClientByEmail(client.getEmail());
			sendMailMdpOublie(cli);
			model.addAttribute("confirmationEnvoiMDP", true);

		
		} catch (NoResultException e) {
			model.addAttribute("confirmationEnvoiMDP", false);

		}
		return "mdpOublie";
	}

	public void sendMailMdpOublie(Client c) {
		SimpleMailMessage message = new SimpleMailMessage();
		String from = "radeema.client@gmail.com";
		String subject = "BIENVENU " + c.getPrenom() + " DANS VOTRE ESPACE CLIENT RADEEMA";
		message.setFrom(from);
		message.setTo(c.getEmail());
		message.setSubject(subject);
		message.setText("Votre mot de passe: " + c.getMDP());
		mailSender.send(message);
	}

}
