package vehicleShepard;

import java.util.Date;

public class Reservation {
	
	//TODO Change service to something else, when we need that done!
	private int resID, userID, typeID, fromDateDay, fromDateMonth, fromDateYear, toDateDay, toDateMonth, toDateYear, extDateDay, extDateMonth, extDateYear, service;
	private String vehicleID;
	private java.sql.Date fromDate, toDate, extDate;
	
	public Reservation(int resID, int userID, int typeID, String vehicleID, java.sql.Date fromDate, java.sql.Date toDate, java.sql.Date extDate, int service)
	{
		this.resID = resID;
		this.userID = userID;
		this.typeID = typeID;
		this.vehicleID = vehicleID;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.extDate = extDate;
		this.fromDateDay = fromDate.getDay();
		this.fromDateMonth = fromDate.getMonth();
		this.fromDateYear = fromDate.getYear();
		this.toDateDay = toDate.getDay();
		this.toDateMonth = toDate.getMonth();
		this.toDateYear = toDate.getYear();
		this.extDateDay = extDate.getDay();
		this.extDateMonth = extDate.getMonth();
		this.extDateYear = extDate.getYear();
		
		//TODO Added this line for testing
		System.out.println("fromDay: " + fromDateDay + " toDay: " + toDateDay);
	}
	
	/**
	 * Returns the difference between the fromDate and the extDate (extDate's default value is toDate)
	 * @return diffInDays The difference in days between fromDate and extDate
	 */
	public int getLength()
	{
		return (fromDate.compareTo(extDate) + 1);
	}

	///////////
	//GETTERS//
	///////////
	
	//Basic information
	public int getResID() {
		return resID;
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
	
	//Dates as ints
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

	public int getExtDateDay() {
		return extDateDay;
	}

	public int getExtDateMonth() {
		return extDateMonth;
	}

	public int getExtDateYear() {
		return extDateYear;
	}
	
	//Dates as java.sql.Date
	public java.sql.Date getFromDate() {
		return fromDate;
	}

	public java.sql.Date getToDate() {
		return toDate;
	}

	public java.sql.Date getExtDate() {
		return extDate;
	}
	
}
