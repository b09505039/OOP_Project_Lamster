package entity;

//import entity.Entity;

public class Mob extends Entity {
	public double nx;
	public double nz;
	public void move(double xa, double za, double rot, double walkSpeed) {		//player move method
//		if(xa != 0 && za != 0) {
//			move(xa, 0, rot,walkSpeed);
//			System.out.println("HI");
//			move(0, za, rot,walkSpeed);
//			return;
//		}
		//System.out.println("what = " +(xa * Math.cos(rot) + za * Math.sin(rot)));

		nx =( xa * Math.cos(rot) + za * Math.sin(rot) )* walkSpeed;		//x direction rotation function
		
		nz =( za * Math.cos(rot) - xa * Math.sin(rot) )* walkSpeed;		//z direction rotation function
		
		nx *= 1.1;				//displacement
		nz *= 1.1;				//displacement
		
		double real_x = (x/8);	//x actual coordinate should be / 8 to satisfy the blocks number
		double real_z = (z/8);	//z actual coordinate should be / 8 to satisfy the blocks number
		
		
		int xSteps = (int) (Math.abs(nx) + 1);		//player's steps
//		System.out.println("xSteps = "+xSteps);
		for (int i = xSteps; i > 0; i--) {
			double xxa = nx ;
			
			if (isFree(real_x + xxa * i / xSteps, real_z)) {		//use isFree method to determine player's collision detection
				x += xxa * i / xSteps;
				break;
			} else {
				xa = 0;
			}
		}
		
		int zSteps = (int) (Math.abs(nz) + 1);		//player's steps
		
		for (int i = zSteps; i > 0; i--) {
			double zza = nz ;
				
			if (isFree(real_x, real_z + zza * i / zSteps)) {		//use isFree method to determine player's collision detection
				//System.out.println("cal = "+(z + zza * i / zSteps));
				z += zza * i / zSteps;
				break;
			} else {
				za = 0;
			}
		}
		
		//x += nx;
		//z += nz;
		//System.out.println("real_x = " + real_x );		//x real coordinate
		//System.out.println("real_z = " + real_z );		//z real coordinate
		

	}

}
