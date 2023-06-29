package app.mailing.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class CampagneMailing {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	@Transient
	private String dateStr;
	private String frequence;
	@Transient
	private int nbEnvoye;
	@Transient
	private int nbNonEnvoye;
	@OneToMany(mappedBy="campagne")
	private List<Mail> mails = new ArrayList<Mail>();
	
	public CampagneMailing() {
		super();
	}
	
	public CampagneMailing(Date date, String dateStr, String frequence, int nbEnvoye, int nbNonEnvoye,
			List<Mail> mails) {
		super();
		this.date = date;
		this.dateStr = dateStr;
		this.frequence = frequence;
		this.nbEnvoye = nbEnvoye;
		this.nbNonEnvoye = nbNonEnvoye;
		this.mails = mails;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDateStr() {
		return jourStr(date.getDay()) + " le " + date.getDate() + " " + moisStr(date.getMonth()) + " " + (1900+date.getYear()) + " à " + date.getHours() + "h";
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	public String getFrequence() {
		return frequence;
	}
	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}
	public int getNbEnvoye() {
		return nbEnvoye;
	}
	public void setNbEnvoye(int nbEnvoye) {
		this.nbEnvoye = nbEnvoye;
	}
	public int getNbNonEnvoye() {
		return nbNonEnvoye;
	}
	public void setNbNonEnvoye(int nbNonEnvoye) {
		this.nbNonEnvoye = nbNonEnvoye;
	}
	public List<Mail> getMails() {
		return mails;
	}
	public void setMails(List<Mail> mails) {
		this.mails = mails;
	}
	
	@Override
	public String toString() {
		return "CampagneMailing [id=" + id + ", date=" + date + ", dateStr=" + dateStr + ", frequence=" + frequence
				+ ", nbEnvoye=" + nbEnvoye + ", nbNonEnvoye=" + nbNonEnvoye  + "]";
	}

	String jourStr(int jour) {
		if(jour == 1) {
			return "lundi";
		}
		switch (jour) {
		case 0: { return "Dimanche"; }
		case 1: { return "Lundi"; }
		case 2: { return "Mardi"; }
		case 3: { return "Mercredi"; }
		case 4: { return "Jeudi"; }
		case 5: { return "Vendredi"; }
		case 6: { return "Samdi"; }
		default:throw new IllegalArgumentException("Unexpected value: " + jour); 
	}
	}
	
	String moisStr(int mois) {
		switch (mois) {
			case 0: { return "janvier"; }
			case 1: { return "février"; }
			case 2: { return "mars"; }
			case 3: { return "avril"; }
			case 4: { return "mai"; }
			case 5: { return "juin"; }
			case 6: { return "juillet"; }
			case 7: { return "août"; }
			case 8: { return "septembre"; }
			case 9: { return "octobre"; }
			case 10: { return "novembre"; }
			case 11: { return "décembre"; }
			default:throw new IllegalArgumentException("Unexpected value: " + mois); 
		}
	}
	
	
}
