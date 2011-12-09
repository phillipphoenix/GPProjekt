package vehicleShepard;

public class Controller {
	
	public static void main(String[] args) {
		new Controller();
	}
	
	//The Controller
	private DesktopView view;	//The main view object
	
	//Date constants
	private static final int DAY = 0;
	private static final int MONTH = 1;
	private static final int YEAR = 2;
	
	//DB Objects
	private static final CustomerDB CUST = new CustomerDB();
	private static final MechanicDB MECH = new MechanicDB();
	private static final ReservationDB RESV = new ReservationDB();
	
	
	public Controller()
	{
		//view = new DesktopView(this);
		getNumOfCustomers();
	}
	
	/////////
	//USERS//
	/////////
	
	//CUSTOMER
	public Object[] getCustomer(int userID)
	{
		
		return CUST.getUserByID(userID);
	}
	
	//TODO TEST METHOD - DELETE AFTER USE!!!
	public int getNumOfCustomers()
	{
		return CUST.getNumberOfCustomers();
	}
	
	public Object[][] getCustomerList()
	{
		return CUST.getList();
	}
	
	public void newCustomer()
	{
		CUST.newUser();
	}
	
	//MECHANIC
	public Object[] getMechanic(int userID)
	{
		
		return MECH.getUserByID(userID);
	}
	
	public Object[][] getMechanicList()
	{
		return MECH.getList();
	}
	
	public void newMechanic()
	{
		MECH.newUser();
	}
	
	////////////////
	//RESERVATIONS//
	////////////////
	/*TODO Change back!!!
	public ArrayList<ArrayList<Reservation>> getReservationArrayList()
	{
		return RESV.getArrayList();
	}
	*/
	
	
}