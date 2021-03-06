package Engine.Objets;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Settings.globalVar;
import Utilitaires.ErrorMessage;
import Utilitaires.ObjectType;

public class Apparition extends AnimatedObject {
	public boolean visible = true;
	private Timer chrono;
	public Apparition(int _x, int _y)
	{
		super(_x, _y, 0, 0, ObjectType.APPARITION, false, true);
		
		nbSpritesPerAnimationSequence = 3;
		ChangeAnimationFrequency(50);
		
		try {
			LoadSpriteSet();
		} catch (IOException e) {
			new ErrorMessage("Couldn't load apparition sprite set...\n" + e.getLocalizedMessage());
		}
		
		this.ChangeSpriteTo(spriteList[currentSprite]);
		StopAnimating();
			
		chrono=new Timer();
		chrono.schedule(new triggerAnimation(), 0,globalVar.tempsApparition );		
	}
	
	
	private class triggerAnimation extends TimerTask
	{
		@Override
		public void run() {
			//System.out.println("chrono apparition");
			if (visible==true)
			{
				visible=false;
				ResumeAnimating();
				
			}
			else {
				visible=true;
				ResumeAnimating();
				
			}
		}
	}

	public void SwitchToNextSprite() {
		if(visible)
		{
			super.SwitchToNextSprite();
			if (currentSprite>=nbSpritesPerAnimationSequence-1)
				StopAnimating();
		}
		else 
		{
			if (getX() == targetX && getY() == targetY && animateOnlyWhenMoving)
				currentSprite = 0;
			else if (!stopAnimation)
			{
				currentSprite -=1;
				if (currentSprite <0)
					currentSprite = nbSpritesPerAnimationSequence-1;
			}
			
			ChangeSpriteTo(spriteList[currentSprite]);
			
			if (currentSprite<=0)
				StopAnimating();
		}
	}
	
	public boolean isSolid() { return !visible; }
	
	public void TogglePause()
	{	
		if (chrono==null)
		{
			chrono=new Timer();
			chrono.schedule(new triggerAnimation(), 0,globalVar.tempsApparition );
			ResumeAnimating();
		}
		else {
			chrono.cancel();
			chrono=null;
			StopAnimating();
		}
	}
	
	@Override
	public void Stop() {
		super.Stop();
		TogglePause();
	}
	
	@Override
	public void Resume() {
		super.Resume();
		TogglePause();
	}
	
	@Override
	public void Kill() {
		chrono.cancel();
		super.Kill();
		
	}
}