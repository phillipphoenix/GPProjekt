package vehicleShepard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * This class is controlling the methods of our vehicles
 */
public class VehicleDB 
{
	//
	
	public void newVehicle(Object[] info)
	{
		//TODO phillip skal lave et objekt array, som giver mig info
		int vehicleID = getNumberOfVehicles() + 1;
		
		Connection conn = ConnectDB.initConn();
		 
		Statement s;
		try 
		{
			s = conn.createStatement();
			
			try 
			{
				s.executeUpdate("INSERT INTO Vehicle (`vehicleID`, `make`, `model`, `odumeter`, `fuel`, `automatic`, `statusID`, `typeID`) VALUES ('" + vehicleID + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "', '" + info[4] + "', '" + info[5] + "', '" + info[6] + "')");
				s.close();
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		finally 
		{
			ConnectDB.closeConn(conn);
		} 
	}
	
	public Object[] getVehicleByID(int vehicleID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] vehicle = new Object[8];
		
		Connection conn = ConnectDB.initConn();
		
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
				vehicle[3] = rs.getInt("odumeter");
				vehicle[4] = rs.getInt("fuel");
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
		
		finally 
		{
			ConnectDB.closeConn(conn);
		}
		
		return vehicle;
	}
	
	private int getNumberOfVehicles()
	{
		int count = 0;
		
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT vehicleID FROM Vehicle");
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
		
		finally 
		{
			ConnectDB.closeConn(conn);
		}
		
		return count;
	}
	
	public Object[][] getList()
	{
		int number = getNumberOfVehicles();
		int count = 0;
		//We want a list of customers in a 2D Array
		Object[][] vehicleList = new Object[number][8]; 		
		
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Customer");
			ResultSet rs = s.getResultSet();
			
			while(rs.next())
			{
				vehicleList[count][0] = rs.getString("vehicleID");
				vehicleList[count][1] = rs.getString("make");
				vehicleList[count][2] = rs.getString("model");
				vehicleList[count][3] = rs.getInt("odumeter");
				vehicleList[count][4] = rs.getInt("fuel");
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
		
		finally 
		{
			ConnectDB.closeConn(conn);
		}
		
		return vehicleList;
	}
	
	public Object[][] getUsers(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] userList = getList();
		
		int number = getNumberOfVehicles();
		
		Object[][] vehicles = Search.stringSearch(searchTerm, userList, number, 7); //TODO No variable called users created... This should be created at the start of this method
				//stringSearch(searchTerm, getList(), number, 7);
		
		return vehicles;
	}
}
