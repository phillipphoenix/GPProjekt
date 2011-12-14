package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomerView extends ViewModel {
	private GridBagConstraints c;
	private Controller cont;
	
	//Components
	private JLabel nameLabel = new JLabel("Name:");
	private JTextField nameFirstField = new JTextField();
	private JTextField nameLastField = new JTextField();

	private JLabel addressLabel = new JLabel("Address:");
	private JTextArea addressArea = new JTextArea("");

	private JLabel phoneLabel = new JLabel("Phone:");
	private JTextField phoneCodeField = new JTextField("");
	private JTextField phoneNumberField = new JTextField("");

	private JLabel countryLabel = new JLabel("Country:");
	private JComboBox<String> countryComboBox = new JComboBox<String>();

	private JLabel licenseLabel = new JLabel("Drivers license no.:");
	private JTextField licenseField = new JTextField("");
	
	private JLabel licenseExpLabel = new JLabel("License expiration date:");
	private JTextField licenseExpField = new JTextField("");

	//Buttons
	private JButton createButton = new JButton("Create customer");
	private JButton cancelButton = new JButton("Cancel");
	
	public CustomerView(Controller cont) {
		this.cont = cont;
		
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		c.ipady = Y_PAD;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
	}
	
	public JFrame showCreateWindow() {
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("New Customer");
		frame.setIconImages(View.systemIconList()); //Move to panel? Just the 16x16 maybe
		frame.setBounds(0, 0, 400, 300);
		frame.setLocationRelativeTo(null);
		
		JPanel content = getCreatePanel();
		content.setBorder(new EmptyBorder(6, 6, 6, 6));
		frame.add(content);
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] info = new String[8];
				
				info[0] = phoneNumberField.getText();
				info[1] = phoneCodeField.getText();
				info[2] = addressArea.getText();
				info[3] = (String) countryComboBox.getSelectedItem();
				info[4] = nameFirstField.getText();
				info[5] = nameLastField.getText();
				info[6] = licenseField.getText();
				info[7] = licenseExpField.getText();
				
				boolean error = false;
				
				for(int i = 0; i < info.length; i++) {
					if(info[i].length() < 1) {
						error = true;
					}
				}
				
				if(error == false) {
					cont.newCustomer(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7]);
					frame.dispose();
				}
			}
		});
		
		frame.setVisible(true);
		
		return frame;
	}
	
	public JFrame showExistingWindow(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel getCreatePanel() {
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
	
	public JPanel getExistingPanel(int userID) {
		// TODO Auto-generated method stub
		return null;
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

		phoneCodeField.setSize(new Dimension(50, ViewModel.COMPONENT_HEIGHT));
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
