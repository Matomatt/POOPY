package Engine.Objets;

import javax.swing.ImageIcon;

import Utilitaires.CoordType;
import Utilitaires.ObjectType;

public class DrawableObjet{

	private int x = 0;
	private int y = 0;
	private int i = 0;
	private int j = 0;
	private ImageIcon sprite;
	
	public DrawableObjet(Objet o) {
		x = (int) (o.getX() - ((o.coordType == CoordType.CENTER)?(int)o.r:0)); y = (int) (o.getY() - ((o.coordType == CoordType.CENTER)?(int)o.r:0));
		i = o.xInMap; j = o.yInMap;
		sprite = ((o.isVisible())?o.sprite:null);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int geti()
	{
		return i;
	}
	
	public int getj()
	{
		return j;
	}

	public ImageIcon getSprite()
	{
		return sprite;
	}
}
