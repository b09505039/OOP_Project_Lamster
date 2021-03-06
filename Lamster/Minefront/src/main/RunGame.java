package main;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import gui.Gameover;

public class RunGame {
	static JFrame frame = new JFrame();

	public RunGame() {							//when user click the PLAY button, a new game will start
		Display game = new Display();
		frame.add(game);					//adding frame into game
		
		//invisible mouse
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		frame.getContentPane().setCursor(blank);
		
		frame.setTitle("Lamster");		//set title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//hit close button to terminate itself
		frame.setResizable(false);			//don't want to be resize
		frame.setVisible(true);				//let window be visible
		frame.pack();
		frame.setLocationRelativeTo(null);	//window in the center
		game.start();						//start method in display.java
		
		stopMenuThread();					//when game started, the Launcher can be closed
	}

	private void stopMenuThread() {				//when game started, the Launcher can be closed
		try {
			Display.getLanucher().stopMenu();	//stopMenu() in Launcher.java, which can let running thread pause
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void win() {					//win method from Player.java, Level.java, and Game.java
		System.out.println("you win");			
		frame.dispose();						//when player go to the end, game window will close
		new Gameover();							//and jump out a "YOU WIN" window
	}
}
