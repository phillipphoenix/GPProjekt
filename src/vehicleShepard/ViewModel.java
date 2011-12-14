package vehicleShepard;

import javax.swing.JFrame;
import javax.swing.JPanel;

abstract public class ViewModel {
	protected static final int X_PAD = 6; //Component default x-axis padding
	protected static final int Y_PAD = 8; //Component default y-axis padding
	protected static final int COMPONENT_HEIGHT = 20; //Component default height - 26 is standard
	
	public abstract JFrame showCreateWindow();
	public abstract JFrame showExistingWindow(int id);
	public abstract JPanel getCreatePanel();
	public abstract JPanel getExistingPanel(int id);
	
	public String datePicker() {
		String date = "";
		
		JPanel panel = new JPanel();
		
		return date;
	}
}