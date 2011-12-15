package vehicleShepard;

import java.util.ArrayList;
import java.sql.*;

/*
 * This class is controlling the methods used to retrieve information about reservations from the database
 */
public class ReservationDB 
{
	//TODO Change method, so it fits to reservations
	public void newReservation(Object[] info)
	{
		
		int resID = getNumberOfReservations() + 1;
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		 
		Statement s;
		try 
		{
			s = conn.createStatement();
			
			try {
				s.executeUpdate("INSERT INTO Reservation (`resID`, `userID`, `typeID`, `vehicleID`, `fromDate`, `toDate`, `extendedDate`, `service`) VALUES ('" + resID + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "', '" + info[4] + "', '" + info[5] + "', '" + info[6] + "')");
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				s.close();
			}
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public Reservation getReservationByID(int resID)
	{
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		Reservation reservation = null;
		
		Statement s;
		try 
		{
			s = conn.createStatement();
			s.executeQuery("SELECT * FROM Reservation WHERE resID=" + resID);
			
			ResultSet rs = s.getResultSet();
			
			while (rs.next()) {
				reservation = new Reservation(resID, rs.getInt("userType"), rs.getInt("userID"), rs.getInt("typeID"), rs.getString("vehicleID"), rs.getDate("fromDate"), rs.getDate("toDate"), rs.getDate("extendedDate"), rs.getInt("service"));
			}
			
			s.close();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return reservation;
	}
	
	public void deleteReservation(int resID)
	{
		Connection conn = Controller.getConnection();
		
		try {
			Statement s = conn.createStatement();
			s.executeQuery("DELETE FROM Reservation WHERE resID=" + resID);
			
			//S is closed
			s.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns an ArrayList full of arrayLists containing Reservations. Each inner arrayList contains reservations for a specific vehicle.
	 * The outer arrayList contains the inner arrayLists, which represents each individual car.
	 * @return ArrayList<ArrayList<Reservation>> The outer arrayList containing the inner arrayLists with reservations.
	 */
	public ArrayList<ArrayList<Reservation>> getArrayList()
	{
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
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
					s2.executeQuery("SELECT * FROM Reservation WHERE vehicleID='" + vehList.getString("vehicleID") + "'");
					ResultSet resList = s2.getResultSet();
					
					//A new innerArrayList is created for each vehicle, which is then filled with the vehicle's reservations.
					ArrayList<Reservation> innerArrayList = new ArrayList<Reservation>();
					
					//Set all reservations for current vehicle into the bottomArrayList
					while (resList.next()) {
						java.sql.Date fromDate = resList.getDate("fromDate");
						java.sql.Date toDate = resList.getDate("toDate");
						java.sql.Date extendedDate = resList.getDate("extendedDate");
						
						Reservation res = new Reservation(resList.getInt("resID"), resList.getInt("userType"), resList.getInt("userID"), resList.getInt("typeID"), resList.getString("vehicleID"), fromDate, toDate, extendedDate, resList.getInt("service"));
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
		
		return outerArrayList;
		
	}
	
	/**
	 * Returns the number of reservations in the database
	 * @return count The number of reservations in the database
	 */
	private int getNumberOfReservations()
	{	
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
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
			System.out.println("count: " + count);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * Get a list of all reservations as an array of the type Object[][]
	 * @return resList The 2D array of reservations
	 */
	public Object[][] getList()
	{
		//We want a list of customers in a 2D Array
		
		int number = getNumberOfReservations();
		Object[][] resList = new Object[number][8];
		int count = 0;
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
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
		
		return resList;
	}
	
	/**
	 * Returns a 2D array containing all reservations which contains info starting with the search term
	 * @param searchString The term for which to search
	 * @return reservations The reservations which fits the search term
	 */
	public Object[][] getReservation(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] resList = getList();
		
		int number = getNumberOfReservations();
		
		Object[][] reservations = Search.stringSearch(searchTerm, resList, number, 8);
		
		return reservations;
	}
	
}
