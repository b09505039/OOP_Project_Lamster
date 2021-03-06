package gui;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
//import javax.swing.JButton;
import javax.swing.JFrame;
//import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JTextField;
import javax.swing.UIManager;

import graphics.Sound;
import input.InputHandler;
//import main.Configuration;
import main.Display;
//import main.RunGame;


public class Gameover extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private int width = 800;
	private int height = 400;
	//private JButton Ok;
	//private Rectangle rOk;
	int w = 0;
	int h = 0;
	//private int button_width = 80;
	//private int button_height = 30;
	
	boolean running = false;
	Thread thread;
	JFrame frame = new JFrame();
	
	JPanel window = new JPanel();
	
	public Gameover() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	//UI manager
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputHandler.MouseButton=0;								//preset to 0
		frame.setUndecorated(true);								//no window's frame
		frame.setSize(new Dimension(width, height));			//set size to 800, 400
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//hit close button to terminate itself
		frame.add(this);
		frame.setLocationRelativeTo(null);						//set the window in the center of screen
		frame.setResizable(false);								//don't want to be resize
		frame.setVisible(true);									//let window be visible
		window.setLayout(null);									//use setLayout to set type(layout)
		
		frame.repaint();										//button won't disappear until mouse go through
		startMenu();											//call startMenu method
		//drawButtons();										//draw buttons
		
		InputHandler input = new InputHandler();
		addKeyListener(input);									//inputHandler's listener
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}
	
	public void updateFrame() {					//update frame when running, so it can change window's location by dragging it
		if(InputHandler.dragged) {
			Point p = frame.getLocation(); 		//Canvas and JFrame both have a method called "gerLocation"
			frame.setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX, p.y + InputHandler.MouseDY - InputHandler.MousePY);
		}
	}
	
	public void startMenu() {				//start Gameover
		running = true;
		thread = new Thread(this, "logo");
		thread.start();						//thread.start() can let running thread start
	}
	
	public void stopMenu() {				//stop Gameover
		try {
			thread.join();					//thread.join() can let running thread pause
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {						//running Gameover
		// TODO Auto-generated method stub
		while(running) {
			try {
			rendermenu();					//call rendermenu()
			} catch (IllegalStateException e) {
				System.out.println("error");
			}
			updateFrame();
		}
		
	}
	
	private void rendermenu() throws IllegalStateException {
		
		BufferStrategy bs = this.getBufferStrategy(); //graphic things that only run one time
		
		if (bs == null) {
			createBufferStrategy(3); 				  //3 dimensional
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);			//fill window with whole black at first
		g.fillRect(0, 0, 800, 400);
		
		String FileName = "/logo.png";		//logo.png in res folder
		InputStream iss = Display.class.getResourceAsStream(FileName);	//change from "" to stream
		try {
			g.drawImage(ImageIO.read(iss), 0, 0, 800, 400, null);		//fill window with image
			if(InputHandler.MouseX > 720 && InputHandler.MouseX < 720+80 && InputHandler.MouseY > 360&& InputHandler.MouseY < 360+30) {
				g.setColor(Color.WHITE);								//if mouse touch the button, it will be white
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("EXIT", 720, 390);							//draw a string EXIT at bottom right
				
				if(InputHandler.MouseButton == 1) {						//click 
					InputHandler.MouseButton = 0;
					System.out.println(Display.lanucher);
					//Display.getLanucher();
					//frame.setVisible(false);
					Sound.altar.play();									//ending sound
					frame.dispose();
					
					//Launcher.rest();
					System.exit(0);										//program will exit
				}
			} else {
				g.setColor(Color.GRAY);									//button is gray without mouse touching
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("EXIT", 720, 390);							//draw a string EXIT at bottom right
			}
			//System.out.println(InputHandler.MouseButton);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error2");
		}
		
		g.dispose();		//close it
		bs.show();			//show the BufferStrategy
	}
	
	//we don't use it here
//	public void drawButtons() {
//		
//		Ok = new JButton("OK");
//		rOk = new Rectangle((width - 100), (height - 70), button_width, button_height );
//		Ok.setBounds(rOk);
//		window.add(Ok);
//		
//		Ok.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame.dispose();
//				System.exit(0);
//			
//			}
//		});
//	}


}
