package vehicleShepard;
/*
 * This is class controlling the methods of our customers
 */

import java.util.ArrayList;

public class Customer extends User
{
	String firstName;
	String lastName;
	String licenseExpirationDate;
	int licenseNumber;
	
	public Customer()
	{
		//
	}
	
	public void newUser()
	{
		
	}
	
	public String[][] getUsers(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		String[][] users = new String[1][1];
		String[][] userList = getList();
		
		int number = getNumberOfCustomers();
		
		for(int i = 0; i < number; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				String search = "" + userList[i][j];
				search = search.toLowerCase().trim();
				
				if(search.startsWith(searchTerm))
				{
					users[i][j] = 
				}
			}
		}
		return users;
	}
	
	public String[] getUserByID(int userID)
	{
		return user;
	}
	
	private int getNumberOfCustomers()
	{
		int number = 0;
		
		return number;
	}
	
	public String[][] getList()
	{
		String[][] nameList = new String[1][1]; 
		//TODO Størrelsen skal ikke være 1,1
		
		int number = getNumberOfCustomers();
		
		for(int i = 0; i < number; i++)
		{
			//kald til database, der returnerer et array
			// currentCust[] = database kald på kunde i
			
			for(int j = 0; j < 7; j++)
			{
				nameList[i][j] = currentCust[j];
			}
		}
		
		return nameList;
	}
}
