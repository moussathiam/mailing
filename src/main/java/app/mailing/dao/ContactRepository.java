package app.mailing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import app.mailing.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

}
