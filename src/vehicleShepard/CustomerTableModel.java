package vehicleShepard;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class CustomerTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Object[][] data;
	String[] columnNames;
	
	public CustomerTableModel(Object[][] data, String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;
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