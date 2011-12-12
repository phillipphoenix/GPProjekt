package vehicleShepard;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainView {
	private JButton newReservationButton = new JButton("New reservation...");
	private JButton listReservationButton = new JButton("Reservations");
	private JButton listCustomerButton = new JButton("Customers");
	private JButton listVehicleButton = new JButton("Vehicles");
	//Find
	
	public MainView() {
		newReservationButton.setIcon(View.loadImageIcon("res/icons/calendar_add.png"));
		listReservationButton.setIcon(View.loadImageIcon("res/icons/calendar.png"));
		listCustomerButton.setIcon(View.loadImageIcon("res/icons/user.png"));
		listVehicleButton.setIcon(View.loadImageIcon("res/icons/car.png"));
	}
	
	public JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel west = new JPanel();
		west.add(newReservationButton);
		west.add(listReservationButton);
		west.add(listCustomerButton);
		west.add(listVehicleButton);
		
		ReservationView center = new ReservationView();
		
		panel.add(west, BorderLayout.WEST);
		panel.add(center.newReservationPanel(), BorderLayout.CENTER);
		
		return panel;
	}
	
}
