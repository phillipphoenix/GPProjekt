package vehicleShepard;

import static org.junit.Assert.*;

import java.sql.*;

import org.junit.Before;
import org.junit.Test;

public class ReservationDBTest {

	private ReservationDB resv;
	private int count = 0;
	private Reservation res;

	@Before
	public void setUp() {
		resv = new ReservationDB();
	}

	@Test
	public void testNewReservation() {
		//Create Sql dates representing the dates given as strings
		java.sql.Date fromDateSql = new java.sql.Date(2011, 12, 02);
		java.sql.Date toDateSql = new java.sql.Date(2011, 12, 24);
		
		//Create a new info array of type Object
		Object[] info = new Object[7];
		
		//Fill the newly made array
		info[0] = 1;
		info[1] = 2;
		info[2] = "TY98331";
		info[3] = fromDateSql;
		info[4] = toDateSql;
		info[5] = toDateSql;
		info[6] = 1;
		
		count = resv.newReservation(-1, false, info);
	}

	@Test
	public void testGetReservationByID() {
		 
		Connection conn = ConnectDB.initConn();
		System.out.println("Connnection: " + conn.toString());
		
		try 
		{
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM Reservation WHERE resID=1");
			ResultSet rs = s.getResultSet();
			
			while (rs.next()) {
				assertEquals(0, rs.getInt("userType"));
				assertEquals(3, rs.getInt("userID"));
				assertEquals(2, rs.getInt("typeID"));
				assertEquals("TY98331", rs.getString("vehicleID"));
				assertEquals(2011, rs.getDate("fromDate").getYear());
				assertEquals(12, rs.getDate("fromDate").getMonth());
				assertEquals(05, rs.getDate("fromDate").getDay());
				assertEquals(2011, rs.getDate("toDate").getYear());
				assertEquals(12, rs.getDate("toDate").getMonth());
				assertEquals(20, rs.getDate("toDate").getDay());
				assertEquals(1, rs.getInt("service"));
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
