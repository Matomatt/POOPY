package Engine;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Settings.*;
import java.awt.*;
import java.util.*;
import java.lang.*;
import javax.swing.*;
import Utilitaires.*;

public class Objet extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected  static double r;
	protected int xInMap = 0, yInMap = 0;
	protected double x=0,y=0;
	protected double targetX = 0, targetY = 0;
	protected  JPanel img;
	protected  int hitbox;
	protected  ObjectType type;
	
	protected BufferedImage sprite;
	
	//Si on donne la position en case
	public Objet(int _x, int _y, ObjectType _type)
	{
		xInMap =_x;
		yInMap =_y;
		
		x=_x*globalVar.tileWidth;
		y=_y*globalVar.tileHeight;
		
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
		
		targetX = x; targetY = y;
		
		//Chargement de sprite par d�faut
		try { sprite = ImageIO.read(new File("./Images/Sprites/default.png"));
		  //sprite = new BufferedImage(globalVar.tileWidth, globalVar.tileHeight, sprite.TYPE_INT_ARGB);
			JLabel sp = new JLabel( new ImageIcon(sprite));
			sp.setBounds(0, 0, globalVar.tileWidth, globalVar.tileHeight);
			this.add(sp);}
	
		catch (IOException ex) { System.console().writer().println("Couldn't open default sprite..."); }
		
		//Les param�tres de base tu connais
		this.setVisible(true);
		this.setSize(globalVar.tileWidth, globalVar.tileHeight);
		this.setLocation((int)x, (int)y);
		this.validate();
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
	
	//Les objets de base ils peuvent g�n�ralement pas bouger aha sont nuls ces blocs solides
	public boolean CanMove(Direction d)
	{
		return false;
	}
	
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
}
