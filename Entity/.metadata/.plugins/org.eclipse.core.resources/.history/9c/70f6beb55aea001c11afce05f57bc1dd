package gui;

import input.InputHandler;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import main.Configuration;
import main.Display;
import main.RunGame;



public class Launcher extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	private JButton play, options, help, quit;
//	private JTextField twidth, theight;
//	private JLabel lwidth, lheight;
	private Rectangle rplay, roptions, rhelp, rquit;
	Configuration config = new Configuration();

	private int width = 800;
	private int height = 400;
	protected int button_width = 80;
	protected int button_height = 40;
	boolean running = false;
	Thread thread;
	JFrame frame = new JFrame();

	public Launcher(int id) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setUndecorated(true);
		//frame.setTitle("c8763 Launcher");
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//getContentPane().add(window);
		frame.add(this);
//		frame.add(display);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		window.setLayout(null);
		//drawButtons();
		
		
		if(id == 0) {
			drawButtons();
		}
		
		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		
		startMenu();
		//display.start();
		frame.repaint(); //button won't disappear until mouse go through
	}
	
	public void updateFrame() {
		if(InputHandler.dragged) {
//			int x = getX();
//			int y = getY();
			Point p = frame.getLocation(); //Canvas and JFrame both have a method called "gerLocation"
			frame.setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX, p.y + InputHandler.MouseDY - InputHandler.MousePY);
		}
	}
	
	public void startMenu() {
		running = true;
		thread = new Thread(this, "menu");
		thread.start();
	}
	
	public void stopMenu() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(running) {
			try {
			rendermenu();
			} catch (IllegalStateException e) {
				System.out.println("error");
			}
			updateFrame();
		}
	}
	
	private void rendermenu() throws IllegalStateException {
		BufferStrategy bs = this.getBufferStrategy(); // graphic things, 只想執行一次
		if (bs == null) {
			createBufferStrategy(3); // 3 dimensional
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
		
		String FileName = "/menu.jpg";
		//String FileName2 = "res/setting/config.xml";
		InputStream iss = Display.class.getResourceAsStream(FileName);
		//InputStream iss2 = Display.class.getResourceAsStream(FileName2);
		try {
			g.drawImage(ImageIO.read(iss), 0, 0, 800, 400, null);
			if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690+80 && InputHandler.MouseY > 100 && InputHandler.MouseY < 100+30) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("PLAY", 690, 130);
				
				if(InputHandler.MouseButton == 1) {
					config.loadConfiguration("/setting/config.xml");
//					try {
//						InputStream read = new FileInputStream(FileName2);
//						config.properties.loadFromXML(read);
//						String width = config.properties.getProperty("width");
//						String height = config.properties.getProperty("height");
//						System.out.println("Width = " + width + ", Height = " + height);
//						config.setResolution(Integer.parseInt(width), Integer.parseInt(height));
//						read.close();
//					} catch(FileNotFoundException e) {
//						config.saveConfiguration("width", 800);
//						config.saveConfiguration("height", 600);
//						config.loadConfiguration(FileName2);
//					} catch(IOException e) {
//						e.printStackTrace();
//					}
					frame.dispose();
					new RunGame();
				}
			} else {
				g.setColor(Color.GRAY);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("PLAY", 690, 130);
			}
			if(InputHandler.MouseX > 625 && InputHandler.MouseX < 625+135 && InputHandler.MouseY > 140 && InputHandler.MouseY < 140+30) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("OPTIONS", 625, 170);
				if(InputHandler.MouseButton == 1) {
					new Options();
				}
			} else {
				g.setColor(Color.GRAY);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("OPTIONS", 625, 170);
			}
			if(InputHandler.MouseX > 685 && InputHandler.MouseX < 685+80 && InputHandler.MouseY > 180 && InputHandler.MouseY < 180+30) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("HELP", 685, 210);
			} else {
				g.setColor(Color.GRAY);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("HELP", 685, 210);
			}
			if(InputHandler.MouseX > 690 && InputHandler.MouseX < 690+80 && InputHandler.MouseY > 220 && InputHandler.MouseY < 220+30) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("EXIT", 690, 250);
				if(InputHandler.MouseButton == 1) {
					//System.out.println("e");
					System.exit(0);
				}
			} else {
				g.setColor(Color.GRAY);
				g.setFont(new Font("verdana", 0, 30));
				g.drawString("EXIT", 690, 250);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		g.dispose();
		bs.show();
	}

	private void drawButtons() {
		play = new JButton("Play!");
		rplay = new Rectangle((width / 2) - (button_width / 2), 90, button_width, button_height);
		play.setBounds(rplay);
		window.add(play);
		
		options = new JButton("Options!");
		roptions = new Rectangle((width / 2) - (button_width / 2), 140, button_width, button_height);
		options.setBounds(roptions);
		window.add(options);
		
		help = new JButton("Help!");
		rhelp = new Rectangle((width / 2) - (button_width / 2), 190, button_width, button_height);
		help.setBounds(rhelp);
		window.add(help);
		
		quit = new JButton("Quit!");
		rquit = new Rectangle((width / 2) - (button_width / 2), 240, button_width, button_height);
		quit.setBounds(rquit);
		window.add(quit);
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.loadConfiguration("res/setting/config.xml");
				frame.dispose();
				new RunGame();
				//System.out.println("Play!!");
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				new Options();
			}
		});
		
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Play!!");
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}

	

}
