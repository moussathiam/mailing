package app.mailing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import app.mailing.entities.Mail;

public interface MailRepository extends JpaRepository<Mail, Long> {

}
