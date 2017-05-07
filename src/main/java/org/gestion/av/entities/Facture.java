package org.gestion.av.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;
@Entity
public class Facture implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String periode;
	private String dateS;
	private String type_fac;
	private String date_exigibiliteS;
	private double montant;
	private double solde;
	private boolean etat;
	@Column
	@Type(type="date")
	private Date date_exigibilite;
	private Date date;
	@ManyToOne
	@JoinColumn(name="code_type_facture")
	private Type_facture type_facture;
	@ManyToOne
	@JoinColumn(name="code_contrat")
	private Contrat contrat;
	@OneToMany(mappedBy="facture")
	private List<Paiement> paiements;
	
	@ManyToOne
	@JoinColumn(name="code_facilite")
	private Facilite facilite;
	
	public Facture() {
		super();
	}

	public Facture(String periode, double montant, double solde, boolean etat, Date date_exigibilite, Date date,
			Type_facture type_facture, Contrat contrat) {
		super();
		this.periode = periode;
		this.montant = montant;
		this.solde = solde;
		this.etat = etat;
		this.date_exigibilite = date_exigibilite;
		this.date = date;
		this.type_facture = type_facture;
		this.contrat = contrat;
	}

	public String getType_fac() {
		return type_fac;
	}

	public void setType_fac(String type_fac) {
		this.type_fac = type_fac;
	}

	public String getDateS() {
		return dateS;
	}

	public void setDateS(String dateS) {
		this.dateS = dateS;
	}

	public String getDate_exigibiliteS() {
		return date_exigibiliteS;
	}

	public void setDate_exigibiliteS(String date_exigibiliteS) {
		this.date_exigibiliteS = date_exigibiliteS;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPeriode() {
		return periode;
	}
	public void setPeriode(String periode) {
		this.periode = periode;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public double getSolde() {
		return solde;
	}
	public void setSolde(double solde) {
		this.solde = solde;
	}
	public Date getDate_exigibilite() {
		return date_exigibilite;
	}
	public void setDate_exigibilite(Date date_exigibilite) {
		this.date_exigibilite = date_exigibilite;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Type_facture getType_facture() {
		return type_facture;
	}
	public void setType_facture(Type_facture type_facture) {
		this.type_facture = type_facture;
	}
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	public List<Paiement> getPaiements() {
		return paiements;
	}
	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}
	public Facilite getFacilite() {
		return facilite;
	}
	public void setFacilite(Facilite facilite) {
		this.facilite = facilite;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	
	
	
	
	
	

	
	
	
	
}
