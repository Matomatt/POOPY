package Engine.Objets;

import java.io.IOException;

import Utilitaires.ErrorMessage;
import Utilitaires.ObjectType;

public class Piege  extends AnimatedObject {
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
		
		solid = false;
	}
}

