package vehicleShepard;

import java.sql.*;

public class ConnectDB 
{
	public static Connection initConn()
	{
		Connection conn = null;

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
