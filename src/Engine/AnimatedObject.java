package Engine;

import Utilitaires.CoordType;
import Utilitaires.Direction;
import Utilitaires.ObjectType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import Data.ImageManager;
import Settings.*;

public class AnimatedObject extends Objet {

	private static final long serialVersionUID = -3920430219062203809L;
	
	protected double vitesse[] = new double[2];
	protected boolean alwaysMoving = false;
	protected boolean stopMovements = false;
	
	protected int nbSpritesPerAnimationSequence = 1;
	protected int currentSprite = 0;
	protected int animationFrequency = 1000; //in milliseconds
	protected ImageIcon spriteList[];
	
	protected boolean stopAnimation = false;
	protected boolean animateOnlyWhenMoving = false;
	
	protected boolean canMove;
	Timer movementTimer;
	protected boolean isAnimated;
	Timer animationTimer;

	public AnimatedObject(int _x, int _y, double vitesseX, double vitesseY, ObjectType _type, boolean _canMove, boolean _isAnimated)
	{
		super(_x, _y, _type);
		vitesse[0] = vitesseX;
		vitesse[1] = vitesseY;
		
		canMove = _canMove;
		isAnimated = _isAnimated;
		
		if (canMove)
		{
			ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { MoveTowardsTarget((double)1/globalVar.FPS); } };
			
			movementTimer = new Timer((int)1000/globalVar.FPS, taskPerformer);
			movementTimer.start();
		}
		if (isAnimated)
		{
			ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { SwitchToNextSprite();; } };
			
			animationTimer = new Timer((int)1000/globalVar.FPS, taskPerformer);
			animationTimer.start();
		}
	}
	
	public void MoveTowardsTarget(double elapsedTime)
	{
		if (x == targetX && y == targetY && !alwaysMoving || stopMovements)
			return;
		if (alwaysMoving)
		{
			//System.out.println(vitesse[0] + " / " + vitesse[1] + " + " + vitesse[1]*globalVar.tileHeight);
			x += vitesse[0]*globalVar.tileWidth;
			y += vitesse[1]*globalVar.tileHeight;
		}
		else 
		{
			if (x < targetX)
			{
				x += vitesse[0]*globalVar.tileWidth;
				if (x > targetX)
					x = targetX;
			}
			else if (x > targetX)
			{
				x -= vitesse[0]*globalVar.tileWidth;
				if (x < targetX)
					x = targetX;
			}
			
			if (y < targetY)
			{
				y += vitesse[1]*globalVar.tileHeight;
				if (y > targetY)
					y = targetY;
			}
			else if (y > targetY)
			{
				y -= vitesse[1]*globalVar.tileHeight;
				if (y < targetY)
					y = targetY;
			}
		}
		
		//System.out.println("Moved to " + x + ", " + y);
		this.setLocation((int)x-((coordType == CoordType.CENTER)?(int)r:0), (int)y-((coordType == CoordType.CENTER)?(int)r:0));
	}
	
	public boolean IsMoving()
	{
		if (x == targetX && y == targetY && !alwaysMoving || stopMovements)
			return false;
		return true;			
	}
	
	public void SwitchToNextSprite()
	{
		if (x == targetX && y == targetY && animateOnlyWhenMoving)
			currentSprite = 0;
		else if (!stopAnimation)
			currentSprite = (currentSprite+1)%nbSpritesPerAnimationSequence;
		
		ChangeSpriteTo(spriteList[currentSprite]);
	}
	
	protected void LoadSpriteSet() throws IOException
	{
		spriteList = new ImageIcon[nbSpritesPerAnimationSequence];
		for (int i=0; i<nbSpritesPerAnimationSequence; i++)
				spriteList[i] = new ImageIcon(ImageManager.LoadImage("./Images/Sprites/" + ObjectType.nameOf(type) + "/" + ObjectType.nameOf(type) + (i+1) + ".png", 
						globalVar.tileWidth, globalVar.tileHeight));
	}
	
	protected void LoadSpriteSet(Direction orientation) throws IOException
	{
		spriteList = new ImageIcon[nbSpritesPerAnimationSequence];
		for (int i=0; i<nbSpritesPerAnimationSequence; i++)
				spriteList[i] = new ImageIcon(ImageManager.LoadImage("./Images/Sprites/" + ObjectType.nameOf(type) + "/" + ObjectType.nameOf(type) + Direction.nameOf(orientation) + (i+1) + ".png", 
						globalVar.tileWidth, globalVar.tileHeight));
	}
	
	protected void ChangeAnimationFrequency(int newDelay)
	{
		animationFrequency = newDelay;
		animationTimer.setDelay(animationFrequency);
	}
	
	public void StopAnimating()
	{
		stopAnimation = true;
		animationTimer.stop();
	}
	
	public void ResumeAnimating()
	{
		stopAnimation = false;
		animationTimer.start();
	}

}
