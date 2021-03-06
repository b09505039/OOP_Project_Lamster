package graphics;

import java.util.Random;

import input.Player;
import level.Block;
import level.Level;
import main.Game;

public class Render3D extends Render {

	public double[] zBuffer;
	public double[] zBufferWall;			//solve perspective problem
	private double renderDistance = 4000;
	private double forward, right, cosine, sine, up, walking;
	private int spriteSheerWidth = 160;		//loaded image "blocks" width
	Random random = new Random();

	int c = 0;

	double h = 0.1;

	public Render3D(int width, int height) {	//Render3D constructor, which parameter extended from Render.java
		super(width, height);
		zBuffer = new double[width * height];
		zBufferWall = new double[width];		//zBufferWall array solves perspective problem
	}

	public void floor(Game game) {		//render the floor's graphic

		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0;			//solve perspective problem
		}

		double floorposition = 8;
		double ceilingPosition = 24;	//set 800 can get rid of ceiling
		forward = game.player.z;		//forward depends on player's z direction movement
		right = game.player.x;			//right depends on player's x direction movement
		up = game.player.y;				//up depends on player's vertical direction movement
		walking = 0;

		double rotation = game.player.rotation; //self rotating: Math.sin(game.time %1000.0 / 80);
		cosine = Math.cos(rotation);			//the rotation function
		sine = Math.sin(rotation);

		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;

			double z = (floorposition + up) / ceiling;
			c = 0;												//determine the ceiling

			if (Player.walk) {									//if player is walking
				walking = Math.sin(game.time / 6.0) * 0.2;		//simulate the shaking range while walking	

				z = (floorposition + up + walking) / ceiling;	//the view will go up and down slightly

			}

			if (Player.crouchWalk && Player.walk) {
				walking = Math.sin(game.time / 6.0) * 0.1;		//simulate the shaking range while crouching	
				z = (floorposition + up + walking) / ceiling;	//the view will go up and down more slightly

			}

			if (Player.runWalk && Player.walk) {
				walking = Math.sin(game.time / 6.0) * 0.6;		//simulate the shaking range while running
				z = (floorposition + up + walking) / ceiling;	//the view will go up and down more aggressively

			}

			if (ceiling < 0) {									// split up and down
				z = (ceilingPosition - up) / -ceiling;
				c = 1;
				if (Player.walk) {
					z = (ceilingPosition - up - walking) / -ceiling;
				}

			}

			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine;			//rotation function
				double yy = z * cosine - depth * sine;			//rotation function
				int xPix = (int) ((xx + right)*4);				//4 is a parameter depends on our image number
				int yPix = (int) ((yy + forward)*4);			//4 is a parameter depends on our image number
				zBuffer[x + y * width] = z;
				// pixels[x + y * width] = ((xPix & 15) * 14)
				// | ((yPix & 15) * 16) << 8;
				// System.out.println(xx + " " + yy);
				if (c == 0) {													//c=0 means it is floor
					pixels[x + y * width] = Texture.blocks.pixels[(xPix & 31)	//floor should load the texture from "blocks" first region
							+ (yPix & 31) * spriteSheerWidth];
				} else {														//c!=0 means it is ceiling
					pixels[x + y * width] = Texture.blocks.pixels[(xPix & 31)	//ceiling should load the texture from "blocks" second region
							+ 32 + (yPix & 31) * spriteSheerWidth];
				}

				if (z > 300) {					//out of our range
					pixels[x + y * width] = 0;	//set pixels to 0
				}

			}
		}

		Level level = game.level;				//generate level, including wall's position
		int size = 100;
		
		for (int xBlock = -size; xBlock <= size; xBlock++) {		//xBlock means xLeft and xRight in renderWall method
			for (int zBlock = -size; zBlock <= size; zBlock++) {	//zBlock means zDistanceLeft and zDistanceRight in renderWall method
				Block block = level.getBlock(xBlock, zBlock);		//blocks location use getBlock method in Block.java to determine it is solid or not
				Block east = level.getBlock(xBlock + 1, zBlock);	//east side of blocks
				Block south = level.getBlock(xBlock, zBlock + 1);	//south side of blocks

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								0);													//render first layer of wall
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								0);													//render first layer of wall
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock,		//load the texture on blocks' four direction: north, south, west, east
								0);													//render first layer of wall
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								0);													//render first layer of wall
					}
				}
			}
		}

		for (int xBlock = -size; xBlock <= size; xBlock++) {		//xBlock means xLeft and xRight in renderWall method
			for (int zBlock = -size; zBlock <= size; zBlock++) {	//zBlock means zDistanceLeft and zDistanceRight in renderWall method
				Block block = level.getBlock(xBlock, zBlock);		//blocks location use getBlock method in Block.java to determine it is solid or not
				Block east = level.getBlock(xBlock + 1, zBlock);	//east side of blocks
				Block south = level.getBlock(xBlock, zBlock + 1);	//south side of blocks

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								0.5);												//render second layer of wall
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								0.5);												//render second layer of wall
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock,		//load the texture on blocks' four direction: north, south, west, east
								0.5);												//render second layer of wall
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								0.5);												//render second layer of wall
					}

				}
			}
		}
		
				
		for (int xBlock = -size; xBlock <= size; xBlock++) {		//xBlock means xLeft and xRight in renderWall method
			for (int zBlock = -size; zBlock <= size; zBlock++) {	//zBlock means zDistanceLeft and zDistanceRight in renderWall method
				Block block = level.getBlock(xBlock, zBlock);		//blocks location use getBlock method in Block.java to determine it is solid or not
				Block east = level.getBlock(xBlock + 1, zBlock);	//east side of blocks
				Block south = level.getBlock(xBlock, zBlock + 1);	//south side of blocks

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								1.5);												//render fourth layer of wall
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								1.5);												//render fourth layer of wall
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock,		//load the texture on blocks' four direction: north, south, west, east
								1.5);												//render fourth layer of wall
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								1.5);												//render fourth layer of wall
					}

				}
			}
		}
		for (int xBlock = -size; xBlock <= size; xBlock++) {		//xBlock means xLeft and xRight in renderWall method
			for (int zBlock = -size; zBlock <= size; zBlock++) {	//zBlock means zDistanceLeft and zDistanceRight in renderWall method
				Block block = level.getBlock(xBlock, zBlock);		//blocks location use getBlock method in Block.java to determine it is solid or not
				Block east = level.getBlock(xBlock + 1, zBlock);	//east side of blocks
				Block south = level.getBlock(xBlock, zBlock + 1);	//south side of blocks

				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								1.0);												//render third layer of wall
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								1.0);												//render third layer of wall
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock,		//load the texture on blocks' four direction: north, south, west, east
								1.0);												//render third layer of wall
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1,		//load the texture on blocks' four direction: north, south, west, east
								1.0);												//render third layer of wall
					}

				}
			}
		}
		for (int xBlock = -size; xBlock <= size; xBlock++) {//xBlock means xLeft and xRight in renderWall method
			for (int zBlock = -size; zBlock <= size; zBlock++) {	//zBlock means zDistanceLeft and zDistanceRight in renderWall method
				Block block = level.getBlock(xBlock, zBlock);
				for (int s = 0; s < block.sprites.size(); s++) {	//to render sprites
					Sprite sprite = block.sprites.get(s);
					renderSprite(xBlock + sprite.x, sprite.y,		//use renderSpritemethod below
							zBlock + sprite.z, h);

				}
			}
		}
		for (int xBlock = -size; xBlock <= size; xBlock++) {
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = level.getBlock(xBlock, zBlock);
				for (int s = 0; s < block.sprites2.size(); s++) {
					Sprite2 sprite2 = block.sprites2.get(s);

					renderSprite2(xBlock + sprite2.x, sprite2.y,
							zBlock + sprite2.z, h);

				}
			}
		}
	}

	public void renderSprite(double x, double y, double z, double hoffset) {	//render sprite
		double upCorrect = -0.125;		//correction coefficient
		double rightCorrect = 0.0625;	//correction coefficient
		double forwardCorrect = 0.0625;	//correction coefficient
		double walkCorrect = 0.0625;	//correction coefficient

		double xc = ((x / 2) - (right * rightCorrect)) * 2 + 0.5;					//for sight shaking, fixing the shaking range while walking	
		double yc = ((y / 2) - (up * upCorrect)) + (walking * walkCorrect) * 2		//for sight shaking, fixing the shaking range while walking	
				+ hoffset;
		double zc = ((z / 2) - (forward * forwardCorrect)) * 2 + 0.5;				//for sight shaking, fixing the shaking range while walking	

		double rotX = xc * cosine - zc * sine;		//rotation function
		double rotY = yc;
		double rotZ = zc * cosine + xc * sine;		//rotation function

		double xCenter = width / 2;
		double yCenter = height / 2;

		double xPixel = rotX / rotZ * height + xCenter;
		double yPixel = rotY / rotZ * height + yCenter;

		double xPixelL = xPixel - height / 2 / rotZ;
		double xPixelR = xPixel + height / 2 / rotZ;

		double yPixelL = yPixel - height / 2 / rotZ;
		double yPixelR = yPixel + height / 2 / rotZ;

		int xpl = (int) xPixelL;
		int xpr = (int) xPixelR;
		int ypl = (int) yPixelL;
		int ypr = (int) yPixelR;

		if (xpl < 0)		//xpl range from 0~width
			xpl = 0;
		if (xpr > width)	//xpl range from 0~width
			xpr = width;
		if (ypl < 0)		//ypl range from 0~height
			ypl = 0;
		if (ypr > height)	//ypl range from 0~height
			ypr = height;

		rotZ *= 5;
		for (int yp = ypl; yp < ypr; yp++) {

			double pixelRotationY = (yp - yPixelR) / (yPixelL - yPixelR);
			int yTexture = (int) (pixelRotationY * 8*4);			// darker by distance
			for (int xp = xpl; xp < xpr; xp++) {
				double pixelRotationX = (xp - xPixelR) / (xPixelL - xPixelR);
				int xTexture = (int) (pixelRotationX * 8*4);		// darker by distance

				if (zBuffer[xp + yp * width] > rotZ) {
					int col = Texture.blocks.pixels[(xTexture & 30) + 96
							+ (yTexture & 30) * spriteSheerWidth];
					if (col != 0xffff00ff ) {			//Removing Backgrounds with color 0xffff00ff
						pixels[xp + yp * width] = col;
						zBuffer[xp + yp * width] = rotZ;
					}

				}
			}
		}

	}
	
	public void renderSprite2(double x, double y, double z, double hoffset) {	//render sprite2
		double upCorrect = -0.125;		//correction coefficient
		double rightCorrect = 0.0625;	//correction coefficient
		double forwardCorrect = 0.0625;	//correction coefficient
		double walkCorrect = 0.0625;	//correction coefficient

		double xc = ((x / 2) - (right * rightCorrect)) * 2 + 0.5;					//for sight shaking, fixing the shaking range while walking	
		double yc = ((y / 2) - (up * upCorrect)) + (walking * walkCorrect) * 2		//for sight shaking, fixing the shaking range while walking	
				+ hoffset;
		double zc = ((z / 2) - (forward * forwardCorrect)) * 2 + 0.5;				//for sight shaking, fixing the shaking range while walking	

		double rotX = xc * cosine - zc * sine;		//rotation function
		double rotY = yc;
		double rotZ = zc * cosine + xc * sine;		//rotation function

		double xCenter = width / 2;
		double yCenter = height / 2;

		double xPixel = rotX / rotZ * height + xCenter;
		double yPixel = rotY / rotZ * height + yCenter;

		double xPixelL = xPixel - height / 2 / rotZ;
		double xPixelR = xPixel + height / 2 / rotZ;

		double yPixelL = yPixel - height / 2 / rotZ;
		double yPixelR = yPixel + height / 2 / rotZ;

		int xpl = (int) xPixelL;
		int xpr = (int) xPixelR;
		int ypl = (int) yPixelL;
		int ypr = (int) yPixelR;

		if (xpl < 0)		//xpl range from 0~width
			xpl = 0;
		if (xpr > width)	//xpl range from 0~width
			xpr = width;
		if (ypl < 0)		//ypl range from 0~height
			ypl = 0;
		if (ypr > height)	//ypl range from 0~height
			ypr = height;

		rotZ *= 5;
		for (int yp = ypl; yp < ypr; yp++) {

			double pixelRotationY = (yp - yPixelR) / (yPixelL - yPixelR);
			int yTexture = (int) (pixelRotationY * 8*4);			// darker by distance
			for (int xp = xpl; xp < xpr; xp++) {
				double pixelRotationX = (xp - xPixelR) / (xPixelL - xPixelR);
				int xTexture = (int) (pixelRotationX * 8*4);		// darker by distance

				if (zBuffer[xp + yp * width] > rotZ) {
					int col = Texture.blocks.pixels[(xTexture & 30) + 128
							+ (yTexture & 30) * spriteSheerWidth];
					if (col != 0xffff00ff ) {			//Removing Backgrounds with color 0xffff00ff
						pixels[xp + yp * width] = col;
						zBuffer[xp + yp * width] = rotZ;
					}

				}
			}
		}

	}

	public void renderWall(double xLeft, double xRight, double zDistanceLeft,
			double zDistanceRight, double yHeight) {		//render wall with fixing clipping and loading texture

		double upCorrect = 0.0625;		//correction coefficient
		double rightCorrect = 0.0625;	//correction coefficient
		double forwardCorrect = 0.0625;	//correction coefficient
		double walkCorrect = -0.0625;	//correction coefficient

		double xcLeft = ((xLeft / 2) - (right * rightCorrect)) * 2;
		double zcLeft = ((zDistanceLeft / 2) - (forward * forwardCorrect)) * 2;

		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBL = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;

		double xcRight = ((xRight / 2) - (right * rightCorrect)) * 2;
		double zcRight = ((zDistanceRight / 2) - (forward * forwardCorrect)) * 2;

		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double yCornerBR = ((+0.5 - yHeight) - (-up * upCorrect + (walking * walkCorrect))) * 2;
		double rotRightSideZ = zcRight * cosine + xcRight * sine;

		//move below clipping
//		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
//		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);
		
		double tex30 = 0;
		double tex40 = 8;
		double clip = 0.5;		//clip graphic programming use Cohen?VSutherland algorithm to solve clipping problem

		// prevent error
		if (rotLeftSideZ < clip && rotRightSideZ < clip) {
			return;
		}
		
		// for broken texture at boundary.
		if (rotLeftSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ)
					/ (rotRightSideZ - rotLeftSideZ);
			rotLeftSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ)
					* clip0;
			rotLeftSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX)
					* clip0;
			tex30 = tex30 + (tex40 - tex30) * clip0;
		}
		if (rotRightSideZ < clip) {
			double clip0 = (clip - rotLeftSideZ)
					/ (rotRightSideZ - rotLeftSideZ);
			rotRightSideZ = rotLeftSideZ + (rotRightSideZ - rotLeftSideZ)
					* clip0;
			rotRightSideX = rotLeftSideX + (rotRightSideX - rotLeftSideX)
					* clip0;
			tex40 = tex30 + (tex40 - tex30) * clip0;
		}
		
		//after moving
		double xPixelLeft = (rotLeftSideX / rotLeftSideZ * height + width / 2);
		double xPixelRight = (rotRightSideX / rotRightSideZ * height + width / 2);

		if (xPixelLeft >= xPixelRight) {
			return;
		}
		int xPixelLeftInt = (int) xPixelLeft;
		int xPixelRightInt = (int) xPixelRight;

		if (xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}

		if (xPixelRightInt > width) {
			xPixelRightInt = width;
		}

		//these four lines of code can not cast (int)
		double yPixelLeftTop = yCornerTL / rotLeftSideZ * height + height / 2;
		double yPixelLeftBottom = yCornerBL / rotLeftSideZ * height + height / 2;
		double yPixelRightTop = yCornerTR / rotRightSideZ * height + height / 2;
		double yPixelRightBottom = yCornerBR / rotRightSideZ * height + height / 2;
		
		// darker by distance
		double tex1 = 1 / rotLeftSideZ;
		double tex2 = 1 / rotRightSideZ;
		double tex3 = tex30 / rotLeftSideZ;
		double tex4 = tex40 / rotRightSideZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft)
					/ (xPixelRight - xPixelLeft);

			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);	//solve perspective problem

			if (zBufferWall[x] > zWall) {
				continue;
			}
			zBufferWall[x] = zWall;

			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall*4);	//darker by distance

			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop)
					* pixelRotation;
			double yPixelBottom = yPixelLeftBottom
					+ (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);

			if (yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if (yPixelBottomInt > height) {
				yPixelBottomInt = height;
			}

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop)
						/ (yPixelBottom - yPixelTop);				//darker by distance
				int yTexture = (int) (8 * pixelRotationY)*4+1;		//darker by distance
				// pixels[x + y * width] = xTexture * 100 + yTexture * 100 * 256;
				pixels[x + y * width] = Texture.blocks.pixels[((xTexture & 31) + 64)		//wall should load the texture from "blocks" third region
						+ (yTexture & 31) * spriteSheerWidth];

				zBuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1)	//darker by distance
						* pixelRotation) * 5;

			}
		}
	}

	public void renderDistanceLimiter() {		//this method is used to fade off in the far way from player
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);

			if (brightness < 0) {
				brightness = 0;
			}

			if (brightness > 255) {
				brightness = 255;
			}

			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xff;
			int b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}

}
