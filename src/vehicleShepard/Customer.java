package vehicleShepard;
/*
 * This class is controlling the methods of our customers
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
	
	public String[] getUserByID(int userID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
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
		//We want a list of customers in a 2D Array
		
		String[][] nameList = new String[1][1]; 
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
				nameList[i][j] = currentCust[j];
			}
		}
		
		return nameList;
	}
	
	public String[][] getUsers(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		String[][] userList = getList();
		
		int number = getNumberOfCustomers();
		
		users = Search.stringSearch(searchTerm, userList, number, 7); //TODO No variable called users created... This should be created at the start of this method
				//stringSearch(searchTerm, getList(), number, 7);
		
		return users;
	}
}
