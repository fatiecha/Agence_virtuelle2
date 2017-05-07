package org.gestion.av.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Echeance implements Serializable{
	@Id
	
	private int ordre;
	private double montant;
	private Date dateEcheance;
	private String etat;
	private String DateS;
	@ManyToOne
	@JoinColumn(name="code_facilite")
	private Facilite facilite;
	public int getOrdre() {
		return ordre;
	}
	
	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public String getDateS() {
		return DateS;
	}

	public void setDateS(String dateS) {
		DateS = dateS;
	}

	public String getEtat() {
		return etat;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public Date getDate() {
		return dateEcheance;
	}
	public void setDate(Date date) {
		this.dateEcheance = date;
	}
	public String isEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public Facilite getFacilite() {
		return facilite;
	}
	public void setFacilite(Facilite facilite) {
		this.facilite = facilite;
	}
	public Echeance(int ordre, double montant, Date date, String etat, Facilite facilite) {
		super();
		this.ordre = ordre;
		this.montant = montant;
		this.dateEcheance = date;
		this.etat = etat;
		this.facilite = facilite;
	}
	public Echeance() {
		super();
	}
}
