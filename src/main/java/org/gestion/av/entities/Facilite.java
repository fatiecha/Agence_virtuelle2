package org.gestion.av.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Facilite implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String etat;
	private double montant;
	@ManyToOne
	@JoinColumn(name="code_contrat")
	private Contrat contrat;
	@OneToMany(mappedBy="facilite")
	private List<Echeance> echeances;
	@OneToMany(mappedBy="facilite")
	private List<Facture> factures;
	
	public Facilite() {
		super();
	}
	
	public Facilite(String etat, double montant, Contrat contrat) {
		super();
		this.etat = etat;
		this.montant = montant;
		this.contrat = contrat;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public List<Echeance> getEcheances() {
		return echeances;
	}

	public void setEcheances(List<Echeance> echeances) {
		this.echeances = echeances;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public List<Facture> getFactures() {
		return factures;
	}

	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}

	
}
