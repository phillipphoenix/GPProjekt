package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class MainView extends Panel {
	private Controller cont;

	private JButton newReservationButton = new JButton("New reservation...");
	private JButton listReservationButton = new JButton("Reservations");
	private JButton listCustomerButton = new JButton("Customers");
	private JButton listVehicleButton = new JButton("Vehicles");

	public MainView(Controller cont) {
		this.cont = cont;

		newReservationButton.setIcon(View.loadImageIcon("res/icons/calendar_add.png"));
		listReservationButton.setIcon(View.loadImageIcon("res/icons/calendar.png"));
		listCustomerButton.setIcon(View.loadImageIcon("res/icons/user.png"));
		listVehicleButton.setIcon(View.loadImageIcon("res/icons/car.png"));
	}

	public JPanel getPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.add(newReservationButton);
		buttons.add(listReservationButton);
		buttons.add(listCustomerButton);
		buttons.add(listVehicleButton);
		
		ReservationGraph graph = new ReservationGraph(cont.getReservationArrayList());
		
		panel.add(buttons, BorderLayout.NORTH);
		panel.add(graph, BorderLayout.CENTER);
		
		return panel;
	}

}
