
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

public class Objet extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected  static double r;
	protected double x=0,y=0;
	protected  JPanel img;
	protected  int hitbox;
	protected  int type;
	
	protected BufferedImage sprite;
	
	public Objet(double _x, double _y)
	{
		x=_x;
		y=_y;
		try { sprite = ImageIO.read(new File("Images/Sprites/default.png"));
			  sprite = new BufferedImage(globalVar.tileWidth, globalVar.tileHeight, sprite.TYPE_INT_ARGB);
			  add( new JLabel(new ImageIcon(sprite)) ); }
		catch (IOException ex) { System.console().writer().println("Couldn't open default sprite..."); }
		
		this.setVisible(true);
	}
 
	public int Hitbox (Objet tocheck)
	{
		int result=0;
		return result;
	}
	
	public void setmap(int[][] map)
	{
		// En fonction de la taille de la grille définir le x et y => set le type de objet a la pos de la map corréspondant 
	}
}
