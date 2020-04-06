package Engine.Objets;
import java.io.IOException;

import Data.ImageManager;
//import Engine.Niveau.keylistener;
import Settings.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import Utilitaires.*;

public class Objet extends JLabel{
	private static final long serialVersionUID = 1L;
	
	protected double r;
	protected CoordType coordType = CoordType.LEFTRIGHTCORNER;
	public int xInMap = 0;

	public int yInMap = 0;
	protected double x=0,y=0;
	protected double targetX = 0, targetY = 0; // target ???
	
	//protected  JPanel img;
	protected  int hitbox;
	protected  ObjectType type;
	
	protected ImageIcon sprite;
	
	//Si on donne la position en case
	public Objet(int _x, int _y, ObjectType _type)
	{
		xInMap = _x;
		yInMap = _y;
		
		x= _x*globalVar.tileWidth;
		y= _y*globalVar.tileHeight;
		type = _type;
		Init();
	}
	
	//Si on donne la position pixel perfect
	public Objet(double _x, double _y, ObjectType _type)
	{
		x=_x;
		y=_y;
		
		type = _type;
		Init();
	}
	
	protected void Init()
	{
		this.setLayout(new BorderLayout());
		this.setOpaque(false);
		
		targetX = x; targetY = y;
		r = globalVar.tileWidth;
		
		//Chargement de sprite par defaut
		try { sprite = new ImageIcon(ImageManager.LoadImage("./Images/Sprites/default.png", globalVar.tileWidth, globalVar.tileHeight) ); }
		catch (IOException ex) { System.out.println("Couldn't open default sprite..."); }

		//Les parametres de base tu connais
		this.setVisible(true);
		this.setSize(globalVar.tileWidth, globalVar.tileHeight);
		Draw();
		this.validate();
	}
	
	protected void Draw()
	{
		this.setLocation((int)x-((coordType == CoordType.CENTER)?(int)r:0), (int)y-((coordType == CoordType.CENTER)?(int)r*2:0));
	}
	
	protected void ChangeSpriteTo(String fileName) throws IOException
	{
		this.setIcon(new ImageIcon(ImageManager.LoadImage("./Images/Sprites/" + fileName, globalVar.tileWidth, globalVar.tileHeight)));
	}
	
	protected void ChangeSpriteTo(ImageIcon newSprite)
	{
		this.setIcon(newSprite);
	}
 
	public int Hitbox (Objet tocheck)
	{
		int result=0;
		return result;
	}
	
	public ObjectType Type()
	{
		return type;
	}
	
	//Les objets de base ils peuvent generalement pas bouger aha sont nuls ces blocs solides
	public boolean CanMove(Direction d) { return false; }
	//Donc ils sont pas en train de bouger (je mets tout �a la comme �a pas besoin de cast explicite pour savoir si l'objet peut se d�placer)
	public boolean IsMoving() { return false; }
	
	//Les petits calculs de ou il va poser ss fesses apr�s avoir boug�
	public int NextCaseX(Direction d)
	{
		if (d == Direction.WEST)
			return xInMap-1;
		if (d == Direction.EAST)
			return xInMap+1;
		return xInMap;
	}
	public int NextCaseY(Direction d)
	{
		if (d == Direction.NORTH)
			return yInMap-1;
		if (d == Direction.SOUTH)
			return yInMap+1;
		return yInMap;
	}
	
	//Mtn qu'on a fait les calculs et qu'on est chaud pour bouger on bouge et on set la cible vers laquelle il va se d�placer (#animation)
	public boolean Move(Direction d)
	{
		xInMap = NextCaseX(d);
		yInMap = NextCaseY(d);
		
		targetX = xInMap*globalVar.tileWidth;
		targetY = yInMap*globalVar.tileHeight;
		
		return true;
	}
	
	public boolean SameTileAs(Objet o)
	{
		if (xInMap == o.xInMap && yInMap == o.yInMap)
			return true;
		return false;
	}
	
	public boolean IsHere(int i, int j)
	{
		return (xInMap == i && yInMap == j);
	}
	
}
