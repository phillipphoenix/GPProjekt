package vehicleShepard;

import java.sql.*;

public class Vehicle {

	private String vehicleID, make, model;
	private int odometer, fuelID, statusID, typeID;
	private boolean automatic;
	
	public Vehicle(String vehicleID, String make, String model, int odometer, int fuelID, boolean automatic, int statusID, int typeID)
	{
		this.vehicleID = vehicleID;
		this.make = make;
		this.model = model;
		this.odometer = odometer;
		this.fuelID = fuelID;
		this.automatic = automatic;
		this.statusID = statusID;
		this.typeID = typeID;
	}
	
	/**
	 * Returns the name of the vehicle type instead of the type ID
	 * @return typeName The name of the vehicle's type
	 */
	public String getTypeName()
	{
		Connection conn = Controller.getConnection();
		String typeName = "No type name found";
		
		try {
			Statement s = conn.createStatement();
			s.executeQuery("SELECT name FROM VehicleType WHERE vehicleTypeID=" + typeID);
			ResultSet rs = s.getResultSet();
			
			if (rs.next()) {
				typeName = rs.getString("name");
			}
			
			s.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return typeName;
		
	}
	
	/**
	 * Returns the name of the vehicle's fuel type instead of the fuel ID
	 * @return fuelName The name of the vehicle's fuel type
	 */
	public String getFuelName()
	{
		Connection conn = Controller.getConnection();
		String fuelName = "No fuel name found";
		
		try {
			Statement s = conn.createStatement();
			s.executeQuery("SELECT name FROM Fuel WHERE fuelID=" + fuelID);
			ResultSet rs = s.getResultSet();
			
			if (rs.next()) {
				fuelName = rs.getString("name");
			}
			
			s.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return fuelName;
	}
	
	//SIMPLE GETTERS

	public String getID() {
		return vehicleID;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getOdometer() {
		return odometer;
	}

	public int getFuelID() {
		return fuelID;
	}

	public int getStatusID() {
		return statusID;
	}

	public int getTypeID() {
		return typeID;
	}

	public boolean isAutomatic() {
		return automatic;
	}
	
	
	
}
