package org.gestion.av.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.gestion.av.dao.IAgenceDao;
import org.gestion.av.entities.Client;
import org.gestion.av.entities.Consommation;
import org.gestion.av.entities.Contrat;
import org.gestion.av.entities.Demande_abonnement;
import org.gestion.av.entities.Facture;
import org.gestion.av.entities.Type_reclamation;

public class AgenceDaoImpl implements IAgenceDao {
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Type_reclamation> getlibelleTypeReclamation() {

		Query query = em.createQuery("select n from Type_reclamation n ");
		List<Type_reclamation> Type_reclamations = new ArrayList<>();

		Type_reclamations = query.getResultList();
		return Type_reclamations;

	}

	@Override
	public Client getClient(long idClient) {
		Query req = em.createQuery("select c from Client c  where id =:x");
		req.setParameter("x", idClient);
		return (Client) req.getSingleResult();
	}


	 @Override
	 public boolean updateClient(Client c) {
		 boolean bool;
		 Client cli=em.find(Client.class, c.getId());
		 String msg=verifierMDP(c.getMDP());
		 if(msg.equals("verifie")){
			cli.setNom(c.getNom());
			cli.setPrenom(c.getPrenom());
			cli.setCIN(c.getCIN());
			cli.setEmail(c.getEmail());
			cli.setTel(c.getTel());
			cli.setMDP(c.getMDP());
			bool=true;
		 }
		 else{
			 bool=false;
		 }
		 return bool;
	 }
	 public String verifierMDP(String mdp){
			int size=mdp.length();
			String msg="non verifie";
			if(size<=10 & size>=5){
				msg="verifie";
			}
			return msg;
			
		}
	 @Override
	 public Client getClientByEmail(String email){
		 Query req = em.createQuery("select c from Client c  where email =:x");
			req.setParameter("x", email);
			return (Client) req.getSingleResult();
		 
	 }
	 @SuppressWarnings("unchecked")
	@Override
	 public List<Consommation> findConsommationByIdContrat(long idContrat){
		 Query req = em.createQuery("select c from Consommation c  where code_contrat=:x");
			req.setParameter("x", idContrat);
			List<Consommation> consommations = new ArrayList<>();

			consommations = req.getResultList();
			return consommations;

		 
	 }

	@Override
	public Contrat getContratById(long idContrat) {
		Query req = em.createQuery("select c from Contrat c  where id =:x");
		req.setParameter("x", idContrat);
		return (Contrat) req.getSingleResult();
	}

	@Override
	public Facture getFactureById(long idFacture) {
		Query req = em.createQuery("select f from Facture f  where id =:x");
		req.setParameter("x", idFacture);
		return (Facture) req.getSingleResult();
	}

	@Override
	public Consommation getConsommationByIdFactureIdContrat(long idFacture, long idContrat) {
		Query req = em.createQuery("select c from Consommation c , Facture f where c.contrat.id =:x and c.periode=f.periode and f.id =:y");
		req.setParameter("x", idContrat);
		req.setParameter("y", idFacture);
		return (Consommation) req.getSingleResult();
	}

	@Override
	public Demande_abonnement getAbonnementByIdContrat(long idContrat) {
		Query req = em.createQuery("select d from Demande_abonnement d ,Contrat c where  d.id=c.demande_abonnement.id and c.id =:x");
		req.setParameter("x", idContrat);
		return (Demande_abonnement) req.getSingleResult();
	}

	public void setEm(EntityManager pEm) {
		this.em = pEm;
	}

	
}
