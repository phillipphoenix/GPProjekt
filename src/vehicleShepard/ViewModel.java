package vehicleShepard;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This abstract class defines some method all other view-class needs to implement.
 *
 */
abstract public class ViewModel {
	//Those fields makes the look consistent
	protected static final int X_PAD = 6; //Component default x-axis padding
	protected static final int Y_PAD = 8; //Component default y-axis padding
	protected static final int COMPONENT_HEIGHT = 20; //Component default height
	
	//Defining methods needing implementation
	public abstract JFrame showCreateWindow();
	public abstract JFrame showExistingWindow(int id);
	protected abstract JPanel getFrameContent();
}