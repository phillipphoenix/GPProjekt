package vehicleShepard;

public class Controller {
	
	public static void main(String[] args) {
		new Controller();
	}
	
	//The Controller
	
	private DesktopView view;	//The main view object
	
	public Controller()
	{
		view = new DesktopView(this);
	}
	
	//CUSTOMER
	public User getUser()
	{
		User user = null;
		Customer cust = new Customer();
		Mechanic mech = new Mechanic();
		
		user = cust.getUser();
		if (user = null) {
			user = mech.getUser();
		}
		
		//return user;
	}
	
	public String[][] getList()
	{
		
	}
	
}