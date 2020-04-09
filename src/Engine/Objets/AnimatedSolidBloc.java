package Engine.Objets;

import java.io.IOException;

import Utilitaires.ErrorMessage;
import Utilitaires.ObjectType;

public class AnimatedSolidBloc extends AnimatedObject {
	public AnimatedSolidBloc(int _x, int _y)
	{
		super(_x, _y, 0, 0, ObjectType.SOLIDBLOC, false, false);
		nbSpritesPerAnimationSequence = 1;
		//ChangeAnimationFrequency(2000);
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			new ErrorMessage("Couldn't load solidbloc sprite set...\n" + e.getLocalizedMessage());
		}
		this.ChangeSpriteTo(spriteList[currentSprite]);
		//StopAnimating();
	}
}
