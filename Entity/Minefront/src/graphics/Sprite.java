package graphics;

public class Sprite {
	//Sprite class is used to generate a two-dimensional bitmap which will always face to the player, even though player's perspective changes. 
	public double x, y, z;
	public boolean removed = false;
	
	public Sprite( double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void tick() {		//detect the changes every second
	}
	
}
