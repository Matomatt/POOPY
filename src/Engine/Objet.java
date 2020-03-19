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
	protected  JPanel img;
	protected  int hitbox;
	protected  ObjectType type;
	
	protected BufferedImage sprite;
	
	public Objet(int _x, int _y, ObjectType _type)
	{
		xInMap =_x;
		yInMap =_y;
		
		x=_x*globalVar.tileWidth;
		y=_y*globalVar.tileHeight;
		
		type = _type;
		Init();
	}
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
		
		try { sprite = ImageIO.read(new File("./Images/Sprites/default.png"));
		  //sprite = new BufferedImage(globalVar.tileWidth, globalVar.tileHeight, sprite.TYPE_INT_ARGB);
			JLabel sp = new JLabel( new ImageIcon(sprite));
			sp.setBounds(0, 0, globalVar.tileWidth, globalVar.tileHeight);
			this.add(sp);}
	
		catch (IOException ex) { System.console().writer().println("Couldn't open default sprite..."); }
		
		this.setVisible(true);
		//this.setBounds((int)x, (int)y-4, globalVar.tileWidth, globalVar.tileHeight);
		this.setSize(globalVar.tileWidth, globalVar.tileHeight);
		this.setLocation((int)x, (int)y);
		this.validate();
	}
 
	public int Hitbox (Objet tocheck)
	{
		int result=0;
		return result;
	}
	
}
