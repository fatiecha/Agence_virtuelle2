package org.gestion.av.serviceImpl;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gestion.av.dao.IAgenceDao;
import org.gestion.av.daoImpl.AgenceDaoImpl;
import org.gestion.av.entities.Client;
import org.gestion.av.metier.CountFIMetier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
 
public class CustomAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
 
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@PersistenceContext
	private EntityManager em;

	private CountFIMetier countFIMetier = new CountFIMetier();
	private AgenceDaoImpl dao = new AgenceDaoImpl();
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) throws IOException,
			ServletException {
		HttpSession pSession = request.getSession();
		
		/*Set some session variables*/
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
        Client clientConnecte = new Client();
        dao.setEm(em);
        clientConnecte = dao.getClientByEmail(authUser.getUsername());

		if (clientConnecte != null) {
			pSession.setAttribute("nbrFacture", countFIMetier.countFactureImpayees(Long.toString(clientConnecte.getId())));
			pSession.setAttribute("clientConnecte", clientConnecte);
			redirectStrategy.sendRedirect(request, response, "/Contrat/listContrats");
		}
	}
 
	protected String determineTargetUrl(Authentication authentication) {
        
		return redirectStrategy.toString();
    }
 
	public RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
 
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
}