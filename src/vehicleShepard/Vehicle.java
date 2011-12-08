package vehicleShepard;
/*
 * This class is controlling the methods of our vehicles
 */
public class Vehicle 
{
	//
	
	public void newVehicle()
	{
		
	}
	
	private int getNumberOfVehicles()
	{
		int number = 0;
		
		return number;
	}
	
	public String[][] getUsers(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		String[][] vehicleList = getList();
		
		int number = getNumberOfVehicles();
		
		users = stringSearch();
				//stringSearch(searchTerm, getList(), number, 7);
		
		return users;
	}
}
