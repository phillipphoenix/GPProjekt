package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	private final static String[] CUSTOMER_COLUMN_NAMES = {"No.", "Phone", "Code", "Address", "Country", "First Name", "Last Name", "License No.", "License Exp."};
	private final static String[] RESERVATION_COLUMN_NAMES = { "No.", "UserID", "Vehicle Type", "Vehicle", "From", "To", "Extended", "Services" };
	private final static String[] VEHICLE_COLUMN_NAMES = { "ID", "Make", "Model", "Odometer", "Fuel", "Automatic", "Status", "Type" };

	StandardTableModel stm;

	private Object[][] data;
	private GridBagConstraints c = new GridBagConstraints();
	private GridBagLayout layout = new GridBagLayout();

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JTextField searchField = new JTextField("");
	private JTable table = new JTable();

	private JButton newButton = new JButton("New");
	private JButton editButton = new JButton("Edit");
	private JButton deleteButton = new JButton("Delete");

	public TableView() {
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(new Dimension(panel.getPreferredSize().width, panel.getPreferredSize().height));
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
	}

	public JPanel getReservationPanel() {
		data = Controller.getReservationList();
		stm = new StandardTableModel(data, RESERVATION_COLUMN_NAMES);
		table.setModel(stm);
		JScrollPane tablePane = new JScrollPane(table);
		layout.setConstraints(tablePane, c);
		panel.add(tablePane);
		
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyTyped(e); //TODO s�tter den selv. N�dvendig?
				if(searchField.getText().length() > 0) {
					stm.setData(Controller.searchReservations(searchField.getText()));
					stm.fireTableDataChanged();
				}
				if(searchField.getText().length() == 0) {
					stm.setData(Controller.getReservationList());
					stm.fireTableDataChanged();
				}
			}
		});

		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReservationView rv = new ReservationView();
				rv.showCreateWindow();
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int resID = (int) table.getValueAt(table.getSelectedRow(), 0);
				//Delete res
			}
		});

		return panel;
	}

	public JPanel getCustomerPanel() {
		data = Controller.getCustomerList();
		stm = new StandardTableModel(data, CUSTOMER_COLUMN_NAMES);
		table.setModel(stm);
		JScrollPane tablePane = new JScrollPane(table);
		layout.setConstraints(tablePane, c);
		panel.add(tablePane);
		
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyTyped(e); //TODO s�tter den selv. N�dvendig?
				if(searchField.getText().length() > 0) {
					stm.setData(Controller.searchCustomers(searchField.getText()));
					stm.fireTableDataChanged();
				}
				if(searchField.getText().length() == 0) {
					stm.setData(Controller.getCustomerList());
					stm.fireTableDataChanged();
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
		deleteButton.setEnabled(false);
		/*deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userID = (int) ctm.getValueAt(table.getSelectedRow(), 0);
				cont.
				System.out.println(userID);
			}
		});*/

		return panel;
	}

	public JPanel getVehiclePanel() {
		data = Controller.getVehicleList();
		stm = new StandardTableModel(data, VEHICLE_COLUMN_NAMES);
		table.setModel(stm);
		JScrollPane tablePane = new JScrollPane(table);
		layout.setConstraints(tablePane, c);
		panel.add(tablePane);
		
		searchField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyTyped(e); //TODO s�tter den selv. N�dvendig?
				if(searchField.getText().length() > 0) {
					stm.setData(Controller.searchVehicles(searchField.getText()));
					stm.fireTableDataChanged();
				}
				if(searchField.getText().length() == 0) {
					stm.setData(Controller.getVehicleList());
					stm.fireTableDataChanged();
				}
			}
		});

		newButton.setEnabled(false);
		editButton.setEnabled(false);
		deleteButton.setEnabled(false);

		return panel;
	}
}