package app.mailing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.mailing.metiers.DateMail;
import app.mailing.metiers.DateMailImpl;


class DateMailTest {
	DateMail dateMail = new DateMailImpl();

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void vendriSuivant1() {
		Date date = dateMail.dateVendrediSuivant(new Date(2023 - 1900, 5, 1));
		assertEquals(true, date.getDate() == 2 && date.getMonth() == 5);
	}
	@Test
	void vendriSuivant2() {
		Date date = dateMail.dateVendrediSuivant(new Date(2023 - 1900, 5, 2));
		assertEquals(true, date.getDate() == 9 && date.getMonth() == 5);
	}
	@Test
	void vendriSuivant3() {
		Date date = dateMail.dateVendrediSuivant(new Date(2023 - 1900, 6, 7));
		assertEquals(true, date.getDate() == 13 && date.getMonth() == 6);
	}
	@Test
	void vendriSuivant4() {
		Date date = dateMail.dateVendrediSuivant(new Date(2024 - 1900, 2, 28));
		assertEquals(true, date.getDate() == 28 && date.getMonth() == 2);
	}
	@Test
	void vendriSuivant5() {
		Date date = dateMail.dateVendrediSuivant(new Date(2025 - 1900, 3, 11));
		assertEquals(true, date.getDate() == 17 && date.getMonth() == 3);
	}
	
	
	@Test
	void moisSuivant1() {
		Date date = dateMail.dateMoisSuivant(new Date(2023 - 1900, 4, 1), true);
		assertEquals(true, date.getDate() == 1 && date.getMonth() == 5);
	}
	@Test
	void moisSuivant2() {
		Date date = dateMail.dateMoisSuivant(new Date(2023 - 1900, 5, 1), true);
		assertEquals(true, date.getDate() == 3 && date.getMonth() == 6);
	}
	@Test
	void moisSuivant3() {
		Date date = dateMail.dateMoisSuivant(new Date(2023 - 1900, 3, 1), true);
		assertEquals(true, date.getDate() == 2 && date.getMonth() == 4);
	}
	@Test
	void moisSuivant4() {
		Date date = dateMail.dateMoisSuivant(new Date(2023 - 1900, 3, 1), true);
		assertEquals(true, date.getDate() == 2 && date.getMonth() == 4);
	}
	@Test
	void moisSuivant5() {
		Date date = dateMail.dateMoisSuivant(new Date(2023 - 1900, 11, 1), true);
		assertEquals(true, date.getDate() == 2 && date.getMonth() == 0 && date.getYear() == 2024 - 1900);
	}
	
}
