package vehicleShepard;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class StadardTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Object[][] data;
	String[] columnNames;
	
	public StadardTableModel(Object[][] data, String[] columnNames) {
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