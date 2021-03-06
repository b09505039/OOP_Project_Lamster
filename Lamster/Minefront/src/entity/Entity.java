package entity;

//import java.util.List;

import level.Level;

public class Entity {			//Entity is used to clarify the relation between player and solidblock(wall)

	public double x, z;
	public double r = 0.4;		//r is used to fix collision detection to the wall

	public Level level;
	public int xTileO = -1;
	public int zTileO = -1;

	protected boolean removed = false;

	protected Entity() {
	}

	public final void updatePos() {			//updates player's position
		int xTile = (int) (x + 0.5);
		int zTile = (int) (z + 0.5);
		if (xTile != xTileO || zTile != zTileO) {
			level.getBlock(xTileO, zTileO).removeEntity(this);

			xTileO = xTile;
			zTileO = zTile;

			if (!removed)
				level.getBlock(xTileO, zTileO).addEntity(this);		//if it isn't removed, addEntity
		}
	}

	public boolean isRemoved() {			//object whether be removed
		return removed;
	}

	public void remove() {					//object whether be removed
		removed = true;
	}

	protected boolean isFree(double xx, double yy) {	//isFree method can determine player's collision detection
		int x0 = (int) (Math.floor(xx   - r  ));		//x0, x1, y0, y1 are used to detect front, back, left and right direction of player
		int x1 = (int) (Math.floor(xx   + r ));			//r is used to fix collision detection to the wall
		int y0 = (int) (Math.floor(yy   - r));
		int y1 = (int) (Math.floor(yy   + r));
		
		//System.out.println("check entity.java 44 = "+(x0));
		//System.out.println("check entity.java 45 = "+(x1));
		//System.out.println("check entity.java 49 = "+(level.getBlock(x0, y0).blocks(this)));
		
		if (level.getBlock(x0, y0).blocks(this)) {		//if front side of player collides with wall, blocks(Entity entity) in Block.java will return blocksMotion is true
			//System.out.println("x0, y0 exp");
			return false;								//then player can go through the wall because isFree method is false
		}
		if (level.getBlock(x1, y0).blocks(this)) {		//if right side of player collides with wall, blocks(Entity entity) in Block.java will return blocksMotion is true
			//System.out.println("x1, y0 exp");
			return false;								//then player can go through the wall because isFree method is false
		}
		if (level.getBlock(x0, y1).blocks(this)) {		//if left side of player collides with wall, blocks(Entity entity) in Block.java will return blocksMotion is true
			//System.out.println("x0, y1 exp");
			return false;								//then player can go through the wall because isFree method is false
		}
		if (level.getBlock(x1, y1).blocks(this)) {		//if back side of player collides with wall, blocks(Entity entity) in Block.java will return blocksMotion is true
			//System.out.println("x1, y1 exp");
			return false;								//then player can go through the wall because isFree method is false
		}
		
		return true;
	}
	
	protected void collide(Entity entity) {
	}
	
	public void tick() {					//detect the changes every second

	}
}