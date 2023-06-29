package app.mailing.metiers;

import java.util.Date;

public interface JourFerier {
	Boolean isFerier(Date date);
	Date paque(int annee);
	boolean isFerierVariable(Date date);
	String dateToString(Date date);
}
