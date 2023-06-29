package app.mailing.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import app.mailing.entities.CampagneMailing;

public interface CampagneMailingRepository extends JpaRepository<CampagneMailing, Long> {

}
