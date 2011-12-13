package vehicleShepard;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TableView extends JFrame {
	private final static Object CUSTOMER[] = { "No.", "First Name", "Last Name" };
	private final static String RESERVATION[] = { "No.", "Two", "Three" };
	private final static String VEHICLE[] = { "No.", "Two", "Three" };
	private final static String VEHICLE_TYPE[] = { "No.", "Two", "Three" };
	
	private Object data[][];
	
	private JFrame frame = new JFrame();
	private JPanel content = new JPanel();
	private JTextField search = new JTextField();
	private JLabel searchLabel = new JLabel("Search:");
	private JTable table;
	
	public TableView(Object data[][]) {
		this.data = data;
		
		content.setLayout(new BorderLayout());
		
		JPanel searchPanel = new JPanel();
		searchPanel.add(searchLabel);
		searchPanel.add(search);
		content.add(searchPanel, BorderLayout.NORTH);
		
		frame.add(content);		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImages(View.systemIconList()); //Move to panel? Just the 16x16 maybe
		frame.setBounds(0, 0, 600, 500);
		frame.setLocationRelativeTo(null);
	}
	
	public JFrame showCustomerWindow() {
		JTable table = new JTable(data, CUSTOMER);
		content.add(table, BorderLayout.CENTER);
		
		frame.setTitle("Customers");
		frame.setVisible(true);
		
		return frame;
	}
}
