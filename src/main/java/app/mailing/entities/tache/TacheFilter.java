package app.mailing.entities.tache;

import java.util.Date;

public class TacheFilter {
	 public String assignee;
	 public String state;
	 public Date dueDateEnd;
	public TacheFilter(String assignee, String state, Date dueDateEnd) {
		super();
		this.assignee = assignee;
		this.state = state;
		this.dueDateEnd = dueDateEnd;
	}
	 
}
