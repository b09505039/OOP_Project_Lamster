package level;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import graphics.Sprite;
import graphics.Sprite2;

public class Block {
	
	public boolean solid = false;
	public boolean blocksMotion = false;						//if detect collision, blocks(Entity entity) in Block.java will return blocksMotion is true
	
	public static Block solidWall = new SolidBlock();			//determine the wall is solid or not, it can help detect collision
	
	public List<Sprite> sprites = new ArrayList<Sprite>();		//arrayList of Sprite
	public List<Sprite2> sprites2 = new ArrayList<Sprite2>();		//arrayList of Sprite2
	public List<Entity> entities = new ArrayList<Entity>();		//arrayList of Entity
	
	public int tex = -1;		//initialization of tex
	public int col = -1;		//initialization of col
	
	public int floorCol = -1;	//initialization of floorCol
	public int ceilCol = -1;	//initialization of ceilCol
	
	public int floorTex = -1;	//initialization of floorTex
	public int ceilTex = -1;	//initialization of ceilTex
	
	public Level level;		//use in init method in Level
	
	
	public int x, y;		//this is every block's coordinate
	
	public int id;
	
	

	public void addSprite(Sprite sprite) {		//add sprite(diamond block)
		sprites.add(sprite);
	}
	
	public void addSprite2(Sprite2 sprite2) {	//add sprite2(rickroll)
		sprites2.add(sprite2);
	}
	
	public void tick() {		//detect the changes every second
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = sprites.get(i);		
			sprite.tick();
			if (sprite.removed) {			//sprite whether be removed
				sprites.remove(i--);
			}
		}
	}
	
	public void removeEntity(Entity entity) {	//remove entity of object
		entities.remove(entity);
	}
	
	public void addEntity(Entity entity) {		//add entity of object
		entities.add(entity);
	}
	
	
	public boolean blocks(Entity entity) {		//if detect collision, blocks(Entity entity) in Block.java will return blocksMotion is true
		return blocksMotion;
	}

//	public void decorate(Level level, int x, int y) {
//	}
}
