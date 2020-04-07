package Engine.Objets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Engine.ErrorMessage;
import Settings.globalVar;
import Utilitaires.ObjectType;

public class Apparition extends AnimatedObject {
	private static final long serialVersionUID = -7805073306416261166L;
	
	public boolean visible = true;
	private Timer chrono;
	private Timer chronoanimation;

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
		chrono.schedule(new triggerAnimation(), 0,globalVar.tempsapparition );		
	}
	
	
	private class triggerAnimation extends TimerTask
	{
		@Override
		public void run() {
			System.out.println("chrono apparition");
			if (visible==true)
			{
				visible=false;
				ResumeAnimating();
				animationTimer.getActionListeners()[0] = new ActionListener() {

		            public void actionPerformed(ActionEvent e) {
		                SwitchToNextSprite(0);

		            }
		        };
			}
			else {
				visible=true;
				ResumeAnimating();
				animationTimer.getActionListeners()[0] = new ActionListener() {

		            public void actionPerformed(ActionEvent e) {
		                SwitchToNextSprite(1);

		            }
		        };
			}
		}
	}

	public void SwitchToNextSprite(int a) {
		if(a==0)
		{// TODO Auto-generated method stub
			if (currentSprite<=nbSpritesPerAnimationSequence-1)
			{
				super.SwitchToNextSprite();
			}
			else
				StopAnimating();
		}
		else {
			if (currentSprite>=0)
			{
				if (x == targetX && y == targetY && animateOnlyWhenMoving)
					currentSprite = 0;
				else if (!stopAnimation)
					currentSprite =(nbSpritesPerAnimationSequence-(currentSprite-1))%nbSpritesPerAnimationSequence;
				
				ChangeSpriteTo(spriteList[currentSprite]);
			}
			
			else
				StopAnimating();
		}
	}
	
	
	
	public void TogglePause()
	{
		System.out.println("Pause ici");	
		if (chrono==null)
		{
			chrono=new Timer();
			chrono.schedule(new triggerAnimation(), 0,globalVar.tempsapparition );
			ResumeAnimating();
		}
		else {
			System.out.println("Pause la");
			chrono.cancel();
			chrono=null;
			StopAnimating();
		}
		
		
	}
	@Override
	public void Kill() {
		// TODO Auto-generated method stub
		chrono.cancel();
		super.Kill();
		
	}
}