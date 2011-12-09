package vehicleShepard;
/*
 * This class is controlling the methods of our vehicles
 */
public class VehicleDB 
{
	//
	
	public void newUser()
	{
		
	}
	
	public Object[] getVehicleByID(int userID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] vehicle = new Object[1];
		
		return vehicle;
	}
	
	private int getNumberOfVehicles()
	{
		int number = 0;
		
		return number;
	}
	
	public Object[][] getList()
	{
		//We want a list of customers in a 2D Array
		
		Object[][] vehicleList = new Object[1][1]; 
		//TODO Størrelsen skal ikke være 1,1
		
		int number = getNumberOfVehicles();
		
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
				vehicleList[i][j] = currentVehi[j];
			}
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
