package vehicleShepard;

/*
 * This class is controlling the methods of our services
 * This implies:
 * 		adding a new service
 * 		finding a service
 * 		getting the number of services
 * 		getting a list of services
 * 		getting a list of services (after search)
 */

import java.sql.*;

public class ServicesDB 
{
	/**
	 * This method creates a new service in our database
	 * It uses an array of objects to do so
	 * @param info
	 */
	public void newService(Object[] info)
	{
		//TODO phillip skal lave et objekt array, som giver mig info
		int number = getNumberOfServices() + 1;
		
		//We get the connection from the Connection class
		Connection conn = Controller.getConnection();
		 
		Statement s;
		
		//We insert the needed data into our database
		
		try 
		{
			s = conn.createStatement();
			
			try 
			{
				s.executeUpdate("INSERT INTO Service (`name`, `price`, `daily`, `vehicleID`, `vehicleTypeID`) VALUES ('" + number + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "')");
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			
			finally
			{
				s.close();
			}
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	}
	
	/**
	 * This method finds a service, from the given name
	 * @param name
	 * @return service
	 */
	public Object[] getServiceByName(int name) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] service = new Object[8];
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		Statement s;
		try 
		{
			s = conn.createStatement();
			s.executeQuery("SELECT name FROM Service WHERE name = " + name);
			
			ResultSet rs = s.getResultSet();
			
			/*
			 * The result is put in a resultset rs
			 * We then take each line of the resultset and 
			 * 		put a result in our array
			 */
			
			while(rs.next())
			{
				service[0] = rs.getString("name");
				service[1] = rs.getString("price");
				service[2] = rs.getString("daily");
				service[3] = rs.getString("vehicleID");
				service[4] = rs.getString("vehicleTypeID");
			}
			
			s.close();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return service;
	}
	
	/**
	 * This method helps us find the number of services
	 * 		in our database
	 * @return number
	 */
	private int getNumberOfServices()
	{	
		int count = 0;
		
		//We get the connection from the Controller class
		Connection conn = Controller.getConnection();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT name FROM Service");
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
		}
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * This method gives us a list of our services
	 * @return serviceList
	 */
	public Object[][] getList()
	{
		int number = getNumberOfServices();
		int count = 0;
		
		Object[][] serviceList = new Object[number][5]; 
		
		//We get connection from the Controller class
		Connection conn = Controller.getConnection();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Service");
			ResultSet rs = s.getResultSet();
			
			/*
			 * The result is put in a resultset rs
			 * We then take each line of the resultset and 
			 * 		put a result in our array
			 */
			
			while(rs.next())
			{
				serviceList[count][0] = rs.getString("name");
				serviceList[count][1] = rs.getString("price");
				serviceList[count][2] = rs.getString("daily");
				serviceList[count][3] = rs.getString("vehicleID");
				serviceList[count][4] = rs.getString("vehicleTypeID");
				
				count++;
			}
			
			s.close();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return serviceList;
	}
	
	/**
	 * This method gives us an array of services
	 * 		from searchresults
	 * @param searchString
	 * @return services
	 */
	public Object[][] getServices(String searchString)
	{		
		//We make it easier to analyze
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] serviceList = getList();
		
		int number = getNumberOfServices();
		
		/*
		 * We use our search method, by giving the needed parameters
		 * 		and it returns an array
		 */
		
		Object[][] services = Search.stringSearch(searchTerm, serviceList, number, 5); 
		
		return services;
	}
}
