package Engine;

import java.io.IOException;

import Utilitaires.ObjectType;

public class AnimatedSolidBloc extends AnimatedObject {
	private static final long serialVersionUID = 7507502771091353712L;

	public AnimatedSolidBloc(int _x, int _y)
	{
		super(_x, _y, 0, 0, ObjectType.SOLIDBLOC, false, true);
		nbSpritesPerAnimationSequence = 4;
		ChangeAnimationFrequency(100);
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			System.out.println("Couldn't load solidbloc sprite set");
			e.printStackTrace();
		}
		this.ChangeSpriteTo(spriteList[currentSprite]);
		StopAnimating();
	}
}
