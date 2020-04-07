package Engine.Objets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Engine.ErrorMessage;
import Utilitaires.ObjectType;

public class BreakableBloc extends AnimatedObject {
	private static final long serialVersionUID = 534601364790057414L;

	public boolean broken = false;
	
	public BreakableBloc(int _x, int _y)
	{
		super(_x, _y, 0, 0, ObjectType.BREAKABLEBLOC, false, true);
		nbSpritesPerAnimationSequence = 6;
		ChangeAnimationFrequency(50);
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			new ErrorMessage("Couldn't load breakablebloc sprite set...\n" + e.getLocalizedMessage());
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
		StopAnimating();
			
	}
	
	public void SwitchToNextSprite()
	{
		super.SwitchToNextSprite();
		if (currentSprite >= nbSpritesPerAnimationSequence-1)
		{
			broken = true;
			StopAnimating();
		}
	}
	
	
	public void Break()
	{
		ResumeAnimating();
		SwitchToNextSprite();
	}
}
