package main;

import graphics.Screen;
import gui.Launcher;

import input.Player;
import input.InputHandler;

import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements Runnable {
	public static int width = 800;
	public static int height = 600;
	private static final long serialVersionUID = 1L;

	private Thread thread;				//do multiple tasks simultaneously, render multiple things at once
	private Screen screen;				//make an object
	private BufferedImage img;
	private Game game;
	private boolean running = false;	//isn't running at the moment
	private int[] pixels;
	private InputHandler input;
	private int newX = 0;
	private int oldX = 0;

	public static int selection = 0;

	long lastFPS;
	long fps;							//count fps

	public static int mouseSpeed;		//control mouse moving speed 

	public static Launcher lanucher;

	public Display() {
		Dimension size = new Dimension(getGameWidth(), getGameHeight());		//allow to put width and height into one object
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		screen = new Screen(getGameWidth(), getGameHeight());
		input = new InputHandler();
		game = new Game(input);
		img = new BufferedImage(getGameWidth(), getGameHeight(),				//graphic things
				BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();	//assign in Render

		
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

	}

	public static Launcher getLanucher() {		//let main function start the launcher interface
		if (lanucher == null) {
			lanucher = new Launcher(0);
		}
		return lanucher;
	}

	public int getGameWidth() {					//get game window's width 
		return width;
	}

	public int getGameHeight() {				//get game window's height
		return height;
	}

	public synchronized void start() {			//initialize
		if (running)							//if running is true, exit this method
			return;
		running = true;							//initialize
		thread = new Thread(this, "game");
		thread.start();
		
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();						//thread.join() can let running thread pause
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}

	long actualfps;

	public void run() {							//calculate fps in this game
		long previousTime = System.nanoTime();
		double na = 1000000000.0 / 60.0;
		double delta = 0;
		// int tickCount = 0;
		// boolean ticked = false;
		int frames = 0;
		//int updates = 0;
		long timer = System.currentTimeMillis();
		requestFocus();							//enable user use their keyboard without clicking the window
		while (running) {

			long currentTime = System.nanoTime();
			delta += (currentTime - previousTime) / na;
			previousTime = currentTime;

			if (delta >= 1) {
				tick();
				//updates++;
				delta--;
			}

			render();
			frames++;

			while (System.currentTimeMillis() - timer > 1000) {		//limit the fps under 60 to reduce CPU loading 
			timer += 1000;
			actualfps = frames;
			frames = 0;
			//updates = 0;
			}

		}
	}

	private void tick() {						//detect the changes every second
		input.tick();
		game.tick();
											//we once use the mouse to control the rotation of view
		newX = InputHandler.MouseX;			//but we finally use keyboard to control
		if (newX > oldX) {					//calculate the mouse moving distance to determine right or left
			Player.turnRight = true;
		}
		if (newX < oldX) {
			Player.turnLeft = true;
		}
		if (newX == oldX) {
			Player.turnLeft = false;
			Player.turnRight = false;
		}
		mouseSpeed = Math.abs(newX - oldX);
		oldX = newX;						//initialize the mouse's coordinate
	}

	private void render() {						//show the current fps on top left if window
		BufferStrategy bs = this.getBufferStrategy();	//graphic things that only run one time
		if (bs == null) {
			createBufferStrategy(3);					//3 dimensional
			return;
		}
		screen.render(game);

		for (int i = 0; i < getGameWidth() * getGameHeight(); i++) {	//create the array of windows' size
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getGameWidth(), getGameHeight(), null);
		g.setFont(new Font("Verdana", 2, 50));		//typing font, style(0:normal/1:bold/2:italics/3:bold and italics),size
		g.setColor(Color.yellow);					//word's color
		g.drawString(actualfps + " FPS", 20, 50);	//print string on the screen with coordinate
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {

		getLanucher();								//start the launcher interface

	}

}
