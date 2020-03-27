package Engine;

import java.io.IOException;

import Utilitaires.Direction;
import Utilitaires.ObjectType;

public class MovingBloc extends AnimatedObject {

	public MovingBloc(int _x, int _y, boolean _selfMoved) {
		super(_x, _y, 3, 3, ObjectType.MOVINGBLOC, true, false, _selfMoved, false);
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			System.out.println("Couldn't load movingbloc sprite set");
			e.printStackTrace();
		}
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}
	
	public boolean CanMove(Direction d)
	{
		if (!IsMoving())
			return true;
		return false;
	}
}
