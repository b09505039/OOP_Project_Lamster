package graphics;



import main.Game;

public class Screen extends Render {

	
	private Render3D  render;

	public Screen(int width, int height) {
		super(width, height);			//extends from Render
		
		render = new Render3D(width, height);
	}

	public void render(Game game) {		
		for (int i = 0; i <width*height; i++) {
			pixels[i] = 0;					//initial pixels array
		}
				
		render.floor(game);					//floor method in Render3D
		//render.renderWall(0, 0.5, 1.5, 1.5, 0);
		//render.renderWall(0, 0, 1, 1.5, 0);
		//render.renderWall(0, 0.5, 1, 1, 0);
		//render.renderWall(0.5, 0.5, 1, 1.5, 0);
		render.renderDistanceLimiter();		//enable to fade off
		
		draw(render, 0, 0);		//draw method is from Render
	}
}
