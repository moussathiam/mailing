package app.mailing.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Contact {
	@Id @GeneratedValue
	private Long id;
	private String prenom;
	private String nom;
	private String lien;
	public Contact() {
		super();
	}
	public Contact(String prenom, String nom, String lien) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.lien = lien;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getLien() {
		return lien;
	}
	public void setLien(String lien) {
		this.lien = lien;
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", lien=" + lien + "]";
	}
	
}
