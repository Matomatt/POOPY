package Engine.Objets;

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
	
	protected double initSpeed[] = new double[2];
	protected double vitesse[] = new double[2];
	protected boolean alwaysMoving = false;
	protected boolean stopMovements = false;
	
	protected int nbSpritesPerAnimationSequence = 1;
	protected int currentSprite = 0;
	protected int animationFrequency = 100; //in milliseconds
	protected ImageIcon spriteList[];
	
	protected static boolean selfMoved = true;
	private static boolean selfAnimated = true;
	public boolean stopAnimation = false;
	protected boolean animateOnlyWhenMoving = false;
	
	protected boolean canMove;
	Timer movementTimer;
	protected boolean isAnimated;
	Timer animationTimer;
	
	public AnimatedObject(int _x, int _y, double vitesseX, double vitesseY, ObjectType _type)
	{
		super(_x, _y, _type);
		Init(vitesseX, vitesseY, true, true, true, true);
	}
	
	public AnimatedObject(int _x, int _y, double vitesseX, double vitesseY, ObjectType _type, boolean _canMove, boolean _isAnimated)
	{
		super(_x, _y, _type);
		Init(vitesseX, vitesseY, _canMove, _isAnimated, true, true);
	}
	
	public AnimatedObject(int _x, int _y, double vitesseX, double vitesseY, ObjectType _type, boolean _canMove, boolean _isAnimated, boolean _selfMoved, boolean _selfAnimated)
	{
		super(_x, _y, _type);
		Init(vitesseX, vitesseY, _canMove, _isAnimated, _selfMoved, _selfAnimated);
	}

	public void Init(double vitesseX, double vitesseY, boolean _canMove, boolean _isAnimated, boolean _selfMoved, boolean _selfAnimated)
	{	
		selfMoved = _selfMoved;
		selfAnimated = _selfAnimated;
		
		initSpeed[0] = vitesse[0] = vitesseX;
		initSpeed[1] = vitesse[1] = vitesseY;
		
		canMove = _canMove;
		isAnimated = _isAnimated;
		
		if (canMove && selfMoved)
		{
			ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { MoveTowardsTarget((double)1/globalVar.CalculusFrequency); } };
			
			movementTimer = new Timer((int)1000/globalVar.CalculusFrequency, taskPerformer);
			movementTimer.start();
		}
		if (isAnimated && selfAnimated)
		{
			ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { SwitchToNextSprite(); } };
			
			animationTimer = new Timer(animationFrequency, taskPerformer);
			animationTimer.start();
		}
		
		ActionListener drawTaskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { Draw(); } };
		new Timer(1000/globalVar.FPS, drawTaskPerformer).start();
	}
	
	public void MoveTowardsTarget(double elapsedTime)
	{
		if (x == targetX && y == targetY && !alwaysMoving || stopMovements)
			return;
		if (alwaysMoving)
		{
			//System.out.println(vitesse[0] + " / " + vitesse[1] + " + " + vitesse[1]*globalVar.tileHeight);
			x += vitesse[0]*globalVar.tileWidth*elapsedTime;
			y += vitesse[1]*globalVar.tileHeight*elapsedTime;
		}
		else 
		{
			if (x < targetX)
			{
				x += vitesse[0]*globalVar.tileWidth*elapsedTime;
				if (x > targetX)
					x = targetX;
			}
			else if (x > targetX)
			{
				x -= vitesse[0]*globalVar.tileWidth*elapsedTime;
				if (x < targetX)
					x = targetX;
			}
			
			if (y < targetY)
			{
				y += vitesse[1]*globalVar.tileHeight*elapsedTime;
				if (y > targetY)
					y = targetY;
			}
			else if (y > targetY)
			{
				y -= vitesse[1]*globalVar.tileHeight*elapsedTime;
				if (y < targetY)
					y = targetY;
			}
		}
		
		//System.out.println("Moved to " + x + ", " + y);
		//this.setLocation((int)x-((coordType == CoordType.CENTER)?(int)r:0), (int)y-((coordType == CoordType.CENTER)?(int)r:0));
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
		animationTimer.setInitialDelay(animationFrequency);
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
	
	public boolean CollideWith(Objet o)
	{
		if (o.x+globalVar.tileWidth < x || o.x > x+globalVar.tileWidth || o.y+globalVar.tileHeight < y || o.y > y+globalVar.tileHeight)
			return false;
		return true;
	}
	
	public boolean SpeedModified()
	{
		return !(vitesse[0] == initSpeed[0] && vitesse[1] == initSpeed[1]);
	}
	
	public void IncreaseSpeed(Direction d, double coeff)
	{
		System.out.println("I am speed");
		if (d == Direction.NORTH || d == Direction.SOUTH)
			vitesse[1]*=coeff;
		else if (d == Direction.WEST || d == Direction.EAST)
			vitesse[0]*=coeff;
	}
	
	public void ResetSpeed()
	{
		System.out.println("My time has come my friend");
		vitesse[0] = initSpeed[0];
		vitesse[1] = initSpeed[1];
	}
	
	public void Pause()
	{
		if (movementTimer != null)
			movementTimer.stop();
		if (animationTimer != null)
			animationTimer.stop();
	}
	
	public void Resume()
	{
		if (movementTimer != null)
			movementTimer.start();
		if (animationTimer != null)
			animationTimer.start();
	}
	
	public void Kill()
	{
		this.Pause();
		movementTimer = null;
		animationTimer = null;
	}

}
