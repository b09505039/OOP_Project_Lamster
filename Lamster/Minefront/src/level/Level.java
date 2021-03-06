package level;

import entity.Entity;
import graphics.Sprite;
import graphics.Sprite2;
import graphics.Texture;
import input.Player;
import main.Game;
//import main.RunGame;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

public class Level {

	public Block[] blocks;
	public int width, height;
	private Block solidWall = new SolidBlock();
	final Random random = new Random(300);

	public int xSpawn;						//xSpawn is the x location when player new game
	public int ySpawn;						//ySpawn is the z location when player new game

	protected int wallCol = 0xB3CEE2;		//initialize wallCol's color
	protected int wallTex = 0;

	protected int floorCol = 0x9CA09B;		//initialize floorCol's color
	protected int floorTex = 0;

	protected int ceilCol = 0x9CA09B;		//initialize ceilCol's color
	protected int ceilTex = 0;

	private List<Entity> entities = new ArrayList<Entity>();

	protected Game game;
	public Player player;

	public void init(Game game, String name, int w, int h, int[] pixels) {		//initialization of entire game, be called from loadlevel
		this.game = game;

		player = game.player;
		

		solidWall.col = Texture.getCol(wallCol);		//solidWall.col is from getCol method in Texture.java with wallCol coefficient
		solidWall.tex = Texture.getCol(wallTex);		//solidWall.tex is from getCol method in Texture.java with wallTex coefficient
		this.width = w;			//start.png's width
		this.height = h;		//start.png's height
		blocks = new Block[width * height];

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int col = pixels[x + y * w] & 0xffffff;	//bitwise operator
				Block block = getBlock(x, y, col);		//Overriding, with color parameter
				
				if (block.tex == -1)
					block.tex = wallTex;					//block.tex = wallTex when	block.tex == -1
				if (block.floorTex == -1)
					block.floorTex = floorTex;				//block.floorTex = floorTex when block.floorTex == -1
				if (block.ceilTex == -1)
					block.ceilTex = ceilTex;				//block.ceilTex = ceilTex when block.ceilTex == -1
				if (block.col == -1)
					block.col = Texture.getCol(wallCol);	//block.col = Texture.getCol(wallCol) when block.col == -1
				if (block.floorCol == -1)
					block.floorCol = Texture.getCol(floorCol);//block.floorCol = Texture.getCol(floorCol) when block.floorCol == -1
				if (block.ceilCol == -1)
					block.ceilCol = Texture.getCol(ceilCol);  //block.ceilCol = Texture.getCol(ceilCol) when block.ceilCol == -1

				if (x == 0 && y == 0) {			//only run one time
					block.addSprite(new Sprite(61, 1, 60));			//add sprite at the end
					block.addSprite2(new Sprite2(41, 1, 23));		//add sprite at the 3*3space
				}

				blocks[x + y * w] = block;
				block.level = this;
				block.x = x;
				block.y = y;
			}
		}

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int col = pixels[x + y * w] & 0xffffff;
				decorateBlock(x, y, blocks[x + y * w], col); //decorateBlock method below can set the spawn point
			}
		}
		
		//player.x = xSpawn * 8.1;
		//player.z = ySpawn * 8.1;
	}

	//constructor can be replaced by init method
	public Level(int width, int height) {		//we don't use the to generate level, because we should load level from startlevel instead of generating randomly
		this.width = width;
		this.height = height;
		blocks = new Block[(width) * (height)];
		generateLevel();
	}

	public void tick() {		//one unit at a time every time it gets called

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
			e.updatePos();			//updates enetity's position

			if (e.isRemoved()) {	//entities whether be removed
				entities.remove(i--);
			}
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				blocks[x + y * width].tick();	//call tick method again to determine it is removed or not
			}
		}

	}

	public void addEntity(Entity e) {		//add entity
		entities.add(e);
		e.level = this;
		e.updatePos();		//updates entity's position
	}

	public void generateLevel() {		//generate level, including wall and sprite

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Block block = null;
				if (random.nextInt(10) == 0) {
					block = new SolidBlock();					//generate wall randomly
				} else {
					block = new Block();
					if (random.nextInt(25) == 0) {
						block.addSprite(new Sprite(0, 0, 0));	//generate sprite randomly
					}
				}
				blocks[x + y * width] = block;

			}
		}

	}

	protected void decorateBlock(int x, int y, Block block, int col) {
		//block.decorate(this, x, y);
		if (col == 0xFFFF00) {			//0xFFFF00 is yellow block in our start.png
			xSpawn = x;					//it is the x location of player spawn point
			ySpawn = y;					//it is the z location of player spawn point
		}
//		if (col == 0x1A2108 || col == 0xff0007) {
//			block.floorTex = 7;
//			block.ceilTex = 7;
//		}
//
//		if (col == 0xC6C6C6)
//			block.col = Texture.getCol(0xa0a0a0);
//		if (col == 0xC6C697)
//			block.col = Texture.getCol(0xa0a0a0);
//
//		if (col == 0x653A00) {
//			block.floorCol = Texture.getCol(0xB56600);
//			block.floorTex = 3 * 8 + 1;
//		}
//
//		if (col == 0x93FF9B) {
//			block.col = Texture.getCol(0x2AAF33);
//			block.tex = 8;
//		}
	}

	protected Block getBlock(int x, int y, int col) {	//generate blocks with corresponding colors in start.png. Overriding
		if (col == 0xFFFFFF)			//white blocks in start.png means solid wall
			return new SolidBlock();
//		if (col == 0x8cfffb) {		//win area
//			// System.out.println("trigger");
//		}
		return new Block();
	}

	public Block getBlock(int x, int y) {		//if detect the collision with wall, blocks(Entity entity) in Block.java will return blocksMotion is true
		if (x < 0 || y < 0 || x >= width || y >= height) {
			// System.out.println("used");
			// System.out.println("width = "+width);
			return solidWall;
		}
		// System.out.println("In Level.java 82 : "+(blocks[x + y * width]));
		return blocks[x + y * width];
	}

	private static Map<String, Level> loaded = new HashMap<String, Level>(); 	//Map Interface of API

	public static Level loadLevel(Game game, String name) {		//load level from StartLevel.java, 
		if (loaded.containsKey(name))
			return loaded.get(name);

		try {

			BufferedImage img = ImageIO.read(Level.class.getResource("/" + name + ".png"));		//load image "start.png" from res folder

			int w = img.getWidth();			//get width from start.png
			int h = img.getHeight();		//get height from start.png
			int[] pixels = new int[w * h];	//pixels array

			img.getRGB(0, 0, w, h, pixels, 0, w);	//RGB of pixels

			Level level = Level.byName(name);		//call StartLevel.java 
			level.init(game, name, w, h, pixels);	//call init method to start the game with correct map
			loaded.put(name, level);

			return level;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Level byName(String name) {			//byName method can return other different name class by using forName
		try {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);	//first character turn into UpperCase

			return (Level) Class.forName("level." + name + "Level").newInstance();		//return in StartLevel.java
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void win() {		//win method from player.win(), and call win method in Game.java to end the game
		game.win();
	}

}
