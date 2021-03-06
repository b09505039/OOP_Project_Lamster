package graphics;

import java.awt.image.BufferedImage;
//import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Texture {
	public static Render blocks = loadBitmap("/blocks.png");		//load blocks.png from /res folder
	//public static Render floor = loadBitmap("/textures/floor1.png");

	private static Render loadBitmap(String fileName) {
		InputStream is = Texture.class.getResourceAsStream(fileName);	//set file name as stream, which is important
		try {
			BufferedImage image = ImageIO.read(is); 					//error: ImageIO.read(new FileInputStream(fileName));
			int width = image.getWidth();								//get the width of blocks image
			int height = image.getHeight();								//get the height of blocks image
			
			Render result = new Render(width,height);					//parameter comes from render = new Render(WIDTH, HEIGHT);
			image.getRGB(0,0,width, height,result.pixels,0,width);		//getRGB is from BufferedImage.java
			return result;
		
		} catch (Exception e) {
			System.out.println("CRASH!");
			throw new RuntimeException(e);
			
		}
	}
	
	
	public static int getCol(int c) {		//getCol method be called from init in Level.java
		int r = (c >> 16) & 0xff;			//use bitwise to get wallCol and wallTex color
		int g = (c >> 8) & 0xff;			//use bitwise to get wallCol and wallTex color
		int b = (c) & 0xff;					//use bitwise to get wallCol and wallTex color

		r = r * 0x55 / 0xff;				//calculating color
		g = g * 0x55 / 0xff;				//calculating color
		b = b * 0x55 / 0xff;				//calculating color

		return r << 16 | g << 8 | b;		//bitwise
	}



}
