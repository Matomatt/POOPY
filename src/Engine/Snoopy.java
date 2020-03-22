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
	
	
	public Snoopy(int _x, int _y)
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
	
	/*
	//Pensez √† rajouter l'animation du sprite 
	public void up() // le d√©placement se fait apr√®s une v√©rif et est call dans niveau 
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
	*/
	
	//A ajouter que si il est dÈj‡ en mvmt bah il peut pas rebouger d'une case, bah ouai sinon il va se tp personne va capter
	public boolean CanMove(Direction towards)
	{
		return true;
	}
	
	//Si il est dans la bonne direction il bouge, sinon il change de direction, il bougera au prochain call
	public boolean Move(Direction d)
	{
		if (orientation == d)
			super.Move(d);
		else
			ChangeOrientationTo(d);
		System.out.println("Moving from " + x + ", " + y + " to " + targetX + ", " + targetY);
		//Ca c'est juste pour te montrer ou il est censÈ aller mais comme j'ai pas fait l'animation bah il y bouge pas vraiment (x et y change pas tavu)
		this.setLocation((int)targetX, (int)targetY);
		return false;
	}
	
	//On pourra changer le sprite ici en fonction de son orientation
	public void ChangeOrientationTo(Direction d)
	{
		orientation = d;
	}
}
