package app.mailing.entities;
import app.mailing.entities.groupe.Entry2;

public class ServiceFinal {
	public String nomService;
	public GroupeFinal groupeManagers;
	public GroupeFinal groupeGestionnaire;
	public ServiceFinal() {
		super();
		groupeManagers = new GroupeFinal();
		groupeGestionnaire = new GroupeFinal();
	}
	
}
