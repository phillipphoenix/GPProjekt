package vehicleShepard;
/*
 * This class is controlling the methods of our mechanic
 */
public class MechanicDB extends UserDB
{
	//
	
	public void newUser()
	{
		
	}
	
	public Object[] getUserByID(int userID) //TODO We should see, if this method should return String[], String[][] or an object of type User(something something).
	{
		Object[] user = new Object[1];
		
		return user;
	}
	
	private int getNumberOfMechanics()
	{
		int number = 0;
		
		return number;
	}
	
	public Object[][] getList()
	{
		//We want a list of customers in a 2D Array
		
		Object[][] nameList = new Object[1][1]; 
		//TODO St�rrelsen skal ikke v�re 1,1
		
		int number = getNumberOfMechanics();
		
		/*
		 * We use two for-each loops to get down at 
		 * the spicifik (i,j) coordinate in our 2D Array
		 * Then we put the information, from our current customer (from the database)
		 * and put it in our new 2D Array
		 */
		for(int i = 0; i < number; i++)
		{
			//kald til database, der returnerer et array
			// currentCust[] = database kald p� kunde i
			
			for(int j = 0; j < 7; j++)
			{
				nameList[i][j] = 1;
				//currentMech[j];
			}
		}
		
		return nameList;
	}
	
	public Object[][] getUsers(String searchString)
	{		
		String searchTerm = searchString.toLowerCase().trim();
		Object[][] userList = getList();
		
		int number = getNumberOfMechanics();
		
		Object[][] users = Search.stringSearch(searchTerm, userList, number, 9); //TODO No variable called users created... This should be created at the start of this method
				//stringSearch(searchTerm, getList(), number, 7);
		
		return users;
	}
}
