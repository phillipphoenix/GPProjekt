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

public class ReservationView extends ViewModel {
	private GridBagConstraints c = new GridBagConstraints();
	private StandardTableModel stm;
	private JFrame frame = new JFrame();
	private JPanel content = new JPanel();

	//Components
	private JLabel userLabel = new JLabel("Name:");
	private int userID; //Need to store userID seperatly, as the TextField contains both name and user id
	private JTextField userField = new JTextField();
	private JButton userButton = new JButton("Select");

	private JLabel dateLabel = new JLabel("Period:");
	private JLabel dateToLabel = new JLabel("to");
	private JTextField dateFromField = new JTextField();
	private JTextField dateToField = new JTextField();
	private JButton dateFromButton = new JButton();
	private JButton dateToButton = new JButton();

	private JLabel vehicleTypeLabel = new JLabel("Vehicle type:");
	private JComboBox<String> vehicleTypeComboBox = new JComboBox<String>();

	private JLabel gearTypeLabel = new JLabel("Gear type:");
	private JComboBox<String> gearTypeComboBox = new JComboBox<String>();

	private JLabel vehicleText = new JLabel("(no vehicle)");

	//Buttons
	private JButton cancelButton = new JButton("Cancel");
	private JButton findButton = new JButton("Find vehicle");
	private JButton okButton = new JButton("OK");

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
		
		findButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int vehicleType = vehicleTypeComboBox.getSelectedIndex();
				boolean automatic;
				if(gearTypeComboBox.getSelectedIndex() == 0) automatic = false;
				else automatic = true; //hvad nu hvis det ikke er selected
				String fromDate = dateFromField.getText();
				String toDate = dateToField.getText();

				if(vehicleTypeComboBox.getSelectedIndex() != -1 &&
						gearTypeComboBox.getSelectedIndex() != -1) {
					vehicleType = vehicleTypeComboBox.getSelectedIndex()+1;
					if(Controller.findAvailableVehicle(vehicleType, automatic, fromDate, toDate) != null) {
						Vehicle v = Controller.findAvailableVehicle(vehicleType, automatic, fromDate, toDate);
						vehicleText.setText(v.getMake() + " " + v.getModel() + " (" + v.getID() + ")" + "   Fuel: " + v.getFuelName() + "   Automatic: " + v.isAutomatic());
					}
					else {
						vehicleText.setText("(no vehicle available with specified parameters)");
					}
				}
			}
		});
	}

	public JFrame showCreateWindow() {
		frame.setTitle("New Reservation");

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();

			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userID = Integer.getInteger(userLabel.getText());
				int typeID = vehicleTypeComboBox.getSelectedIndex();
				String vehicleID = "NO89141"; //M���h
				String fromDate = dateFromField.getText();
				String toDate = dateToField.getText();
				int service = 1; //TODO What to do? :)

				Controller.newReservation(userID, typeID+1, vehicleID, fromDate, toDate, service);
			}
		});

		frame.setVisible(true);

		return frame;
	}

	public JFrame showExistingWindow(int resID) {
		frame.setTitle("Edit Reservation " + resID);

		//Setting values
		Reservation res = Controller.getReservation(resID);
		Customer cust = Controller.getCustomer(res.getUserID());
		userField.setText(cust.getFirstName() + " " + cust.getLastName() + " (" + res.getUserID() + ")");
		userID = res.getUserID();
		dateFromField.setText(res.getFromDate());
		dateToField.setText(res.getToDate());
		Vehicle v = Controller.getVehicle(res.getVehicleID());
		vehicleTypeComboBox.setSelectedIndex(v.getTypeID()-1);
		boolean automatic = v.isAutomatic();
		if(!automatic) gearTypeComboBox.setSelectedIndex(0); else gearTypeComboBox.setSelectedIndex(1);
		vehicleText.setText(v.getMake() + " " + v.getModel() + " (" + v.getID() + ")" + "   Fuel: " + v.getFuelName() + "   Automatic: " + v.isAutomatic());
		userButton.setEnabled(false);

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});

		frame.setVisible(true);

		return frame;
	}

	public JPanel getFrameContent() {
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
		//c.ipady = Y_PAD; //TODO M�ske Insets istedet

		//FILL PANEL - fills up the remaining space in case of resizing
		JPanel fillPanel = new JPanel();
		fillPanel.setLayout(null);
		//fillPanel.setBorder(new LineBorder(Color.RED));
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
		c.anchor = GridBagConstraints.WEST; //Beh�ves m�ske ikke
		userButton.setPreferredSize(new Dimension(userButton.getPreferredSize().width, COMPONENT_HEIGHT));
		layout.setConstraints(userButton, c);
		panel.add(userButton);

		return panel;
	}

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

	private JPanel getVehiclePanel() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder("Vehicle"));
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.add(vehicleText);

		return panel;
	}

	private JPanel getButtonPanel() {
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, X_PAD, Y_PAD));
		buttons.add(cancelButton);
		buttons.add(findButton);
		buttons.add(okButton);
		okButton.setEnabled(false);

		JSeparator sep = new JSeparator();
		sep.setForeground(Color.GRAY);

		JPanel panel = new JPanel(new BorderLayout());
		//panel.add(sep, BorderLayout.NORTH); //TODO Remove?
		panel.add(buttons, BorderLayout.SOUTH);

		return panel;
	}
}