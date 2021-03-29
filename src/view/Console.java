package view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Console {
	private int actionChosen = 0;		//Store the request that the user has made in the form of an integer from 1-9
	private int nameOrNumber = 0;		//Determines what the user wants to change in a member, 1 for name or 2 for personalNumber
	private int typeOrLength = 0;		//Determines what the user wants to change in a boat, 1 for boat type or 2 for length
	private int compactOrVerbose = 0;	//Determines what type of list the user wants displayed, 1 for compact list and 2 for verbose list

	public void displayWelcome() {
		System.out.println("Welcome to this boat club's member registry!\n");
	}
	
	//When a request has been successfully handled, display this message
	public void displaySuccess() {
		System.out.println("Action successful!\n");
	}
	
	//When a request can't be executed due to an error, the error message is printed using this method.
	public void displayError(String error) {
		System.out.println(error + "\n");
	}
	
	//This method displays the options that the user can choose from
	public void displayActions() {
		System.out.println("Below the possible actions you can perform are listed:");
		System.out.println("1: Add member");
		System.out.println("2: Delete member");
		System.out.println("3: Change member");
		System.out.println("4: Look at a member's information");
		System.out.println("5: Register a new boat");
		System.out.println("6: Delete a boat");
		System.out.println("7: Change a boat's information");
		System.out.println("8: Show list of members");
		System.out.println("9: Quit the program");
	}
	
	//The user chooses what action he or she wants to perform
	public void makeChoice() {
		System.out.print("Please choose your action: ");
		
		//Validate the input so it is an integer between 1-9
		String temp = getInput();
		if(!validateInputInt(temp) || Integer.parseInt(temp) < 1 || Integer.parseInt(temp) > 9) {
			throw new InputMismatchException("Your choice must be an integer between 1-9!");
		}
		
		actionChosen = Integer.parseInt(temp);
	}
	
	public Boolean wantsToAddMember() {
		return actionChosen == 1;
	}
	
	public Boolean wantsToDeleteMember() {
		return actionChosen == 2;
	}
	
	public Boolean wantsToChangeMember() {
		return actionChosen == 3;
	}
	
	public Boolean wantsToViewMember() {
		return actionChosen == 4;
	}
	
	public Boolean wantsToRegisterBoat() {
		return actionChosen == 5;
	}
	
	public Boolean wantsToDeleteBoat() {
		return actionChosen == 6;
	}
	
	public Boolean wantsToChangeBoat() {
		return actionChosen == 7;
	}
	
	public Boolean wantsToListMembers() {
		return actionChosen == 8;
	}
	
	public Boolean wantsToQuit() {
		return actionChosen == 9;
	}
	
	/*
	 * This method is called if the user wants to add a member. Requests the name and personal number of the person, validates the information and then the info is returned to the controller.
	 */
	public String[] askForMemberInfo() {
		System.out.print("Please enter your name: ");
		String name = getInput();
		
		System.out.print("Please enter your personal number: ");
		String personalNumber = getInput();
		if(!validateInputInt(personalNumber))
			throw new InputMismatchException("Personal number must only contain numbers!");
		
		String[] result = {name, personalNumber};
		return result;
	}
	
	public String askForMemberId() {
		System.out.print("\nPlease enter the ID of the member in question: ");
		String id = getInput();
		return id;
	}
	
	/*
	 * When the user wants to change information in a member. First requests what information to be changed, checks that an integer has been entered.
	 * Then asks for the new information, and returns it to the controller
	 */
	public String askForNewInfoMember() {
		System.out.print("Please enter which information to change, 1 for name and 2 for personal number: ");
		String infoToChange = getInput();
		if(!validateInputInt(infoToChange))
			throw new InputMismatchException("Input must be an integer!");
		
		nameOrNumber = Integer.parseInt(infoToChange);
		
		System.out.print("\nPlease enter the new information: ");
		String newInfo = getInput();
		
		//If the personal number is to be changed, validate the new number
		if(nameOrNumber == 2) {
			if(!validateInputInt(newInfo))
				throw new InputMismatchException("Personal number must only contain numbers!");
		}
		return newInfo;
	}
	
	//This method allows the controller to find out if the user wants to change the name of the user
	public Boolean wantsToChangeMemberName() {
		return nameOrNumber == 1;
	}
	
	//This method allows the controller to find out if the user wants to change the personalNumber of the user
	public Boolean wantsToChangeMemberNumber() {
		return nameOrNumber == 2;
	}
	
	//Displays information about a specific member to the user
	public void displayMember(model.Member member) {
		String info = "Name: " + member.getName() + "   Personal Number: " + member.getPersonalNumber() + "   Number of boats: " + member.getNumberOfBoats();
		System.out.println(info);
	}
	
	/*
	 * When a boat is to be registered. The method requests boat information, makes sure that 2 pieces of information has been entered and returns it to the user.
	 */
	public String[] askForBoatInfo() {
		System.out.print("Please enter the boat type as one of the following: Sailboat, Motorsailer, Canoe, Other: ");
		String type = getInput();
		
		System.out.print("Please enter the length of the boat in feet: ");
		String length = getInput();
		
		//Check so length has been entered as a positive integer
		if(!validateInputInt(length) || Integer.parseInt(length) < 0)
			throw new InputMismatchException("Length must be entered as a positive integer!");
		
		//Return both the length and the type of the boat
		String[] result = {type, length};
		return result;
	}
	
	public String askForBoatId() {
		System.out.print("Please enter the ID of the boat in question: ");
		return getInput();
	}
	
	/*
	 * When the user wants to change information about a boat. The function asks what information to check, validates that input, 
	 * then requests the new information and returns it to controller.
	 */
	public String askForNewInfoBoat() {
		System.out.print("Please enter which information to change, 1 for boat type and 2 for length: ");
		String infoToChange = getInput();
		
		if(!validateInputInt(infoToChange))
			throw new NumberFormatException("Input must be an integer!");
		
		typeOrLength = Integer.parseInt(infoToChange);
		
		System.out.print("Please enter the new information: ");
		String info = getInput();
		
		//If length is chosen to be changed, check that an integer was entered
		if(wantsToChangeBoatLength()) {
			if(!validateInputInt(info))
				throw new NumberFormatException("Length must be entered as an integer!");
		}
		
		return info;
	}
	
	//Method for the controller to be able to check if the user wants to change the type of a boat
	public Boolean wantsToChangeBoatType() {
		return typeOrLength == 1;
	}
	
	//Method for the controller to be able to check if the user wants to change the length of a boat
	public Boolean wantsToChangeBoatLength() {
		return typeOrLength == 2;
	}
	
	/*
	 * When the user wants to see a list of all members. The user selects the type of list, the method validates the input and then passes on the request to the controller.
	 */
	public void chooseListType() {
		System.out.print("\nPlease enter the type of list you want. 1 for compact list and 2 for verbose list: ");
		String info = getInput();
		
		if(!validateInputInt(info))
			throw new NumberFormatException("Input must be an integer!");
		
		compactOrVerbose = Integer.parseInt(info);
	}
	
	public Boolean wantsCompactList() {
		return compactOrVerbose == 1;
	}
	
	public Boolean wantsVerboseList() {
		return compactOrVerbose == 2;
	}
	
	public void displayQuitMessage() {
		System.out.println("Terminating the program and saving the information...");
		System.out.println("Termination complete!");
	}
	
	/*
	 * Create the compact list by looping through the array of members and adding their information to a StringBuilder. 
	 * When the loop is done, print the list
	 */
	public void displayCompactList(Iterator<model.Member> members) {
		StringBuilder result = new StringBuilder();
		
		if(!members.hasNext())
			result.append("There are no members registered!\n");
		else {
			result.append("Members printed displaying name, ID and number of boats:\n");
			
			while(members.hasNext()) {
				model.Member temp = members.next();
				result.append(temp.getName() + "   ");
				result.append(temp.getId() + "   ");
				result.append(temp.getNumberOfBoats() + "\n");
			}
		}
		
		System.out.println(result.toString());
	}
	
	/*
	 * Create the verbose list by looping through the array of members and adding their information, 
	 * aswell as information about their boats to a StringBuilder. When the loop is done, print the list
	 */
	public void displayVerboseList(Iterator<model.Member> members) {
		StringBuilder result = new StringBuilder();
		
		if(!members.hasNext()) {	//If the iterator has no objects
			result.append("There are no members registered!\n");
		}
		
		else {
			result.append("Members printed displaying name, personal number, ID and information about their registered boats:\n");
			while(members.hasNext()) {
				model.Member temp = members.next();
				
				//Add the member information
				result.append(temp.getName() + "   ");
				result.append(temp.getPersonalNumber() + "   ");
				result.append(temp.getId() + "\n");
				
				//Add the information about the member's boats
				if(temp.getNumberOfBoats() > 0) {
					ArrayList<model.Boat> boats = temp.getBoats();
					for(int j = 0; j < boats.size(); j++) {
						model.Boat tempBoat = boats.get(j);
						result.append("\t");
						result.append(tempBoat.getType() + "   ");
						result.append("Length: " + tempBoat.getLength() + " feet   ");
						result.append("ID: " + tempBoat.getId() +  "\n");
					}
				}
			}
		}
		
		System.out.println(result.toString());
	}
	
	//Private helping method collecting the input from the user
	private String getInput() {
		Scanner scan = new Scanner(System.in);
		String string = scan.nextLine();
		return string;
	}
	
	//Private method checking if the input only contains numbers
	private Boolean validateInputInt(String s) {
		for(int i = 0; i < s.length(); i++) {
			if(!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		
		return true;
	}
}