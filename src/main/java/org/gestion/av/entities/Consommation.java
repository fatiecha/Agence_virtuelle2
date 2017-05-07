package org.gestion.av.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
@Entity

public class Consommation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8297830126055059825L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private long id;
	private String periode;
	@Column
	@Type(type="date")
	private Date date_releve;
	private int nbr_jour;
	private double index_lu;
	private double volume_consomme;
	private double volume_facture;
	private String type_lec;
	private String dateS;
	@ManyToOne
	@JoinColumn(name="code_type_lecture")
	private Type_lecture type_lecture;
	@ManyToOne
	@JoinColumn(name="code_contrat")
	private Contrat contrat;
	public Consommation() {
		super();
	}
	
	public Consommation(String periode, Date date_releve, int nbr_jour, double index_lu, double volume_consomme,
			double volume_facture, Type_lecture lecture, Contrat contrat) {
		super();
		this.periode = periode;
		this.date_releve = date_releve;
		this.nbr_jour = nbr_jour;
		this.index_lu = index_lu;
		this.volume_consomme = volume_consomme;
		this.volume_facture = volume_facture;
		this.type_lecture = lecture;
		this.contrat = contrat;
	}
	
	public String getDateS() {
		return dateS;
	}

	public void setDateS(String dateS) {
		this.dateS = dateS;
	}

	public String getType_lec() {
		return type_lec;
	}
	public void setType_lec(String type_lec) {
		this.type_lec = type_lec;
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
	public Date getDate_releve() {
		return date_releve;
	}
	public void setDate_releve(Date date_releve) {
		this.date_releve = date_releve;
	}
	public int getNbr_jour() {
		return nbr_jour;
	}
	public void setNbr_jour(int nbr_jour) {
		this.nbr_jour = nbr_jour;
	}
	public double getIndex_lu() {
		return index_lu;
	}
	public void setIndex_lu(double index_lu) {
		this.index_lu = index_lu;
	}
	public double getVolume_consomme() {
		return volume_consomme;
	}
	public void setVolume_consomme(double volume_consomme) {
		this.volume_consomme = volume_consomme;
	}
	public double getVolume_facture() {
		return volume_facture;
	}
	public void setVolume_facture(double volume_facture) {
		this.volume_facture = volume_facture;
	}
	public Type_lecture getLecture() {
		return type_lecture;
	}
	public void setLecture(Type_lecture lecture) {
		this.type_lecture = lecture;
	}
	public Contrat getContrat() {
		return contrat;
	}
	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	public Type_lecture getType_lecture() {
		return type_lecture;
	}
	public void setType_lecture(Type_lecture type_lecture) {
		this.type_lecture = type_lecture;
	}

}
