package Engine.Objets;

import java.io.IOException;

import Utilitaires.Direction;
import Utilitaires.ObjectType;

public class MovingBloc extends AnimatedObject {

	public MovingBloc(int _x, int _y, boolean _selfMoved) {
		super(_x, _y, 4.5, 4.5, ObjectType.MOVINGBLOC, true, true, _selfMoved, true);
		
		ChangeAnimationFrequency(100);
		nbSpritesPerAnimationSequence = 6;//15;
		animateOnlyWhenMoving = false;
		
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
