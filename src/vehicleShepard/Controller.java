package vehicleShepard;

import java.util.ArrayList;
import java.sql.*;

public class Controller {
	
	public static void main(String[] args) {
		new Controller();
	}
	
	//The Controller
	private static final Connection connection = ConnectDB.initConn();
	private View view;	//The main view object
	
	//DB Objects
	private static final UserDB USER = new UserDB();
	private static final ReservationDB RESV = new ReservationDB();
	private static final VehicleDB VEHC = new VehicleDB();
	
	
	public Controller()
	{
		view = new View(this);
		
		getReservation(1);
	}
	
	///////////////////////
	//DATABASE CONNECTION//
	///////////////////////
	
	public static Connection getConnection()
	{
		return connection;
	}
	
	public static void closeConnection()
	{
		ConnectDB.closeConn(connection);
	}
	
	/////////////
	//FUNCTIONS//
	/////////////
	
	public Vehicle findAvailableVehicle(int typeID, boolean automatic, String fromDate, String toDate)
	{
		return VEHC.getAvailableVehicle(typeID, automatic, fromDate, toDate);
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
	
	public void newCustomer(String phone, String phoneCode, String adress, String country, String firstName, String lastName, String licenceNumber, String licenceExpDate)
	{
		String[] info = new String[8];
		
		info[0] = phone;
		info[1] = phoneCode;
		info[2] = adress;
		info[3] = country;
		info[4] = firstName;
		info[5] = lastName;
		info[6] = licenceNumber;
		info[7] = licenceExpDate;
		
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
	
	public static Reservation getReservation(int resID)
	{
		System.out.println("getReservation started!");
		return RESV.getReservationByID(resID);
	}
	
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
	
	public void newReservation(int userID, int typeID, String vehicleID, java.sql.Date fromDate, java.sql.Date toDate, java.sql.Date extDate, int service)
	{
		Object[] info = new Object[7];
		
		info[0] = userID;
		info[1] = typeID;
		info[2] = vehicleID;
		info[3] = fromDate;
		info[4] = toDate;
		info[5] = extDate;
		info[6] = service;
		
		RESV.newReservation(info);
	}
	
	////////////
	//VEHICLES//
	////////////
	
	public Object[] getVehicle(int vehicleID)
	{
		return VEHC.getVehicleByID(vehicleID);
	}
	
	public Object[][] getVehicleList()
	{
		return VEHC.getList();
	}
	
	public Object[][] searchVehicle(String searchString)
	{
		return VEHC.getVehicles(searchString);
	}
	
	public void newVehicle(int vehicleID, String make, String model, int odometer, int fuel, boolean automatic, int statusID, int typeID)
	{
		Object[] info = new Object[8];
		
		info[0] = vehicleID;
		info[1] = make;
		info[2] = model;
		info[3] = odometer;
		info[4] = fuel;
		info[5] = automatic;
		info[6] = statusID;
		info[7] = typeID;
		
		VEHC.newVehicle(info);
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