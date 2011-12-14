package vehicleShepard;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	private	static final String TITLE = "Vehicle Shepherd";
	private static final String VERSION = "1.0";

	private JPanel contentPane;
	private Controller cont;
	private GridBagConstraints c;
	
	private JButton previous = new JButton("Previous");
	private JButton next = new JButton("Next");
	
	private JButton newButton = new JButton("New");
	private JButton editButton = new JButton("Edit");
	private JButton deleteButton = new JButton("Delete");

	/**
	 * Creates the main frame.
	 */
	public View(Controller cont) {
		this.cont = cont;
		//TODO tjek at der i controllern er forbindelse til db
		
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		contentPane = (JPanel) getContentPane();
		contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));
		
		// TODO Panel containing content OH Yeah!
		JPanel content = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		content.setLayout(layout);
		contentPane.add(content);

		// Reservation graph panel
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.5;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
		graphPanel.setBorder(new EtchedBorder());
		ReservationGraph graph = new ReservationGraph(cont.getReservationArrayList()); //TODO size after ContainerSize
		graphPanel.add(graph);
		layout.setConstraints(graphPanel, c);
		content.add(graphPanel, c);
		
		// Tables (tabbed pane)
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.5;
		c.fill = GridBagConstraints.BOTH;

		JTabbedPane tPane = new JTabbedPane();

		JPanel reservationPanel = new TableView(cont).getReservationPanel();
		reservationPanel.setName("Reservation");
		tPane.add(reservationPanel);
		
		JPanel customerPanel = new TableView(cont).getCustomerPanel();
		customerPanel.setName("Customer");
		tPane.add(customerPanel);
		
		JPanel vehiclePanel = new TableView(cont).getVehiclePanel();
		vehiclePanel.setName("Vehicle");
		tPane.add(vehiclePanel);
		
		layout.setConstraints(tPane, c);
		content.add(tPane);

		
		
		setJMenuBar(getMenu());		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		setIconImages(systemIconList());
		setBounds(0, 0, 800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Creates the menu bar.
	 */
	private JMenuBar getMenu() {
		JMenuBar menuBar = new JMenuBar();

		//MENU 1
		JMenu menu1 = new JMenu("Main");
		JMenuItem menu1_1 = new JCheckBoxMenuItem("Always on top");
		if(isAlwaysOnTopSupported() == false) menu1_1.setEnabled(false);
		JMenuItem menu1_2 = new JMenuItem("Exit");
		menu1.add(menu1_1);
		menu1.addSeparator();
		menu1.add(menu1_2);
		menuBar.add(menu1);
		menu1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isAlwaysOnTop())
					setAlwaysOnTop(false);
				else
					setAlwaysOnTop(true);
			}
		});
		menu1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		//MENU 2
		JMenu menu2 = new JMenu("Reservation");
		JMenuItem menu2_1 = new JMenuItem("New Reservation...");
		JMenuItem menu2_2 = new JMenuItem("View Reservations");
		menu2.add(menu2_1);
		menu2.add(menu2_2);
		menuBar.add(menu2);
		menu2_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReservationView rv = new ReservationView(cont);
				rv.showCreateWindow();
			}
		});

		//MENU 3
		JMenu menu3 = new JMenu("Customer");
		JMenuItem menu3_1 = new JMenuItem("New Customer...");
		JMenuItem menu3_2 = new JMenuItem("View Customers");
		menu3.add(menu3_1);
		menu3.add(menu3_2);
		menuBar.add(menu3);
		menu3_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CustomerView cv = new CustomerView(cont);
				cv.showCreateWindow();
			}
		});

		//MENU 4
		JMenu menu4 = new JMenu("Vehicle");
		JMenuItem menu4_1 = new JMenuItem("New Vehicle...");
		JMenuItem menu4_2 = new JMenuItem("New Vehicle Type...");
		JMenuItem menu4_3 = new JMenuItem("View Vehicles");
		JMenuItem menu4_4 = new JMenuItem("View Vehicle Types");
		menu4.add(menu4_1);
		menu4.add(menu4_2);
		menu4.add(menu4_3);
		menu4.add(menu4_4);
		menuBar.add(menu4);

		//MENU 5
		JMenu menu5 = new JMenu("Service");
		JMenuItem menu5_1 = new JMenuItem("New Service...");
		JMenuItem menu5_2 = new JMenuItem("View Service");
		menu5.add(menu5_1);
		menu5.add(menu5_2);
		menuBar.add(menu5);

		//MENU 6
		JMenu menu6 = new JMenu("About");
		JMenuItem menu6_1 = new JMenuItem("About " + TITLE);
		menu6.add(menu6_1);
		menuBar.add(menu6);
		menu6_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});

		return menuBar;
	}

	/**
	 * Sets all relevant system icons (e.g. taskbar, window)
	 */
	public static ArrayList<Image> systemIconList() {
		ArrayList<Image> iconList = new ArrayList<Image>();
		iconList.add(loadImage("res/icons/sys_small.png"));
		iconList.add(loadImage("res/icons/sys_medium.png"));
		iconList.add(loadImage("res/icons/sys_large.png"));

		return iconList;
	}

	/**
	 * Loads a image from source folder.
	 * 
	 * @param url The URL of the image to be loaded.
	 * @return The selected image as Image.
	 */
	public final static Image loadImage(String url) {
		return Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemClassLoader().getResource(url));
	}

	/**
	 * Loads a image from source folder.
	 * 
	 * @param url The URL of the image to be loaded.
	 * @return The selected image as ImageIcon.
	 */
	public final static ImageIcon loadImageIcon(String url) {
		return new ImageIcon(ClassLoader.getSystemClassLoader().getResource(url));
	}

	private void showAboutDialog() {
		JOptionPane.showMessageDialog(this, TITLE + " " + VERSION + "\n\n Is Awesome and stuff",
				"About " + TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
}
