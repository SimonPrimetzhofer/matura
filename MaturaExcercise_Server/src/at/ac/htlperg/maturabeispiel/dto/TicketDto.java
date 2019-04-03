package at.ac.htlperg.maturabeispiel.dto;

import java.util.Date;

public class TicketDto {
	public int id;
	public String description;
	public Date submittedOn;
	
	//Priority
	public int priorityID;
	public String priorityName;
	
	//State
	public int stateID;
	public String stateName;
	
	//User
	public int userID;
	public String lastName;
	public String firstName;
	public String userName;
}
