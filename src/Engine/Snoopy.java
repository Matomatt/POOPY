package Engine;
import java.io.IOException;

import Utilitaires.*;

public class Snoopy extends AnimatedObject {
	private static final long serialVersionUID = -7782172256474576827L;
	
	Direction orientation = null;
	
	public Snoopy(int _x, int _y)
	{
		super(_x, _y, 0.02, 0.02, ObjectType.SNOOPY, true, true);
		
		animateOnlyWhenMoving = true;
		nbSpritesPerAnimationSequence = 4;
		
		ChangeAnimationFrequency(100);
		
		try {
			this.ChangeSpriteTo("/snoopy/snoopySOUTH1.png");
		} catch (IOException e) {
			System.out.println("Couldn't load snoopySOUTH1.png ");
			e.printStackTrace();
		}
	}
	
	//A ajouter que si il est déjà en mvmt bah il peut pas rebouger d'une case, bah ouai sinon il va se tp personne va capter
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
			super.Move(d);
		else
			ChangeOrientationTo(d);
		System.out.println("Moving from " + x + ", " + y + " to " + targetX + ", " + targetY);
		//Ca c'est juste pour te montrer ou il est censé aller mais comme j'ai pas fait l'animation bah il y bouge pas vraiment (x et y change pas tavu)
		//this.setLocation((int)targetX, (int)targetY);
		return false;
	}
	
	//On pourra changer le sprite ici en fonction de son orientation
	public void ChangeOrientationTo(Direction d)
	{
		if (orientation != d)
		{
			orientation = d;
			try {
				LoadSpriteSet(orientation);
			} catch (IOException e) {
				System.out.println("Couldn't load snoopy sprite set");
				e.printStackTrace();
			}
		}
		
	}
}
