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
	
	public String[][] getList()
	{
		//We want a list of customers in a 2D Array
		
		String[][] VehicleList = new String[1][1]; 
		//TODO Størrelsen skal ikke være 1,1
		
		int number = getNumberOfVehicles();
		
		/*
		 * We use two for-each loops to get down at 
		 * the spicifik (i,j) coordinate in our 2D Array
		 * Then we put the information, from our current vehicle (from the database)
		 * and put it in our new 2D Array
		 */
		for(int i = 0; i < number; i++)
		{
			//kald til database, der returnerer et array
			// currentCust[] = database kald på kunde i
			
			for(int j = 0; j < 7; j++)
			{
				VehicleList[i][j] = currentVehicle[j];
			}
		}
		
		return VehicleList;
	}
	
	public Object[][] getVehicles(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] VehicleList = getList();
		
		int number = getNumberOfVehicles();
		
		Object[][] vehicles = Search.stringSearch(searchTerm, VehicleList, number, 9);
		
		return vehicles;
	}
}
