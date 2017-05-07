package org.gestion.av.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class Compte  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private double mt_impaye;
	private double mt_exigible;
	private double credit;
	private double provision;
	@OneToOne
	
	private Contrat contrat;
	private long idContrat;
	
	
	public long getIdContrat() {
		return idContrat;
	}
	public void setIdContrat(long idContrat) {
		this.idContrat = idContrat;
	}
	public double getMt_impaye() {
		return mt_impaye;
	}
	public void setMt_impaye(double mt_impaye) {
		this.mt_impaye = mt_impaye;
	}
	public double getMt_exigible() {
		return mt_exigible;
	}
	public void setMt_exigible(double mt_exigible) {
		this.mt_exigible = mt_exigible;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getProvision() {
		return provision;
	}
	public void setProvision(double provision) {
		this.provision = provision;
	}
	public Compte() {
		super();
	}
	public Compte(double mt_impaye, double mt_exigible, double credit, double provision) {
		super();
		this.mt_impaye = mt_impaye;
		this.mt_exigible = mt_exigible;
		this.credit = credit;
		this.provision = provision;
	}
	
	
}
