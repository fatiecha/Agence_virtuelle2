package org.gestion.av.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
@Entity
public class Client implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String nom;
	private String prenom;
	private String CIN;
	private String email;
	private String tel;
	private String MDP;
	@OneToMany(mappedBy="client")
	private List<Demande_abonnement> demande_abonnements;
	@ManyToMany
	@JoinTable(name="Con_Cli",joinColumns=
			@JoinColumn(name="CODE_CLI"),inverseJoinColumns=
			@JoinColumn(name="CODE_CON"))
	private List<Contrat> contrats;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public Client(String email, String mDP) {
		super();
		this.email = email;
		MDP = mDP;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getCIN() {
		return CIN;
	}
	public void setCIN(String cIN) {
		CIN = cIN;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMDP() {
		return MDP;
	}
	public void setMDP(String mDP) {
		MDP = mDP;
	}
	
	
	public List<Demande_abonnement> getDemande_abonnements() {
		return demande_abonnements;
	}
	public void setDemande_abonnements(List<Demande_abonnement> demande_abonnements) {
		this.demande_abonnements = demande_abonnements;
	}
	
	public List<Contrat> getContrats() {
		return contrats;
	}
	public void setContrats(List<Contrat> contrats) {
		this.contrats = contrats;
	}
	public Client() {
		super();
	}
	public Client(String nom, String prenom, String cIN, String email, String tel, String mDP) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		CIN = cIN;
		this.email = email;
		this.tel = tel;
		MDP = mDP;
	}
	public Client(long id, String nom, String prenom, String cIN, String email, String tel, String mDP) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		CIN = cIN;
		this.email = email;
		this.tel = tel;
		MDP = mDP;
	}

	}
