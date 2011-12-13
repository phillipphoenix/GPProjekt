package vehicleShepard;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VehicleView extends Panel {
	private GridBagConstraints c;
	
	public VehicleView() {
		c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.ipadx = X_PAD;
		c.ipady = Y_PAD;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
	}

	public JFrame showCreateWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	public JFrame showExistingWindow() {
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel getCreatePanel() {
		// TODO Auto-generated method stub
		return null;
	}

	public JPanel getExistingPanel() {
		// TODO Auto-generated method stub
		return null;
	}
}
