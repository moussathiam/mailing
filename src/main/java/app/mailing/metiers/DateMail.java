package app.mailing.metiers;

import java.util.Date;

public interface DateMail {
	Date dateJourSuivant(Date dateActu);
	Date dateVendrediSuivant(Date dateActu);
	Date dateMoisSuivant(Date dateActu, Boolean first);
	Date vendrediFerier(Date date);
}
