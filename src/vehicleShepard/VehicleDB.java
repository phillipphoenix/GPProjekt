package vehicleShepard;

/*
 * This class is controlling the methods containing 
 * 		methods using our database
 * This implies:
 * 		getting an available vehicle
 * 		making a new vehicle
 * 		getting a vehicle by its ID
 * 		getting a list of vehicles
 * 		getting a list of vehicles (after search)
 */

import java.sql.*;
import java.util.ArrayList;

public class VehicleDB 
{
	
	/////////////
	//FUNCTIONS//
	/////////////
	
	/**
	 * This method returns a vehicle with the indicated parameters available for rent within the indicated period
	 * @param typeID The vehicle typeID to search for
	 * @param automatic The type of gear
	 * @param fromDate The from date to look for
	 * @param toDate The to date to look for
	 * @return availableVehicle The available vehicle with the lowest value of odometer, or null if no one is available
	 */
	public Vehicle getAvailableVehicle(int typeID, boolean automatic, java.sql.Date fromDate, java.sql.Date toDate)
	{
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		Vehicle availableVehicle = null;
		ArrayList2D<Object> availableVehicleList = new ArrayList2D<Object>(8);
		ArrayList<Object> rowsToRemove = new ArrayList<Object>();
		int count = 0;
		
		try {
			Statement s1 = conn.createStatement();
			
			s1.executeQuery("SELECT * FROM Vehicle WHERE typeID=" + typeID);
			ResultSet rs1 = s1.getResultSet();
			
			while (rs1.next()) {
				availableVehicleList.set2D(count, 0, rs1.getString("vehicleID"));
				availableVehicleList.set2D(count, 1, rs1.getString("make"));
				availableVehicleList.set2D(count, 2, rs1.getString("model"));
				availableVehicleList.set2D(count, 3, rs1.getInt("odometer"));
				availableVehicleList.set2D(count, 4, rs1.getInt("fuelID"));
				availableVehicleList.set2D(count, 5, rs1.getBoolean("automatic"));
				availableVehicleList.set2D(count, 6, rs1.getInt("statusID"));
				availableVehicleList.set2D(count, 7, rs1.getInt("typeID"));
				count++;
			}
			
			
			//Adds vehicles to the remove list, if it doesn't have the same gear type
			for (int i = 0; i < availableVehicleList.getNumRows(); i++) {
				boolean vehcAuto = (boolean) availableVehicleList.get2D(i, 5);
				if (vehcAuto = automatic) {
					rowsToRemove.add(i);
				}
			}
			
			//Removes vehicles from the availableVehicleList
			int countRemove = 0;
			for (int j = 0; j < rowsToRemove.size(); j++) {
				availableVehicleList.removeRow((int) rowsToRemove.get(j) - countRemove);
				countRemove++;
			}
			
			//Reset the remove count and the remove arrayList
			countRemove = 0;
			rowsToRemove.clear();
			
			//This adds vehicles from the list which are not available in the indicated time period to the remove list
			for (int k = 0; k < availableVehicleList.getNumRows(); k++) {
				boolean onRemoveList = false;
				try {
					Statement s2 = conn.createStatement();
					s2.executeQuery("SELECT * FROM Reservation WHERE vehicleID='" + availableVehicleList.get2D(k, 0) + "'");
					ResultSet rs2 = s2.getResultSet();
					
					while (rs2.next() && onRemoveList) {
						java.sql.Date fDate = rs2.getDate("fromDate");
						java.sql.Date eDate = rs2.getDate("extendedDate");
						
						/*
						 * Checks if the dates lie inside the dates we want to make a reservation
						 * on and if they do, adds them to the rowsToRemove arrayList
						 */
						if (fDate.after(fromDate) && fDate.before(toDate) && onRemoveList) {
							rowsToRemove.add(k);
							onRemoveList = true;
						}
						if (eDate.after(fromDate) && eDate.before(toDate) && onRemoveList) {
							rowsToRemove.add(k);
							onRemoveList = true;
						}
						if (fDate.before(fromDate) && eDate.after(toDate) && onRemoveList) {
							rowsToRemove.add(k);
							onRemoveList = true;
						}
					}
					
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			//Removes vehicles from the availableVehicleList
			for (int j = 0; j < rowsToRemove.size(); j++) {
				availableVehicleList.removeRow((int) rowsToRemove.get(j) - countRemove);
				countRemove++;
			}
			
			int countLowest = 0;
			for (int l = 1; l < availableVehicleList.getNumRows(); l++) {
				if ((int)availableVehicleList.get2D(l, 3) < (int)availableVehicleList.get2D(countLowest, 3)) {
					countLowest = l;
				}
			}
			
			String vehicleID = (String) availableVehicleList.get2D(countLowest, 0);
			String make = (String) availableVehicleList.get2D(countLowest, 1);
			String model = (String) availableVehicleList.get2D(countLowest, 2);
			int odometer = (int) availableVehicleList.get2D(countLowest, 3);
			String fuelName = (String) availableVehicleList.get2D(countLowest, 4);
			boolean auto = (boolean) availableVehicleList.get2D(countLowest, 5);
			int statusID = (int) availableVehicleList.get2D(countLowest, 6);
			int type = (int) availableVehicleList.get2D(countLowest, 7);
			
			availableVehicle = new Vehicle(vehicleID, make, model, odometer, fuelName, auto, statusID, type);
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Returns the available vehicle with the lowest number in odometer, or null if no one is available
		return availableVehicle;
	}
	
	////////////
	//VEHICLES//
	////////////
	
	/**
	 * This method creates a new vehicle in our database
	 * This happens by the help of an array
	 * @param info
	 */
	public void newVehicle(Object[] info)
	{
		int vehicleID = getNumberOfVehicles() + 1;
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		Statement s;
		
		//We insert the needed data into our database
		
		try 
		{
			s = conn.createStatement();
			
			try 
			{
				s.executeUpdate("INSERT INTO Vehicle (`vehicleID`, `make`, `model`, `odometer`, `fuelID`, `automatic`, `statusID`, `typeID`) VALUES ('" + vehicleID + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "', '" + info[4] + "', '" + info[5] + "', '" + info[6] + "')");
				s.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	/**
	 * This method finds a vehicle, from the given name
	 * @param vehicleID
	 * @return vehicle
	 */
	public Vehicle getVehicleByID(String vehicleID)
	{
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		Vehicle vehicle = null;
		
		Statement s;
		try 
		{
			s = conn.createStatement();
			s.executeQuery("SELECT * FROM Vehicle WHERE vehicleID='" + vehicleID + "'");
			ResultSet rs = s.getResultSet();
			
			while(rs.next())
			{
				int fuelID = rs.getInt("fuelID");
				String fuelName = "";
				
				try {
					Statement sFuel = conn.createStatement();
					
					sFuel.executeQuery("SELECT name FROM Fuel WHERE fuelID=" + fuelID);
					ResultSet rsFuel = sFuel.getResultSet();
					
					while (rsFuel.next()) {
						fuelName = rsFuel.getString("name");
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				
				vehicle = new Vehicle(rs.getString("vehicleID"), rs.getString("make"), rs.getString("model"), rs.getInt("odometer"), fuelName, rs.getBoolean("automatic"), rs.getInt("StatusID"), rs.getInt("typeID"));
			}
			
			s.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();			
		}
		
		return vehicle;
	}
	
	/**
	 * This method gives us the number of vehicles in 
	 * 		our database
	 * @return number
	 */
	private int getNumberOfVehicles()
	{
		int count = 0;
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT vehicleID FROM Vehicle");
			
			ResultSet rs = s.getResultSet();
			
			/*
			 * The result is put in a resultset rs
			 * We then take each line of the resultset and 
			 * 		count +1 each time
			 */
			
			while(rs.next())
			{
				count++;
			}
			
			s.close();
			System.out.println("count: " + count);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * This method creates a list of vehicles in our database
	 * @return vehicles
	 */
	public Object[][] getList()
	{
		int number = getNumberOfVehicles();
		int count = 0;
		//We want a list of customers in a 2D Array
		Object[][] vehicleList = new Object[number][8]; 		
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Vehicle");
			
			ResultSet rs = s.getResultSet();
			
			/*
			 * The result is put in a resultset rs
			 * We then take each line of the resultset and 
			 * 		put a result in our array
			 */
			
			while(rs.next())
			{
				vehicleList[count][0] = rs.getString("vehicleID");
				vehicleList[count][1] = rs.getString("make");
				vehicleList[count][2] = rs.getString("model");
				vehicleList[count][3] = rs.getInt("odometer");
				vehicleList[count][4] = rs.getInt("fuelID");
				vehicleList[count][5] = rs.getInt("automatic");
				vehicleList[count][6] = rs.getInt("statusID");
				vehicleList[count][7] = rs.getInt("typeID");
				
				count++;
			}
			
			s.close();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return vehicleList;
	}
	
	/**
	 * This method gives us a list of vehicles after a search
	 * @param searchString
	 * @return vehicles
	 */
	public Object[][] getVehicles(String searchString)
	{		
		//We make it easier to analyze
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] vehicleList = getList();
		
		int number = getNumberOfVehicles();
		
		/*
		 * We use our search method, by giving the needed parameters
		 * 		and it returns an array
		 */
		
		Object[][] vehicles = Search.stringSearch(searchTerm, vehicleList, number, 8); //TODO No variable called users created... This should be created at the start of this method
				//stringSearch(searchTerm, getList(), number, 7);
		
		return vehicles;
	}
	
	////////////////
	//VEHICLETYPES//
	////////////////
	
	/**
	 * This method finds and returns the number 
	 * 		of vehicle types we have in our database. 		
	 * @return number
	 */
	private int getNumberOfVehicleTypes()
	{
		int count = 0;
		
		//We get the connection from Controller class
		Connection conn = Controller.getConnection();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT vehicleTypeID FROM VehicleType");
			
			ResultSet rs = s.getResultSet();
			
			/*
			 * The result is put in a resultset rs
			 * We then take each line of the resultset and 
			 * 		count +1 each time
			 */
			
			while(rs.next())
			{
				count++;
			}
			
			s.close();
			System.out.println("count: " + count);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * Returns the names of all vehicle types in an array of strings
	 * @return vehTypeNames The names of all vehicle types in an array of strings
	 */
	public String[] getVehicleTypeNames()
	{
		int count = 0;
		int number = getNumberOfVehicleTypes();
		String[] vehTypeNames = new String[number];
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT name FROM VehicleType ORDER BY vehicleTypeID");
			
			ResultSet rs = s.getResultSet();
			
			/*
			 * We take the string from our resultset
			 * 		and put it into an array.
			 * Thereafter we count +1 so that the next
			 * 		name is put on the next spot. 
			 */
			
			while(rs.next())
			{
				vehTypeNames[count] = rs.getString("name");
				count++;
			}
			
			s.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return vehTypeNames;
	}
	
	/**
	 * Returns the price rates of all vehicle types in an array of ints
	 * @return vehTypePrices The price rates of all vehicle types in an array of ints
	 */
	public int[] getVehicleTypePrices()
	{
		int count = 0;
		int number = getNumberOfVehicleTypes();
		int[] vehTypePrices = new int[number];
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT priceRate FROM VehicleType ORDER BY vehicleTypeID");
			
			ResultSet rs = s.getResultSet();
			
			/*
			 * For each line in the resultset, we take the
			 * 		priceRate and put it in our array
			 * Thereafter we count +1 so that the next
			 * 		price is put on the next spot
			 */
			
			while(rs.next())
			{
				vehTypePrices[count] = rs.getInt("priceRate");
				count++;
			}
			
			s.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return vehTypePrices;
	}
}
