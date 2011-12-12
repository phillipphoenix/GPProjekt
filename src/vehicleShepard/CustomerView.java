package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class CustomerView extends Panel {
	private GridBagConstraints c;
	
	//Components
	private JLabel nameLabel = new JLabel("Name:");
	private JTextField nameFirstField = new JTextField("John");
	private JTextField nameLastField = new JTextField("Johnson");

	private JLabel addressLabel = new JLabel("Address:");
	private JTextArea addressArea = new JTextArea("Wall Street 8\n90210 Beverly Hills");

	private JLabel phoneLabel = new JLabel("Phone:");
	private JTextField phoneCodeField = new JTextField("+45");
	private JTextField phoneNumberField = new JTextField("82949292");

	private JLabel countryLabel = new JLabel("Country:");
	private JComboBox<String> countryComboBox = new JComboBox<String>();

	private JLabel licenseLabel = new JLabel("Drivers license no.:");
	private JTextField licenseField = new JTextField("F255-9215-0094");

	private JButton createButton = new JButton("Create customer");
	private JButton cancelButton = new JButton("Cancel");
	
	public CustomerView() {
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		c.ipady = Y_PAD;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
	}

	public JPanel newCustomerPanel() {
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
		
		//FILL PANEL - fills up the remaining space in case of resizing
		JPanel fillPanel = new JPanel();
		fillPanel.setLayout(null);
		//fillPanel.setBorder(new LineBorder(Color.RED));
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 2;
		c.weighty = 1;
		c.fill = GridBagConstraints.REMAINDER;
		layout.setConstraints(fillPanel, c);
		panel.add(fillPanel);

		//BUTTON PANEL
		JPanel buttonPanel = getButtonPanel();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.HORIZONTAL;
		layout.setConstraints(buttonPanel, c);
		panel.add(buttonPanel);

		return panel;
	}

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
		
		countryComboBox.addItem("USA");
		countryComboBox.addItem("Denmark");
		countryComboBox.addItem("France");
		countryComboBox.setPreferredSize(
				new Dimension(countryComboBox.getPreferredSize().width, COMPONENT_HEIGHT)
				);
		layout.setConstraints(countryComboBox, c);
		panel.add(countryComboBox);

		return panel;
	}

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
		
		addressArea.setBorder(new JTextField().getBorder());
		layout.setConstraints(addressArea, c);
		panel.add(addressArea);

		return panel;
	}

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

	private JPanel getPhonePanel() {
		JPanel panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		panel.setLayout(layout);
		c.weightx = 0.1;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		phoneCodeField.setSize(new Dimension(50, Panel.COMPONENT_HEIGHT));
		layout.setConstraints(phoneCodeField, c);
		c.gridx = 1;
		c.weightx = 0.9;
		layout.setConstraints(phoneNumberField, c);
		panel.add(phoneCodeField);
		panel.add(phoneNumberField);

		return panel;
	}

	private JPanel getButtonPanel() {
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, X_PAD, Y_PAD));
		buttons.add(cancelButton);
		buttons.add(createButton);

		JSeparator sep = new JSeparator();
		sep.setForeground(Color.GRAY);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(sep, BorderLayout.NORTH);
		panel.add(buttons, BorderLayout.SOUTH);

		return panel;
	}
}
