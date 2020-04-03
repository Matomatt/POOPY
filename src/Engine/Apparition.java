package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Utilitaires.ObjectType;

public class Apparition extends AnimatedObject {
	private static final long serialVersionUID = -7805073306416261166L;
	
	public boolean visible = true;
	
	public Apparition(int _x, int _y)
	{
		super(_x, _y, 0, 0, ObjectType.APPARITION, false, true);
		
		nbSpritesPerAnimationSequence = 2;
		ChangeAnimationFrequency(50);
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			System.out.println("Couldn't load solidbloc sprite set");
			e.printStackTrace();
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
		StopAnimating();
		
		ActionListener togglePresenceTask = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { TogglePresence(); } };
		
		
			
	}
	
	public void TogglePresence()
	{
		ResumeAnimating();
		SwitchToNextSprite();
	}
}