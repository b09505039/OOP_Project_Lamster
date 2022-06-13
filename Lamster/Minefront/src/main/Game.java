package main;

import graphics.Sound;
import input.InputHandler;
import input.Player;

//import java.awt.event.KeyEvent;
//
//import graphics.Render;
//import graphics.Texture;
import level.Level;

public class Game {

	public int time;
	public Player player;
	public Level level;
	
	public Game(InputHandler input) {			//Game constructor include player's informations
		player = new Player(input);
		//level = new Level(20, 20);			//used to be our outer wall
		level = Level.loadLevel(this, "start"); //load the start level
		player.level = level;
		level.player = player;
//		System.out.println(level.xSpawn);
//		System.out.println(level.ySpawn);
		player.x = level.xSpawn * 8;			//player's spawn x location
		player.z = level.ySpawn * 8;			//player's spawn x location
		player.ff=1;
		Sound.cave5.play();						//cave scary sound
		
		level.addEntity(player);
		
	}

	public void tick() {		//one unit at a time every time it gets called
		time++;
		level.tick();
	}
	public static int w = 640;
	public static int h = 480;
	public void win() {			//win method from Player.java, Level.java
		RunGame.win();
	}
}
