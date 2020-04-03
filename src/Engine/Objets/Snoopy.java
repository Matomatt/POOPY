package Engine.Objets;
import java.io.IOException;

import Utilitaires.*;

public class Snoopy extends AnimatedObject {
	private static final long serialVersionUID = -7782172256474576827L;
	
	public Direction orientation = null;
	
	public Snoopy(int _x, int _y, boolean _selfMoved)
	{
		super(_x, _y, 3, 3, ObjectType.SNOOPY, true, true, _selfMoved, true);
		
		animateOnlyWhenMoving = true;
		nbSpritesPerAnimationSequence = 4;
		
		ChangeAnimationFrequency(100);
		
		this.ChangeOrientationTo(Direction.SOUTH);
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}
	
	//A ajouter que si il est deja en mvmt il peut pas rebouger d'une case, bah ouai sinon il va jamais s'arreter personne va capter
	public boolean CanMove(Direction towards)
	{
		if (!IsMoving() && orientation == towards)
			return true;
		return false;
	}
	
	//Si il est dans la bonne direction il bouge, sinon il change de direction, il bougera au prochain call
	public boolean Move(Direction d)
	{
		if (orientation == d)
			return super.Move(d);
		else
			ChangeOrientationTo(d);
		
		return false;
	}
	
	//On pourra changer le sprite ici en fonction de son orientation
	public boolean ChangeOrientationTo(Direction d)
	{
		if (orientation != d)
		{
			orientation = d;
			try {
				LoadSpriteSet(orientation);
			} catch (IOException e) {
				System.out.println("Couldn't load snoopy " + Direction.nameOf(d) + " sprite set");
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public void RefreshSprite()
	{
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}
}
