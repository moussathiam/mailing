package app.mailing.metiers;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import app.mailing.entities.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@EnableScheduling
public class EmailServiceImpl implements EmailService {
	
	@Autowired private JavaMailSender javaMailSender;
	@Value("${spring.mail.username}") private String sender;

	private int counteur = 0; 
	
	// TO SEND A SIMPLE EMAIL
	@Override
	public String sendSimpleMail(EmailDetails details) {
        try {
        	
            SimpleMailMessage mailMessage = new SimpleMailMessage();
 
            mailMessage.setFrom("moussathiam80@gmail.com");
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
 
            javaMailSender.send(mailMessage);
            System.out.println("ENVOYER-------------------");
            return "Mail Sent Successfully...";
        } catch (Exception e) {
        	System.out.println("NON ENVOYER-------------------" + e);
            return "Error while Sending Mail";
        }
	}

	// TO SEND AN EMAIL WITH ATTACHEMENT
	@Override
	public String sendMailWithAttachment(EmailDetails details) {
		// Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
 
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
 
            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
 
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
	}
	
	@Override
//	@Scheduled(cron="*/10 * * * * *")
    public void doSomething() {
		System.out.println("change ---- " + counteur + LocalDateTime.now());
		counteur = counteur + 1;
    }
	
	
	

}
