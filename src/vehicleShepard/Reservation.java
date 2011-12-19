package vehicleShepard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;

public class Reservation {
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
		String[] fDateSep = fromDate.split("-");
		String[] eDateSep = extDate.split("-");
		
		GregorianCalendar fromCalendar = new GregorianCalendar(Integer.parseInt(fDateSep[0]), Integer.parseInt(fDateSep[1]), Integer.parseInt(fDateSep[2]));
		GregorianCalendar extCalendar = new GregorianCalendar(Integer.parseInt(eDateSep[0]), Integer.parseInt(eDateSep[1]), Integer.parseInt(eDateSep[2]));
		
		fromCalendar.getTimeInMillis();
		
		long millSecDiff = extCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis();
		int length = (int) (millSecDiff / (24 * 60 * 60 * 1000) + 1);
		return length;
	}
	
	//ADVANCED GETTERS
	
	public String getTypeName()
	{
		Connection conn = Controller.getConnection();
		String typeName = "No type name found";
		
		try {
			Statement s = conn.createStatement();
			s.executeQuery("SELECT name FROM VehicleType WHERE vehicleTypeID=" + typeID);
			
			ResultSet rs = s.getResultSet();
			
			/*
			 * The result is put in a resultset rs
			 * We just want the first one
			 */
			
			if (rs.next()) {
				typeName = rs.getString("name");
			}
			
			s.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return typeName;
		
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
	
	//Dates as ints
	
	public int getFromDateDay()
	{
		String[] fDateSep = fromDate.split("-");
		return Integer.parseInt(fDateSep[2]);
	}
	
	public int getFromDateMonth()
	{
		String[] fDateSep = fromDate.split("-");
		return Integer.parseInt(fDateSep[1]);
	}
	
	public int getFromDateYear()
	{
		String[] fDateSep = fromDate.split("-");
		return Integer.parseInt(fDateSep[0]);
	}
	
	public int getExtDateDay()
	{
		String[] eDateSep = extDate.split("-");
		return Integer.parseInt(eDateSep[2]);
	}
	
	public int getExtDateMonth()
	{
		String[] eDateSep = extDate.split("-");
		return Integer.parseInt(eDateSep[1]);
	}
	
	public int getExtDateYear()
	{
		String[] eDateSep = extDate.split("-");
		return Integer.parseInt(eDateSep[0]);
	}
}
