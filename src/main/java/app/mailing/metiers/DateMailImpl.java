package app.mailing.metiers;

import java.util.Date;

public class DateMailImpl implements DateMail{
	JourFerier jourFerier = new JourFerierImpl();
	@Override
	public Date dateJourSuivant(Date dateActu) {
		dateActu.setHours(8);
		dateActu.setMinutes(0);
		dateActu.setSeconds(0);
		dateActu.setDate(dateActu.getDate() + 1);
		if(dateActu.getDay() == 0 || dateActu.getDay() == 6 || jourFerier.isFerier(dateActu)) {
			return dateJourSuivant(dateActu);
		}
		else {
			return dateActu;
		}
	}
	
	@Override
	public Date dateVendrediSuivant(Date dateActu) {
		dateActu.setHours(8);
		dateActu.setMinutes(0);
		dateActu.setSeconds(0);
		dateActu.setDate(dateActu.getDate() + 1) ;
		if( dateActu.getDay() == 5) {
			return vendrediFerier(dateActu);
		}
		else {
			return dateVendrediSuivant(dateActu);
		}
	}
	
	@Override
	public Date dateMoisSuivant(Date dateActu, Boolean first) {
		if(first) {
			dateActu.setMonth(dateActu.getMonth() + 1);
			dateActu.setDate(1);
			dateActu.setHours(8);
			dateActu.setMinutes(0);
			dateActu.setSeconds(0);
		}
		
		 if( dateActu.getDay() == 0 || dateActu.getDay() == 6 || jourFerier.isFerier(dateActu)) {
			 dateActu.setDate(dateActu.getDate() + 1);
			 return dateMoisSuivant(dateActu, false);
		 }
		 else {
			 return dateActu;
		 }
	}
	
	@Override
	public Date vendrediFerier(Date date){
		if(jourFerier.isFerier(date)) {
			date.setDate(date.getDate() - 1);
			return vendrediFerier(date);
		}else {
			return date;
		}
	}
}
