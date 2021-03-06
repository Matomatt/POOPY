package Engine.Objets;

import java.io.IOException;

import Utilitaires.Direction;
import Utilitaires.ErrorMessage;
import Utilitaires.ObjectType;

public class TapisRoulant extends AnimatedObject {
	public Direction orientation = Direction.NORTH;
	
	public TapisRoulant(int _x, int _y, Direction _orientation)
	{
		super(_x, _y, 0, 0, ObjectType.TAPISROULANT, false, true);
		orientation = _orientation;
		solid = false;
		
		nbSpritesPerAnimationSequence = 3;
		ChangeAnimationFrequency(100);
		
		try {
			LoadSpriteSet(orientation);
		} catch (IOException e) {
			new ErrorMessage("Couldn't load tapisroulant sprite set...\n" + e.getLocalizedMessage());
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}
	
	public boolean CollideWith(AnimatedObject o)
	{
		if (super.CollideWith(o) && 
				((o.initSpeed[0]*1.5 == o.vitesse[0] && (orientation == Direction.WEST || orientation == Direction.EAST)) || 
						(o.initSpeed[1]*1.5 == o.vitesse[1] && (orientation == Direction.NORTH || orientation == Direction.SOUTH))) )
		{
			return true;
		}
		return false;
	}
	
	public String SavingInfo() {
		return ObjectType.mapIdOf(type) + "" + ((orientation == Direction.NORTH)?1:((orientation == Direction.SOUTH)?2:((orientation == Direction.EAST)?3:4)));
	}
}