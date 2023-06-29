package app.mailing.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Mail {
	@Id @GeneratedValue
	private Long id;
	private String maildestinataire;
	private Boolean etat;
	private String message;
	@ManyToOne
	private CampagneMailing campagne;

	public Mail() {
		super();
	}

	public Mail(String maildestinataire, Boolean etat, String message,
			CampagneMailing campagne) {
		super();
		this.maildestinataire = maildestinataire;
		this.etat = etat;
		this.message = message;
		this.campagne = campagne;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMaildestinataire() {
		return maildestinataire;
	}
	public void setMaildestinataire(String maildestinataire) {
		this.maildestinataire = maildestinataire;
	}
	public Boolean getEtat() {
		return etat;
	}
	public void setEtat(Boolean etat) {
		this.etat = etat;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public CampagneMailing getCampagne() {
		return campagne;
	}

	public void setCampagne(CampagneMailing campagne) {
		this.campagne = campagne;
	}

	@Override
	public String toString() {
		return "Mail [id=" + id + ", maildestinataire=" + maildestinataire  + ", etat=" + etat + ", message=" + message + ", campagne=" + campagne + "]";
	}
	
}
