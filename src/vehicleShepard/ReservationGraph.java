package vehicleShepard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * Defines the reservation graph
 *
 */
public class ReservationGraph extends JPanel {

	//Start position of graph view (area of reservation rectangles)
	private static final int GRAPH_X_POS = 100;
	private static final int GRAPH_Y_POS = 15;

	//Single "bar" properties
	private static final int BAR_HEIGHT = 11;
	private static final int X_PADDING = 20;
	private static final int Y_PADDING = 5;
	private static final int SPACING = 2;
	private static final int UNIT = 20;

	//Number constants
	private final static int AREA_RESERVATION_ID = 0;
	private final static int AREA_MONTH = 1;
	private final static int AREA_YEAR = 2;
	private final static int AREA_X = 3;
	private final static int AREA_Y = 4;
	private final static int AREA_WIDTH = 5;
	private final static int AREA_HEIGHT = 6;

	//Reservation information
	private ArrayList<ArrayList<Reservation>> allRes;
	private ArrayList<int[]> resOverlay = new ArrayList<int[]>();

	//Information about the selected view
	private String selectedStartDate;
	private int selectedYear;
	private int selectedMonth;
	private String selectedEndDate;
	private int selectedMaximumDayInMonth;

	/**
	 * Sets reservation to be drawn and a period to be shown.
	 * Also adds a MouseListener, to make the graph clickable.
	 * 
	 * @param res All reservations for every vehicle to be drawn
	 * @param sDate Start date of shown period
	 * @param eDate End date of shown period
	 */
	public ReservationGraph(ArrayList<ArrayList<Reservation>> res, String sDate, String eDate) {
		this.allRes = res;
		setView(sDate, eDate);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX(), y = e.getY();
				int resID = getResIdByCoordinate(x, y);
				if(resID > 0) {
					Object[][] data = {};
					String[] colNames = {};
					StandardTableModel stm = new StandardTableModel(data, colNames);
					
					ReservationView rp = new ReservationView(stm);
					rp.showExistingWindow(resID);
					System.out.println("Clicked at reservation " + resID);
				}
			}
		});
	}

	/**
	 * Paints the graph. Is called automatically upon object creation of this class
	 */
	public void paint(Graphics gSimple) {
		super.paintComponent(gSimple);

		//Setting the graphics to the little more advanced Graphic2D
		Graphics2D g = (Graphics2D)gSimple;

		//Getting calendar instance
		GregorianCalendar cCal = (GregorianCalendar) GregorianCalendar.getInstance();
		int thisDay = cCal.get(Calendar.DAY_OF_MONTH);
		int thisMonth = cCal.get(Calendar.MONTH)+1;
		int thisYear = cCal.get(Calendar.YEAR);

		//Font
		String fontName = UIManager.getDefaults().getFont("Label.font").toString();
		int fontSize = 11;
		int fontHeight = 8;

		//Draw day labels
		if(thisMonth >= selectedMonth && thisYear >= selectedYear)
			g.setColor(Color.LIGHT_GRAY);
		int labelXPos = GRAPH_X_POS + X_PADDING;
		int pad = 0;

		for(int i = 1; i <= selectedMaximumDayInMonth; i++) {

			g.setFont(new Font(fontName, Font.PLAIN, fontSize));
			if(i < 10) pad = 2; else pad = 5; //Small corrections
			g.drawString(""+i, labelXPos-pad+(UNIT/2), 10); //11x8 - size of font in px

			if(i == thisDay && thisMonth == selectedMonth && thisYear == selectedYear) {
				g.setColor(Color.BLACK);
				g.setFont(new Font(fontName, Font.BOLD, fontSize));
				g.drawString(""+i, labelXPos-pad+(UNIT/2), 10);
				g.setColor(Color.DARK_GRAY);
			}
			labelXPos += UNIT;
		}

		//Draw reservations
		int resXPos;							//Reservation x position
		int resYPos = GRAPH_Y_POS + Y_PADDING;	//Reservation y position
		int[] area;								//Area of a single graph element

		//Loops through all ArrayLists containing reservations
		for(ArrayList<Reservation> vehicleRes : allRes) {

			resXPos = GRAPH_X_POS + X_PADDING;
			
			//Draw reservations of a single vehicle
			for(Reservation r : vehicleRes) {

				int width;
				resXPos = (r.getFromDateDay()-1)*UNIT;

				int fromDelta = deltaDays(r.getFromDate(), selectedStartDate);
				int toDelta = deltaDays(selectedEndDate, r.getToDate());

				if(toDelta > 0 && fromDelta > 0) { //If reservation extends beyond the view
					resXPos = 0;
					width = (r.getLength() - fromDelta - toDelta) * UNIT - SPACING;
				}
				else if(fromDelta > 0) { //If reservation starts in previous month
					resXPos = 0;
					width = (r.getLength()-fromDelta)*UNIT - SPACING;
				}
				else if(toDelta > 0) { //If reservation ends in following month
					width = (r.getLength()-toDelta)*UNIT - SPACING;
				}
				else { //If reservation starts and ends in the current month
					width = r.getLength() * UNIT - SPACING;
				}

				//Stores all rectangles for later use
				area = new int[7];
				area[AREA_RESERVATION_ID] = r.getResID();					//Reservation ID
				area[AREA_YEAR] = selectedYear;
				area[AREA_MONTH] = selectedMonth;

				area[AREA_X] = GRAPH_X_POS + X_PADDING + resXPos + SPACING;	//X-pos
				area[AREA_Y] = resYPos;										//Y-pos
				area[AREA_WIDTH] = width;									//Width
				area[AREA_HEIGHT] = BAR_HEIGHT;								//Height

				resOverlay.add(area);

				//Draw actual reservation
				Rectangle2D.Double shadow = new Rectangle2D.Double(area[AREA_X]+1, area[AREA_Y]+1, area[AREA_WIDTH], area[AREA_HEIGHT]);
				g.setColor(Color.LIGHT_GRAY);
				g.fill(shadow);

				Rectangle2D.Double rect = new Rectangle2D.Double(area[AREA_X], area[AREA_Y], area[AREA_WIDTH], area[AREA_HEIGHT]);
				g.setColor(new Color(74, 111, 167)); // Color.BLUE
				g.fill(rect);

				//Vehicle label
				g.setColor(new Color(26, 56, 96));
				g.drawString("(" + r.getVehicleID() + ") " + r.getTypeName(), 2, resYPos+(fontHeight/2)+(BAR_HEIGHT/2));
			}

			resYPos+= BAR_HEIGHT + Y_PADDING; // Next vehicle
		}

		setPanelHeight(resYPos); //Sets the height of this pannel so it matches the graphsheight
		//Draw horizontal line
		g.setColor(Color.DARK_GRAY);
		g.drawLine(0, 14, GRAPH_X_POS+(selectedMaximumDayInMonth*UNIT)+X_PADDING*2, 14); //Top horizontal		
	}

	/**
	 * Sets the preferred height of this JPanel.
	 * Needed as graphics does not set the height of the panel.
	 * 
	 * @param height The height to be set
	 */
	private void setPanelHeight(int height) {
		setPreferredSize(new Dimension(getPreferredSize().width, height));
	}

	/**
	 * Takes a coordinate and translates it in to a reservation ID.
	 * 
	 * @param x X-coordinate of the click
	 * @param y Y-coordinate of the click
	 * @return Reservation ID if the click is inside the boundaries of a reservation, otherwise -1
	 */
	private int getResIdByCoordinate(int x, int y) {
		for(int[] o : resOverlay) {
			if(x >= o[AREA_X] && x <= o[AREA_X] + o[AREA_WIDTH]
					&& y >= o[AREA_Y] && y <= o[AREA_Y] + o[AREA_HEIGHT]
							&& o[AREA_YEAR] == selectedYear
							&& o[AREA_MONTH] == selectedMonth) {
				return o[AREA_RESERVATION_ID];
			}
		}
		return -1;
	}

	/**
	 * Calculates the difference between to dates, in days.
	 * 
	 * @param fromDate The data to be calculated from
	 * @param toDate The data to be calculated from
	 * @return Difference between days, in days.
	 */
	private int deltaDays(String fromDate, String toDate) {
		String[] fDateSep = fromDate.split("-");
		String[] eDateSep = toDate.split("-");

		GregorianCalendar fromCalendar = new GregorianCalendar(Integer.parseInt(fDateSep[0]), Integer.parseInt(fDateSep[1]), Integer.parseInt(fDateSep[2]));
		GregorianCalendar extCalendar = new GregorianCalendar(Integer.parseInt(eDateSep[0]), Integer.parseInt(eDateSep[1]), Integer.parseInt(eDateSep[2]));

		fromCalendar.getTimeInMillis();

		long millSecDiff = extCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis();
		int delta = (int) (millSecDiff / (24 * 60 * 60 * 1000));
		return delta;
	}

	/**
	 * Set the current view to a new period and repaints the graph.
	 * 
	 * @param startDate The start date of the month to be viewed
	 * @param endDate The end date of the month to be viewed
	 */
	public void setView(String startDate, String endDate) {
		selectedStartDate = startDate;
		selectedYear = Integer.parseInt(selectedStartDate.split("-")[0]);
		selectedMonth = Integer.parseInt(selectedStartDate.split("-")[1]);
		selectedEndDate = endDate;
		selectedMaximumDayInMonth = Integer.parseInt(selectedEndDate.split("-")[2]);

		repaint();
	}

	/**
	 * Sets new data to be shown
	 * 
	 * @param newData The new data
	 */
	public void setNewData(ArrayList<ArrayList<Reservation>> newData) {
		allRes = newData;
		setView(selectedStartDate, selectedEndDate);
	}
}