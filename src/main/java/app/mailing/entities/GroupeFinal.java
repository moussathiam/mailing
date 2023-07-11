package app.mailing.entities;

import java.util.ArrayList;

import app.mailing.entities.groupe.Entry2;


public class GroupeFinal {
	 public Entry2 data;
	 public ArrayList<UserTaches> users;
	 public int nbTacheEnCoursTotal;
	 public int nbTacheEnClotureeTotal;
	public GroupeFinal() {
		super();
		users = new ArrayList<UserTaches>();
		nbTacheEnCoursTotal = 0;
		nbTacheEnClotureeTotal = 0;
	}
	 
}
