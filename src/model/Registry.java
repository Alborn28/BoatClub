package model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Registry {
	//An arraylist for temporarily storing the members, when the program terminates the information is stored in a file
	private ArrayList<model.Member> members = new ArrayList<model.Member>();
	
	private int idCounter = 1;	//A counter responsible for creating unique ID:s for all the registered members and boats
	
	public Registry() {
		members = new ArrayList<model.Member>();
	}
	
	/*
	 * A new member is registered and stored in the arraylist.
	 */
	public void addMember(String name, String personalNumber) {
		members.add(new model.Member(name, personalNumber, Integer.toString(idCounter++)));
		return;
	}
	
	/*
	 * The entered id is checked so it belongs to a member. If the member exists, he is deleted from the registry. If not, an exception is thrown to the controller.
	 */
	public void deleteMember(String id) {
		if(memberExists(id)) {
			for(int i = 0; i < members.size(); i++) {
				model.Member member = members.get(i);
				if(member.getId().equals(id)) {
					members.remove(i);
					return;
				}
			}
		}
		
		else {
			throw new NoSuchElementException("Error! A member with that ID does not exist!");
		}
	}
	
	/*
	 *First the id of the member is checked, in that case edit the member's name. If the member does not exist throw an exception to the controller.
	 */
	public void changeMemberName(String id, String info) {
		if(memberExists(id)) {
			model.Member member = pickOutMember(id);
			member.setName(info);
			return;
		}
		
		throw new NoSuchElementException("Error! A member with that ID does not exist!");
	}
	
	/*
	 *First the id of the member is checked, in that case edit the member's personal number. If the member does not exist throw an exception to the controller.
	 */
	public void changeMemberPersonalNumber(String id, String info) {
		if(memberExists(id)) {
			model.Member member = pickOutMember(id);
			member.setPersonalNumber(info);
			return;
		}
		
		throw new NoSuchElementException("Error! A member with that ID does not exist!");
	}
	
	/*
	 * The member with the entered id, if it exists, a copy of the member is returned.
	 */
	public Member getMember(String id) {
		if(memberExists(id)) {
			model.Member member = pickOutMember(id);
			Member result = new Member(member.getName(), member.getPersonalNumber(), member.getId());
			ArrayList<Boat> boats = member.getBoats();
			for(int i = 0; i < boats.size(); i++)
				result.addBoat(boats.get(i));
			return result;
		}
		
		else
			throw new NoSuchElementException("Error! A member with that ID does not exist!");
	}

	public void addBoat(String id, String type, int length) {
		if(memberExists(id)) {
			model.Member member = pickOutMember(id);
			try {
				member.addBoat(new model.Boat(type, length, Integer.toString(idCounter++)));
				return;
			}
			
			catch(Exception e) {
				throw new InputMismatchException("Boat type must be entered as either Sailboat, Motorsailer, Canoe or Other!");
			}
		}
		
		throw new NoSuchElementException("Error! A member with that ID does not exist!");
	}
	
	/*
	 * If the user wants to delete a boat. First check if a member with the entered id exists, if it does then loop through that member's boats
	 * and check if the entered boatId corresponds to one of his or her owned boats. If it does, delete that boat.
	 */
	public void deleteBoat(String memberId, String boatId) {
		if(memberExists(memberId)) {
			model.Member member = pickOutMember(memberId);
			ArrayList<Boat> boats = member.getBoats();
			
			if(boats.size() > 0) {
				Boat boat = new Boat();
				for(int i = 0; i < boats.size(); i++) {
					boat = boats.get(i);
					if(boat.getId().equals(boatId)) {
						boats.remove(i);
						return;
					}
				}
				
				//If the boat was not found, send error back to the user
				throw new NoSuchElementException("Error! The boat does not exist or is not owned by this member!");
			}
			
			throw new NoSuchElementException("Error! The boat does not exist or is not owned by this member!");
		}
		
		throw new NoSuchElementException("Error! A member with that ID does not exist!");
	}
	
	/*
	 * If the user wants to change a boat's type. First check if a member with the entered id exists, if it does then loop through that member's boats
	 * and check if the entered boatId corresponds to one of his or her owned boats. If it does, change the type of that boat
	 */
	public void changeBoatType(String memberId, String boatId, String info) {
		if(memberExists(memberId)) {
			model.Member member = pickOutMember(memberId);
			ArrayList<Boat> boats = member.getBoats();
			
			if(boats.size() > 0) {
				Boat boat = new Boat();
				for(int i = 0; i < boats.size(); i++) {
					boat = boats.get(i);
					if(boat.getId().equals(boatId)) {
						try {
							boat.setType(info);
							return;
						}
						catch(Exception e) {
							throw new InputMismatchException("Boat type must be entered as either Sailboat, Motorsailer, Canoe or Other!");
						}
					}
				}
				
				throw new NoSuchElementException("Error! The boat does not exist or is not owned by this member!");
			}
			
			throw new NoSuchElementException("Error! The boat does not exist or is not owned by this member!");
		}
		
		throw new NoSuchElementException("Error! A member with that ID does not exist!");
	}
	
	/*
	 * If the user wants to change a boat's length. First check if a member with the entered id exists, if it does then loop through that member's boats
	 * and check if the entered boatId corresponds to one of his or her owned boats. If it does, change the length of that boat
	 */
	public void changeBoatLength(String memberId, String boatId, int info) {
		if(memberExists(memberId)) {
			model.Member member = pickOutMember(memberId);
			ArrayList<Boat> boats = member.getBoats();
			
			if(boats.size() > 0) {
				Boat boat = new Boat();
				for(int i = 0; i < boats.size(); i++) {
					boat = boats.get(i);
					if(boat.getId().equals(boatId)) {
							boat.setLength(info);
							return;

					}
				}
				
				throw new NoSuchElementException("Error! The boat does not exist or is not owned by this member!");
			}
			
			throw new NoSuchElementException("Error! The boat does not exist or is not owned by this member!");
		}
		
		throw new NoSuchElementException("Error! A member with that ID does not exist!");
	}
	
	//Send back an iterator over the members in the system in order for the view to not be able to change anything in the members list
	public Iterator<Member> getMembers() {
		return members.iterator();
	}
	
	//Private helper method to check if a requested member exists in the system
	private Boolean memberExists(String id) {
		for(model.Member member : members) {
			if(member.getId().equals(id)) {
				return true;
			}
		}
		
		return false;
	}
		
	//Private helper method to pick out a member based on ID
	private model.Member pickOutMember(String id) {
		model.Member member = new model.Member();
		for(int i = 0; i < members.size(); i++) {
			member = members.get(i);
			if(member.getId().equals(id)) {
				return member;
			}
		}
		
		return member;
	}
	
	/*
	 * Method for saving the information when quitting the program
	 */
	public void save() {
		Persistence persistence = new Persistence();
		persistence.save(members);
	}
	
	/*
	 * Method for loading the information from a previous session when starting the program
	 */
	public void load() {
		Persistence persistence = new Persistence();
		Object[] result = persistence.load();
		members = (ArrayList<Member>) result[0];
		idCounter = (int) result[1];		//Set the idCounter to the value it was when the information was saved to the file
	}
}
