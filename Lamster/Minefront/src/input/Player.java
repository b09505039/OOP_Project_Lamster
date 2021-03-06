package input;

import entity.Mob;
import graphics.Sound;

public class Player extends Mob {

	public double y;
	// public double z;
	public double rotation;
	public double xa;
	public double za;

	public double rotationa;						//rotationa is a variable of rotation speed
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean walk = false;				// for sight shaking
	public static boolean crouchWalk = false;		// for sight shaking
	public static boolean runWalk = false;			// for sight shaking
	public double walkSpeed = 0;
	public int ff = 1;
	public int GY = 1;
	public int nu = 1;

	private InputHandler input;

	public Player(InputHandler input) {				//player's controller
		this.input = input;
	}

	public void tick() {							//detect the changes every second
		double rotationSpeed = 0.02;				//player's view rotating speed

		// double xMove = 0;
		// double zMove = 0;

		double jumpHeight = 2.5;					//how high can player jump
		double crouchHeight = 0.3;					//how low can player crouch

		xa = 0;
		za = 0;

		// System.out.println(walk);

		if (input.forward) {						//when user press w button on keyboard
			za++;									//za=za+1, when user press w button
			// zMove++;
			walk = true;							//and turn boolean walk to true
		}
		if (input.back) {							//when user press s button on keyboard
			za--;									//za=za-1, when user press s button
			// zMove--;
			walk = true;							//and turn boolean walk to true
		}
		if (input.left) {							//when user press a button on keyboard
			xa--;									//xa=xa-1, when user press a button
			// xMove--;
			walk = true;							//and turn boolean walk to true
		}
		if (input.right) {							//when user press d button on keyboard
			xa++;									//xa=xa+1, when user press d button
			// xMove++;
			walk = true;							//and turn boolean walk to true
		}

		if (input.rleft) {							//when user press left button on keyboard
			rotationa -= rotationSpeed;
		}

		if (input.rright) {							//when user press right button on keyboard
			rotationa += rotationSpeed;
		}
		rotation += rotationa;
		rotationa *= 0.5;

		if (input.stop) {							//press p button can tp to end
			//x = level.xSpawn * 8.1;
			//z = level.xSpawn * 8.1;
			x = 61 * 8;
			z = 57 * 8;
			xa = 0;
			za = 0;
		}
//
		move(xa, za, rotation, walkSpeed);			//send these parameter to move method in Mob.java can calculate player's movement
//		if (xa != 0 || za != 0) {
//			move(xa, za, rotation, walkSpeed);
//		}
//
		if (input.jump) {							//when user press space button on keyboard
			y += jumpHeight;
			input.run = false;						//and turn boolean run to false
		}

		if (input.crouch) {							//when user press ctrl button on keyboard
			y -= crouchHeight;
			input.run = false;						//and turn boolean run to false
			crouchWalk = true;						//and turn boolean crouchWalk to true
			walkSpeed = 1.0;						//walk speed will be slow
		}

		if (input.run) {							//when user press shift button on keyboard
			walkSpeed = 2.0;						//walk speed will be fast
			walk = true;							//and turn boolean walk to true
			runWalk = true;							//and turn boolean runWalk to true
		}

		if (!input.forward && !input.back && !input.left && !input.right) {
			walk = false;
		}

		if (!input.crouch) {
			crouchWalk = false;

		}

		if (!input.run) {
			runWalk = false;

		}
		
		if(x >=60 * 8 && z >= 59 * 8 && ff==1) {		//if player go through the end at x = 61, z = 60 
			Sound.hal4.play();							//ending sound
			win();										//player wins, game is over
			ff = 0;
			
		}
		
		if(x >= 44*8 && z >= 24*8 &&x <= (44+1)*8 && z <= (24+1)*8 && GY==1) {	//play rickroll in 3*3 space
			Sound.NGGYU.play();
			GY = 0;
		}
		
		if(x >= 28*8 && z >= 36*8 &&x <= (28+1)*8 && z <= (36+1)*8 && nu==1) {	//play sound in middle of map
			Sound.nuance1.play();
			nu = 0;
		}

		// xa += ((xMove * Math.cos(rotation)) + (zMove * Math.sin(rotation)))
		// * walkSpeed;
		// za += ((zMove * Math.cos(rotation)) - (xMove * Math.sin(rotation)))
		// * walkSpeed;

		// x += xa;
		// System.out.println(x);
		y *= 0.9;
		// z += za;

		// xa *= 0.1;
		// za *= 0.1;

//		System.out.println("x = " + x );
//		System.out.println("y = " + y );
//		System.out.println("z = " + z );
	}

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean rleft, boolean rright,
			boolean jump, boolean crouch, boolean run) { 	//detect the controller's changes every second
	}

	public void win() {		//win method will call level.java
		level.win();			
	}
}
