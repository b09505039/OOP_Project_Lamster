package level;

import entity.Entity;
import input.Player;

public class WinBlock extends Block {
	
	public void addEntity(Entity entity) {
		System.out.println("x = "+x +",y = "+ y);
		super.addEntity(entity);
		//System.out.println("x = "+x +",y = "+ y);
		if (entity instanceof Player) {
			//System.out.println("trigger");
			((Player)entity).win();
		}
	}

}
