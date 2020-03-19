package Engine;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Settings.globalVar;
import Utilitaires.*;

public class Snoopy extends Objet {
	private static final long serialVersionUID = 1L;
	Direction orientation = Direction.NORTH;
	
	
	public Snoopy(double _x, double _y)
	{
		super(_x, _y, ObjectType.SNOOPY);
		this.remove(0);
		try {
			this.add(new JLabel(new ImageIcon(ImageIO.read(new File("./Images/Sprites/poopy.png")))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Pensez à rajouter l'animamtion du sprite 
	public void up() // le déplacement se fait après une vérif et est call dans niveau 
	{
		orientation= Direction.NORTH;
	 	yInMap-=1;
	 	y -= globalVar.tileHeight;
	 	this.setLocation((int)x,(int)y);
	}
	public void right() 
	{
		orientation= Direction.EAST;
		xInMap+=1; 
		x += globalVar.tileWidth;
		this.setLocation((int)x,(int)y);
	}
	
	public void left() 
	{
		orientation= Direction.WEST;
		xInMap-=1; 
		x -= globalVar.tileWidth;
		this.setLocation((int)x,(int)y);
	}
	public void down()
	{
		orientation= Direction.WEST;
		y += globalVar.tileHeight;
		this.setLocation((int)x,(int)y);
	}
	
	
	
}
