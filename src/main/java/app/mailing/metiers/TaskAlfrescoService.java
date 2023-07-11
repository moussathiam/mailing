package app.mailing.metiers;

import java.util.ArrayList;
import java.util.Date;

import app.mailing.entities.groupe.List;
import app.mailing.entities.tache.Tache;
import app.mailing.entities.tache.TacheFiltreReponse;
import app.mailing.entities.user.DetailUser;

public interface TaskAlfrescoService {
	ArrayList<Tache> getTasks();
	List getGroupes();
	app.mailing.entities.user.List getUsersGroupe(String groupe_id);
	DetailUser getDetailUser(String user_id);
	TacheFiltreReponse getTachesFilter(String assignee, String state, Date date);
}
