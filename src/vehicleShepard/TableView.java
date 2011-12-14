package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private final static String RESERVATION[] = { "No.", "UserID", "Vehicle Type", "Vehicle", "From", "To", "Extended", "Services" };
	private final static String VEHICLE[] = { "ID", "Make", "Model", "Odometer", "Fuel", "Automatic", "Status", "Type" };
	
	private Controller cont;
	private Object[][] data;
	private GridBagConstraints c = new GridBagConstraints();
	private GridBagLayout layout = new GridBagLayout();
	
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
		
		panel.setLayout(layout);
		
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTH;


		c.gridy = 0;
		c.gridx = 0;
		layout.setConstraints(searchLabel, c);
		panel.add(searchLabel);
		
		c.gridx = 1;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(searchField, c);
		panel.add(searchField);
		
		c.gridx = 2;
		c.weightx = 0;
		newButton.setIcon(View.loadImageIcon("res/icons/add.png"));
		layout.setConstraints(newButton, c);
		panel.add(newButton);
		
		c.gridx = 3;
		editButton.setIcon(View.loadImageIcon("res/icons/pencil.png"));
		layout.setConstraints(editButton, c);
		panel.add(editButton);
		
		c.gridx = 4;
		deleteButton.setIcon(View.loadImageIcon("res/icons/delete.png"));
		layout.setConstraints(deleteButton, c);
		panel.add(deleteButton);
		
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
	}
	
	public JPanel getReservationPanel() {
		data = cont.getReservationList();
		table = new JTable(data, RESERVATION);
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height));
		JScrollPane tablePane = new JScrollPane(table);
		layout.setConstraints(tablePane, c);
		panel.add(tablePane);
		
		return panel;
	}
	
	public JPanel getCustomerPanel() {
		data = cont.getCustomerList();
		table = new JTable(data, CUSTOMER);
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height));
		JScrollPane tablePane = new JScrollPane(table);
		layout.setConstraints(tablePane, c);
		panel.add(tablePane);
		
		return panel;
	}

	public JPanel getVehiclePanel() {
		data = cont.getVehicleList();
		table = new JTable(data, VEHICLE);
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height));
		JScrollPane tablePane = new JScrollPane(table);
		layout.setConstraints(tablePane, c);
		panel.add(tablePane);
		
		return panel;
	}
}
