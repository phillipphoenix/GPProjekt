package vehicleShepard;
/*
 * This class is making sure of our search-methods
 */
public class Search 
{
	/**
	 * Takes an arrayList2D and converts it into a normal 2D array
	 * @param arrayList2D The arrayList2D to convert
	 * @return array The converted arrayList2D
	 */
	public static Object[][] arrayList2DToArray(ArrayList2D arrayList2D)
	{
		int numRows = arrayList2D.getNumRows();
		int numCols = arrayList2D.getNumCols();
		Object[][] array = new Object[numRows][numCols];

		/*
		 * We take the data on a place in the
		 * 		2D arraylist and put it on the 
		 * 		same spot in our 2D array
		 */

		for (int i = 0; i < numRows; i++) 
		{
			for (int j = 0; j < numCols; j++) 
			{
				array[i][j] = arrayList2D.get2D(i, j);
			}
		}

		return array;
	}

	/**
	 * Takes a search term and a 2D array for which to search in
	 * and then returns a 2D array with all entries which fits the search.
	 * It also needs a number of rows (listSize) and the number of columns in the search list.
	 * @param searchTerm The term for which to search
	 * @param searchList The 2D array in which to search
	 * @param listSize The number of rows in the 2D array
	 * @param numCols The number of columns in the 2D array
	 * @return array The 2D array of entries that fits the search term
	 */
	public static Object[][] stringSearch(String searchTerm, Object[][] searchList, int listSize, int numCols)
	{
		ArrayList2D<Object> searchArray = new ArrayList2D<Object>(numCols);
		boolean isAdded = false;

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
				if(search.startsWith(searchTerm) && isAdded == false)
				{
					int n = searchArray.getNumRows();
					searchArray.addRows(n);

					isAdded = true;

					/*
					 * We have found a match and added a row, now we want to
					 * fill in the row with the information from our complete
					 * userlist (getList)
					 */
					for(int k = 0; k < numCols; k++)
					{
						searchArray.set2D(n, k, searchList[i][k]);
					}
				}
			}
			isAdded = false;
		}

		/*
		 * We found out the program had a lot of errors when
		 * 		we wrote something that didn't fit, therfore
		 * 		we made a method to check if an arraylist
		 * 		was empty and if it were, it should return
		 * 		a null.
		 * If the arraylist had any content it would make
		 * 		an array.
		 */
		if(searchArray.isEmpty() == true)
		{
			return null;
		}
		else
		{
			return arrayList2DToArray(searchArray);
		}
	}
}
