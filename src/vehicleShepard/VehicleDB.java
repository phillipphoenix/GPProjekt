package vehicleShepard;

/*
 * This class is controlling the methods containing 
 * 		methods using our database
 * This implies:
 * 		making a new vehicle
 * 		getting a vehicle by its ID
 * 		getting a list of vehicles
 * 		getting a list of vehicles (after search)
 */

import java.sql.*;

public class VehicleDB 
{
	
	/////////////
	//FUNCTIONS//
	/////////////
	
	//TODO Make implementation for this method!!!
	public Vehicle getAvailableVehicle(int typeID, boolean automatic, String fromDate, String toDate)
	{
		String fuelName = "Diesel";
		return new Vehicle("BF01337", "Mercedes", "SLR 5.4", 120376, fuelName, true, 3, 5);
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
	public Object[] getVehicleByID(String vehicleID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] vehicle = new Object[8];
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		Statement s;
		try 
		{
			s = conn.createStatement();
			s.executeQuery("SELECT vehicleID FROM Vehicle WHERE vehicleID = " + vehicleID + "");
			ResultSet rs = s.getResultSet();
			
			while(rs.next())
			{
				vehicle[0] = rs.getString("vehicleID");
				vehicle[1] = rs.getString("make");
				vehicle[2] = rs.getString("model");
				vehicle[3] = rs.getInt("odometer");
				vehicle[4] = rs.getInt("fuelID");
				vehicle[5] = rs.getInt("automatic");
				vehicle[6] = rs.getInt("statusID");
				vehicle[7] = rs.getInt("typeID");
			}
			
			s.close();
		} 
		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
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
