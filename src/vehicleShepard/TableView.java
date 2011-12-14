package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TableView {
	private final static Object CUSTOMER[] = {"No.", "Phone", "Code", "Address", "Country", "First Name", "Last Name", "License No.", "License Exp."};
	private final static String RESERVATION[] = { "No.", "Two", "Three" };
	private final static String VEHICLE[] = { "ID", "Make", "Model", "Odometer", "Fuel", "Automatic", "Status", "Type" };
	private final static String VEHICLE_TYPE[] = { "No.", "Two", "Three" };
	
	private Controller cont;
	private Object[][] data;
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JTextField searchField = new JTextField("");
	private JLabel searchLabel = new JLabel("Search:");
	private JTable table;
	
	private JButton newButton = new JButton("New");
	private JButton editButton = new JButton("Edit");
	private JButton deleteButton = new JButton("Delete");
	
	public TableView(Controller contt) {
		this.cont = contt;
		
		panel.setLayout(new BorderLayout());
		
		JPanel searchPanel = new JPanel();
		searchField.setPreferredSize(new Dimension(100, ViewModel.COMPONENT_HEIGHT)); //TODO ikke færdig
		newButton.setIcon(View.loadImageIcon("res/icons/add.png"));
		editButton.setIcon(View.loadImageIcon("res/icons/pencil.png"));
		deleteButton.setIcon(View.loadImageIcon("res/icons/delete.png"));
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		searchPanel.add(newButton);
		searchPanel.add(editButton);
		searchPanel.add(deleteButton);
		
		panel.add(searchPanel, BorderLayout.NORTH);
	}
	
	public JPanel getReservationPanel() {
		newButton.setEnabled(false);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		table = new JTable(cont.getReservationList(), RESERVATION);
		panel.add(new JScrollPane(table));
		
		return panel;
	}
	
	public JPanel getCustomerPanel() {
		data = cont.getCustomerList();
		table = new JTable(data, CUSTOMER);
		panel.add(new JScrollPane(table));
		
		
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				data = cont.searchCustomers("bob");
			}
		});
		
		/*searchField.getDocument().addDocumentListener(new DocumentListener() {
			public void removeUpdate(DocumentEvent arg0) {
				updateTable();
			}
			public void insertUpdate(DocumentEvent arg0) {
				updateTable();
			}
			public void changedUpdate(DocumentEvent arg0) {
				updateTable();
			}
			
			private void updateTable() {
				table = new JTable(cont.searchCustomers("bob"), CUSTOMER);
			}
		});*/
		
		
		return panel;
	}
	
	public JPanel getVehiclePanel() {
		newButton.setEnabled(false);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		table = new JTable(cont.getVehicleList(), VEHICLE);
		panel.add(new JScrollPane(table));
		
		return panel;
	}
	
	public JPanel getVehicleTypePanel() {
		newButton.setEnabled(false);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);
		table = new JTable(cont.getVehicleList(), VEHICLE); //TODO needs to be vehicle type list
		panel.add(new JScrollPane(table));
		
		return panel;
	}
}
