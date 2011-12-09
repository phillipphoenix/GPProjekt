package vehicleShepard;
/*
 * This class is controlling the methods of reservations
 */
public class ReservationDB 
{
	//
	
	public int[] fromDate(int resID)
	{
		// I want an Array to hold my date in int
		int[] fromDate = new int[3];
		
		// We take the day, month and year from the DB
		//TODO DB Connection
		int day = 1; //resID.day;
		int month = 3; //resID.month;
		int year = 1992; //resID.year;
		
		fromDate[0] = day;
		fromDate[1] = month;
		fromDate[2] = year;
		
		return fromDate;
	}
	
	public int[] toDate(int resID)
	{
		// I want an Array to hold my date in int
		int[] toDate = new int[3];
		
		// We take the day, month and year from the DB
		//TODO DB Connection
		int day = 24; //resID.day;
		int month = 10; //resID.month;
		int year = 1993; //resID.year;
		
		toDate[0] = day;
		toDate[1] = month;
		toDate[2] = year;
		
		return toDate;
	}
}
