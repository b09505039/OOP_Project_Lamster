package level;

public class SolidBlock extends Block {

	public SolidBlock() {		//determine the wall is solid or not, it can help detect collision
		solid = true;
		blocksMotion = true;	//if player collides with wall, blocks(Entity entity) will return blocksMotion is true
	}
}
