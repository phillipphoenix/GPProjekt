package vehicleShepard;

import java.util.ArrayList;

public class Controller {
	
	public static void main(String[] args) {
		new Controller();
	}
	
	//The Controller
	private View view;	//The main view object
	
	//DB Objects
	private static final UserDB USER = new UserDB();
	private static final ReservationDB RESV = new ReservationDB();
	private static final VehicleDB VEHC = new VehicleDB();
	
	
	public Controller()
	{
		view = new View(this);
	}
	
	/////////
	//USERS//
	/////////
	
	//CUSTOMER
	public Object[] getCustomer(int userID)
	{
		return USER.getUserByID(true, userID);
	}
	
	public Object[][] getCustomerList()
	{
		return USER.getList(true);
	}
	
	public Object[][] searchCustomers(String searchString)
	{
		return USER.getUsers(true, searchString);
	}
	
	public void newCustomer(String phone, String phoneCode, String adress, String firstName, String lastName, String licenceNumber, String licenceExpDate)
	{
		String[] info = new String[7];
		
		info[0] = phone;
		info[1] = phoneCode;
		info[2] = adress;
		info[3] = firstName;
		info[4] = lastName;
		info[5] = licenceNumber;
		info[6] = licenceExpDate;
		
		USER.newUser(true, info);
	}
	
	//MECHANIC
	public Object[] getMechanic(int userID)
	{
		return USER.getUserByID(false, userID);
	}
	
	public Object[][] getMechanicList()
	{
		return USER.getList(false);
	}
	
	public Object[][] searchMechanic(String searchString)
	{
		return USER.getUsers(false, searchString);
	}
	
	public void newMechanic(int phone, int phoneCode, String address, String country, String firmName)
	{
		Object[] info = new Object[5];
		
		info[0] = phone;
		info[1] = phoneCode;
		info[2] = address;
		info[3] = country;
		info[4] = firmName;
		
		USER.newUser(false, info);
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
	
	public Object[][] getReservationList()
	{
		return RESV.getList();
	}
	
	////////////////
	//VEHICLETYPES//
	////////////////
	
	public String[] getVehTypeNames()
	{
		return VEHC.getVehicleTypeNames();
	}
	
	public int[] getVehTypePrices()
	{
		return VEHC.getVehicleTypePrices();
	}
}