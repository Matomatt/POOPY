package Engine.Objets;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;

import Settings.globalVar;
import Utilitaires.*;

public class Snoopy extends AnimatedObject {
	private static final long serialVersionUID = -7782172256474576827L;
	
	public Direction orientation = null;
	
	public boolean immune = false;
	private int counterImmuneTimer = 0;
	private float transparency = 1.0f;
	
	Timer immuneTimer = new Timer( 200, new ActionListener() { public void actionPerformed(ActionEvent arg0) { ToggleTransparency(); } });
	Timer pwmTransparency = new Timer( 10, new ActionListener() { public void actionPerformed(ActionEvent arg0) { executePwmTransparency(); } });
	
	public Snoopy(int _x, int _y, boolean _selfMoved)
	{
		super(_x, _y, 4.5, 4.5, ObjectType.SNOOPY, true, true, _selfMoved, true);
		
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
				System.out.println("Couldn't load snoopy " + Direction.nameOf(d) + " sprite set");
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public void RefreshSprite()
	{
		this.update(this.getGraphics());
	}
	
	public void StartImmunity()
	{
		immune = true;
		counterImmuneTimer = 0;
		pwmTransparency.restart();
		immuneTimer.restart();
	}
	
	protected void executePwmTransparency() {
		this.setVisible(!this.isVisible());
	}
	
	protected void ToggleTransparency()
	{
		if (pwmTransparency.isRunning())
		{
			pwmTransparency.stop();
			this.setVisible(true);
		}
		else
			pwmTransparency.restart();
		
		if ((counterImmuneTimer+=1)*immuneTimer.getDelay() > globalVar.immuneTime)
			EndImmunity();
	}
	
	public void EndImmunity()
	{
		transparency = 1.0f;
		immune = false;
		this.setVisible(true);
		pwmTransparency.stop();
		immuneTimer.stop();
	}
	
	public void Pause()
	{
		super.Pause();
		if (immuneTimer != null)
			immuneTimer.stop();
		if (pwmTransparency != null)
			pwmTransparency.stop();
	}
	
	public void Resume()
	{
		super.Resume();
		if (immuneTimer != null)
			immuneTimer.start();
		if (pwmTransparency != null)
			pwmTransparency.start();
	}
	
	public void Kill()
	{
		super.Pause();
		super.Kill();
		immuneTimer = null;
		pwmTransparency = null;
	}
}
