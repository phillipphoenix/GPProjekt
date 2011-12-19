package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Defines the create and edit customer windows.
 * 
 * @author Anders
 *
 */
public class CustomerView extends ViewModel {
	private GridBagConstraints c = new GridBagConstraints();
	private StandardTableModel ctm;

	//Content
	private JFrame frame = new JFrame();
	private JPanel content = new JPanel();

	//Components
	private JLabel nameLabel = new JLabel("Name:");
	private JTextField nameFirstField = new JTextField();
	private JTextField nameLastField = new JTextField();

	private JLabel addressLabel = new JLabel("Address:");
	private JTextField addressField = new JTextField("");

	private JLabel phoneLabel = new JLabel("Phone:");
	private JTextField phoneCodeField = new JTextField("");
	private JTextField phoneNumberField = new JTextField("");

	private JLabel countryLabel = new JLabel("Country:");
	private JComboBox<String> countryComboBox = new JComboBox<String>();

	private JLabel licenseLabel = new JLabel("Drivers license no.:");
	private JTextField licenseField = new JTextField("");

	private JLabel licenseExpLabel = new JLabel("License expiration date:");
	private JTextField licenseExpField = new JTextField("");
	private boolean expDateValid;

	//Buttons
	private JButton cancelButton = new JButton("Cancel");
	private JButton okButton = new JButton("OK");

	/**
	 * Initialize all components
	 * 
	 * @param stm TableModel for the table
	 */
	public CustomerView(StandardTableModel ctm) {
		this.ctm = ctm;

		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		c.ipady = Y_PAD;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImages(View.systemIconList());
		frame.setBounds(0, 0, 400, 300);
		frame.setLocationRelativeTo(null);

		content = getFrameContent();
		content.setBorder(new EmptyBorder(6, 6, 6, 6));
		frame.add(content);

		licenseExpField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				if(View.isValidDate(licenseExpField.getText())) {
					expDateValid = true;
					licenseExpField.setForeground(Color.BLACK);
				}
				else {
					expDateValid = false;
					licenseExpField.setForeground(Color.RED);
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}

	/**
	 * Creates the final window for creation of a new customer
	 * 
	 * @return frame The final New Customer window
	 */
	public JFrame showCreateWindow() {
		frame.setTitle("New Customer");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] info = new String[8];

				info[0] = phoneNumberField.getText();
				info[1] = phoneCodeField.getText();
				info[2] = addressField.getText();
				info[3] = (String) countryComboBox.getSelectedItem();
				info[4] = nameFirstField.getText();
				info[5] = nameLastField.getText();
				info[6] = licenseField.getText();
				info[7] = licenseExpField.getText();
				System.out.println("Jo");

				if(isValidCustomer()) {
					Controller.newCustomer(Integer.parseInt(info[0]), Integer.parseInt(info[1]), info[2], info[3], info[4], info[5], info[6], info[7]);
					ctm.setData(Controller.getCustomerList());
					frame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "You have entered something wrong", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		frame.setVisible(true);

		return frame;
	}

	/**
	 * Creates the final window for editing of existing customer.
	 * Also sets the value of all components in the window.
	 * 
	 * @return frame The final Edit Customer
	 */
	public JFrame showExistingWindow(final int userID) {
		frame.setTitle("Edit Customer");

		Customer cust = Controller.getCustomer(userID);

		phoneNumberField.setText(""+cust.getPhone());
		phoneCodeField.setText(""+cust.getPhoneCode());
		addressField.setText(cust.getAddress());
		countryComboBox.setSelectedItem(cust.getCountry());
		nameFirstField.setText(cust.getFirstName());
		nameLastField.setText(cust.getLastName());
		licenseField.setText(cust.getLicenseNumber());
		licenseExpField.setText(cust.getLicenseExpDate());

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] info = new String[8];

				info[0] = phoneNumberField.getText();
				info[1] = phoneCodeField.getText();
				info[2] = addressField.getText();
				info[3] = (String) countryComboBox.getSelectedItem();
				info[4] = nameFirstField.getText();
				info[5] = nameLastField.getText();
				info[6] = licenseField.getText();
				info[7] = licenseExpField.getText();

				if(isValidCustomer()) {
					Controller.updateCustomer(userID, Integer.parseInt(info[0]), Integer.parseInt(info[1]), info[2], info[3], info[4], info[5], info[6], info[7]);
					ctm.setData(Controller.getCustomerList());
					frame.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "The entered values are not valid", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		frame.setVisible(true);

		return frame;
	}

	/**
	 * Sets the layout of all components.
	 * 
	 * @return panel Panel with all components added to the layout
	 */
	public JPanel getFrameContent() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		panel.setLayout(layout);
		layout.setConstraints(panel, c);

		//LABELS
		c.gridy = 0;
		c.weightx = 0;

		c.gridx = 0;
		layout.setConstraints(phoneLabel, c);
		panel.add(phoneLabel);

		c.gridy = 1;
		layout.setConstraints(nameLabel, c);
		panel.add(nameLabel);

		c.gridy = 2;
		layout.setConstraints(addressLabel, c);
		panel.add(addressLabel);

		c.gridy = 3;
		layout.setConstraints(countryLabel, c);
		panel.add(countryLabel);

		c.gridy = 4;
		layout.setConstraints(licenseLabel, c);
		panel.add(licenseLabel);

		c.gridy = 5;
		layout.setConstraints(licenseExpLabel, c);
		panel.add(licenseExpLabel);

		//PANELS
		c.gridx = 1;
		c.weightx = 1;

		c.gridy = 0;
		JPanel phonePanel = getPhonePanel();
		layout.setConstraints(phonePanel, c);
		panel.add(phonePanel);

		c.gridy = 1;
		JPanel namePanel = getNamePanel();
		layout.setConstraints(namePanel, c );
		panel.add(namePanel);

		c.gridy = 2;
		JPanel addressPanel = getAddressPanel();
		layout.setConstraints(addressPanel, c );
		panel.add(addressPanel);

		c.gridy = 3;
		JPanel countryPanel = getCountryPanel();
		layout.setConstraints(countryPanel, c );
		panel.add(countryPanel);

		c.gridy = 4;
		JPanel licensePanel = getLicensePanel();
		layout.setConstraints(licensePanel, c );
		panel.add(licensePanel);

		c.gridy = 5;
		JPanel licenseExpPanel = getLicenseExpPanel();
		layout.setConstraints(licenseExpPanel, c );
		panel.add(licenseExpPanel);


		//FILL PANEL - fills up the remaining space in case of resizing
		JPanel fillPanel = new JPanel();
		fillPanel.setLayout(null);
		//fillPanel.setBorder(new LineBorder(Color.RED));
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.weighty = 1;
		c.fill = GridBagConstraints.REMAINDER;
		layout.setConstraints(fillPanel, c);
		panel.add(fillPanel);

		//BUTTON PANEL
		JPanel buttonPanel = getButtonPanel();
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(buttonPanel, c);
		panel.add(buttonPanel);

		return panel;
	}

	/**
	 * Creates the panel containing license number informations
	 * 
	 * @return panel The license number panel including textfield
	 */
	private JPanel getLicensePanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		layout.setConstraints(licenseField, c);
		panel.add(licenseField);

		return panel;
	}

	/**
	 * Creates the panel containing license expiration informations
	 * 
	 * @return panel The license expiration panel including textfield
	 */
	private JPanel getLicenseExpPanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		layout.setConstraints(licenseExpField, c);
		panel.add(licenseExpField);

		return panel;
	}

	/**
	 * Creates the panel containing country information
	 * 
	 * @return panel The country panel including ComboBox with all countries
	 */
	private JPanel getCountryPanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		String[] countries = { //TODO skrump!
				"Afghanistan",
				"Albania",
				"Algeria",
				"Andorra",
				"Angola",
				"Antigua and Barbuda",
				"Argentina",
				"Armenia",
				"Australia",
				"Austria",
				"Azerbaijan",
				"Bahamas",
				"Bahrain",
				"Bangladesh",
				"Barbados",
				"Belarus",
				"Belgium",
				"Belize",
				"Benin",
				"Bhutan",
				"Bolivia",
				"Bosnia and Herzegovina",
				"Botswana",
				"Brazil",
				"Brunei",
				"Bulgaria",
				"Burkina Faso",
				"Burundi",
				"Cambodia",
				"Cameroon",
				"Canada",
				"Cape Verde",
				"Central African Republic",
				"Chad",
				"Chile",
				"China",
				"Colombi",
				"Comoros",
				"Congo (Brazzaville)",
				"Congo",
				"Costa Rica",
				"Cote d'Ivoire",
				"Croatia",
				"Cuba",
				"Cyprus",
				"Czech Republic",
				"Denmark",
				"Djibouti",
				"Dominica",
				"Dominican Republic",
				"East Timor (Timor Timur)",
				"Ecuador",
				"Egypt",
				"El Salvador",
				"Equatorial Guinea",
				"Eritrea",
				"Estonia",
				"Ethiopia",
				"Fiji",
				"Finland",
				"France",
				"Gabon",
				"Gambia, The",
				"Georgia",
				"Germany",
				"Ghana",
				"Greece",
				"Grenada",
				"Guatemala",
				"Guinea",
				"Guinea-Bissau",
				"Guyana",
				"Haiti",
				"Honduras",
				"Hungary",
				"Iceland",
				"India",
				"Indonesia",
				"Iran",
				"Iraq",
				"Ireland",
				"Israel",
				"Italy",
				"Jamaica",
				"Japan",
				"Jordan",
				"Kazakhstan",
				"Kenya",
				"Kiribati",
				"Korea, North",
				"Korea, South",
				"Kuwait",
				"Kyrgyzstan",
				"Laos",
				"Latvia",
				"Lebanon",
				"Lesotho",
				"Liberia",
				"Libya",
				"Liechtenstein",
				"Lithuania",
				"Luxembourg",
				"Macedonia",
				"Madagascar",
				"Malawi",
				"Malaysia",
				"Maldives",
				"Mali",
				"Malta",
				"Marshall Islands",
				"Mauritania",
				"Mauritius",
				"Mexico",
				"Micronesia",
				"Moldova",
				"Monaco",
				"Mongolia",
				"Morocco",
				"Mozambique",
				"Myanmar",
				"Namibia",
				"Nauru",
				"Nepa",
				"Netherlands",
				"New Zealand",
				"Nicaragua",
				"Niger",
				"Nigeria",
				"Norway",
				"Oman",
				"Pakistan",
				"Palau",
				"Panama",
				"Papua New Guinea",
				"Paraguay",
				"Peru",
				"Philippines",
				"Poland",
				"Portugal",
				"Qatar",
				"Romania",
				"Russia",
				"Rwanda",
				"Saint Kitts and Nevis",
				"Saint Lucia",
				"Saint Vincent",
				"Samoa",
				"San Marino",
				"Sao Tome and Principe",
				"Saudi Arabia",
				"Senegal",
				"Serbia and Montenegro",
				"Seychelles",
				"Sierra Leone",
				"Singapore",
				"Slovakia",
				"Slovenia",
				"Solomon Islands",
				"Somalia",
				"South Africa",
				"Spain",
				"Sri Lanka",
				"Sudan",
				"Suriname",
				"Swaziland",
				"Sweden",
				"Switzerland",
				"Syria",
				"Taiwan",
				"Tajikistan",
				"Tanzania",
				"Thailand",
				"Togo",
				"Tonga",
				"Trinidad and Tobago",
				"Tunisia",
				"Turkey",
				"Turkmenistan",
				"Tuvalu",
				"Uganda",
				"Ukraine",
				"United Arab Emirates",
				"United Kingdom",
				"United States",
				"Uruguay",
				"Uzbekistan",
				"Vanuatu",
				"Vatican City",
				"Venezuela",
				"Vietnam",
				"Yemen",
				"Zambia",
				"Zimbabwe"
		};

		for(String country : countries) {
			countryComboBox.addItem(country);
		}

		countryComboBox.setPreferredSize(
				new Dimension(countryComboBox.getPreferredSize().width, COMPONENT_HEIGHT)
				);
		countryComboBox.setSelectedIndex(-1);
		layout.setConstraints(countryComboBox, c);
		panel.add(countryComboBox);

		return panel;
	}

	/**
	 * Creates the panel containing address informations
	 * 
	 * @return panel The address panel including textfield
	 */
	private JPanel getAddressPanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		addressField.setBorder(new JTextField().getBorder());
		layout.setConstraints(addressField, c);
		panel.add(addressField);

		return panel;
	}

	/**
	 * Creates the panel containing name informations
	 * 
	 * @return panel The name panel including two textfields (firstname, lastname)
	 */
	private JPanel getNamePanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 0.5;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		layout.setConstraints(nameFirstField, c);
		c.gridx = 1;
		layout.setConstraints(nameLastField, c);
		panel.add(nameFirstField);
		panel.add(nameLastField);

		return panel;
	}

	/**
	 * Creates the phone panel containing phonenumber and phone code informations
	 * 
	 * @return panel The phone panel including two textfield (code, phone number)
	 */
	private JPanel getPhonePanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = X_PAD;
		c.fill = GridBagConstraints.HORIZONTAL;

		JLabel plus = new JLabel("+");
		layout.setConstraints(plus, c);
		panel.add(plus);

		c.gridx = 1;
		c.weightx = 0.2;
		layout.setConstraints(phoneCodeField, c);
		panel.add(phoneCodeField);

		c.gridx = 2;
		c.weightx = 0.8;
		layout.setConstraints(phoneNumberField, c);

		panel.add(phoneNumberField);

		return panel;
	}

	/**
	 * Creates the button panel
	 * 
	 * @return panel The button panel including Cancel and OK button
	 */
	private JPanel getButtonPanel() {
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, X_PAD, Y_PAD));
		buttons.add(cancelButton);
		buttons.add(okButton);

		JSeparator sep = new JSeparator();
		sep.setForeground(Color.GRAY);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(sep, BorderLayout.NORTH);
		panel.add(buttons, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Checks if the current input data have the ability to be a valid customer
	 * 
	 * @return true if all inputs are valid, otherwise false
	 */
	private boolean isValidCustomer() {
		String[] info = new String[8];

		info[0] = phoneNumberField.getText();
		info[1] = phoneCodeField.getText();
		info[2] = addressField.getText();
		info[3] = (String) countryComboBox.getSelectedItem();
		info[4] = nameFirstField.getText();
		info[5] = nameLastField.getText();
		info[6] = licenseField.getText();
		info[7] = licenseExpField.getText();

		try {
			Integer.parseInt(phoneNumberField.getText());
			Integer.parseInt(phoneCodeField.getText());
		} catch (NumberFormatException nfe) {
			System.out.println("OMG FALSE");
			return false;
		}

		if(info[0].length() <= 11 && info[0].length() > 0 &&
				info[1].length() < 4 && info[1].length() > 0 &&
				info[2].length() < 255 && info[2].length() > 0 &&
				countryComboBox.getSelectedIndex() != -1 &&
				info[4].length() < 255 && info[4].length() > 0 &&
				info[5].length() < 255 && info[5].length() > 0 &&
				info[6].length() < 255 && info[6].length() > 0 &&
				View.isValidDate(info[7])) {

			return true;
		}

		return false;
	}
}