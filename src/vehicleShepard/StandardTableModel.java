package vehicleShepard;

import javax.swing.table.AbstractTableModel;

/**
 * Defines a TableModel used for this software system,
 * based upon the AbstactTableModel.
 * 
 * @author Anders
 */
public class StandardTableModel extends AbstractTableModel {
	//Data and column names
	private Object[][] data;
	private String[] columnNames;
	
	/**
	 * Sets the data and column names based on user input.
	 * 
	 * @param data The data to be shown in the table
	 * @param columnNames Column names
	 */
	public StandardTableModel(Object[][] data, String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;
	}
	
	/**
	 * @param col The column to be returned. First column is 0
	 * @return Name of the column
	 */
	public String getColumnName(int col) {
	    return columnNames[col];
	}

	/**
	 * @return Number of columns
	 */
	public int getColumnCount() {
		return columnNames.length;
	}

	/**
	 * @return Number of rows
	 */
	public int getRowCount() {
		return data.length;
	}

	/**
	 * Gets the object (value) of the specified field in the table.
	 * 
	 * @param row Row number
	 * @param col Column number
	 */
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	/**
	 * Sets new data for the table and fires a update command
	 * 
	 * @param newData The new data
	 */
	public void setData(Object[][] newData) {
		data = newData;
		fireTableDataChanged();
	}
}