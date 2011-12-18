package vehicleShepard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class BackupOfReservationGraph extends JPanel {
	//Start position of graph view
	private static final int GRAPH_X_POS = 100;
	private static final int GRAPH_Y_POS = 15;

	//Single "graph bar" properties
	private static final int BAR_HEIGHT = 11;
	private static final int X_PADDING = 20;
	private static final int Y_PADDING = 5;
	private static final int SPACING = 2;
	private static final int UNIT = 20;
	private int fit;						//TODO ah pis crap

	//Number constants
	private final static int AREA_RESERVATION_ID = 0;
	private final static int AREA_X = 1;
	private final static int AREA_Y = 2;
	private final static int AREA_WIDTH = 3;
	private final static int AREA_HEIGHT = 4;

	private ArrayList<ArrayList<Reservation>> allRes;
	private ArrayList<int[]> resOverlay = new ArrayList<int[]>();

	private Date selectedStartDate;
	private Date selectedEndDate;

	public BackupOfReservationGraph(ArrayList<ArrayList<Reservation>> res) {
		this.allRes = res;

		/* Using MouseAdapter, so we only need to override
		 * the necessary methods (in contrast to MouseListener,
		 *  where all actions must be declared) */
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX(), y = e.getY();
				System.out.println("Clicked @ "+x+"."+y);
				int resID = getResIdByCoordinate(x, y);
				if(resID > 0) {
					//ReservationView rp = new ReservationView();
					//rp.showExistingWindow(resID);
				}
			}
		});
	}

	public void paintComponent(Graphics gSimple) {
		//super.paintComponent(gSimple); //TODO Is this necessary?
		Graphics2D g = (Graphics2D)gSimple;

		//MORE CALENDAR STUFF
		int selYear = 2011;
		int selMonth = 12;

		Calendar sCal = GregorianCalendar.getInstance();
		sCal.set(selYear, selMonth-1, 1);
		selectedStartDate = sCal.getTime();
		sCal.set(selYear, selMonth-1, sCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		selectedEndDate = sCal.getTime();

		int daysInSelMonth = sCal.getActualMaximum(Calendar.DAY_OF_MONTH);

		//Getting calendar instance
		GregorianCalendar cCal = (GregorianCalendar) GregorianCalendar.getInstance();
		int thisDay = cCal.get(Calendar.DAY_OF_MONTH);
		int daysInThisMonth = cCal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int thisMonth = cCal.get(Calendar.MONTH); //TODO Month in a year? WHAAaaat!?
		int thisYear = cCal.get(Calendar.YEAR);

		//Font
		String fontName = UIManager.getDefaults().getFont("Label.font").toString();
		int fontSize = 11;
		int fontHeight = 8;

		//Draw day labels
		g.setColor(Color.LIGHT_GRAY);
		int labelXPos = GRAPH_X_POS + X_PADDING;
		int pad = 0;

		for(int i = 1; i <= daysInSelMonth; i++) {

			g.setFont(new Font(fontName, Font.PLAIN, fontSize));
			if(i < 10) pad = 2; else pad = 5; //TODO Use fontmetrics instead
			g.drawString(""+i, labelXPos-pad+(UNIT/2), 10); //11x8

			if(i == thisDay) {
				g.setColor(Color.BLACK);
				g.setFont(new Font(fontName, Font.BOLD, fontSize));
				g.drawString(""+i, labelXPos-pad+(UNIT/2), 10);
				g.setColor(Color.DARK_GRAY);
			}
			labelXPos += UNIT;
		}

		//Draw reservations of a single vehicle
		int resXPos;							//Reservation x position
		int resYPos = GRAPH_Y_POS + Y_PADDING;	//Reservation y position
		int[] area;								//Area of a single graph element

		Calendar rCal = GregorianCalendar.getInstance();

		for(ArrayList<Reservation> vehicleRes : allRes) {

			resXPos = GRAPH_X_POS + X_PADDING;


			for(Reservation r : vehicleRes) {

				rCal.set(r.getFromDateYear(), r.getFromDateMonth()-1, r.getFromDateDay());
				Date fromDay = rCal.getTime();
				rCal.set(r.getExtDateYear(), r.getExtDateMonth()-1, r.getExtDateDay());
				Date toDay = rCal.getTime();
				
				int width = r.getLength()*UNIT - SPACING;
				resXPos = (r.getFromDateDay()-1)*UNIT;
				
				if(fromDay.before(selectedStartDate)) {
					long fromDelta = ((selectedStartDate.getTime() - fromDay.getTime())/86400000) + 1;
					System.out.println("Reservationen " + r.getResID() + " har start " + fromDelta + " dage f�r det valgte view");
					resXPos = 0;
					width = (r.getLength() - (int)fromDelta - 1) * UNIT;
				}




				area = new int[5];
				area[AREA_RESERVATION_ID] = r.getResID();						//Reservation ID

				area[AREA_X] = GRAPH_X_POS + X_PADDING + resXPos + SPACING;	//X-pos
				area[AREA_Y] = resYPos;										//Y-pos
				area[AREA_WIDTH] = width;			//Width
				area[AREA_HEIGHT] = BAR_HEIGHT;								//Height

				resOverlay.add(area);

				Rectangle2D.Double shadow = new Rectangle2D.Double(area[AREA_X]+1, area[AREA_Y]+1, area[AREA_WIDTH], area[AREA_HEIGHT]);

				g.setColor(Color.LIGHT_GRAY);
				g.fill(shadow);

				Rectangle2D.Double rect = new Rectangle2D.Double(area[AREA_X], area[AREA_Y], area[AREA_WIDTH], area[AREA_HEIGHT]);

				g.setColor(Color.BLUE);
				g.fill(rect);

				//Draw arrows (if the reservation extends beyond the view)
				//g.draw(getRightTriangle(area[AREA_X] + area[AREA_WIDTH], area[AREA_Y]));
				//g.draw(getLeftTriangle(area[AREA_X], area[AREA_Y]));
				//TODO UNCONTROLLABLE :O


				/*/TODO Get this date shit to work
					int selectedYear = 2011;
					int selectedMonth = 12;

					if(r.getToYear() >= selectedYear) {
						if(r.getToMonth() > thisMonth || r.getToMonth() <= 12) {

						}
					}*/

				//Vehicle label
				g.drawString("Vehicle (" + r.getVehicleID() + ")", 2, resYPos+(fontHeight/2)+(BAR_HEIGHT/2));
			}

			resYPos+= BAR_HEIGHT + Y_PADDING;
		}

		//Draw horizontal, vertical and diagonal line
		g.setColor(Color.BLACK);
		g.drawLine(0, 14, GRAPH_X_POS+(daysInSelMonth*UNIT)+X_PADDING*2, 14); //Top horizontal
		g.drawLine(GRAPH_X_POS, 0, GRAPH_X_POS, resYPos); // Left vertical
		g.drawLine(GRAPH_X_POS+(daysInSelMonth*UNIT)+X_PADDING*2, 0, GRAPH_X_POS+(daysInSelMonth*UNIT)+X_PADDING*2, resYPos); // Right vertical
		//g.drawLine(0, resYPos, graphXPos+(daysInMonth*UNIT)+X_PADDING*2, resYPos); //Bottom horizontal
		g.drawString("December" + " " + "2012", 2, 10);
	}

	private Polygon getRightTriangle(int xPos, int yPos) {
		Point p1 = new Point(xPos, yPos);
		Point p2 = new Point(xPos + BAR_HEIGHT, yPos + BAR_HEIGHT/2);
		Point p3 = new Point(xPos, yPos + BAR_HEIGHT - 1);

		int[] pX = {p1.x, p3.x, p2.x};
		int[] pY = {p1.y, p3.y, p2.y};

		return new Polygon(pX, pY, 3);
	}

	private Polygon getLeftTriangle(int xPos, int yPos) {
		Point p1 = new Point(xPos, yPos);
		Point p2 = new Point(xPos-BAR_HEIGHT, yPos + BAR_HEIGHT/2);
		Point p3 = new Point(xPos, yPos + BAR_HEIGHT - 1);

		int[] pX = {p1.x, p3.x, p2.x};
		int[] pY = {p1.y, p3.y, p2.y};

		return new Polygon(pX, pY, 3);
	}

	private int getResIdByCoordinate(int x, int y) {
		for(int[] o : resOverlay) {
			if(x >= o[AREA_X] && x <= o[AREA_X] + o[AREA_WIDTH]
					&& y >= o[AREA_Y] && y <= o[AREA_Y] + o[AREA_HEIGHT]) {
				return o[AREA_RESERVATION_ID];
			}
		}
		return -1;
	}
}