package level;

public class StartLevel extends Level {		//StartLevel is the first level to be loaded
	public StartLevel() {
		super(0,0);		//extends from Level
	}

	protected void decorateBlock(int x, int y, Block block, int col) {	//decorateBlock is from Level, which can decorate texture on blocks
		super.decorateBlock(x, y, block, col);
	}

	protected Block getBlock(int x, int y, int col) {	//getBlock is from Level, which can determine which block the start map will generate
		return super.getBlock(x, y, col);
	}
}
