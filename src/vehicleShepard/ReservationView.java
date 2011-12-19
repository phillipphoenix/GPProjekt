package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import sun.awt.WindowClosingListener;

/**
 * Defines the create and edit reservation windows.
 * 
 * @author Anders
 *
 */
public class ReservationView extends ViewModel {
	private GridBagConstraints c = new GridBagConstraints();
	private StandardTableModel stm;
	
	//Content
	private JFrame frame = new JFrame();
	private JPanel content = new JPanel();
	
	//Components
	private JLabel userLabel = new JLabel("Name:");
	private JTextField userField = new JTextField();
	private JButton userButton = new JButton("Select");

	private JLabel dateLabel = new JLabel("Period:");
	private JLabel dateToLabel = new JLabel("to");
	private JTextField dateFromField = new JTextField();
	private JTextField dateToField = new JTextField();
	private JButton dateFromButton = new JButton();
	private JButton dateToButton = new JButton();
	private boolean dateFromValid = false;
	private boolean dateToValid = false;

	private JLabel vehicleTypeLabel = new JLabel("Vehicle type:");
	private JComboBox<String> vehicleTypeComboBox = new JComboBox<String>();

	private JLabel gearTypeLabel = new JLabel("Gear type:");
	private JComboBox<String> gearTypeComboBox = new JComboBox<String>();

	private JLabel vehicleText = new JLabel("(no vehicle)");

	//This reservation
	private int existingResID;
	private String vehicleID;
	private int userID = -1;
	private int vehicleType = -1;
	private boolean aut;
	private String fromDate;
	private String toDate;

	//Buttons
	private JButton cancelButton = new JButton("Cancel");
	private JButton findButton = new JButton("Find vehicle");
	private JButton okButton = new JButton("OK");
	
	/**
	 * Initialize all components
	 * 
	 * @param stm TableModel for the table
	 */
	public ReservationView(StandardTableModel stm) {
		this.stm = stm;

		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		c.ipady = Y_PAD;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		String[] types = Controller.getVehTypeNames();
		for(int i = 0; i < types.length; i++) {
			vehicleTypeComboBox.addItem(types[i]);
		}
		vehicleTypeComboBox.setSelectedIndex(-1);

		gearTypeComboBox.addItem("Manual");
		gearTypeComboBox.addItem("Automatic");
		gearTypeComboBox.setSelectedIndex(-1);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImages(View.systemIconList());
		frame.setBounds(0, 0, 400, 300);
		frame.setLocationRelativeTo(null);

		content = getFrameContent();
		content.setBorder(new EmptyBorder(6, 6, 6, 6));
		frame.add(content);

		userButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showCustomerSelector();
			}
		});
		dateFromField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(View.isValidDate(dateFromField.getText())) {
					dateFromValid = true;
					dateFromField.setForeground(Color.BLACK);
					fromDate = dateFromField.getText();
				}
				else {
					dateFromValid = false;
					dateFromField.setForeground(Color.RED);
				}
				okButton.setEnabled(false);
			}
		});
		dateToField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(View.isValidDate(dateToField.getText())) {
					dateToValid = true;
					dateToField.setForeground(Color.BLACK);
					toDate = dateToField.getText();
				}
				else {
					dateToValid = false;
					dateToField.setForeground(Color.RED);
				}
				okButton.setEnabled(false);
			}
		});
		vehicleTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				okButton.setEnabled(false);
				vehicleType = vehicleTypeComboBox.getSelectedIndex()+1;
			}
		});
		gearTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				okButton.setEnabled(false);
				if(gearTypeComboBox.getSelectedIndex() == 0) aut = false;
				else aut = true;
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}

	/**
	 * Adds final actionlisteners and creates the final window
	 * for creation of a new reservation
	 * 
	 * @return frame The final New Reservation window
	 */
	public JFrame showCreateWindow() {
		frame.setTitle("New Reservation");
		
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isValidReservation()) {
					if(Controller.findAvailableVehicle(vehicleType, aut, fromDate, toDate) != null) {
						// If a available vehicle is found
						Vehicle v = Controller.findAvailableVehicle(vehicleType, aut, fromDate, toDate);
						vehicleText.setText(v.getMake() + " " + v.getModel() + " (" + v.getID() + ")" + "   Fuel: " + v.getFuelName() + "   Automatic: " + v.isAutomatic());
						vehicleID = v.getID();
						okButton.setEnabled(true);
					}
					else {
						vehicleText.setText("(no vehicle available with specified parameters)");
						okButton.setEnabled(false);
					}
				}
				else {
					vehicleText.setText("(please set all parameters)");
					okButton.setEnabled(false);
				}
			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.newReservation(userID, 0, vehicleType, vehicleID, fromDate, toDate, 0); //Parameter 2 are userType (0 = customer)
				stm.setData(Controller.getReservationList()); //Update reservation table with the new reservation
				frame.dispose();
			}
		});

		frame.setVisible(true);

		return frame;
	}
	
	/**
	 * Adds final actionlisteners and creates the final window
	 * for editing of existing reservation.
	 * Also sets the value of all components in the window.
	 * 
	 * @return frame The final Edit Reservation
	 */
	public JFrame showExistingWindow(int resID) {
		existingResID = resID;
		frame.setTitle("Edit Reservation " + resID);

		
		Reservation res = Controller.getReservation(resID);
		Customer cust = Controller.getCustomer(res.getUserID());
		Vehicle veh = Controller.getVehicle(res.getVehicleID());
		
		//Setting component values
		userField.setText(cust.getFirstName() + " " + cust.getLastName() + " (" + res.getUserID() + ")");
		dateFromField.setText(res.getFromDate());
		dateToField.setText(res.getToDate());
		vehicleTypeComboBox.setSelectedIndex(veh.getTypeID()-1);
		boolean automatic = veh.isAutomatic();
		if(!automatic) gearTypeComboBox.setSelectedIndex(0); else gearTypeComboBox.setSelectedIndex(1);
		vehicleText.setText(veh.getMake() + " " + veh.getModel() + " (" + veh.getID() + ")" + "   Fuel: " + veh.getFuelName() + "   Automatic: " + veh.isAutomatic());
		userButton.setEnabled(false);
		
		//Setting values of reservation
		vehicleID = res.getVehicleID();
		userID = cust.getUserID();
		vehicleType = res.getTypeID();
		aut = veh.isAutomatic();
		fromDate = res.getFromDate();
		toDate = res.getToDate();
		
		//Containing values for the already created reservation
		final int oldResID = resID;
		final String oldFromDate = res.getFromDate();
		final String oldToDate = res.getToDate();
		final String oldVehicleID = veh.getID();
		final int oldVehicleType = res.getTypeID();
		
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isValidReservation()) {
					Controller.removeReservation(existingResID); //Removes the reservation to allow for checking available days
					if(Controller.findAvailableVehicle(vehicleType, aut, fromDate, toDate) != null) {
						// If a available vehicle is found
						Vehicle v = Controller.findAvailableVehicle(vehicleType, aut, fromDate, toDate);
						vehicleText.setText(v.getMake() + " " + v.getModel() + " (" + v.getID() + ")" + "   Fuel: " + v.getFuelName() + "   Automatic: " + v.isAutomatic());
						vehicleID = v.getID();
						vehicleType = v.getTypeID();
						okButton.setEnabled(true);
					}
					else {
						vehicleText.setText("(no vehicle available with specified parameters)");
						okButton.setEnabled(false);

					}
					Controller.newReservationByID(oldResID, userID, 0, oldVehicleType, oldVehicleID, oldFromDate, oldToDate, 0); //Sets old reservation back in
				}
				else {
					vehicleText.setText("(please set all parameters)");
					okButton.setEnabled(false);
				}
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.removeReservation(oldResID); //Removes old reservation
				Controller.newReservationByID(oldResID, userID, 0, vehicleType, vehicleID, fromDate, toDate, 0); //Creates the updated reservation
				stm.setData(Controller.getReservationList()); //Update reservation table with the updated reservation
				frame.dispose();
			}
		});

		frame.setVisible(true);

		return frame;
	}

	/**
	 * Opens a window making it possible to select a user for the reservation
	 */
	private void showCustomerSelector() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImages(View.systemIconList());
		frame.setBounds(0, 0, 650, 400);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Select Customer");

		JPanel content = (JPanel) frame.getContentPane();
		JPanel customerPanel = new TableView().getCustomerPanel(true, this, frame); //Gets a standard customertable, but with a select button
		content.add(customerPanel);

		frame.setVisible(true);
	}

	/**
	 * Sets the layout of all components.
	 * 
	 * @return panel Panel with all components added to the layout
	 */
	protected JPanel getFrameContent() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		layout.setConstraints(panel, c);

		//LABELS
		c.gridy = 0;
		c.weightx = 0;

		c.gridx = 0;
		layout.setConstraints(userLabel, c);
		panel.add(userLabel);

		c.gridy = 1;
		layout.setConstraints(dateLabel, c);
		panel.add(dateLabel);

		c.gridy = 2;
		layout.setConstraints(vehicleTypeLabel, c);
		panel.add(vehicleTypeLabel);

		c.gridy = 3;
		layout.setConstraints(gearTypeLabel, c);
		panel.add(gearTypeLabel);


		//PANELS
		c.gridx = 1;
		c.weightx = 1;

		c.gridy = 0;
		JPanel namePanel = getNamePanel();
		layout.setConstraints(namePanel, c );
		panel.add(namePanel);

		c.gridy = 1;
		JPanel periodPanel = getPriodPanel();
		layout.setConstraints(periodPanel, c);
		panel.add(periodPanel);

		c.gridy = 2;
		JPanel vehicleTypePanel = getVehicleTypePanel();
		layout.setConstraints(vehicleTypePanel, c);
		panel.add(vehicleTypePanel);

		c.gridy = 3;
		JPanel gearTypePanel = getGearTypePanel();
		layout.setConstraints(gearTypePanel, c);
		panel.add(gearTypePanel);

		//VEHICLE PANEL
		c.gridy = 4;
		c.gridx = 0;
		c.gridwidth = 2;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTH;
		JPanel vehiclePanel = getVehiclePanel();
		layout.setConstraints(vehiclePanel, c);
		panel.add(vehiclePanel);

		//FILL PANEL - fills up the remaining space in case of resizing
		JPanel fillPanel = new JPanel();
		fillPanel.setLayout(null);
		c.gridx = 0;
		c.gridy = 5;
		c.weighty = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.REMAINDER;
		layout.setConstraints(fillPanel, c);
		panel.add(fillPanel);

		//BUTTON PANEL
		JPanel buttonPanel = getButtonPanel();
		c.gridx = 0;
		c.gridy = 6;
		c.weighty = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(buttonPanel, c);
		panel.add(buttonPanel);

		return panel;
	}

	/**
	 * Creates the panel containing customer informations
	 * 
	 * @return panel The name panel including textfield and select button
	 */
	private JPanel getNamePanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		layout.setConstraints(panel, c);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		layout.setConstraints(userField, c);
		userField.setPreferredSize(new Dimension(userField.getPreferredSize().width, COMPONENT_HEIGHT));
		userField.setEditable(false);
		panel.add(userField);

		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST; //Behøves måske ikke
		userButton.setPreferredSize(new Dimension(userButton.getPreferredSize().width, COMPONENT_HEIGHT));
		layout.setConstraints(userButton, c);
		panel.add(userButton);

		return panel;
	}

	/**
	 * Creates the panel containing period informations
	 * 
	 * @return panel The period panel including textfields and calender buttuns (which does nothing) //TODO ok?
	 */
	private JPanel getPriodPanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		layout.setConstraints(panel, c);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		dateFromField.setPreferredSize(new Dimension(dateFromField.getPreferredSize().width, COMPONENT_HEIGHT));
		layout.setConstraints(dateFromField, c);
		panel.add(dateFromField);

		c.gridx = 1;
		c.weightx = 0;
		dateFromButton.setIcon(View.loadImageIcon("res/icons/calendar.png"));
		dateFromButton.setBorder(null);
		dateFromButton.setOpaque(true);
		dateFromButton.setContentAreaFilled(false);
		dateFromButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		layout.setConstraints(dateFromButton, c);
		panel.add(dateFromButton);

		c.gridx = 2;
		c.weightx = 0;
		layout.setConstraints(dateToLabel, c);
		panel.add(dateToLabel);

		c.gridx = 3;
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		dateToField.setPreferredSize(new Dimension(dateToField.getPreferredSize().width, COMPONENT_HEIGHT));
		layout.setConstraints(dateToField, c);
		panel.add(dateToField);

		c.gridx = 4;
		c.weightx = 0;
		dateToButton.setIcon(View.loadImageIcon("res/icons/calendar.png"));
		dateToButton.setBorder(null);
		dateToButton.setOpaque(true);
		dateToButton.setContentAreaFilled(false);
		dateToButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		layout.setConstraints(dateToButton, c);
		panel.add(dateToButton);

		return panel;
	}

	/**
	 * Creates the panel containing vehicle type information
	 * 
	 * @return panel The vehicle type panel including a ComboBox
	 */
	private JPanel getVehicleTypePanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		layout.setConstraints(panel, c);

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		vehicleTypeComboBox.setPreferredSize(
				new Dimension(vehicleTypeComboBox.getPreferredSize().width, COMPONENT_HEIGHT)
				);
		layout.setConstraints(vehicleTypeComboBox, c);
		panel.add(vehicleTypeComboBox);

		return panel;
	}

	/**
	 * Creates the panel containing vehicle gear type information
	 * 
	 * @return panel The vehicle gear type panel including a ComboBox
	 */	
	private JPanel getGearTypePanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		layout.setConstraints(panel, c);

		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		gearTypeComboBox.setPreferredSize(
				new Dimension(gearTypeComboBox.getPreferredSize().width, COMPONENT_HEIGHT)
				);
		layout.setConstraints(gearTypeComboBox, c);
		panel.add(gearTypeComboBox);

		return panel;
	}

	/**
	 * Creates the panel containing general vehicle information of selected vehicle
	 * 
	 * @return panel The general vehicle information of selected vehicle
	 */
	private JPanel getVehiclePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Vehicle"));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(vehicleText);

		return panel;
	}

	/**
	 * Creates the button panel
	 * 
	 * @return panel The button panel including Cancel, Find Vehicle and OK button
	 */
	private JPanel getButtonPanel() {
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, X_PAD, Y_PAD));
		buttons.add(cancelButton);
		buttons.add(findButton);
		buttons.add(okButton);
		okButton.setEnabled(false);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(buttons, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Sets the userID for reservation.
	 * 
	 * @param user UserID to be set
	 */
	public void setUserID(int user) {
		userID = user;
		Customer c = Controller.getCustomer(user);
		userField.setText(c.getFirstName() + " " + c.getLastName() + " (" + user + ")");
	}
	
	/**
	 * Checks if the current input data have the ability to be a valid reservation
	 * 
	 * @return true if all inputs are valid, otherwise false
	 */
	public boolean isValidReservation() {
		return vehicleTypeComboBox.getSelectedIndex() != -1 ||
				gearTypeComboBox.getSelectedIndex() != -1 ||
				dateFromValid == true ||
				dateToValid == true ||
				userID > -1;
	}
}