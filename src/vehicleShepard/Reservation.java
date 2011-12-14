package vehicleShepard;

import java.util.Date;

public class Reservation {
	
	//TODO Change service to something else, when we need that done!
	private int resID, userType, userID, typeID, fromDateDay, fromDateMonth, fromDateYear, toDateDay, toDateMonth, toDateYear, extDateDay, extDateMonth, extDateYear, service;
	private String vehicleID;
	private java.sql.Date fromDate, toDate, extDate;
	
	public Reservation(int resID, int userType,int userID, int typeID, String vehicleID, java.sql.Date fromDate, java.sql.Date toDate, java.sql.Date extDate, int service)
	{
		this.resID = resID;
		this.userType = userType;
		this.userID = userID;
		this.typeID = typeID;
		this.vehicleID = vehicleID;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.extDate = extDate;
		this.fromDateDay = fromDate.getDate();
		this.fromDateMonth = fromDate.getMonth();
		this.fromDateYear = fromDate.getYear();
		this.toDateDay = toDate.getDate();
		this.toDateMonth = toDate.getMonth();
		this.toDateYear = toDate.getYear();
		this.extDateDay = extDate.getDate();
		this.extDateMonth = extDate.getMonth();
		this.extDateYear = extDate.getYear();
	}
	
	/**
	 * Returns the difference between the fromDate and the extDate (extDate's default value is toDate) in days
	 * @return diffInDays The difference in days between fromDate and extDate
	 */
	public int getLength()
	{
		long millSecDiff = extDate.getTime() - fromDate.getTime();
		int length = (int) (millSecDiff / (24 * 60 * 60 * 1000) + 1);
		return length;
		
	}

	///////////
	//GETTERS//
	///////////
	
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
