package Engine.Objets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;

import Settings.globalVar;
import Utilitaires.*;

public class Snoopy extends AnimatedObject {
	public Direction orientation = null;
	
	public boolean immune = false;
	private int counterImmuneTimer = 0;
	
	Timer immuneTimer = new Timer( 200, new ActionListener() { public void actionPerformed(ActionEvent arg0) { ToggleTransparency(); } });
	Timer pwmTransparency = new Timer( 1, new ActionListener() { public void actionPerformed(ActionEvent arg0) { executePwmTransparency(); } });
	
	public Snoopy(int _xInMap, int _yInMap, int _x, int _y, int _targetX, int _targetY, int _initSpeedX, int _initSpeedY, int _vitesseX, int _vitesseY, int _orientation)
	{
		super(_xInMap, _yInMap, _vitesseX/10, _vitesseY/10, ObjectType.SNOOPY, true, true, false, true);
		x = _x; y = _y;
		targetX = _targetX; targetY = _targetY;
		initSpeed[0] = _initSpeedX/10; initSpeed[1] = _initSpeedY/10;
		
		animateOnlyWhenMoving = true;
		nbSpritesPerAnimationSequence = 4;
		
		ChangeAnimationFrequency(100);
		
		this.ChangeOrientationTo(Direction.directionOfId(_orientation));
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}
	
	public Snoopy(int _x, int _y, boolean _selfMoved)
	{
		super(_x, _y, 4.5, 4.5, ObjectType.SNOOPY, true, true, _selfMoved, true);
		
		solid = false;
		animateOnlyWhenMoving = true;
		nbSpritesPerAnimationSequence = 4;
		
		ChangeAnimationFrequency(100);
		
		this.ChangeOrientationTo(Direction.SOUTH);
		this.ChangeSpriteTo(spriteList[currentSprite]);
	}

	//A ajouter que si il est deja en mvmt il peut pas rebouger d'une case, bah ouai sinon il va jamais s'arreter personne va capter
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
			return super.Move(d);
		else
			ChangeOrientationTo(d);
		
		return false;
	}
	
	//On pourra changer le sprite ici en fonction de son orientation
	public boolean ChangeOrientationTo(Direction d)
	{
		if (orientation != d)
		{
			orientation = d;
			try {
				LoadSpriteSet(orientation);
			} catch (IOException e) {
				new ErrorMessage("Couldn't load snoopy " + Direction.nameOf(d) + " sprite set...\n" + e.getLocalizedMessage());
			}
			return true;
		}
		return false;
	}
	
	public void StartImmunity()
	{
		immune = true;
		counterImmuneTimer = 0;
		pwmTransparency.restart();
		immuneTimer.restart();
	}
	
	protected void executePwmTransparency() {
		visible = !visible;
	}
	
	protected void ToggleTransparency()
	{
		if (pwmTransparency.isRunning())
		{
			pwmTransparency.stop();
			visible = true;
		}
		else
			pwmTransparency.restart();
		
		if ((counterImmuneTimer+=1)*immuneTimer.getDelay() > globalVar.immuneTime)
			EndImmunity();
	}
	
	public void EndImmunity()
	{
		immune = false;
		visible = true;
		pwmTransparency.stop();
		immuneTimer.stop();
	}
	
	public void Stop()
	{
		super.Stop();
		if (immuneTimer != null)
			immuneTimer.stop();
		if (pwmTransparency != null)
			pwmTransparency.stop();
	}
	
	public void Resume()
	{
		super.Resume();
		if (immuneTimer != null && immune)
			immuneTimer.start();
		if (pwmTransparency != null && immune)
			pwmTransparency.start();
	}
	
	public void Kill()
	{
		super.Stop();
		super.Kill();
		immuneTimer = null;
		pwmTransparency = null;
	}

	public String SavingInfo() {
		return (xInMap + " " + yInMap + " " + (int)getX() + " " + (int)getY() + " " + (int)targetX + " " + (int)targetY + " " + (int)(initSpeed[0]*10) + " " + (int)(initSpeed[1]*10) + " " + (int)(vitesse[0]*10) + " " + (int)(vitesse[1]*10) + " " + Direction.idOf(orientation));
	}

	public void setSelfMoved(boolean b) {
		selfMoved = b;
	}
}
