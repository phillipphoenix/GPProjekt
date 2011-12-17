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
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
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

	private JButton previousMonthButton = new JButton("<");
	private JButton nextMonthButton = new JButton(">");
	private JSpinner yearSpinner;
	private SpinnerDateModel sdm;

	private Date selectedFromDate, selectedToDate;

	/**
	 * Creates the main frame.
	 */
	public View(Controller controller) {
		contentPane = (JPanel) getContentPane();
		contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));

		// TODO Panel containing content OH Yeah!
		content = new JPanel();
		content.setLayout(layout);
		contentPane.add(content);

		// Graph buttons
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.anchor = GridBagConstraints.WEST;
		layout.setConstraints(previousMonthButton, c);
		content.add(previousMonthButton, c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.NORTH;
		GregorianCalendar spinnerCalendar = (GregorianCalendar) Calendar.getInstance();
		Date now = spinnerCalendar.getTime();
		spinnerCalendar.add(Calendar.YEAR, -200);
		Date spinnerStart = spinnerCalendar.getTime();
		spinnerCalendar.add(Calendar.YEAR, 300);
		Date spinnerEnd = spinnerCalendar.getTime();

		final SpinnerDateModel sdm = new SpinnerDateModel(now, spinnerStart, spinnerEnd, Calendar.YEAR);
		yearSpinner = new JSpinner(sdm);
		yearSpinner.setEditor(new JSpinner.DateEditor(yearSpinner, "yyyy"));
		
		final GregorianCalendar selCal = (GregorianCalendar) Calendar.getInstance(); //TODO Er det godt med final her?
		final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		
		selCal.set(selCal.get(Calendar.YEAR), selCal.get(Calendar.MONTH), 1);
		selectedFromDate = selCal.getTime();
		selCal.set(selCal.get(Calendar.YEAR), selCal.get(Calendar.MONTH), selCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		selectedToDate = selCal.getTime();
		
		String sqlFromDate = new java.sql.Date(selectedFromDate.getTime()).toString();
		String sqlToDate = new java.sql.Date(selectedFromDate.getTime()).toString();
		graph = new ReservationGraph(Controller.getReservationArrayList(sqlFromDate, sqlToDate), sqlFromDate, sqlToDate); //TODO size after ContainerSize
		//graph = new ReservationGraph(Controller.getReservationArrayList("2012-01-01", "2012-01-31")); //TODO size after ContainerSize
		//graph = new ReservationGraph(Controller.getReservationArrayList(sqlFromDate, sqlToDate)); //TODO size after ContainerSize
		//graph = new ReservationGraph2(); //TODO size after ContainerSize
		//graph = new ReservationGraph3(Controller.getReservationArrayList(sqlFromDate, sqlToDate)); //TODO size after ContainerSize
		
		previousMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selYear = Integer.parseInt(yearFormat.format(yearSpinner.getValue()));
				selCal.set(selYear, selCal.get(Calendar.MONTH)-1, 1);
				selectedFromDate = selCal.getTime();
				sdm.setValue(selCal.getTime());
				selCal.set(selYear, selCal.get(Calendar.MONTH), selCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				selectedToDate = selCal.getTime();
			}
		});
		
		yearSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int selYear = Integer.parseInt(yearFormat.format(yearSpinner.getValue()));
				selCal.set(selYear, selCal.get(Calendar.MONTH), 1);
				selectedFromDate = selCal.getTime();
				selCal.set(selYear, selCal.get(Calendar.MONTH), selCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				selectedToDate = selCal.getTime();
				
				System.out.println("selectedFromDate=" + new java.sql.Date(selectedFromDate.getTime()) + " selectedToDate=" + new java.sql.Date(selectedToDate.getTime())); //Just for debug
				graph.setView(new java.sql.Date(selectedFromDate.getTime())+"", new java.sql.Date(selectedToDate.getTime())+"");
			}
		});
		
		nextMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selCal.set(selCal.get(Calendar.YEAR), selCal.get(Calendar.MONTH)+1, 1);
				selectedFromDate = selCal.getTime();
				selCal.set(selCal.get(Calendar.YEAR), selCal.get(Calendar.MONTH), selCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				selectedToDate = selCal.getTime();
				sdm.setValue(selCal.getTime());
			}
		});

		layout.setConstraints(yearSpinner, c);
		content.add(yearSpinner, c);

		c.gridx = 2;
		c.anchor = GridBagConstraints.EAST;
		layout.setConstraints(nextMonthButton, c);
		content.add(nextMonthButton, c);

		// Graph panel
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.5;
		c.weightx = 1;
		c.gridwidth = 3;
		c.fill = GridBagConstraints.BOTH;
		JPanel graphPanel = new JPanel();
		graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
		graphPanel.setBorder(new EtchedBorder());
		
		
		graphPanel.add(graph);
		layout.setConstraints(graphPanel, c);
		content.add(graphPanel, c);

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

	private void showAboutDialog() {
		JOptionPane.showMessageDialog(this, TITLE + " " + VERSION + "\n\n Is Awesome and stuff",
				"About " + TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
}
