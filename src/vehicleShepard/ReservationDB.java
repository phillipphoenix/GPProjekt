package vehicleShepard;

import java.util.ArrayList;
import java.sql.*;

/*
 * This class is controlling the methods of reservations
 */
public class ReservationDB 
{
	
	/**
	 * Returns an ArrayList full of arrayLists containing Reservations. Each inner arrayList contains reservations for a specific vehicle.
	 * The outer arrayList contains the inner arrayLists, which represents each individual car.
	 * @return ArrayList<ArrayList<Reservation>> The outer arrayList containing the inner arrayLists with reservations.
	 */
	public ArrayList<ArrayList<Reservation>> getArrayList()
	{
		Connection conn = ConnectDB.initConn();
		ArrayList<ArrayList<Reservation>> outerArrayList = new ArrayList<ArrayList<Reservation>>();
		
		try {
			Statement s1 = conn.createStatement();
			s1.executeQuery("SELECT * FROM Vehicle");
			ResultSet vehList = s1.getResultSet();
			
			//Checks if there are more vehicles left
			while (vehList.next()) {
				
				//If there are more vehicles, get all reservations for the current vehicle
				try {
					Statement s2 = conn.createStatement();
					s2.executeQuery("SELECT * FROM Reservation WHERE vehicleID=" + vehList.getString("vehicleID"));
					ResultSet resList = s2.getResultSet();
					
					//A new innerArrayList is created for each vehicle, which is then filled with the vehicle's reservations.
					ArrayList<Reservation> innerArrayList = new ArrayList<Reservation>();
					
					//Set all reservations for current vehicle into the bottomArrayList
					while (resList.next()) {
						java.sql.Date fromDate = resList.getDate("fromDate");
						java.sql.Date toDate = resList.getDate("fromDate");
						java.sql.Date extendedDate = resList.getDate("fromDate");
						
						Reservation res = new Reservation(resList.getInt("resID"), resList.getInt("userID"), resList.getInt("typeID"), resList.getString("vehicleID"), fromDate, toDate, extendedDate, resList.getInt("service"));
						innerArrayList.add(res);
					}
					
					//Add the bottonArrayList (for the current vehicle) to the topArrayList
					outerArrayList.add(innerArrayList);
					s2.close();
					
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//Close s1 Statement after use
			s1.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			ConnectDB.closeConn(conn);
		}
		
		return outerArrayList;
		
	}
	
	/**
	 * Returns the number of reservations in the database
	 * @return count The number of reservations in the database
	 */
	private int getNumberOfReservations()
	{	
		Connection conn = ConnectDB.initConn();
		
		int count = 0;
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT resID FROM Reservation");
			ResultSet rs = s.getResultSet();
			while(rs.next())
			{
				count++;
			}
			
			s.close();
			ConnectDB.closeConn(conn);
			System.out.println("count: " + count);
			return count;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnectDB.closeConn(conn);
			
			return count;
		}		
	}
	
	/**
	 * Get a list of all reservations as an array of the type Object[][]
	 * @return resList The 2D array
	 */
	public Object[][] getList()
	{
		//We want a list of customers in a 2D Array
		
		int number = getNumberOfReservations();
		Object[][] resList = new Object[number][8];
		int count = 0;
		
		Connection conn = ConnectDB.initConn();
		
		try {
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Reservation");
			ResultSet rs = s.getResultSet();
			while (rs.next()) {
				resList[count][0] = rs.getInt("resID");
				resList[count][1] = rs.getInt("userID");
				resList[count][2] = rs.getInt("typeID");
				resList[count][3] = rs.getString("vehicleID");
				resList[count][4] = rs.getDate("fromDate");
				resList[count][5] = rs.getDate("toDate");
				resList[count][6] = rs.getDate("extendedDate");
				resList[count][7] = rs.getInt("service");
				count++;
			}
		//Close the statement after use
		s.close();
			
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			ConnectDB.closeConn(conn);
		}
		
		return resList;
	}
	
	//TODO check if this is needed, or make something else
	/*
	public int[] fromDate(int resID)
	{
		// I want an Array to hold my date in int
		int[] fromDate = new int[3];
		
		// We take the day, month and year from the DB
		//TODO DB Connection
		int day = 1; //resID.day;
		int month = 3; //resID.month;
		int year = 1992; //resID.year;
		
		fromDate[0] = day;
		fromDate[1] = month;
		fromDate[2] = year;
		
		return fromDate;
	}
	
	public int[] toDate(int resID)
	{
		// I want an Array to hold my date in int
		int[] toDate = new int[3];
		
		// We take the day, month and year from the DB
		//TODO DB Connection
		int day = 24; //resID.day;
		int month = 10; //resID.month;
		int year = 1993; //resID.year;
		
		toDate[0] = day;
		toDate[1] = month;
		toDate[2] = year;
		
		return toDate;
	}
	*/
}
