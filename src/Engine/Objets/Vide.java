package Engine.Objets;

import java.io.IOException;

import javax.swing.ImageIcon;

import Engine.ErrorMessage;
import Utilitaires.ObjectType;

public class Vide extends Objet {
	
	public Vide(int _x, int _y)
	{
		super(_x, _y, ObjectType.VIDE);
		
		try {
			ChangeSpriteTo("vide/vide.png");
		} catch (IOException e) {
			new ErrorMessage("Impossible de récupérer le sprite vide...\n" + e.getLocalizedMessage());
		}
		this.sprite = null;
		solid = false;
	}

	public ImageIcon getSprite()
	{
		return null;
	}
}
