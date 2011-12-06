package vehicleShepard;

import java.util.ArrayList;

public class ArrayList2D<Type> 
{
	private ArrayList<ArrayList<Type>> array;
	
	public ArrayList2D()
	{
		array = new ArrayList<ArrayList<Type>>();
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
	
	public void add(Type data, int row)
	{
		while(row >= getNumRows())
		{
			array.add(new ArrayList<Type>());
		}
		
		array.get(row).add(data);
		
	}
}