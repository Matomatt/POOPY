package Engine.Objets;

import javax.swing.ImageIcon;

public class DrawableObjet{

	private int x = 0;
	private int y = 0;
	private ImageIcon sprite;
	
	public DrawableObjet(Objet o) {
		x = o.xInMap;
		y = o.yInMap;
		sprite = o.sprite;
	}
	
	public int getX()
	{
		return (int) x;
	}
	
	public int getY()
	{
		return (int) y;
	}

	public ImageIcon getSprite()
	{
		return sprite;
	}
}
