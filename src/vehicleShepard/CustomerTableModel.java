package vehicleShepard;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class CustomerTableModel extends AbstractTableModel {
	Object[][] data;
	String[] colNames;
	
	public CustomerTableModel(Object[][] data, String[] colNames) {
		this.data = data;
		this.colNames = colNames;
	}
	
	public CustomerTableModel() {

	}

	public int getColumnCount() {
		return colNames.length;
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
