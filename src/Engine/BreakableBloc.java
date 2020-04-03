package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
			System.out.println("Couldn't load solidbloc sprite set");
			e.printStackTrace();
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
		StopAnimating();
		
		ActionListener endOfCycleDetecter = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { BreakingCycle(); } };
		
		animationTimer.removeActionListener(animationTimer.getActionListeners()[0]);
		animationTimer.addActionListener(endOfCycleDetecter);
			
	}
	
	public void Break()
	{
		ResumeAnimating();
		SwitchToNextSprite();
	}
	
	public void BreakingCycle()
	{
		SwitchToNextSprite();
		if (currentSprite >= nbSpritesPerAnimationSequence-1)
		{
			broken = true;
			StopAnimating();
		}
	}
}
