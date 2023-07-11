package app.mailing;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.mailing.metiers.DateMail;
import app.mailing.metiers.DateMailImpl;
import app.mailing.metiers.EmailService;
import app.mailing.metiers.JourFerier;
import app.mailing.metiers.JourFerierImpl;
import app.mailing.metiers.SslWarningRemover;
import app.mailing.metiers.TaskAlfrescoService;
import app.mailing.metiers.TaskAlfrescoServiceImpl;

@SpringBootApplication
public class MailingApplication implements CommandLineRunner {

	@Autowired
	private EmailService emailService;

	private DateMail dateMail = new DateMailImpl();
	private JourFerier jourFerier = new JourFerierImpl();
	private TaskAlfrescoService tastAlfServ = new TaskAlfrescoServiceImpl();
	public SslWarningRemover sslWarningRemover = new SslWarningRemover();

	Timer t = new Timer();
	int conteur = 0;

	public static void main(String[] args) {
		SpringApplication.run(MailingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		t.schedule(recursiveTaskByWeek(), date);
//		t.schedule(recursiveTaskByMonth(), date);
		
	}

	public TimerTask recursiveTaskByWeek() {
		return new TimerTask() {
			@Override
			public void run() {
				//---Programmation pour la semaine prochaine
				Date date = dateMail.dateVendrediSuivant(new Date());
				t.schedule(recursiveTaskByWeek(), date);
				System.out.println("Prochain vendredi date --- " + date);
				
				//---La tàche à faire
				
			}
		};
	}
	
	public TimerTask recursiveTaskByMonth() {
		return new TimerTask() {
			@Override
			public void run() {
				//---Programmation pour le mois prochain
				Date date = dateMail.dateMoisSuivant(new Date(), true);
				t.schedule(recursiveTaskByMonth(), date);
				System.out.println("Prochaine mois date --- " + date);
				
				//---La tàache à faire
				
			}
		};
	}

}






//Date datetest = new Date();
//datetest.setDate(datetest.getDate() + 0) ;
//datetest.setMonth(datetest.getMonth() + 0) ;

//Mail mail = new Mail("moussathiam80@gmail.com", "S",datetest, false);
//mailRepository.save(mail);


//System.out.println("DATE --- " + datetest);
//System.out.println("RESULTAT DAT TEST " + dateMail.dateVendrediSuivant(datetest));
//System.out.println("RESULTAT TEST ISFERIER -- " + datetest + "  --  " + jourFerier.isFerier(datetest));




//Contact contact = contactRepository.findById(Long.parseLong("1")).get();
//System.out.println("MailingApplication.run()");
//System.out.println(contact);
//EmailDetails emailDetails = new EmailDetails("moussathiam80@gmail.com", "test sender", "TEST", "");
//emailService.sendSimpleMail(emailDetails);




//Date date = new Date(2023 - 1900, 5, 19, 12, 13);
//t.schedule(recursiveTaskTest(), date);  
//Date date = new Date(2023 - 1900, 5, 19, 12, 13);
//t.schedule(recursiveTaskTest(), date); 



