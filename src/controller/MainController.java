package controller;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class MainController {
	
	//Start the program by displaying a welcome message and loading the information from a previous session if possible
	public void start(view.Console ui, model.Registry methods) {
		ui.displayWelcome();
		methods.load();
		chooseAction(ui, methods);
	}
	
	/*
	 * This method is used to get a request of an action the user wants to perform from the view and passing the required information on to the model to do what the user requested
	 */
	public void chooseAction(view.Console ui, model.Registry methods) {
		ui.displayActions();					//Display the different actions the user can choose from
		
		try {
			ui.makeChoice();
		}
		catch(InputMismatchException e) {
			ui.displayError(e.getMessage());
		}
		
	
		if(ui.wantsToAddMember()) {
			try {
				//Get the member info from the view and pass it on to the model
				String[] info = ui.askForMemberInfo();
				String name = info[0];
				String personalNumber = info[1];
				
				methods.addMember(name, personalNumber);
				ui.displaySuccess();
			}
			catch(InputMismatchException e) {
				ui.displayError(e.getMessage());
			}
		}

		else if(ui.wantsToDeleteMember()) {
			//Get the id of the member to be deleted from the view and pass it to the model
			String id = ui.askForMemberId();
			
			try {
				methods.deleteMember(id);
				ui.displaySuccess();
			}
			
			catch(NoSuchElementException e) {
				ui.displayError(e.getMessage());
			}
		}
			
		else if(ui.wantsToChangeMember()) {
			//Get the id of the member to change, what information to change and the new information from the view and pass it to the model
			String id = ui.askForMemberId();
			
			try {
				String info = ui.askForNewInfoMember();
				
				if(ui.wantsToChangeMemberName()) {
					methods.changeMemberName(id, info);
					ui.displaySuccess();
				}
				
				else if(ui.wantsToChangeMemberNumber()) {
					methods.changeMemberName(id, info);
					ui.displaySuccess();
				}
				
				else {
					ui.displayError("Incorrect input when entering what info to change!");
				}
			}
			
			catch(InputMismatchException e) {
				ui.displayError(e.getMessage());
			}
			
			catch(NoSuchElementException e) {
				ui.displayError(e.getMessage());
			}
		}
		
		else if(ui.wantsToViewMember()) {	
			//Request the member from the model, and send it to the view who will display it to the user
			String id = ui.askForMemberId();
			
			try {
				model.Member member = methods.getMember(id);
				ui.displayMember(member);
			}
			
			catch(NoSuchElementException e) {
				ui.displayError(e.getMessage());
			}
		}
			
		else if(ui.wantsToRegisterBoat()) {		
			//Get id of the member who wants to register the boat as well as information about the boat and pass it to the model
			String id = ui.askForMemberId();
			
			try {
				String[] boatInfo = ui.askForBoatInfo();
				String type = boatInfo[0];
				int length = Integer.parseInt(boatInfo[1]);
				methods.addBoat(id, type, length);
				ui.displaySuccess();
			}
			
			catch(InputMismatchException e) {
				ui.displayError(e.getMessage());
			}
		}
				
		else if(ui.wantsToDeleteBoat()) {	
			//Get member id of the member who owns the boat and id of the boat to be deleted and pass it to model
			String id = ui.askForMemberId();
			
			String boatId = ui.askForBoatId();
			
			try {
				methods.deleteBoat(id, boatId);
				ui.displaySuccess();
			}
			
			catch(NoSuchElementException e) {
				ui.displayError(e.getMessage());
			}
		}
				
		else if(ui.wantsToChangeBoat()) {
			String id = ui.askForMemberId();
			
			String boatId = ui.askForBoatId();
			
			try {
				String newInfo = ui.askForNewInfoBoat();
				if(ui.wantsToChangeBoatType()) {
					methods.changeBoatType(id, boatId, newInfo);
					ui.displaySuccess();
				}
				
				else if(ui.wantsToChangeBoatLength()) {
					methods.changeBoatLength(id, boatId, Integer.parseInt(newInfo));
					ui.displaySuccess();
				}
				
				else
					ui.displayError("Incorrect input when entering what info to change!");
					
			}
			
			catch(NumberFormatException e) {
				ui.displayError(e.getMessage());
			}
			
			catch(NoSuchElementException e) {
				ui.displayError(e.getMessage());
			}
		}
			
		else if(ui.wantsToListMembers()) {		
			//Request what type of list the user wants from the view, then send the list of the members to the view who displays the correct list that the user chose
			ui.chooseListType();
			
			if(ui.wantsCompactList()) {
				ui.displayCompactList(methods.getMembers());
			}
			
			else if(ui.wantsVerboseList()) {
				ui.displayVerboseList(methods.getMembers());
			}
			
			else {
				ui.displayError("Incorrect input when choosing a list!");
			}
		}
		
		else if(ui.wantsToQuit()) {
			//If a quit request is made, tell the model to save all the information that has been stored and then terminate the program using return
			methods.save();
			ui.displayQuitMessage();
			return;
		}
		
		//When a request has been handled, the method calls itself to ask and wait for a new request. Does not apply if the request is a quit request, in that case the program has been terminated.
		chooseAction(ui, methods);
	}
}
