package vehicleShepard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ListView {
	
	JFrame frame = new JFrame();
	JTextField search = new JTextField();
	
	public ListView() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImages(View.systemIconList()); //Move to panel? Just the 16x16 maybe
		frame.setBounds(0, 0, 600, 500);
		frame.setLocationRelativeTo(null);
		/*
		JPanel content = getNewPanel();
		content.setBorder(new EmptyBorder(6, 6, 6, 6));
		frame.add(content);
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				
			}
		});
		
		frame.setVisible(true);
		
		return frame;*/
	}
	
	public static JTable getCustomerList() {
		//frame.setTitle("Customers");
		
		String colNames[] = { "Name", "Tal"};
		
		Object data[][] = {
				{ "Række 1", 123 },
				{ "Række 2", 5141 }
		};
		
		JTable table = new JTable(data, colNames);
		
		return table;
	}
}
