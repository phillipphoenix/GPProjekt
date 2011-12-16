package vehicleShepard;

import java.util.ArrayList;
import java.sql.*;
import java.io.IOException;
import java.lang.Integer;
import java.net.*;

public class Controller {
	
	public static void main(String[] args) {
		new Controller();
	}
	
	//The Controller
	private static Connection connection;
	private View view;	//The main view object
	
	//DB Objects
	private static final UserDB USER = new UserDB();
	private static final ReservationDB RESV = new ReservationDB();
	private static final VehicleDB VEHC = new VehicleDB();
	
	
	public Controller()
	{
		/*
		 * We would like to check if our user had connection
		 * 		to the web, before trying to connect to the
		 * 		database.
		 */
		try 
		{
			final URL url = new URL("http://itu.dk/mysql");
			final URLConnection urlConn = url.openConnection();
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		connection = ConnectDB.initConn();
		
		view = new View(this);
		
		//Makes the connection close at exiting the program
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() 
		{
		    public void run() 
		    {
		        closeConnection();
		    }
		}));
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
	
	public boolean testConn()
	{
		/*
		 * We would like to check if our user had connection
		 * 		to the web, before trying to connect to the
		 * 		database.
		 */
		
		boolean testConn = false;
		try 
		{
			final URL url = new URL("http://itu.dk/mysql");
			final URLConnection urlConn = url.openConnection();
			
			testConn = true;
			
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return testConn;
	}
	
	/////////////
	//FUNCTIONS//
	/////////////
	
	public static Vehicle findAvailableVehicle(int typeID, boolean automatic, String fromDate, String toDate)
	{
		return VEHC.getAvailableVehicle(typeID, automatic, fromDate, toDate);
	}
	
	/////////
	//USERS//
	/////////
	
	//CUSTOMER
	public static Customer getCustomer(int userID)
	{
		Object[] info = USER.getUserByID(true, userID);
		Customer customer = new Customer((int)info[0], (int)info[1], (int)info[2], (String)info[3], (String)info[4], (String)info[5], (String)info[6], (String)info[7], (String)info[8]);
		return customer;
	}
	
	public static Object[][] getCustomerList()
	{
		return USER.getList(true);
	}
	
	public static Object[][] searchCustomers(String searchString)
	{
		return USER.getUsers(true, searchString);
	}
	
	/**
	 * Creates a new user by giving an array of info 
	 * 		to create this new customer
	 * @param phone
	 * @param phoneCode
	 * @param adress
	 * @param country
	 * @param firstName
	 * @param lastName
	 * @param licenceNumber
	 * @param licenceExpDate
	 */
	public static void newCustomer(String phone, String phoneCode, String adress, String country, String firstName, String lastName, String licenceNumber, String licenceExpDate)
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
		
		//This creates a new 
		USER.newUser(true, info, new Object[10]);
	}
	
	//MECHANIC
	public static Object[] getMechanic(int userID)
	{
		return USER.getUserByID(false, userID);
	}
	
	public static Object[][] getMechanicList()
	{
		return USER.getList(false);
	}
	
	public static Object[][] searchMechanics(String searchString)
	{
		return USER.getUsers(false, searchString);
	}
	
	/**
	 * Creates a new mechanic by giving an array of info 
	 * 		to create this new mechanic
	 * @param phone
	 * @param phoneCode
	 * @param address
	 * @param country
	 * @param firmName
	 */
	public static void newMechanic(int phone, int phoneCode, String address, String country, String firmName)
	{
		Object[] info = new Object[5];
		
		info[0] = phone;
		info[1] = phoneCode;
		info[2] = address;
		info[3] = country;
		info[4] = firmName;
		
		USER.newUser(false, new Object[10], info);
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
	public static ArrayList<ArrayList<Reservation>> getReservationArrayList()
	{
		return RESV.getArrayList();
	}
	
	public static Object[][] getReservationList()
	{
		return RESV.getList();
	}
	
	public static Object[][] searchReservations(String searchString)
	{
		return RESV.getReservation(searchString);
	}
	
	/**
	 * Creates a new reservation by giving an array of info 
	 * 		to create this new reservation
	 * @param userID
	 * @param typeID
	 * @param vehicleID
	 * @param fromDate
	 * @param toDate
	 * @param service
	 */
	public static void newReservation(int userID, int typeID, String vehicleID, String fromDate, String toDate, int service)
	{
		//Create a new info array of type Object
		Object[] info = new Object[7];
		
		//Fill the newly made array
		info[0] = userID;
		info[1] = typeID;
		info[2] = vehicleID;
		info[3] = fromDate;
		info[4] = toDate;
		info[5] = toDate;
		info[6] = service;
		
		//Give the method in ReservationDB the information
		RESV.newReservation(info);
	}
	
	public static void removeReservation(int resID)
	{
		RESV.removeReservation(resID);
	}
	
	////////////
	//VEHICLES//
	////////////
	
	public static Vehicle getVehicle(String vehicleID)
	{
		return VEHC.getVehicleByID(vehicleID);
	}
	
	public static Object[][] getVehicleList()
	{
		return VEHC.getList();
	}
	
	public static Object[][] searchVehicles(String searchString)
	{
		return VEHC.getVehicles(searchString);
	}
	
	/**
	 * Creates a new vehicle by giving an array of info 
	 * 		to create this new vehicle
	 * @param vehicleID
	 * @param make
	 * @param model
	 * @param odometer
	 * @param fuel
	 * @param automatic
	 * @param statusID
	 * @param typeID
	 */
	public static void newVehicle(String vehicleID, String make, String model, int odometer, int fuel, boolean automatic, int statusID, int typeID)
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
	
	public static String[] getVehTypeNames()
	{
		return VEHC.getVehicleTypeNames();
	}
	
	public static int[] getVehTypePrices()
	{
		return VEHC.getVehicleTypePrices();
	}
}