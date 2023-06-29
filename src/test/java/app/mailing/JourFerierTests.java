package app.mailing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.mailing.metiers.JourFerier;
import app.mailing.metiers.JourFerierImpl;


class JourFerierTests {

	JourFerier jourFerier = new JourFerierImpl();
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void jourDeAn() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 0, 1)));
	}
	
	@Test
	void lundiPaque() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 3, 10)));
	}
	
	@Test
	void feteDuTravail() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 4, 1)));
	}
	
	@Test
	void victoire1945() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 4, 8)));
	}
	
	@Test
	void ascension() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 4, 18)));
	}
	
	@Test
	void pentecode() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 4, 29)));
	}
	
	@Test
	void feteNational() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 6, 14)));
	}
	
	@Test
	void assomption() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 7, 15)));
	}
	
	@Test
	void toussain() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 10, 1)));
	}
	
	@Test
	void armistice() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 10, 1)));
	}
	
	@Test
	void noel() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 11, 25)));
	}
	
	@Test
	void vendrediSaint() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 3, 7)));
	}
	
	@Test
	void deuxiemeJourNoel() {
		assertEquals(true, jourFerier.isFerier(new Date(2023 - 1900, 11, 26)));
	}
	
	@Test
	void paque() {
		Date datePaque = jourFerier.paque(2023);
		assertEquals(true, datePaque.getDate() == 9 && datePaque.getMonth() == 3);
	}

}
