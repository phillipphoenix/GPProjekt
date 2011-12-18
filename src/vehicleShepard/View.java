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
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	private	static final String TITLE = "Vehicle Shepherd";
	private static final String VERSION = "1.0";

	private GridBagConstraints c = new GridBagConstraints();
	private GridBagLayout layout = new GridBagLayout();
	private JPanel contentPane;
	private JPanel content;
	private ReservationGraph graph;

	//Date spinner
	private JSpinner dateSpinner;
	private SpinnerDateModel sdm;

	//Date fields
	private Date selectedFromDate, selectedToDate;
	private GregorianCalendar selectedCal; //TODO Er det godt med final her?
	private SimpleDateFormat yearFormat;
	private SimpleDateFormat monthFormat;
	private String sqlFromDate;
	private String sqlToDate;

	/**
	 * Creates the main frame.
	 */
	public View(Controller controller) {
		contentPane = (JPanel) getContentPane();
		contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

		content = new JPanel();
		content.setLayout(layout);
		contentPane.add(content);
		
		
		// Spinner
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 6;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.EAST;
		
		GregorianCalendar spinnerCalendar = (GregorianCalendar) Calendar.getInstance();
		Date now = spinnerCalendar.getTime();
		spinnerCalendar.add(Calendar.YEAR, -200);
		Date spinnerStart = spinnerCalendar.getTime();
		spinnerCalendar.add(Calendar.YEAR, 300);
		Date spinnerEnd = spinnerCalendar.getTime();
		sdm = new SpinnerDateModel(now, spinnerStart, spinnerEnd, Calendar.YEAR);
		dateSpinner = new JSpinner(sdm);
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MMMM-yyyy"));
		
		selectedCal = (GregorianCalendar) Calendar.getInstance();
		yearFormat = new SimpleDateFormat("yyyy");
		monthFormat = new SimpleDateFormat("MM");
		
		dateSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				updateSelectedDate();
				//System.out.println("selectedFromDate=" + new java.sql.Date(selectedFromDate.getTime()) + " selectedToDate=" + new java.sql.Date(selectedToDate.getTime())); //TODO Just for debug
				graph.setView(new java.sql.Date(selectedFromDate.getTime())+"", new java.sql.Date(selectedToDate.getTime())+"");
			}
		});
		
		layout.setConstraints(dateSpinner, c);
		content.add(dateSpinner, c);
		
		c.gridx = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.WEST;
		JButton updateButton = new JButton();
		updateButton.setIcon(loadImageIcon("res/icons/arrow_refresh.png"));
		updateButton.setBorder(null);
		updateButton.setOpaque(true);
		updateButton.setContentAreaFilled(false);
		updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				graph.setNewData(Controller.getReservationArrayList());
			}
		});
		
		layout.setConstraints(updateButton, c);
		content.add(updateButton, c);


		// Graph panel
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.5;
		c.weightx = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
		
		updateSelectedDate();
		
		graph = new ReservationGraph(Controller.getReservationArrayList(), sqlFromDate, sqlToDate);
		graph.setView(new java.sql.Date(selectedFromDate.getTime())+"", new java.sql.Date(selectedToDate.getTime())+"");
		graphPanel.add(graph);
		JScrollPane sp = new JScrollPane(graphPanel);
		sp.setBorder(null);
		layout.setConstraints(new JScrollPane(sp), c);
		content.add(sp, c);

		
		// Tables (tabbed pane)
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;

		JTabbedPane tPane = new JTabbedPane();

		JPanel reservationPanel = new TableView().getReservationPanel();
		tPane.addTab("Reservations", loadImageIcon("res/icons/calendar.png"), reservationPanel);

		JPanel customerPanel = new TableView().getCustomerPanel();
		tPane.addTab("Customers", loadImageIcon("res/icons/user.png"), customerPanel);

		JPanel vehiclePanel = new TableView().getVehiclePanel();
		tPane.addTab("Vehicles", loadImageIcon("res/icons/car.png"), vehiclePanel);

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
	
	protected void updateSelectedDate() {
		int selYear = Integer.parseInt(yearFormat.format(dateSpinner.getValue()));
		int selMonth = Integer.parseInt(monthFormat.format(dateSpinner.getValue()));
		selectedCal.set(selYear, selMonth-1, 1);
		selectedFromDate = selectedCal.getTime();
		sqlFromDate = new java.sql.Date(selectedFromDate.getTime()).toString();
		selectedCal.set(selYear, selMonth-1, selectedCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		selectedToDate = selectedCal.getTime();
		sqlToDate = new java.sql.Date(selectedFromDate.getTime()).toString();
		
		System.out.println(selectedCal.getActualMaximum(Calendar.DAY_OF_MONTH)); //TODO For debug
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
	
	public static boolean isValidDate(String date) { //TODO http://www.dreamincode.net/forums/topic/14886-date-validation-using-simpledateformat/
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date testDate = null;
		try {
			testDate = dFormat.parse(date);
		} catch (ParseException e) {
			return false;
		}

		if (dFormat.format(testDate).equals(date) == false) {
			return false;
		}

		return true;
	}

	private void showAboutDialog() {
		String msg = "A exam project by\nLauge Djuraas (ladj@itu.dk)\nPhillip Phoelich (ppho@itu.dk)\nAnders Højmark (ahoe@itu.dk)\n\nIT University of Copenhagen\nDecember 2011";
		JOptionPane.showMessageDialog(this, TITLE + " " + VERSION + "\n\n" + msg,
				"About " + TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
}
