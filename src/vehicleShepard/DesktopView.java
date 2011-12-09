package vehicleShepard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import static javax.swing.GroupLayout.Alignment.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class DesktopView extends JFrame {
	private	static final String TITLE = "Vehicle Shepard";
	
	private Controller controller;
	private JPanel contentPane;
	
	public DesktopView(Controller controller) {
		this.controller = controller;
		
		makeFrame();
	}
	
	private void makeFrame() {
		contentPane = (JPanel) getContentPane();
		//contentPane.setLayout(null);
		//contentPane.setLayout(new GridBagLayout());
		contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));
		//makeMenuBar();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		setSystemIcons();
		setBounds(0, 0, 800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Sets all relevant system icons (e.g. taskbar, window)
	 */
	//TODO Add more icons
	private void setSystemIcons() {
		ArrayList<Image> iconList = new ArrayList<Image>();
		iconList.add(loadImage("small.png"));
		iconList.add(loadImage("medium.png"));
		iconList.add(loadImage("large.png"));
		setIconImages(iconList);
	}
	
	/**
	 * Loads a image from local disk
	 * 
	 * @param url The URL of the image to be loaded
	 * @return The selected image
	 */
	private Image loadImage(String url) {
		return Toolkit.getDefaultToolkit().getImage(
				ClassLoader.getSystemClassLoader().getResource(url));
	}
}
