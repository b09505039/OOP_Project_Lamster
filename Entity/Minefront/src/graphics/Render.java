package graphics;
//import main.Game;

public class Render {
	public final int width;
	public final int height;
	public final int[] pixels;

	public Render(int width, int height) {	//parameter comes from render = new Render(WIDTH, HEIGHT);
		this.width = width;					//initializing an integer of 800
		this.height = height;
		pixels = new int[height * width];	//pixels is an array contain the whole window's pixels
		
		
	}
	

	public void draw(Render render, int xOffset, int yOffset) {		//render
		for (int y = 0; y < render.height; y++) {					//in order to fill the whole screen with two for loop
			int yPix = y + yOffset;
			if (yPix < 0 || yPix >= height) {						//this can be omitted
				continue;
			}

			for (int x = 0; x < render.width; x++) {
				int xPix = x + xOffset;
				if (xPix < 0 || xPix >= width) {
					continue;
				}
				
				int alpha = render.pixels[x + y * render.width];	//if there's nothing to render, don't bother rendering

				if (alpha > 0) {									//avoid void pixels data if screen.java add  * (random.nextInt(5) / 4)
					pixels[xPix + yPix * width] = alpha;
				}
			}
		}
	}
}
