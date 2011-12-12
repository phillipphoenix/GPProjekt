package vehicleShepard;

/*
 * This class is made for the sole purpose of connecting
 * 		and disconnecting to & from our database
 * This implies:
 * 		connecting to our database
 * 		closing a connection to our database
 */

import java.sql.*;

public class ConnectDB 
{
	/**
	 * This method connects to our database
	 * @return conn
	 */
	public static Connection initConn()
	{
		//We make sure there is no connection
		Connection conn = null;

		/*
		 * We try to connect using username, password and
		 * 		an url to our database
		 * Then it uses our dowloaded drivers to make a 
		 * 		connection called conn
		 */
		
        try
        {
            String userName = "vehicleShepard";
            String password = "MyLittlePonyIsAwesome";
            String url = "jdbc:mysql://mysql.itu.dk/vehicleShepard";
            Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            conn = DriverManager.getConnection (url, userName, password);
            System.out.println ("Database connection established");
            
            return conn;
        }
        
        catch (Exception e)
        {
            System.err.println ("Cannot connect to database server");
            
            return conn;
        }
	}

	/**
	 * This method closes a connection to our database
	 * @param conn
	 */
	public static void closeConn(Connection conn)
	{
		if (conn != null)
        {
            try
            {
                conn.close ();
                System.out.println ("Database connection terminated");
            }
            catch (Exception e) { /* ignore close errors */ }
        }
	}
}
