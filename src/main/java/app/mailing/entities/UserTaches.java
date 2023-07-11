package app.mailing.entities;


import java.util.ArrayList;

import app.mailing.entities.tache.Tache;
import app.mailing.entities.user.DetailUser;

public class UserTaches {
	public DetailUser detail;
	public ArrayList<Tache> taches;
	public int nbTacheEncours;
	public int nbTacheCloturee;
	public int nbTacheATraiter48h;
	public int nbTacheStockHorsDelai;
	public UserTaches() {
		super();
		taches = new ArrayList<Tache>();
		nbTacheEncours = 0;
		nbTacheCloturee = 0;
		nbTacheATraiter48h = 0;
		nbTacheStockHorsDelai = 0;
	}
	
}
