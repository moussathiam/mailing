package app.mailing.metiers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JourFerierImpl implements JourFerier{
	List<String> ferierFix = new ArrayList<String>();

	public JourFerierImpl() {
		ferierFix.add("01/01");
		ferierFix.add("01/05");
		ferierFix.add("08/05");
		ferierFix.add("14/07");
		ferierFix.add("15/08");
		ferierFix.add("01/11");
		ferierFix.add("11/11");
		ferierFix.add("25/12");
		ferierFix.add("25/12");
		ferierFix.add("26/12");
	}

	@Override
	public Boolean isFerier(Date date) {
		int jour = date.getDate();
		int mois = date.getMonth() + 1;
		
		String jourStr = jour < 10 ?  "0" + jour : "" + jour;
		String moisStr = mois < 10 ?  "0" + mois : "" + mois;
		
		return ferierFix.contains(jourStr + "/" + moisStr) || isFerierVariable(date);
	}
	
	@Override
	public Date paque(int annee) {
		int n = annee % 19;
		int c = annee / 100;
		int u = annee % 100;
		int s = c / 4;
		int t = c % 4; 
		int p = (c + 8) / 25;
		int q = (c - p + 1) / 3;
		int e = (19*n + c - s - q + 15) % 30;
		int b = u / 4;
		int d = u % 4;
		int l = (2*t + 2*b - e - d +32) % 7;
		int h = (n + 11*e + 22*l) / 451;
		int m = (e + l - 7*h + 114) / 31;
		int j = (e + l - 7*h + 114) % 31 + 1 ;

		return new Date(annee - 1900, m - 1, j);
	}
	
	@Override
	public boolean isFerierVariable(Date date) {
		List<String> ferierVariable = new ArrayList<String>();
		Date dateFerier = paque(1900 + date.getYear());
		
		dateFerier.setDate(dateFerier.getDate() + 1);
		ferierVariable.add(dateToString(dateFerier));

		dateFerier.setDate(dateFerier.getDate() + 38);
		ferierVariable.add(dateToString(dateFerier));
		
		dateFerier.setDate(dateFerier.getDate() + 11);
		ferierVariable.add(dateToString(dateFerier));
		
		dateFerier.setDate(dateFerier.getDate() - 52);
		ferierVariable.add(dateToString(dateFerier));
		
		int jour = date.getDate();
		int mois = date.getMonth() + 1;
		String jourStr = jour < 10 ?  "0" + jour : "" + jour;
		String moisStr = mois < 10 ?  "0" + mois : "" + mois;
		
		return ferierVariable.contains(jourStr + "/" + moisStr);
	}
	
	@Override
	public String dateToString(Date date){
		int jour;
		int mois;
		String jourStr;
		String moisStr;
		jour = date.getDate();
		mois = date.getMonth() + 1;
		jourStr = jour < 10 ?  "0" + jour : "" + jour;
		moisStr = mois < 10 ?  "0" + mois : "" + mois;
		return jourStr + "/" + moisStr; 
	}
	
}

