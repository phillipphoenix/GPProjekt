package vehicleShepard;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

public class VehicleDBTest {

	@Test
	public void testGetVehicleByID() {
		 
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Vehicle WHERE vehicleID=BF01337");
			ResultSet rs = s.getResultSet();
			
			while (rs.next()) {
				assertEquals("BF01337", rs.getString("vehicleID"));
				assertEquals("Mercedes", rs.getString("make"));
				assertEquals("SLR 5.4", rs.getString("model"));
				assertEquals(120376, rs.getInt("odometer"));
				assertEquals(1, rs.getInt("fuel"));
				assertEquals(1, rs.getInt("automatic"));
				assertEquals(0, rs.getInt("status"));
				assertEquals(5, rs.getInt("type"));
			}
			s.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	ConnectDB.closeConn(conn);
	}

}
