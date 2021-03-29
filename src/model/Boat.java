package model;

public class Boat {
	private BoatType boatType;
	private int length;
	private String id;
	
	private enum BoatType {
		Sailboat, 
		Motorsailer, 
		Canoe, 
		Other 
	}
	
	public Boat() {
		
	}
	
	public Boat(String type, int length, String id) {
		boatType = BoatType.valueOf(type);
		this.length = length;
		this.id = id;
	}
	
	public BoatType getType() {
		return boatType;
	}
	
	public void setType(String type) throws Exception {
		boatType = BoatType.valueOf(type);
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	//Method used loading the information saved from a previous session, in order to keep the same ID and not generate a new one.
	protected void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
