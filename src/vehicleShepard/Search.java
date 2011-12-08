package vehicleShepard;
/*
 * This class is making sure of our search 'engine'
 */
public class Search 
{
	public static String[][] arrayListToArray()
	{
		
	}
	
	public static String[][] stringSearch(String searchTerm, String[][] searchList, int listSize, int numCols)
	{
		ArrayList2D searchArray = new ArrayList2D(numCols);
		
		for(int i = 0; i < listSize; i++)
		{
			for(int j = 0; j < numCols; j++)
			{
				// We make it easier for ourselves to search
				String search = "" + searchList[i][j];
				search = search.toLowerCase().trim();
				
				/*
				 * We have chosen only to search by the start of the strings.
				 * First we find a match, if there is a match we add another 
				 * row in our 2D ArrayList.
				 */
				if(search.startsWith(searchTerm))
				{
					int x = searchArray.getNumRows();
					searchArray.addRows(x);
					
					/*
					 * We have found a match and added a row, now we want to
					 * fill in the row with the information from our complete
					 * userlist (getList)
					 */
					for(int k = 0; k < 7; k++)
					{
						searchArray.set2D(x, k, searchList[i][k]);
					}
				}
			}
		}
		
		return searchArray;
	}
}
