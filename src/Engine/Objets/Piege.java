package Engine.Objets;

import java.io.IOException;

import Engine.ErrorMessage;
import Utilitaires.ObjectType;

public class Piege  extends AnimatedObject {
	private static final long serialVersionUID = -8154030431806512333L;

	public Piege(int _x, int _y)
	{
		super(_x, _y, 0, 0, ObjectType.PIEGE, false, true);
		nbSpritesPerAnimationSequence = 1;
		animationFrequency = 200;
		animationTimer.setDelay(animationFrequency);
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			new ErrorMessage("Couldn't load piege sprite set...\n" + e.getLocalizedMessage());
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
		//StopAnimating();
	}
}

