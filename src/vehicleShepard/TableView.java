package vehicleShepard;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 * Defines the layout and content of the table panels, including
 * table, search field and buttons.
 * 
 */

public class TableView {
	//Column names for the tables
	private final static String[] CUSTOMER_COLUMN_NAMES = {"UserID", "Phone", "Code", "Address", "Country", "First Name", "Last Name", "License No.", "License Exp."};
	private final static String[] RESERVATION_COLUMN_NAMES = { "No.", "UserID", "Vehicle Type", "Vehicle", "From", "To", "Extended", "Services" };
	private final static String[] VEHICLE_COLUMN_NAMES = { "ID", "Make", "Model", "Odometer", "Fuel", "Automatic", "Status", "Type" };

	//Data and table model for the table
	private Object[][] data;
	private StandardTableModel stm;

	//Fields needed for layout of components
	private GridBagConstraints c = new GridBagConstraints();
	private GridBagLayout layout = new GridBagLayout();

	//Components
	private JPanel panel = new JPanel();
	private JTextField searchField = new JTextField("");
	private JTable table = new JTable();

	private JButton newButton = new JButton("New");
	private JButton editButton = new JButton("Edit");
	private JButton deleteButton = new JButton("Delete");

	/**
	 * Constructs the table panel with buttons, searchfield and table
	 */
	public TableView() {
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setPreferredScrollableViewportSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height));
		table.getTableHeader().setReorderingAllowed(false);
		panel.setLayout(layout);
		c.weightx = 0;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 1;
		searchField.setPreferredSize(new Dimension(searchField.getPreferredSize().width, newButton.getPreferredSize().height));
		layout.setConstraints(searchField, c);
		panel.add(searchField);

		c.gridx = 1;
		c.weightx = 0;
		newButton.setIcon(View.loadImageIcon("res/icons/add.png"));
		layout.setConstraints(newButton, c);
		panel.add(newButton);

		c.gridx = 2;
		editButton.setIcon(View.loadImageIcon("res/icons/pencil.png"));
		layout.setConstraints(editButton, c);
		panel.add(editButton);

		c.gridx = 3;
		deleteButton.setIcon(View.loadImageIcon("res/icons/delete.png"));
		layout.setConstraints(deleteButton, c);
		panel.add(deleteButton);


		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 4;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;

		JScrollPane tablePane = new JScrollPane(table);
		layout.setConstraints(tablePane, c);
		panel.add(tablePane);
	}

	/**
	 *  Sets actionlisteners, table model and finally returns the complete panel
	 * @return Reservation table panel
	 */
	public JPanel getReservationPanel() {
		data = Controller.getReservationList();
		stm = new StandardTableModel(data, RESERVATION_COLUMN_NAMES);
		table.setModel(stm);

		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(searchField.getText().length() > 0) {
					if(Controller.searchReservations(searchField.getText()) == null) {
						Object[][] newData = {};
						stm.setData(newData);
					}
					else {
						stm.setData(Controller.searchReservations(searchField.getText()));
					}
				}
				else if(searchField.getText().length() == 0) {
					stm.setData(Controller.getReservationList());
				}
			}
		});

		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReservationView rv = new ReservationView(stm);
				rv.showCreateWindow();
			}
		});
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					ReservationView rv = new ReservationView(stm);
					rv.showExistingWindow(getSelectedID());
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					int resID = getSelectedID();
					int option = JOptionPane.showConfirmDialog(panel, "Are you sure want to delete reservation " + resID, "Delete", JOptionPane.OK_CANCEL_OPTION);
					if(option == 0) {
						Controller.removeReservation(resID);
						stm.setData(Controller.getReservationList());
					}
				}
			}
		});

		return panel;
	}

	/**
	 * Shows returns a customer panel with select button
	 * 
	 * @param showSelectButton If true, a select button will be displayed under the table
	 * @param rv Parent ReservationView
	 * @param f The frame to be closed when clicking select
	 * @return Customer table panel + select button
	 */
	public JPanel getCustomerPanel(boolean showSelectButton, final ReservationView rv, final JFrame f) {
		if(showSelectButton) {
			c.gridx = 0;
			c.gridy = 2;
			c.gridwidth = 4;
			c.weighty = 0;
			JButton selectButton = new JButton("Select");
			selectButton.setIcon(View.loadImageIcon("res/icons/asterisk_orange.png"));
			layout.setConstraints(selectButton, c);
			panel.add(selectButton);

			selectButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(getSelectedID() != -1) {
						rv.setUserID(getSelectedID());
						f.dispose();
					}
				}
			});
		}

		return getCustomerPanel();
	}

	/**
	 * Sets actionlisteners, table model and finally returns the complete panel
	 * @return Customer table panel
	 */
	public JPanel getCustomerPanel() {
		data = Controller.getCustomerList();
		stm = new StandardTableModel(data, CUSTOMER_COLUMN_NAMES);
		table.setModel(stm);

		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(searchField.getText().length() > 0) {
					if(Controller.searchCustomers(searchField.getText()) == null) {
						Object[][] newData = {};
						stm.setData(newData);
					}
					else {
						stm.setData(Controller.searchCustomers(searchField.getText()));
					}
				}
				else if(searchField.getText().length() == 0) {
					stm.setData(Controller.getCustomerList());
				}
			}
		});

		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerView cv = new CustomerView(stm);
				cv.showCreateWindow();
				stm.setData(Controller.getCustomerList());
			}
		});
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					CustomerView cv = new CustomerView(stm);
					cv.showExistingWindow(getSelectedID());
					stm.setData(Controller.getCustomerList());
				}
			}
		});
		deleteButton.setEnabled(false);

		return panel;
	}

	/**
	 *  Sets actionlisteners, table model and finally returns the complete panel
	 * @return Vehicle table panel
	 */
	public JPanel getVehiclePanel() {
		data = Controller.getVehicleList();
		stm = new StandardTableModel(data, VEHICLE_COLUMN_NAMES);
		table.setModel(stm);

		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(searchField.getText().length() > 0) {
					if(Controller.searchVehicles(searchField.getText()) == null) {
						Object[][] newData = {};
						stm.setData(newData);
					}
					else {
						stm.setData(Controller.searchVehicles(searchField.getText()));
					}
				}
				else if(searchField.getText().length() == 0) {
					stm.setData(Controller.getVehicleList());
				}
			}
		});

		newButton.setEnabled(false);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);

		return panel;
	}

	/**
	 * Gets the value from first column at the selected row
	 * 
	 * @return Value at selected row in first column, if no selection have been made -1 is returned
	 */
	private int getSelectedID() {
		if(table.getSelectedRow() != -1)
			return (int) table.getValueAt(table.getSelectedRow(), 0);
		else return -1;
	}
}