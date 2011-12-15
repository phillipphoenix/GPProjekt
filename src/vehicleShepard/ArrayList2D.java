package vehicleShepard;

/*
 *This class is able to make a 2D arrayList
 *We need this because of our search
 *In order to make an array of our search result we need
 *		a temporary location for our search results
 *		but we don't need how large it has to be 
 *		therefore we use an 2D arrayList
 *
 *This implies:
 *		making a 2D arrayList
 *		getting the number of rows
 *		getting the number of columns
 *		getting the number of columns (different)
 *		making a 2D arrayList with data
 *		making a 2D array with data
 *		adding rows
 */

import java.util.ArrayList;


public class ArrayList2D<Type> 
{
	//The arrayList2D is generic, just like a normal arrayList
	
	private ArrayList<ArrayList<Type>> array;
	private int numCols;
	
	public ArrayList2D(int numberOfColumns)
	{
		array = new ArrayList<ArrayList<Type>>();
		numCols = numberOfColumns;
	}
	
	/**
	 * Returns the number of rows
	 * @return numRows The number of rows in the arrayList2D
	 */
	public int getNumRows()
	{
		return array.size();
	}
	
	/**
	 * Returns the number of columns in the first row (row 0)
	 * It doesn't take the longest one
	 * @return numCols The number of columns in the first row
	 */
	public int getNumCols()
	{
		return array.get(0).size();
	}
	
	/**
	 * Returns the number of columns in the indicated row
	 * @param row The row from which to get the number of columns
	 * @return numCols The number of columns in the indicated row
	 */
	public int getNumCols(int row)
	{
		return array.get(row).size();
	}
	
	/**
	 * Returns the data in from the arrayList2D on the spot (row,col)
	 * @param row The row containing the wanted information
	 * @param col The column containing the wanted information
	 * @return data The data on the indicated spot
	 */
	public Type get2D(int row, int col)
	{
		return array.get(row).get(col);
	}
	
	/**
	 * Sets the data in from the arrayList2D on the spot (row,col)
	 * @param row The row in which to set the indicated data
	 * @param col The column in which to set the indicated data
	 * @param data The data to set on the indicated spot
	 */
	public void set2D(int row, int col, Type data)
	{
		array.get(row).set(col, data);
	}
	
	/**
	 * Removes the row of the outer array with the given number
	 * @param row The row to remove
	 */
	public void removeRow(int row)
	{
		array.remove(row);
	}
	
	/**
	 * Adds rows until the indicated row number.
	 * This ensures, that there are enough rows, if it is called.
	 * @param row The number of rows, that should be in the arrayList2D
	 */
	public void addRows(int row)
	{
		while (row >= getNumRows())
		{
			array.add(new ArrayList<Type>());
			for (int i = 0; i < numCols; i++) 
			{
				array.get(getNumRows() - 1).add(null);
			}
		}
	}
	
}