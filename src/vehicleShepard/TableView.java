package vehicleShepard;

import java.awt.BorderLayout;
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

public class TableView {
	private final static Object CUSTOMER[] = {"No.", "Phone", "Code", "Address", "Country", "First Name", "Last Name", "License No.", "License Exp."};
	private final static String RESERVATION[] = { "No.", "Two", "Three" };
	private final static String VEHICLE[] = { "ID", "Make", "Model", "Odometer", "Fuel", "Automatic", "Status", "Type" };
	private final static String VEHICLE_TYPE[] = { "No.", "Two", "Three" };
	
	private Controller cont;
	
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JTextField searchField = new JTextField("NOGET DER SØGES PÅ");
	private JLabel searchLabel = new JLabel("Search:");
	private JTable table;
	
	private JButton newButton = new JButton("New");
	private JButton editButton = new JButton("Edit");
	private JButton deleteButton = new JButton("Delete");
	
	public TableView(Controller cont) {
		this.cont = cont;
		
		panel.setLayout(new BorderLayout());
		
		JPanel searchPanel = new JPanel();
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
	
	public JPanel getCustomerPanel() {
		table = new JTable(cont.getCustomerList(), CUSTOMER);
		//table.setFillsViewportHeight(true);
		//table = new JTable(cont.searchCustomers("bob"), CUSTOMER);
		panel.add(new JScrollPane(table));
		searchField.getDocument().addDocumentListener(new DocumentListener() {
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
				String searchString = searchField.getText();
				table = new JTable(cont.searchCustomers("bob"), CUSTOMER);
			}
		});
		
		return panel;
	}
	
	public JPanel getVehiclePanel() {
		table = new JTable(cont.getVehicleList(), VEHICLE);
		//table.setFillsViewportHeight(true);
		panel.add(new JScrollPane(table));
		
		return panel;
	}
}
