package vehicleShepard;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
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
	private Controller controller;

	/**
	 * Creates the main frame.
	 */
	public View(Controller controller) {
		this.controller = controller;
		contentPane = (JPanel) getContentPane();
		contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));
		
		ReservationView reservation1 = new ReservationView();
		contentPane.add(reservation1.newReservationPanel());
		
		ReservationView reservation2 = new ReservationView();
		//contentPane.add(reservation2.viewReservationPanel());
		
		CustomerView customer = new CustomerView();
		//contentPane.add(customer.newCustomerPanel());
		/*
		ArrayList<Reservation> vehicle1 = new ArrayList<Reservation>();
		vehicle1.add(new Reservation(1, "FU83991", 1, 12, 2011, 4, 12, 2011));
		vehicle1.add(new Reservation(2, "FU83991", 5, 12, 2011, 5, 12, 2011));
		vehicle1.add(new Reservation(5, "FU83991", 6, 12, 2011, 12, 12, 2011));
		vehicle1.add(new Reservation(6, "FU83991", 13, 12, 2011, 13, 12, 2011));
		vehicle1.add(new Reservation(7, "FU83991", 16, 12, 2011, 20, 12, 2011));
		vehicle1.add(new Reservation(8, "FU83991", 21, 12, 2011, 23, 12, 2011));
		vehicle1.add(new Reservation(9, "FU83991", 24, 12, 2011, 31, 12, 2011));
		
		ArrayList<Reservation> vehicle2 = new ArrayList<Reservation>();
		vehicle2.add(new Reservation(3, "HE72111", 1, 12, 2011, 4, 12, 2011));
		vehicle2.add(new Reservation(4, "HE72111", 9, 12, 2011, 12, 12, 2011));
		vehicle2.add(new Reservation(10, "HE72111", 14, 12, 2011, 30, 12, 2011));
		vehicle2.add(new Reservation(11, "HE72111", 31, 12, 2011, 31, 12, 2011));
		
		ArrayList<Reservation> vehicle3 = new ArrayList<Reservation>();
		vehicle3.add(new Reservation(12, "FU83991", 26, 11, 2011, 4, 12, 2011));
		vehicle3.add(new Reservation(13, "FU83991", 5, 12, 2011, 17, 12, 2011));
		
		ArrayList<Reservation> vehicle4 = new ArrayList<Reservation>();
		vehicle4.add(new Reservation(14, "FU83991", 26, 12, 2011, 4, 1, 2012));
		vehicle4.add(new Reservation(15, "FU83991", 6, 1, 2012, 10, 1, 2012));
		
		ArrayList<ArrayList<Reservation>> reservations = new ArrayList<ArrayList<Reservation>>();
		reservations.add(vehicle1);
		reservations.add(vehicle2);
		reservations.add(vehicle3);
		reservations.add(vehicle4);
		
		ReservationGraph resGraph = new ReservationGraph(reservations);
		JPanel pp = new JPanel();
		pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
		//pp.setLayout(null);
		pp.add(resGraph);
		contentPane.add(pp);
		//contentPane.add(resGraph);*/

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
		JMenu menu1 = new JMenu("File");
		JMenuItem menu1_1 = new JMenuItem("Open");
		JMenuItem menu1_2 = new JMenuItem("Save");
		JMenuItem menu1_3 = new JMenuItem("Exit");
		menu1.add(menu1_1);
		menu1.add(menu1_2);
		menu1.add(new JSeparator());
		menu1.add(menu1_3);
		menuBar.add(menu1);
		menu1_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		//MENU 2
		JMenu menu2 = new JMenu("Edit");
		JMenuItem menu2_1 = new JMenuItem("New Reservation");
		JMenuItem menu2_2 = new JMenuItem("Two");
		JMenuItem menu2_3 = new JMenuItem("Three");
		menu2.add(menu2_1);
		menu2.add(menu2_2);
		menu2.add(menu2_3);
		menuBar.add(menu2);

		//MENU 3
		JMenu menu3 = new JMenu("Help");
		JMenuItem menu3_2 = new JCheckBoxMenuItem("Always on top");
		if(isAlwaysOnTopSupported() == false) menu3_2.setEnabled(false);
		JMenuItem menu3_3 = new JMenuItem("About " + TITLE);
		menu3.add(menu3_3);
		menu3.addSeparator();
		menu3.add(menu3_2);
		menuBar.add(menu3);
		menu3_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isAlwaysOnTop())
                    setAlwaysOnTop(false);
                else
                    setAlwaysOnTop(true);
			}
		});
		menu3_3.addActionListener(new ActionListener() {
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