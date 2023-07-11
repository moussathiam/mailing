package app.mailing.entities.groupe;

import java.util.ArrayList;

import app.mailing.entities.ServiceFinal;

public class ListGroupeFinal {
	public String nomGroupe;
	public ArrayList<Entry2> listManagers;
	public ArrayList<ServiceFinal> list;
	public ListGroupeFinal() {
		super();
		list = new ArrayList<ServiceFinal>();
	}
}
