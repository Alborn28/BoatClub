package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Persistence {
	/*
	 * Save the members and boats into a file called "Memberlist.txt"
	 */
	protected void save(ArrayList<Member> members) {
		try {
			File saveFile = new File("Memberlist.txt");
			//Always create a new file. If it already exists then delete the old one and create a new
			if(!saveFile.createNewFile()) {
				saveFile.delete();
				saveFile.createNewFile();
			}
			
			FileWriter myWriter = new FileWriter(saveFile);
			//Loop through the members list and write all the members' information along with their boats to the file
			//One line represents a member or a boat. The first character on each line is either a 1 or a 2, 1 stands for member and 2 for boat
			for(int i = 0; i < members.size(); i++) {
				Member member = members.get(i);
				StringBuilder result = new StringBuilder();
				result.append("1," + member.getName() + "," + member.getPersonalNumber() + "," + member.getId() + "\n");
				if(member.getNumberOfBoats() > 0) {
					ArrayList<model.Boat> boats = member.getBoats();
					for(int j = 0; j < boats.size(); j++) {
						Boat boat = boats.get(j);
						result.append("2," + boat.getType() + "," + boat.getLength() + "," + boat.getId() + "\n");
					}
					
				}
				
				myWriter.write(result.toString());
			}
			
			myWriter.close();
		}
		
		catch(IOException e) {
			return;
		}
	}
	
	/*
	 * Load the saved information from the previous session from the Memberlist file
	 */
	protected Object[] load() {
		int highestIdNumber = 1;		//Track the highest ID number
		
		try {
			ArrayList<Member> members = new ArrayList<Member>();
			File loadFile = new File("Memberlist.txt");
			if(loadFile.exists()) {
				Scanner reader = new Scanner(loadFile);
				
				while(reader.hasNext()) {
					String[] info = reader.nextLine().split(",");
					if(Integer.parseInt(info[0]) == 1) {		//1 in the first spot means that it is a member
						Member member = new Member(info[1], info[2], info[3]);
						members.add(member);
						
						int idNumber = Integer.parseInt(member.getId());
						if(idNumber > highestIdNumber)
							highestIdNumber = idNumber;
					}
					
					else {		//If it's not a member, it is a boat 
						Boat boat = new Boat(info[1], Integer.parseInt(info[2]), info[3]);	
						members.get(members.size() - 1).addBoat(boat);		//Add the boat to the member lastly added to the system
						
						int idNumber = Integer.parseInt(boat.getId());
						if(idNumber > highestIdNumber)
							highestIdNumber = idNumber;
					}
				}
				
				reader.close();
				Object result[] = {members, (highestIdNumber + 1)};
				return result;
			}
			
			//If there is no file to load from, just return an empty arraylist
			Object result[] = {new ArrayList<Member>(), highestIdNumber};
			return result;
		}
		
		catch(FileNotFoundException e) {
			Object result[] = {new ArrayList<Member>(), highestIdNumber};
			return result;
		}
	}
}
