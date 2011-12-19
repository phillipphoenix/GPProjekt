package vehicleShepard;

import static org.junit.Assert.*;

import java.sql.*;

import org.junit.Before;
import org.junit.Test;

public class ReservationDBTest {

	@Test
	public void testGetReservationByID() {
		 
		Connection conn = ConnectDB.initConn();
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Reservation WHERE resID=1");
			ResultSet rs = s.getResultSet();
			
			while (rs.next()) {
				assertEquals(0, rs.getInt("userType"));
				assertEquals(1, rs.getInt("userID"));
				assertEquals(2, rs.getInt("typeID"));
				assertEquals("PA29610", rs.getString("vehicleID"));
				assertEquals("2011-12-16", rs.getString("fromDate"));
				assertEquals("2011-12-24", rs.getString("toDate"));
				assertEquals(0, rs.getInt("service"));
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
