package org.gestion.av.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.gestion.av.dao.IAgenceDao;
import org.gestion.av.entities.Client;
import org.gestion.av.entities.Consommation;
import org.gestion.av.entities.Contrat;
import org.gestion.av.entities.Demande_abonnement;
import org.gestion.av.entities.Facture;
import org.gestion.av.entities.Type_reclamation;
import org.gestion.av.service.IAgenceService;
import org.springframework.transaction.annotation.Transactional;

import com.couchbase.client.java.document.json.JsonObject;

@Transactional
public class AgenceServiceImpl implements IAgenceService {
	private IAgenceDao dao;

	
	public void setDao(IAgenceDao dao) {
		this.dao = dao;
	}

	

	@Override
	public List<Type_reclamation> getlibelleTypeReclamation() {
		return dao.getlibelleTypeReclamation();
	}

	@Override
	public Client getClient(long idClient) {

		return dao.getClient(idClient);
	}



	@Override
	public boolean updateClient(Client c) {
		
		return dao.updateClient(c);
	}
	@Override
	public Client getClientByEmail(String email){
		return dao.getClientByEmail(email);
	}
	@Override
	public String findConsommationByIdContrat(long idContrat){
		String ret= "[";
		List<Consommation> l = new ArrayList<>();
		l = dao.findConsommationByIdContrat(idContrat);
//		System.out.println("[");
		for (int i = 0; i < l.size(); i++) {
			Consommation r = l.get(i);
			JsonObject consommationJson1 = JsonObject.empty().put("volumeConsomme",
					r.getVolume_consomme()).put("periode", r.getPeriode());
//			System.out.println(consommationJson1 + ",");
			ret = ret+consommationJson1 + ",";

		}
//		System.out.println("]");
		ret = ret+"]";
		String[] ary = ret.split("");
		ary[ary.length - 2]="";
		StringBuilder builder = new StringBuilder();
		for(String s : ary) {
		    builder.append(s);
		}
		ret= builder.toString();
//		char[] rettab = ret.toCharArray();
//		rettab[rettab.length - 2]='\0';
//		ret = rettab.toString();
//		System.out.println(ret);
		return ret;

	}



	@Override
	public Contrat getContratById(long idContrat) {
		
		return dao.getContratById(idContrat);
	}



	@Override
	public Facture getFactureById(long idFacture) {
		return dao.getFactureById(idFacture);
	}



	@Override
	public Consommation getConsommationByIdFactureIdContrat(long idFacture, long idContrat) {
		return dao.getConsommationByIdFactureIdContrat(idFacture, idContrat);
	}



	@Override
	public Demande_abonnement getAbonnementByIdContrat(long idContrat) {
		return dao.getAbonnementByIdContrat(idContrat);
	}
}
