package model;

import java.util.ArrayList;

public class Member {
	private String name;
	private String personalNumber;
	private String id;
	private ArrayList<Boat> boats;		//List of all the boats that this member owns
	
	public Member() {
		
	}
	
	public Member(String name, String personalNumber, String id) {
		this.name = name;
		this.personalNumber = personalNumber;
		this.boats = new ArrayList<Boat>();
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	} 
	
	public String getPersonalNumber() {
		return personalNumber;
	}
	
	public void setPersonalNumber(String number) {
		this.personalNumber = number;
	}
	
	public String getId() {
		return id;
	}
	
	public void addBoat(Boat boat) {
		boats.add(boat);
	}

	public int getNumberOfBoats() {
		return boats.size();
	}
	
	public ArrayList<Boat> getBoats() {
		return boats;
	}
}