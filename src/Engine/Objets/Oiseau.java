package Engine.Objets;

import java.io.IOException;

import Engine.ErrorMessage;
import Utilitaires.ObjectType;

public class Oiseau extends Objet {

	public Oiseau(int _xInMap, int _yInMap)
	{
		super(_xInMap, _yInMap, ObjectType.OISEAU);
		
		try {
			this.ChangeSpriteTo("oiseau/oiseau1.png");
		} catch (IOException e) {
			new ErrorMessage("Couldn't load oiseau's sprite...\n" + e.getLocalizedMessage());
		}
		
		solid = false;
	}
}
