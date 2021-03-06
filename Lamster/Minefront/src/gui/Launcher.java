package gui;

import input.InputHandler;

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
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
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
import main.Configuration;
import main.Display;
import main.RunGame;



public class Launcher extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	//private JButton play, options, help, quit;
	//private Rectangle rplay, roptions, rhelp, rquit;
	Configuration config = new Configuration();

	private int width = 800;
	private int height = 400;
	protected int button_width = 80;
	protected int button_height = 40;
	boolean running = false;
	Thread thread;
	JFrame frame = new JFrame();
	
	public int cred = 0;

	public Launcher(int id) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());	//UI manager
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setUndecorated(true);
		frame.setSize(new Dimension(width, height));// set window's size
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);							//window in the center
		frame.setLocationRelativeTo(null);			//window in the center 2
		frame.setResizable(false);					//don't want to be resize
		frame.setVisible(true);						//let window be visible
		window.setLayout(null);
		
		
//		if(id == 0) {								//draw some buttons on Launcher
//			drawButtons();
//		}
		
		InputHandler input = new InputHandler();
		addKeyListener(input);						//inputHandler's listener
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
		startMenu();
		frame.repaint(); 							//button won't disappear until mouse go through
	}
	
	public void updateFrame() {						//update frame when running, so it can change window's location by dragging it
		if(InputHandler.dragged) {
			Point p = frame.getLocation(); 			//Canvas and JFrame both have a method called "gerLocation"
			frame.setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX, p.y + InputHandler.MouseDY - InputHandler.MousePY);
		}
	}
	
	public void startMenu() {						//start Launcher
		running = true;
		thread = new Thread(this, "menu");
		thread.start();								//thread.start() can let running thread start
	}
	
	public void stopMenu() {						//stop Launcher
		try {
			thread.join();							//thread.join() can let running thread pause
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {								//running Launcher
		// TODO Auto-generated method stub
		while(running) {
			try {
				if (cred  == 1) {
					creditt();
				} else if(cred == 0) {
					rendermenu();					//call rendermenu()
				}							
			} catch (IllegalStateException e) {
				System.out.println("error");
			}
			updateFrame();
		}
	}
	
	private void rendermenu() throws IllegalStateException {
		BufferStrategy bs = this.getBufferStrategy(); 	//graphic things that only run one time
		if (bs == null) {
			createBufferStrategy(3); 				  	//3 dimensional
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);		//fill window with whole black at first
		g.fillRect(0, 0, 800, 400);
		
		String FileName = "/menu.jpg";					//menu.jpg in res folder
		//String FileName2 = "res/setting/config.xml";
		InputStream iss = Display.class.getResourceAsStream(FileName);	//change from "" to stream
		//InputStream iss2 = Display.class.getResourceAsStream(FileName2);
		
		//draw PLAY, OPTIONS, HELP, EXIT buttons 
		try {
			g.drawImage(ImageIO.read(iss), 0, 0, 800, 400, null);		//fill window with image
			if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690+80 && InputHandler.MouseY > 100 && InputHandler.MouseY < 100+30) {
				g.setColor(Color.WHITE);								//if mouse touch the button, it will be white
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("PLAY", 690, 130);							//draw a string PLAY
				if(InputHandler.MouseButton == 1) {						//click 
					config.loadConfiguration("/setting/config.xml");	//load configuration from /setting/config.xml
//					try {
//					InputStream read = new FileInputStream(FileName2);
//					config.properties.loadFromXML(read);
//					String width = config.properties.getProperty("width");
//					String height = config.properties.getProperty("height");
//					System.out.println("Width = " + width + ", Height = " + height);
//					config.setResolution(Integer.parseInt(width), Integer.parseInt(height));
//					read.close();
//				} catch(FileNotFoundException e) {
//					config.saveConfiguration("width", 800);
//					config.saveConfiguration("height", 600);
//					config.loadConfiguration(FileName2);
//				} catch(IOException e) {
//					e.printStackTrace();
//				}
					frame.dispose();									//after clicking play button dispose it
					Sound.click.play();									//clicking sound
					new RunGame();										//it will call RunGame to start the game
				}
			} else {													//button is gray without mouse touching
				g.setColor(Color.GRAY);
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("PLAY", 690, 130);							//draw a string PLAY
			}
			if(InputHandler.MouseX > 625 && InputHandler.MouseX < 625+135 && InputHandler.MouseY > 140 && InputHandler.MouseY < 140+30) {
				g.setColor(Color.WHITE);								//if mouse touch the button, it will be white
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("OPTIONS", 625, 170);						//draw a string OPTIONS
				if(InputHandler.MouseButton == 1) {						//click 
					Sound.click.play();									//clicking sound
					new Options();										//it will new a option window
				}
			} else {
				g.setColor(Color.GRAY);									//button is gray without mouse touching
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("OPTIONS", 625, 170);						//draw a string OPTIONS
			}
			if(InputHandler.MouseX > 685 && InputHandler.MouseX < 685+80 && InputHandler.MouseY > 180 && InputHandler.MouseY < 180+30) {
				g.setColor(Color.WHITE);								//if mouse touch the button, it will be white
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("CREDIT", 645, 210);						//draw a string CREDIT
				if (InputHandler.MouseButton == 1) {
					Sound.click.play();									//clicking sound
					cred=1;
				}
			} else {
				g.setColor(Color.GRAY);									//button is gray without mouse touching
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("CREDIT", 645, 210);						//draw a string CREDIT
			}
			if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690+80 && InputHandler.MouseY > 220 && InputHandler.MouseY < 220+30) {
				g.setColor(Color.WHITE);								//if mouse touch the button, it will be white
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("EXIT", 690, 250);							//draw a string EXIT
				if(InputHandler.MouseButton == 1) {						//click 
					//System.out.println("e");
					Sound.click.play();									//clicking sound
					System.exit(0);										//game will close after clicking EXIT button
					
				}
			} else {
				g.setColor(Color.GRAY);									//button is gray without mouse touching
				g.setFont(new Font("verdana", 0, 30));					//typing style
				g.drawString("EXIT", 690, 250);							//draw a string EXIT
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		g.dispose();			//close it
		bs.show();				//show the BufferStrategy
	}
	
	private void creditt() throws IllegalStateException {
		BufferStrategy bs = this.getBufferStrategy(); 	//graphic things that only run one time
		if (bs == null) {
			createBufferStrategy(3); 					//3 dimensional
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);						//fill window with whole black at first
		g.fillRect(0, 0, 800, 400);

		String FileName = "/credit.png";				//credit.png in res folder
		InputStream iss = Display.class.getResourceAsStream(FileName);
		
		try {
			g.drawImage(ImageIO.read(iss), 0, 0, 800, 400, null);	//fill window with image
			
			if(InputHandler.MouseX > 720 && InputHandler.MouseX < 720+80 && InputHandler.MouseY > 360&& InputHandler.MouseY < 360+30) {
				g.setColor(Color.WHITE);					//if mouse touch the button, it will be white
				g.setFont(new Font("verdana", 0, 30));		//typing style
				g.drawString("BACK", 710, 390);				//draw a string BACK
				if (InputHandler.MouseButton == 1) {		//click 
					Sound.click.play();						//clicking sound
					//System.out.println("e");
					cred=0;									//set cred to 0
				}
			} else {
				g.setColor(Color.GRAY);						//button is gray without mouse touching
				g.setFont(new Font("verdana", 0, 30));		//typing style
				g.drawString("BACK", 710, 390);				//draw a string BACK
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		g.dispose();
		bs.show();
	}
	
//	public static void rest() {
//		System.out.println("processing");
//		//frame.setVisible(true);
//	}
	
	//We used to draw buttons with JButton and Rectangle and JPanel
//	private void drawButtons() {
//		play = new JButton("Play!");
//		rplay = new Rectangle((width / 2) - (button_width / 2), 90, button_width, button_height);
//		play.setBounds(rplay);
//		window.add(play);
//		
//		options = new JButton("Options!");
//		roptions = new Rectangle((width / 2) - (button_width / 2), 140, button_width, button_height);
//		options.setBounds(roptions);
//		window.add(options);
//		
//		help = new JButton("Help!");
//		rhelp = new Rectangle((width / 2) - (button_width / 2), 190, button_width, button_height);
//		help.setBounds(rhelp);
//		window.add(help);
//		
//		quit = new JButton("Quit!");
//		rquit = new Rectangle((width / 2) - (button_width / 2), 240, button_width, button_height);
//		quit.setBounds(rquit);
//		window.add(quit);
//		
//		play.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				config.loadConfiguration("res/setting/config.xml");
//				frame.dispose();
//				new RunGame();
//				//System.out.println("Play!!");
//			}
//		});
//		
//		options.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				frame.dispose();
//				new Options();
//			}
//		});
//		
//		help.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Play!!");
//			}
//		});
//		
//		quit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
//			}
//		});
//	}

	

}
