package vehicleShepard;

import javax.swing.table.AbstractTableModel;

public class StandardTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; //TODO JOOOOH! :)
	Object[][] data;
	String[] columnNames;
	
	public StandardTableModel(Object[][] data, String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;
	}
	
	public String getColumnName(int col) {
	    return columnNames[col];
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setData(Object[][] newData) {
		data = newData;
		fireTableDataChanged();
	}
}