package Engine.Objets;

import java.io.IOException;

import Utilitaires.Direction;
import Utilitaires.ObjectType;

public class TapisRoulant extends AnimatedObject {
	private static final long serialVersionUID = -8880741442049111378L;
	
	public Direction orientation = Direction.NORTH;
	
	public TapisRoulant(int _x, int _y, Direction _orientation)
	{
		super(_x, _y, 0, 0, ObjectType.TAPISROULANT, false, true);
		orientation = _orientation;
		
		nbSpritesPerAnimationSequence = 3;
		ChangeAnimationFrequency(100);
		
		try {
			LoadSpriteSet(orientation);
		} catch (IOException e) {
			System.out.println("Couldn't load tapisroulant sprite set");
			e.printStackTrace();
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}
	
	public boolean CollideWith(AnimatedObject o)
	{
		if (super.CollideWith(o) && 
				((o.initSpeed[0]*2 == o.vitesse[0] && (orientation == Direction.WEST || orientation == Direction.EAST)) || 
						(o.initSpeed[1]*2 == o.vitesse[1] && (orientation == Direction.NORTH || orientation == Direction.SOUTH))) )
		{
			return true;
		}
		return false;
	}
}