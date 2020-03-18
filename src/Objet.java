import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import Settings.*;


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
			  sprite = new BufferedImage(globalVar.tileWidth, globalVar.tileHeight, sprite.TYPE_INT_ARGB); }
		catch (IOException ex) { System.console().writer().println("Couldn't open default sprite..."); }
	}
 
 
	public int Hitbox (Objet tocheck)
	{
		int result=0;
		return result;
	}
	
	

}







