package vehicleShepard;

public class Reservation {
	
	//TODO Chance service to something else, when we need that done!
	private int resID, userID, typeID, fromDateDay, fromDateMonth, fromDateYear, toDateDay, toDateMonth, toDateYear, extDateDay, extDateMonth, extDateYear, service;
	private String vehicleID;
	
	public Reservation(int resID, int userID, int typeID, String vehicleID, java.sql.Date fromDate, java.sql.Date toDate, java.sql.Date extDate, int service)
	{
		this.resID = resID;
		this.userID = userID;
		this.typeID = typeID;
		this.vehicleID = vehicleID;
		this.fromDateDay = fromDate.getDay();
		this.fromDateMonth = fromDate.getMonth();
		this.fromDateYear = fromDate.getYear();
		this.toDateDay = toDate.getDay();
		this.toDateMonth = toDate.getMonth();
		this.toDateYear = toDate.getYear();
		this.extDateDay = extDate.getDay();
		this.extDateMonth = extDate.getMonth();
		this.extDateYear = extDate.getYear();
	}

	///////////
	//GETTERS//
	///////////
	
	public int getResID() {
		return resID;
	}

	public int getUserID() {
		return userID;
	}

	public int getTypeID() {
		return typeID;
	}

	public int getFromDateDay() {
		return fromDateDay;
	}

	public int getFromDateMonth() {
		return fromDateMonth;
	}

	public int getFromDateYear() {
		return fromDateYear;
	}

	public int getToDateDay() {
		return toDateDay;
	}

	public int getToDateMonth() {
		return toDateMonth;
	}

	public int getToDateYear() {
		return toDateYear;
	}

	public String getVehicleID() {
		return vehicleID;
	}

}
