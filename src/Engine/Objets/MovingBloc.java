package Engine.Objets;

import java.io.IOException;

import Engine.ErrorMessage;
import Utilitaires.Direction;
import Utilitaires.ObjectType;

public class MovingBloc extends AnimatedObject {
	private static final long serialVersionUID = -3537496655119019160L;
	
	public boolean moved = false;
	
	public MovingBloc(int _x, int _y, boolean _selfMoved) {
		super(_x, _y, 4.5, 4.5, ObjectType.MOVINGBLOC, true, true, _selfMoved, true);
		
		ChangeAnimationFrequency(100);
		nbSpritesPerAnimationSequence = 6; //15;
		animateOnlyWhenMoving = false;
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			new ErrorMessage("Couldn't load movingbloc sprite set...\n" + e.getLocalizedMessage());
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}
	
	public boolean Move(Direction d)
	{
		moved = true;
		return super.Move(d);
	}
	public boolean CanMove(Direction d)
	{
		if (!IsMoving())
			return true;
		return false;
	}
}
