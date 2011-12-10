package vehicleShepard;
/*
 * This class is controlling the methods of our customers
 */

import java.util.ArrayList;
import java.sql.*;

public class CustomerDB extends UserDB
{
	String firstName;
	String lastName;
	String licenseExpirationDate;
	int licenseNumber;
	
	public CustomerDB()
	{
		//
	}
	
	public void newUser()
	{
		Connection conn = ConnectDB.initConn();
		 
		Statement s;
		try 
		{
			s = conn.createStatement();
			
			try 
			{
				int count = 7;
				s.executeUpdate("INSERT INTO Customer (`userID`, `phone`, `phoneCode`, `adress`, `firstName`, `lastName`, `licenceNumber`, `licenceExpDate`) VALUES ('" + count + "', '12', '12', '12', '12', '12', '12', '0000-00-00')");
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
		
		ConnectDB.closeConn(conn); 
	}
	
	public Object[] getUserByID(int userID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] user = new Object[1];
		
		return user;
	}
	
	private int getNumberOfCustomers()
	{	
		Connection conn = ConnectDB.initConn();
		
		int count = 0;
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT userID FROM Customer");
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
	
	public Object[][] getList()
	{
		//We want a list of customers in a 2D Array
		
		Object[][] nameList = new Object[1][1]; 
		//TODO Størrelsen skal ikke være 1,1
		
		int number = getNumberOfCustomers();
		
		/*
		 * We use two for-each loops to get down at 
		 * the spicifik (i,j) coordinate in our 2D Array
		 * Then we put the information, from our current customer (from the database)
		 * and put it in our new 2D Array
		 */
		for(int i = 0; i < number; i++)
		{
			//kald til database, der returnerer et array
			// currentCust[] = database kald på kunde i
			
			for(int j = 0; j < 7; j++)
			{
				nameList[i][j] = 1;
						//currentCust[j];
				//TODO ingen currentcust
			}
		}
		
		return nameList;
	}
	
	public Object[][] getUsers(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] userList = getList();
		
		int number = getNumberOfCustomers();
		
		Object[][] users = Search.stringSearch(searchTerm, userList, number, 7); //TODO No variable called users created... This should be created at the start of this method
				//stringSearch(searchTerm, getList(), number, 7);
		
		return users;
	}
}
