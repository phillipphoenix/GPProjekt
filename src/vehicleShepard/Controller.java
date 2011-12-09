package vehicleShepard;

import java.util.ArrayList;

public class Controller {
	
	public static void main(String[] args) {
		new Controller();
	}
	
	//The Controller
	private DesktopView view;	//The main view object
	public static final int DAY = 0;
	public static final int MONTH = 1;
	public static final int YEAR = 2;
	
	public Controller()
	{
		view = new DesktopView(this);
	}
	
	////////
	//USER//
	////////
	
	//CUSTOMER
	public Object[] getCustomer(int userID)
	{
		CustomerDB cust = new CustomerDB();
		
		return cust.getUserByID(userID);
	}
	
	public Object[][] getCustomerList()
	{
		CustomerDB cust = new CustomerDB();
		
		return cust.getList();
	}
	
	public void newCustomer()
	{
		CustomerDB cust = new CustomerDB();
		
		cust.newUser(); //TODO Add parameters, when Lauge has added them to the signature
	}
	
	//MECHANIC
	public Object[] getMechanic(int userID)
	{
		MechanicDB mech = new MechanicDB();
		
		return mech.getUserByID(userID);
	}
	
	public Object[][] getMechanicList()
	{
		MechanicDB mech = new MechanicDB();
		
		return mech.getList();
	}
	
	public void newMechanic()
	{
		MechanicDB mech = new MechanicDB();
		
		mech.newUser(); //TODO Add parameters, when Lauge has added them to the signature
	}
	
	////////////////
	//RESERVATIONS//
	////////////////
	
	public ArrayList<Reservation> getReservationList()
	{
		ReservationDB reservations = new ReservationDB;
		
		return reservations.getArrayList();
	}
	
	
}