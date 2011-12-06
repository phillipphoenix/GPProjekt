package vehicleShepard;

import java.util.ArrayList;

public class ArrayList2D<Type> 
{
	private ArrayList<ArrayList<Type>> array;
	private int numCols;
	
	public ArrayList2D(int numberOfColumns)
	{
		array = new ArrayList<ArrayList<Type>>();
		numCols = numberOfColumns;
	}
	
	public int getNumRows()
	{
		return array.size();
	}
	
	public int getNumCols(int row)
	{
		return array.get(row).size();
	}
	
	public Type get2D(int row, int col)
	{
		return array.get(row).get(col);
	}
	
	public void set2D(int row, int col, Type data)
	{
		array.get(row).set(col, data);
	}
	
	public void addRows(int row)
	{
		while (row >= getNumRows())
		{
			array.add(new ArrayList<Type>());
			for (int i = 0; i < numCols; i++) {
				array.get(getNumRows() - 1).add(null);
			}
		}
	}
	
}