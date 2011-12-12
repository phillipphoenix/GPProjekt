package vehicleShepard;

/*
 * This class is controlling the methods of our users
 * This implies: 
 * 		creating a new user
 * 		getting a user by his or her ID
 * 		getting the number of customers
 * 		getting a list of customers
 * 		getting a list of customers (from a search)
 */

import java.sql.*;

public class UserDB 
{
	/**
	 * This method creates a new user, 
	 * 		either customer or mechanic.
	 * We use a boolean to check this
	 * 		and all the info we get from an array
	 * @param customer 
	 * @param info
	 */
	public void newUser(Boolean customer, Object[] info)
	{
		//TODO phillip skal lave et objekt array, som giver mig info
		int userID = getNumberOfUsers(customer) + 1;
		
		//We connect to our database
		Connection conn = ConnectDB.initConn();
		 
		Statement s;
		try 
		{
			//This is a class from our driver
			s = conn.createStatement();
			
			/*
			 * Here we want to insert our new user 
			 * 		into the right table
			 * We do this by checking if we are making a new
			 * 		customer or mechanic
			 */
			
			try 
			{
				if(customer)
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
			//Close the connection
			ConnectDB.closeConn(conn);
		} 
	}
	
	/**
	 * This method gets a user by their ID
	 * We use boolean again to see if its a customer or mechanic
	 * UserID talks for itself
	 * @param customer
	 * @param userID
	 * @return
	 */
	public Object[] getUserByID(Boolean customer, int userID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] user;
		
		//We determine the length of the array depending on the user-type
		if(customer = true)
		{
			user = new Object[9]; 
		}
		else
		{
			user = new Object[6]; 
		}
		
		//We connect to our database
		Connection conn = ConnectDB.initConn();
		
		Statement s;
		
		/*
		 * Here we want to select our user 
		 * 		from the right table
		 * We do this by checking if we are taking a
		 * 		customer or mechanic
		 */
		
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
			
			/*
			 * The result is put in a resultset rs
			 * We then take each line of the resultset and 
			 * 		put a result in our array
			 */
			
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
		}
		
		finally 
		{
			//Close the connection
			ConnectDB.closeConn(conn);
		}
		
		return user;
	}
	
	/**
	 * This method gets the number of users in a table
	 * Again
	 * @param customer
	 * @return
	 */
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
