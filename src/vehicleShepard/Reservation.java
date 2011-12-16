package vehicleShepard;

import java.util.Calendar;

public class Reservation {
	
	//TODO Change service to something else, when we need that done!
	private int resID, userType, userID, typeID, service;
	private String vehicleID, fromDate, toDate, extDate;
	
	public Reservation(int resID, int userType,int userID, int typeID, String vehicleID, String fromDate, String toDate, String extDate, int service)
	{
		this.resID = resID;
		this.userType = userType;
		this.userID = userID;
		this.typeID = typeID;
		this.vehicleID = vehicleID;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.extDate = extDate;
	}
	
	/**
	 * Returns the difference between the fromDate and the extDate (extDate's default value is toDate) in days
	 * @return diffInDays The difference in days between fromDate and extDate
	 */
	public int getLength()
	{
		return 1;
	}
	
	//SIMPLE GETTERS
	
	//Basic information
	public int getResID() {
		return resID;
	}
	
	public int getUserType() {
		return userType;
	}

	public int getUserID() {
		return userID;
	}

	public int getTypeID() {
		return typeID;
	}
	
	public int getService() {
		return service;
	}

	public String getVehicleID() {
		return vehicleID;
	}
	
	//Date information
	
	//Dates as String
	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public String getExtDate() {
		return extDate;
	}
	
}
