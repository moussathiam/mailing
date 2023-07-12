package app.mailing.entities.tache;

import java.util.Date;

public class TacheFilter {
	 public String assignee;
	 public String state;
	 public Date dueDateStart;
	 public Date dueDateEnd;
	 public Date endDateStart;
	public TacheFilter(String assignee, String state, Date dueDateStart, Date dueDateEnd, Date endDateStart) {
		super();
		this.assignee = assignee;
		this.state = state;
		this.dueDateStart = dueDateStart;
		this.dueDateEnd = dueDateEnd;
		this.endDateStart = endDateStart;
	}
}
