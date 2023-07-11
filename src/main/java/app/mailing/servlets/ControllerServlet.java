package app.mailing.servlets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import app.mailing.entities.ServiceFinal;
import app.mailing.entities.User;
import app.mailing.entities.UserTaches;
import app.mailing.entities.groupe.Entry;
import app.mailing.entities.groupe.List;
import app.mailing.entities.tache.Tache;
import app.mailing.entities.tache.TacheFiltreReponse;
import app.mailing.entities.user.DetailUser;
import app.mailing.metiers.TaskAlfrescoService;
import app.mailing.metiers.TaskAlfrescoServiceImpl;
@RestController
public class ControllerServlet {
//	@Autowired
//	private CampagneMailingRepository campMailRep;
	TaskAlfrescoService taskService = new TaskAlfrescoServiceImpl();
	
	@GetMapping(path="/taches")
	public ArrayList<Tache> getTaches() {
		ArrayList<Tache> taches = taskService.getTasks();
		return taches;
	}
		
	@GetMapping(path="/groupes")
	public ArrayList<ServiceFinal> getGroupes() {
		int nbRequette = 0;
		int nbGroup = 0;
		int nbUser = 0;
		ArrayList<ServiceFinal> listeServiceFinal = new ArrayList<ServiceFinal>();
		List groupes = taskService.getGroupes();
		nbRequette++;
		for (Entry entryGroupe : groupes.entries) {
			int index = entryGroupe.entry.displayName.lastIndexOf("_");
			if(index >= 0) {
				String nomService = entryGroupe.entry.displayName.substring(0,index);
				int positionGroupe = indexOfGroupe(nomService, listeServiceFinal); 
				if(positionGroupe >= 0) {
					if(entryGroupe.entry.displayName.substring(index+1).equals("Gestionnaire")) {
						listeServiceFinal.get(positionGroupe).groupeGestionnaire.data = entryGroupe.entry;
					}
					if(entryGroupe.entry.displayName.substring(index+1).equals("Manager")) {
						listeServiceFinal.get(positionGroupe).groupeManagers.data = entryGroupe.entry;
					}
				}else {
					ServiceFinal serviceFinal = new ServiceFinal();
					serviceFinal.nomService = nomService;
					if(entryGroupe.entry.displayName.substring(index + 1).equals("Gestionnaire")) {
						serviceFinal.groupeGestionnaire.data = entryGroupe.entry;
						listeServiceFinal.add(serviceFinal);
					}
					if(entryGroupe.entry.displayName.substring(index + 1).equals("Manager")) {
						serviceFinal.groupeManagers.data = entryGroupe.entry;
						listeServiceFinal.add(serviceFinal);
					}
					
				}
			}
		}
		
		for (int i = 0; i < listeServiceFinal.size(); i++) {
			app.mailing.entities.user.List usersGes = taskService.getUsersGroupe(listeServiceFinal.get(i).groupeGestionnaire.data.id);
			nbRequette++;
			nbGroup++;
			for (app.mailing.entities.user.Entry entry : usersGes.entries) {
				DetailUser user = taskService.getDetailUser(entry.entry.id);
				nbRequette++;
				nbUser++;
				UserTaches userTaches= new UserTaches();
				userTaches.detail = user;
				listeServiceFinal.get(i).groupeGestionnaire.users.add(userTaches);
			}
			app.mailing.entities.user.List usersMan = taskService.getUsersGroupe(listeServiceFinal.get(i).groupeManagers.data.id);
			nbRequette++;
			nbGroup++;
			for (app.mailing.entities.user.Entry entry : usersMan.entries) {
				DetailUser user = taskService.getDetailUser(entry.entry.id);
				nbRequette++;
				nbUser++;
				UserTaches userTaches= new UserTaches();
				userTaches.detail = user;
				listeServiceFinal.get(i).groupeManagers.users.add(userTaches);
			}
		}
		

		for (int i = 0; i < listeServiceFinal.size(); i++) {
			int nbTacheEnCoursTotal = 0;
			int nbTacheEnClotureeTotal = 0;
			for (int j = 0; j < listeServiceFinal.get(i).groupeGestionnaire.users.size(); j++) {
				UserTaches userTache = listeServiceFinal.get(i).groupeGestionnaire.users.get(j);
				int nbTacheEncours = taskService.getTachesFilter(userTache.detail.id, "", null).numberOfElements;
				int nbTacheCloturee = taskService.getTachesFilter(userTache.detail.id, "completed", null).numberOfElements;
				int nbTacheATraiter48h = taskService.getTachesFilter(userTache.detail.id, "", new Date()).numberOfElements;
				nbRequette += 3;
				nbTacheEnCoursTotal += nbTacheEncours;
				nbTacheEnClotureeTotal += nbTacheCloturee;
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).nbTacheEncours = nbTacheEncours;
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).nbTacheCloturee = nbTacheCloturee;
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).nbTacheATraiter48h = nbTacheATraiter48h;
			}
			listeServiceFinal.get(i).groupeGestionnaire.nbTacheEnCoursTotal = nbTacheEnCoursTotal;
			listeServiceFinal.get(i).groupeGestionnaire.nbTacheEnClotureeTotal = nbTacheEnClotureeTotal;
		}
		
		System.out.println("Nb requette " + nbRequette);
		System.out.println("Nb Group " + nbGroup);
		System.out.println("Nb User " + nbUser);
		
		return listeServiceFinal;
	}
	
	
	
	@GetMapping(path="/users")
	public app.mailing.entities.user.List getUsersGroupe() {
		app.mailing.entities.user.List users = taskService.getUsersGroupe("GROUP_GG_GED_PRO_DC_Sarreguemines_Gestionnaire");
		return users;
	}
	@GetMapping(path="/dusers")
	public DetailUser getDetailUser() {
		DetailUser user = taskService.getDetailUser("");
		return user;
	}
	@GetMapping(path="/tachesfilter")
	public TacheFiltreReponse getTachesFilter() {
		TacheFiltreReponse taches = taskService.getTachesFilter("chaplet", "completed", new Date());
		return taches;
	}
	@GetMapping(path="/test")
	public String getTest() {
		ArrayList<ServiceFinal> groupes = getGroupes();
		String tmptTotal = "";
		for (ServiceFinal groupe : groupes) {
			int nbTacheEnCoursTotal = groupe.groupeGestionnaire.nbTacheEnCoursTotal;
			int nbTacheEnClotureeTotal = groupe.groupeGestionnaire.nbTacheEnClotureeTotal;
			
			String tmp = "<p style=\"color : black\">Bonjour,</p>";
			tmp += "<p>Veillez trouver ci-après quelques statistiques sur les tâches de votre service :</p>";
			tmp += "<p>";
			tmp += " - Nombre de tâche en cours : <b>"+ nbTacheEnCoursTotal +"</b></br>";
			tmp += " - Nombre de tâche clôturées : <b>"+ nbTacheEnClotureeTotal +"</b> </p>";
			tmp += "</p>";
			
			tmp += "<p>";
			tmp += " - Tâches par gestionnaire : </br>";
			tmp += "<table style=\"border-spacing : 0;\">";
				tmp += "<thead>";
					tmp += "<tr style=\"border : 1px solid red\">";
						tmp += "<th style=\"border : 1px solid #555; border-right : none\">Gestionnaire</th>";
						tmp += "<th style=\"border : 1px solid #555; border-right : none\">Tâches en cours</th>";
						tmp += "<th style=\"border : 1px solid #555; border-right : none\">Taches cloturées</th>";
						tmp += "<th style=\"border : 1px solid #555; border-right : none\">A traiter sous 48h</th>";
						tmp += "<th style=\"border : 1px solid #555\">Stock hors délai</th>";
					tmp += "</tr>";
				tmp += "</thead>";
			tmp += "<tbody>";
				for (UserTaches user : groupe.groupeGestionnaire.users) {
					tmp += "<tr style=\"border : 1px solid red\">";
						tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">"+ user.detail.firstName + " "+ user.detail.lastName + "</td>";
						tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">"+ user.nbTacheEncours + "</td>";
						tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">"+ user.nbTacheCloturee + "</td>";
						tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">"+ user.nbTacheATraiter48h + "</td>";
						tmp += "<td style=\"border : 1px solid #555; border-top : none;\">"+ user.nbTacheStockHorsDelai + "</td>";
					tmp += "</tr>";
				}
			tmp += "</tbody>";
			tmp += "</table>";
			tmp += "</p>";
			tmp += "</br></br></br></br></br>";
			tmptTotal += tmp;
		}
		
		

		

//		tmp += "<p>";
//		tmp += " - Tâches dont l'échéance est sous 48h : </br>";
//		tmp += "<table style=\"border-spacing : 0;\">";
//			tmp += "<thead>";
//				tmp += "<tr style=\"border : 1px solid red\">";
//					tmp += "<th style=\"border : 1px solid #555; border-right : none\">Action</th>";
//					tmp += "<th style=\"border : 1px solid #555; border-right : none\">Date d'échéance</th>";
//					tmp += "<th style=\"border : 1px solid #555\">Gestionnaire</th>";
//				tmp += "</tr>";
//			tmp += "</thead>";
//		tmp += "<tbody>";
//			tmp += "<tr style=\"border : 1px solid red\">";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">Completer le doc</td>";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">16/29/2023</td>";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none;\">Billon Nadage</td>";
//			tmp += "</tr>";
//			tmp += "<tr style=\"border : 1px solid red\">";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">Traiter le doc</td>";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">16/29/2023</td>";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none;\">Billon Nadage</td>";
//			tmp += "</tr>";
//			tmp += "<tr style=\"border : 1px solid red\">";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">Traiter le doc</td>";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">16/29/2023</td>";
//				tmp += "<td style=\"border : 1px solid #555; border-top : none;\">Billon Nadage</td>";
//			tmp += "</tr>";
//		tmp += "</tbody>";
//		tmp += "</table>";
//		tmp += "</p>";
		
		return tmptTotal;
	}
	
	
	
	int indexOfGroupe(String nomService, ArrayList<ServiceFinal> liste) {
		for (int i = 0; i < liste.size(); i++) {
			if(nomService.equals(liste.get(i).nomService)) {
				return i;
			}
		}
		return -1;
	}
}
