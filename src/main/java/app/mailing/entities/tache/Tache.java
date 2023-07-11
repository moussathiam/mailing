package app.mailing.entities.tache;

import java.sql.Timestamp;
import java.util.Date;

import app.mailing.entities.User;
	
public class Tache {
    public Long id;
    public String name;
    public String description;
//  category
    public User assignee;
    public String created;
    public String dueDate;
    public String endDate;
//  duration
    public int priority;
    public Long parentTaskId;
    public String parentTaskName;
    public String processInstanceId;
    public String processInstanceName;
    public String processDefinitionId;
    public String processDefinitionName;
    public String processDefinitionDescription;
    public String processDefinitionKey;
    public String processDefinitionCategory;
    public int processDefinitionVersion;
    public String processDefinitionDeploymentId;
    public String formKey;
//  processInstanceStartUserId
    public Boolean initiatorCanCompleteTask;
    public Boolean adhocTaskCanBeReassigned;
    public String taskDefinitionKey;
    public String executionId;
    public Boolean memberOfCandidateGroup;
    public Boolean memberOfCandidateUsers;
    public Boolean managerOfCandidateGroup;
	public Tache() {
		super();
	}
	
	public Tache(Long id, String name, String description, User assignee, String created, String dueDate, String endDate,
			int priority, Long parentTaskId, String parentTaskName, String processInstanceId,
			String processInstanceName, String processDefinitionId, String processDefinitionName,
			String processDefinitionDescription, String processDefinitionKey, String processDefinitionCategory,
			int processDefinitionVersion, String processDefinitionDeploymentId, String formKey,
			Boolean initiatorCanCompleteTask, Boolean adhocTaskCanBeReassigned, String taskDefinitionKey,
			String executionId, Boolean memberOfCandidateGroup, Boolean memberOfCandidateUsers,
			Boolean managerOfCandidateGroup) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.assignee = assignee;
		this.created = created;
		this.dueDate = dueDate;
		this.endDate = endDate;
		this.priority = priority;
		this.parentTaskId = parentTaskId;
		this.parentTaskName = parentTaskName;
		this.processInstanceId = processInstanceId;
		this.processInstanceName = processInstanceName;
		this.processDefinitionId = processDefinitionId;
		this.processDefinitionName = processDefinitionName;
		this.processDefinitionDescription = processDefinitionDescription;
		this.processDefinitionKey = processDefinitionKey;
		this.processDefinitionCategory = processDefinitionCategory;
		this.processDefinitionVersion = processDefinitionVersion;
		this.processDefinitionDeploymentId = processDefinitionDeploymentId;
		this.formKey = formKey;
		this.initiatorCanCompleteTask = initiatorCanCompleteTask;
		this.adhocTaskCanBeReassigned = adhocTaskCanBeReassigned;
		this.taskDefinitionKey = taskDefinitionKey;
		this.executionId = executionId;
		this.memberOfCandidateGroup = memberOfCandidateGroup;
		this.memberOfCandidateUsers = memberOfCandidateUsers;
		this.managerOfCandidateGroup = managerOfCandidateGroup;
	}

}
