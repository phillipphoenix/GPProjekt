package vehicleShepard;

import java.util.ArrayList;

public class Controller {
	
	public static void main(String[] args) {
		new Controller();
	}
	
	//The Controller
	private DesktopView view;	//The main view object
	
	//DB Objects
	private static final CustomerDB CUST = new CustomerDB();
	private static final MechanicDB MECH = new MechanicDB();
	private static final ReservationDB RESV = new ReservationDB();
	
	
	public Controller()
	{
		view = new DesktopView();
	}
	
	/////////
	//USERS//
	/////////
	
	//CUSTOMER
	public Object[] getCustomer(int userID)
	{
		
		return CUST.getUserByID(userID);
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
	
	/**
	 * Returns an ArrayList full of arrayLists containing Reservations. Each inner arrayList contains reservations for a specific vehicle.
	 * The outer arrayList contains the inner arrayLists, which represents each individual car.
	 * @return ArrayList<ArrayList<Reservation>> The outer arrayList containing the inner arrayLists with reservations.
	 */
	public ArrayList<ArrayList<Reservation>> getReservationArrayList()
	{
		return RESV.getArrayList();
	}
	
	
	
}