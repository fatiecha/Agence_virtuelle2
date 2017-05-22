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
public class Paiement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private Date date;
	@ManyToOne
	@JoinColumn(name = "code_type_paiement")
	private Type_paiement type_paiement;
	@ManyToOne
	@JoinColumn(name = "code_facture")
	private Facture facture;

	public Paiement() {
		super();
	}

	public Paiement(Date date, Type_paiement type_paiement, Facture facture) {
		super();
		this.date = date;
		this.type_paiement = type_paiement;
		this.facture = facture;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Type_paiement getType_paiement() {
		return type_paiement;
	}

	public void setType_paiement(Type_paiement type_paiement) {
		this.type_paiement = type_paiement;
	}

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	

}
