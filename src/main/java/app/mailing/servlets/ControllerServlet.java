package app.mailing.servlets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import app.mailing.entities.EmailDetails;
import app.mailing.entities.ServiceFinal;
import app.mailing.entities.User;
import app.mailing.entities.UserTaches;
import app.mailing.entities.groupe.Entry;
import app.mailing.entities.groupe.List;
import app.mailing.entities.tache.Tache;
import app.mailing.entities.tache.TacheFiltreReponse;
import app.mailing.entities.user.DetailUser;
import app.mailing.metiers.EmailService;
import app.mailing.metiers.TaskAlfrescoService;
import app.mailing.metiers.TaskAlfrescoServiceImpl;
@RestController
public class ControllerServlet {
	TaskAlfrescoService taskService = new TaskAlfrescoServiceImpl();
	@Autowired
	private EmailService emailService;
	
	@GetMapping(path="/taches")
	public ArrayList<Tache> getTaches() {
		ArrayList<Tache> taches = taskService.getTasks();
		return taches;
	}
		
	@GetMapping(path="/groupes")
	public ArrayList<ServiceFinal> getGroupes() {
		String frequenceEnvoi = "H";
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
			int nbTache48hTotal = 0;
			for (int j = 0; j < listeServiceFinal.get(i).groupeGestionnaire.users.size(); j++) {
				UserTaches userTache = listeServiceFinal.get(i).groupeGestionnaire.users.get(j);
				
				Date dateNow = new Date();
				dateNow.setHours(22);
				dateNow.setMinutes(0);
				dateNow.setSeconds(0);
				
//				dueSateStart.setDate(15);
//				dueSateStart.setMonth(5);
				
				
				Date dueDateEnd = new Date();
				dueDateEnd.setHours(22);
				dueDateEnd.setMinutes(0);
				dueDateEnd.setSeconds(0);
				dueDateEnd.setDate(dueDateEnd.getDate() + 2);
				
				Date endDateStart = new Date();
				endDateStart.setHours(22);
				endDateStart.setMinutes(0);
				endDateStart.setSeconds(0);
				if(frequenceEnvoi.equals("H")) {
					endDateStart.setDate(endDateStart.getDate() - 5);
				}else {
					endDateStart.setDate(1);
					endDateStart.setMonth(endDateStart.getMonth() - 1);
				}
				
				int nbTacheEncours = taskService.getTachesFilter(userTache.detail.id, "", null, null, null).numberOfElements;
				int nbTacheCloturee = taskService.getTachesFilter(userTache.detail.id, "completed", null, null, endDateStart).numberOfElements;
				int nbTacheHorsDelai = taskService.getTachesFilter(userTache.detail.id, "", null, dateNow, null).numberOfElements;
				ArrayList<Tache> taches48h = taskService.getTachesFilter(userTache.detail.id, "", dateNow, dueDateEnd, null).content;
				int nbTacheATraiter48h = taches48h.size();
				
				nbRequette += 3;
				nbTacheEnCoursTotal += nbTacheEncours;
				nbTacheEnClotureeTotal += nbTacheCloturee;
				nbTache48hTotal += nbTacheATraiter48h;
				
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).nbTacheEncours = nbTacheEncours;
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).nbTacheCloturee = nbTacheCloturee;
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).nbTacheATraiter48h = nbTacheATraiter48h;
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).nbTacheStockHorsDelai = nbTacheHorsDelai;
				listeServiceFinal.get(i).groupeGestionnaire.users.get(j).taches = taches48h;
			}
			listeServiceFinal.get(i).groupeGestionnaire.nbTacheEnCoursTotal = nbTacheEnCoursTotal;
			listeServiceFinal.get(i).groupeGestionnaire.nbTacheEnClotureeTotal = nbTacheEnClotureeTotal;
			listeServiceFinal.get(i).groupeGestionnaire.nbTache48hTotal = nbTache48hTotal;
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
		
		Date dateStart = new Date();
		dateStart.setDate(1);
		dateStart.setMonth(6);
		
		Date dateEnd = new Date();
//		dateEnd.setDate(1);
//		dateEnd.setMonth(0);
//		dateEnd.setDate(dateStart.getDate() + 2);
		
		TacheFiltreReponse taches = taskService.getTachesFilter("chaplet", "", dateStart, dateEnd, null);
		return taches;
	}
	
	@GetMapping(path="/test")
	public String getTest() {
		ArrayList<ServiceFinal> groupes = getGroupes();
		String objetMail = "GED - Statistiques mensuelles";
		String tmptTotal = "";
		for (ServiceFinal groupe : groupes) {
			int nbTacheEnCoursTotal = groupe.groupeGestionnaire.nbTacheEnCoursTotal;
			int nbTacheEnClotureeTotal = groupe.groupeGestionnaire.nbTacheEnClotureeTotal;
				
			String tmp = "<p>Bonjour,</p>";
			tmp += "<p>Veuillez trouver ci-après quelques statistiques sur les tâches de votre service <b>« "+ groupe.nomService +" »</b> :</p>";
			tmp += "<div style=\"margin-bottom: 15px; padding-right: 10px; padding-left: 10px\">";
				tmp += "<div> - Nombre de tâches en cours : <b>"+ nbTacheEnCoursTotal +"</b></div>";
				tmp += "<div> - Nombre de tâches clôturées : <b>"+ nbTacheEnClotureeTotal +"</b></div>";
			tmp += "</div>";
			
			tmp += "<div style=\"margin-bottom: 15px; padding-right: 10px; padding-left: 10px\">";
				tmp += "<div> - Tâches par gestionnaire : </div>";
				tmp += "<table style=\"border-spacing : 0; padding-right: 5px; padding-left: 5px\">";
					tmp += "<thead>";
						tmp += "<tr>";
							tmp += "<th style=\"border : 1px solid #555; border-right : none; padding-right : 10px; padding-left: 10px\">Gestionnaire</th>";
							tmp += "<th style=\"border : 1px solid #555; border-right : none; padding-right : 10px; padding-left: 10px\">Tâches en cours</th>";
							tmp += "<th style=\"border : 1px solid #555; border-right : none; padding-right : 10px; padding-left: 10px\">Tâches clôturées</th>";
							tmp += "<th style=\"border : 1px solid #555; border-right : none; padding-right : 10px; padding-left: 10px\">A traiter sous 48 h</th>";
							tmp += "<th style=\"border : 1px solid #555; padding-right : 10px; padding-left: 10px\">Stock hors délai</th>";
						tmp += "</tr>";
					tmp += "</thead>";
					tmp += "<tbody>";
						for (UserTaches user : groupe.groupeGestionnaire.users) {
							tmp += "<tr>";
								tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none; padding-right : 10px; padding-left: 10px\">"+ user.detail.firstName + " "+ user.detail.lastName + "</td>";
								tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none; padding-right : 10px; padding-left: 10px\">"+ user.nbTacheEncours + "</td>";
								tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none; padding-right : 10px; padding-left: 10px\">"+ user.nbTacheCloturee + "</td>";
								tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none; padding-right : 10px; padding-left: 10px\">"+ user.nbTacheATraiter48h + "</td>";
								tmp += "<td style=\"border : 1px solid #555; border-top : none; padding-right : 10px; padding-left: 10px\">"+ user.nbTacheStockHorsDelai + "</td>";
							tmp += "</tr>";
						}
					tmp += "</tbody>";
				tmp += "</table>";
			tmp += "</div>";
			
			
			if(groupe.groupeGestionnaire.nbTache48hTotal > 0) {
				tmp += "<div style=\"margin-bottom: 15px; padding-right: 10px; padding-left: 10px\">";
					tmp += "<div> - Tâches dont l'échéance est sous 48 h : </div>";
					tmp += "<table style=\"border-spacing : 0; padding-right: 5px; padding-left: 5px\">";
						tmp += "<thead>";
							tmp += "<tr>";
								tmp += "<th style=\"border : 1px solid #555; border-right : none; padding-right : 10px; padding-left: 10px\">Action</th>";
								tmp += "<th style=\"border : 1px solid #555; border-right : none; padding-right : 10px; padding-left: 10px\">Date d'échéance</th>";
								tmp += "<th style=\"border : 1px solid #555; padding-right : 10px; padding-left: 10px\">Gestionnaire</th>";
							tmp += "</tr>";
						tmp += "</thead>";
						tmp += "<tbody>";
							for (UserTaches user : groupe.groupeGestionnaire.users) {
								for (Tache tache : user.taches) {
									tmp += "<tr>";
										tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none; padding-right : 10px; padding-left: 10px\">"+ tache.name +"</td>";
										tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none; padding-right : 10px; padding-left: 10px\">"+ tache.dueDate.substring(8, 10) +"/"+ tache.dueDate.substring(5, 7) +"/"+ tache.dueDate.substring(0, 4) +"</td>";
										tmp += "<td style=\"border : 1px solid #555; border-top : none; padding-right : 10px; padding-left: 10px\">"+ tache.assignee.firstName +" "+ tache.assignee.lastName +"</td>";
									tmp += "</tr>";
								}
							}
						tmp += "</tbody>";
					tmp += "</table>";
				tmp += "</div>";
			}
			
						
			tmp += "<div style=\"margin-bottom: 10px\">Merci de ne pas répondre directement à cet email.</div>";
			tmp += "<div>"
					+ "<div>Cordialement,</div>"
					+ "<div><span style=\"font-style: italic\">La GED Alfresco</span></div>"
				+ "</div>";
			
			tmp += "<br/><br/><br/><br/>";
			tmptTotal += tmp;
			
			
				
			if(groupe.nomService.equals("GG_GED_SIN_RG_RD")) {
//				EmailDetails emailDetails = new EmailDetails("emad1142002@yahoo.fr", tmp, groupe.nomService, "");
//				emailService.sendSimpleMail(emailDetails);
				EmailDetails emailDetails = new EmailDetails("moussathiam80@gmail.com", tmp, objetMail, "");
				emailService.sendSimpleMail(emailDetails);
			}
		
			
		}
		
		return tmptTotal;
	}
	
	
	
	
	
	@GetMapping(path="/test2")
	public String getTest2() {
			String tmp = "<p style=\"color : black\">Bonjour,</p>";
			tmp += "<p>Veillez trouver ci-après quelques statistiques sur les tâches de votre service « moussa » :</p>";
			tmp += "<div style=\"margin-bottom: 15px; padding-right: 15px; padding-left: 15px\">";
				tmp += "<div> - Nombre de tâches en cours : <b>12</b></div>";
				tmp += "<div> - Nombre de tâches clôturées : <b>13</b></div>";
			tmp += "</div>";
			
			tmp += "<div style=\"margin-bottom: 15px; padding-right: 15px; padding-left: 15px\">";
			tmp += "<div> - Tâches par gestionnaire : </div>";
			tmp += "<table style=\"border-spacing : 0; padding-right: 5px; padding-left: 5px\">";
			tmp += "<thead>";
				tmp += "<tr>";
					tmp += "<th style=\"border : 1px solid #555; border-right : none\">Gestionnaire</th>";
					tmp += "<th style=\"border : 1px solid #555; border-right : none\">Tâches en cours</th>";
					tmp += "<th style=\"border : 1px solid #555; border-right : none\">Taches cloturées</th>";
					tmp += "<th style=\"border : 1px solid #555; border-right : none\">A traiter sous 48h</th>";
					tmp += "<th style=\"border : 1px solid #555\">Stock hors délai</th>";
				tmp += "</tr>";
			tmp += "</thead>";
			tmp += "<tbody>";
			
				tmp += "<tr>";
					tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none; padding-right : 10px; padding-left: 10px\">Moussa THIAM</td>";
					tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">10</td>";
					tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">13</td>";
					tmp += "<td style=\"border : 1px solid #555; border-top : none; border-right : none\">12</td>";
					tmp += "<td style=\"border : 1px solid #555; border-top : none;\">33</td>";
				tmp += "</tr>";
			
			tmp += "</tbody>";
			tmp += "</table>";
			tmp += "</div>";
			
			tmp += "<div style=\"margin-bottom: 10px\">Merci de ne pas répondre directement à cet email</div>";
			tmp += "<div>"
					+ "<div>Cordialement</div>"
					+ "<div><span style=\"font-style: italic\">La GED Alfresco</span></div>"
				+ "</div>";
			
			
//			EmailDetails emailDetails = new EmailDetails("moussathiam80@gmail.com", "test sender", "TEST", "");
//			emailService.sendSimpleMail(emailDetails);
		
		return tmp;
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
