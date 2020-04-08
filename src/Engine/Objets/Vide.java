package Engine.Objets;

import javax.swing.ImageIcon;

import Utilitaires.ObjectType;

public class Vide extends Objet {
	
	public Vide(int _x, int _y)
	{
		super(_x, _y, ObjectType.VIDE);
	}

	public ImageIcon getSprite()
	{
		return null;
	}
}
