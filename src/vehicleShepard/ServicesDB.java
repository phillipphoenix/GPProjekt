package vehicleShepard;
/*
 * This class is controlling the methods of our services
 */

import java.sql.*;

public class ServicesDB 
{
	public void newService(String[] info)
	{
		//TODO phillip skal lave et objekt array, som giver mig info
		int number = getNumberOfServices() + 1;
		
		Connection conn = ConnectDB.initConn();
		 
		Statement s;
		
		try 
		{
			s = conn.createStatement();
			
			try 
			{
				s.executeUpdate("INSERT INTO Service (`name`, `price`, `daily`, `vehicleID`, `vehicleTypeID`) VALUES ('" + number + "', '" + info[0] + "', '" + info[1] + "', '" + info[2] + "', '" + info[3] + "')");
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
	
	public Object[] getServiceByName(int name) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] service = new Object[8];
		
		Connection conn = ConnectDB.initConn();
		
		Statement s;
		try 
		{
			s = conn.createStatement();
			s.executeQuery("SELECT name FROM Service WHERE name = " + name);
			ResultSet rs = s.getResultSet();
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally 
		{
			ConnectDB.closeConn(conn);
		}
		
		return service;
	}
	
	private int getNumberOfServices()
	{	
		int count = 0;
		
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT name FROM Service");
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
		int number = getNumberOfServices();
		int count = 0;
		
		Object[][] serviceList = new Object[number][5]; 
		
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Service");
			ResultSet rs = s.getResultSet();
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally 
		{
			ConnectDB.closeConn(conn);
		}
		
		return serviceList;
	}
	
	public Object[][] getUsers(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] serviceList = getList();
		
		int number = getNumberOfServices();
		
		Object[][] services = Search.stringSearch(searchTerm, serviceList, number, 5); //TODO No variable called users created... This should be created at the start of this method
				//stringSearch(searchTerm, getList(), number, 7);
		//TODO 8 variabler?
		
		return services;
	}
}
