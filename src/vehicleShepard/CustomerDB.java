package vehicleShepard;
/*
 * This class is controlling the methods of our customers
 */

import java.sql.*;

public class CustomerDB extends UserDB
{	
	public void newUser(String[] info)
	{
		//TODO phillip skal lave et objekt array, som giver mig info
		int userID = getNumberOfCustomers() + 1;
		
		Connection conn = ConnectDB.initConn();
		 
		Statement s;
		try 
		{
			s = conn.createStatement();
			
			try 
			{
				s.executeUpdate("INSERT INTO Customer (`userID`, `phone`, `phoneCode`, `adress`, `firstName`, `lastName`, `licenceNumber`, `licenceExpDate`) VALUES ('" + userID + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "', '" + info[4] + "', '" + info[5] + "', '" + info[6] + "')");
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
	
	public Object[] getUserByID(int userID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] user = new Object[8];
		
		Connection conn = ConnectDB.initConn();
		
		Statement s;
		try 
		{
			s = conn.createStatement();
			s.executeQuery("SELECT userID FROM Customer WHERE userID = " + userID + "");
			ResultSet rs = s.getResultSet();
			
			while(rs.next())
			{
				user[0] = rs.getString("userID");
				user[1] = rs.getString("phone");
				user[2] = rs.getString("phoneCode");
				user[3] = rs.getString("adress");
				user[4] = rs.getString("firstName");
				user[5] = rs.getString("lastName");
				user[6] = rs.getString("licence");
				user[7] = rs.getString("licenceExpDate");
			}
			
			s.close();
		} 
		
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ConnectDB.closeConn(conn);
		}
		
		finally 
		{
			ConnectDB.closeConn(conn);
		}
		
		return user;
	}
	
	private int getNumberOfCustomers()
	{	
		int count = 0;
		
		Connection conn = ConnectDB.initConn();
		
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
		int number = getNumberOfCustomers();
		int count = 0;
		
		/*
		 * We want a list of customers in a 2D Array
		 * The size is the number of customers as rows 
		 * and we know that we need 8 collums.
		 */
		Object[][] userList = new Object[number][8]; 
		
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Customer");
			ResultSet rs = s.getResultSet();
			
			while(rs.next())
			{
				userList[count][0] = rs.getString("userID");
				userList[count][1] = rs.getString("phone");
				userList[count][2] = rs.getString("phoneCode");
				userList[count][3] = rs.getString("adress");
				userList[count][4] = rs.getString("firstName");
				userList[count][5] = rs.getString("lastName");
				userList[count][6] = rs.getString("licence");
				userList[count][7] = rs.getString("licenceExpDate");
				
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
		
		return userList;
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
