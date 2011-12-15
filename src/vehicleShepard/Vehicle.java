package vehicleShepard;

import java.sql.*;

public class Vehicle {

	String vehicleID, make, model;
	int odometer, fuelID, statusID, typeID;
	boolean automatic;
	
	public Vehicle(String vehicleID, String make, String model, int odometer, String fuelName, boolean automatic, int statusID, int typeID)
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
	public String getVehicleTypeName()
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
	
	//SIMPLE GETTERS

	public String getVehicleID() {
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
