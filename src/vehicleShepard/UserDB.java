package vehicleShepard;

/*
 * This class is controlling the methods of our users
 */

import java.sql.*;

public class UserDB 
{
	public void newUser(Boolean customer, String[] info)
	{
		//TODO phillip skal lave et objekt array, som giver mig info
		int userID = getNumberOfUsers(customer) + 1;
		
		Connection conn = ConnectDB.initConn();
		 
		Statement s;
		try 
		{
			s = conn.createStatement();
			
			try 
			{
				if(customer = true)
				{
					s.executeUpdate("INSERT INTO Customer (`userID`, `phone`, `phoneCode`, `address`, `firstName`, `lastName`, `licenceNumber`, `licenceExpDate`) VALUES ('" + userID + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "', '" + info[4] + "', '" + info[5] + "', '" + info[6] + "')");
				}
				else
				{
					s.executeUpdate("INSERT INTO Mechanic (`userID`, `phone`, `phoneCode`, `address`, `country`, `firmName`) VALUES ('" + userID + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "', '" + info[4] + "')");
				}
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			finally
			{
				s.close();
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
	
	public Object[] getUserByID(Boolean customer, int userID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] user;
		
		if(customer = true)
		{
			user = new Object[9]; 
		}
		else
		{
			user = new Object[6]; 
		}
		
		Connection conn = ConnectDB.initConn();
		
		Statement s;
		try 
		{
			s = conn.createStatement();
			
			if(customer)
			{
				s.executeQuery("SELECT userID FROM Customer WHERE userID = " + userID);
			}
			else
			{
				s.executeQuery("SELECT userID FROM Mechanic WHERE userID = " + userID);
			}
			
			ResultSet rs = s.getResultSet();
			
			while(rs.next())
			{
				if(customer)
				{
					user[0] = rs.getString("userID");
					user[1] = rs.getString("phone");
					user[2] = rs.getString("phoneCode");
					user[3] = rs.getString("address");
					user[4] = rs.getString("country");
					user[5] = rs.getString("firstName");
					user[6] = rs.getString("lastName");
					user[7] = rs.getString("licence");
					user[8] = rs.getString("licenceExpDate");
				}
				else
				{
					user[0] = rs.getString("userID");
					user[1] = rs.getString("phone");
					user[2] = rs.getString("phoneCode");
					user[3] = rs.getString("address");
					user[4] = rs.getString("country");
					user[5] = rs.getString("firmName");
				}
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
	
	private int getNumberOfUsers(Boolean customer)
	{	
		int count = 0;
		
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			
			if(customer)
			{
				s.executeQuery("SELECT userID FROM Customer");
			}
			else
			{
				s.executeQuery("SELECT userID FROM Mechanic");
			}
			
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
	
	public Object[][] getList(Boolean customer)
	{
		int number = getNumberOfUsers(customer);
		int count = 0;
		
		
		
		Object[][] userList;
		
		if(customer = true)
		{
			userList = new Object[number][9]; 
		}
		else
		{
			userList = new Object[number][6]; 
		}
		
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			
			if(customer)
			{
				s.executeQuery("SELECT * FROM Customer");
			}
			else
			{
				s.executeQuery("SELECT * FROM Mechanic");
			}
			
			ResultSet rs = s.getResultSet();
			
			while(rs.next())
			{
				if(customer)
				{
					userList[count][0] = rs.getString("userID");
					userList[count][1] = rs.getString("phone");
					userList[count][2] = rs.getString("phoneCode");
					userList[count][3] = rs.getString("address");
					userList[count][4] = rs.getString("country");
					userList[count][5] = rs.getString("firstName");
					userList[count][6] = rs.getString("lastName");
					userList[count][7] = rs.getString("licence");
					userList[count][8] = rs.getString("licenceExpDate");
				}
				else
				{
					userList[count][0] = rs.getString("userID");
					userList[count][1] = rs.getString("phone");
					userList[count][2] = rs.getString("phoneCode");
					userList[count][3] = rs.getString("address");
					userList[count][4] = rs.getString("country");
					userList[count][5] = rs.getString("firmName");
				}
				
				
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
	
	public Object[][] getUsers(Boolean customer, String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] userList = getList(customer);
		
		int number = getNumberOfUsers(customer);
		
		Object[][] users = Search.stringSearch(searchTerm, userList, number, 8); //TODO No variable called users created... This should be created at the start of this method
				//stringSearch(searchTerm, getList(), number, 7);
		//TODO 8 variabler?
		
		return users;
	}
}
