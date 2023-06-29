package app.mailing.servlets;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.mailing.dao.CampagneMailingRepository;
import app.mailing.entities.CampagneMailing;
import app.mailing.entities.Mail;

@Controller
public class ControllerServlet {
	@Autowired
	private CampagneMailingRepository campMailRep;
	
	@GetMapping(path="/")
	public String getHome(Model model) {
		List<CampagneMailing> campagnes = campMailRep.findAll();
		System.out.println("LES CAMPAGNES DE MAILING");
		System.out.println(campagnes.get(0).getMails());
		model.addAttribute("nom", "moussa thiam");
		model.addAttribute("campagnes", campagnes);
		
		for (int i = 0; i < campagnes.size(); i++) {
			int nbEnvoye = 0;
			int nbNonEnvoye = 0;
			for (Mail mail : campagnes.get(i).getMails()) {
				if(mail.getEtat()) {
					nbEnvoye++;
				}else {
					nbNonEnvoye++;
				}
			}
			campagnes.get(i).setNbEnvoye(nbEnvoye);
			campagnes.get(i).setNbNonEnvoye(nbNonEnvoye);
		}
		return "accueil";
	}
	
	@GetMapping(path="/configuration")
	public String getConfiguration() {
		return "configuration";
	}
}
