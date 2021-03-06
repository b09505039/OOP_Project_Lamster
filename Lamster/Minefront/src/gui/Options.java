package gui;


import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.Configuration;


public class Options extends JFrame  {
	private static final long serialVersionUID = 1L;
	
	private Choice resolution = new Choice();
	Configuration config = new Configuration();
	private JTextField twidth, theight;
	private JLabel lwidth, lheight, actwidth, actheight;
	private int width = 540;
	private int height = 440;
	private JButton Ok;
	private Rectangle rOk, rresolution;
	int w = 0;
	int h = 0;
	private int button_width = 80;
	private int button_height = 30;
	
	
	JPanel window = new JPanel();
	
	public Options() {
		
		setTitle("Options");		//title of Options window
		setSize(new Dimension(width, height));		//size is 540, 440
		add(window);
		setLocationRelativeTo(null);	//set the window in the center of screen
		setVisible(true);				//let window be visible
		setResizable(false);			//don't want to be resize
		window.setLayout(null);			//use setLayout to set type(layout)
		drawButtons();					//draw buttons
		repaint();						//button won't disappear until mouse go through
	}
	
	public void drawButtons() {
		
		Ok = new JButton("OK");		//build an OK button
		rOk = new Rectangle((width - 100), (height - 70), button_width, button_height );
		Ok.setBounds(rOk);
		window.add(Ok);
		
		rresolution = new Rectangle(50, 80,80, 25);		//create drop-down menu
		resolution.setBounds(rresolution);				//set its location
		resolution.add("640, 480");						//add the selections in the drop-down menu
		resolution.add("800, 600");						//add the selections in the drop-down menu
		resolution.add("1024, 768");					//add the selections in the drop-down menu
		
		window.add(resolution);
		
		lwidth = new JLabel("Width:");					//build a new label called Actual Width:
		lwidth.setBounds(25, 150, 120, 20);				//set lwidth's location
		lwidth.setFont(new Font("Verdana", 0, 14 ));	//typing style
		window.add(lwidth);
		
		twidth = new JTextField();			//create a space to type
		twidth.setBounds(80, 150, 60, 20);	//its location
			window.add(twidth);
			
		actwidth = new JLabel("Actual width: " + config.getWidth());	//build a new label called Actual width: , and there is resolution behind it
		actwidth.setBounds(170, 150, 150, 20);							//set actwidth's location
		actwidth.setFont(new Font("Verdana", 0, 14 ));					//typing style
		window.add(actwidth );
		
		lheight = new JLabel("Height:");				//build a new label called Actual Height:
		lheight.setBounds(25, 180, 120, 20);			//set lheight's location
		lheight.setFont(new Font("Verdana", 0, 14 ));	//typing style
		window.add(lheight);
		
		theight = new JTextField();			//create a space to type
		theight.setBounds(80, 180, 60, 20);	//its location
		window.add(theight);
		
		actheight = new JLabel("Actual height: " + config.getHeight());	//build a new label called Actual width: , and there is resolution behind it
		actheight.setBounds(170, 180, 150, 20);							//set actheight's location
		actheight.setFont(new Font("Verdana", 0, 14 ));					//typing style
		window.add(actheight );
		
		Ok.addActionListener(new ActionListener() {		//click OK button
			public void actionPerformed(ActionEvent e) {
				dispose();			//after clicking OK button, close the window
			
				config.saveConfiguration("width", parseWidth());	//and save the width changes in config.xml with int type
				config.saveConfiguration("height", parseHeight());	//and save the height changes in config.xml with int type
				
				
				
			}
		});
	}
	
	private void drop() {		//resolution basic selection form
		int selection = resolution.getSelectedIndex();
		
		if (selection == 0) {	//drop-down menu first selection
			w = 640;
			h = 480;
		}
		if (selection == 1 || selection == -1) {	//drop-down menu second selection
			w = 800;
			h = 600;
		}
		if (selection == 2 ) {	//drop-down menu third selection
			w = 1024;
			h = 768;
		}
		config.saveConfiguration("width", w);		//save the width changes in config.xml
		config.saveConfiguration("height", h);		//save the height changes in config.xml
	}
	
	private int parseWidth() {			//change the width's type from string to int
		try {
		int w = Integer.parseInt(twidth.getText());
		return w;
		} catch (NumberFormatException e) {
			drop();
			return w;
		}
	}
	
	private int parseHeight() {			//change the height's type from string to int
		try {
		int h = Integer.parseInt(theight.getText());
		return h;
		} catch (NumberFormatException e) {
			drop();
			return h;
		}
		
	}
}
