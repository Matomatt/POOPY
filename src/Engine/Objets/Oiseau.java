package Engine.Objets;

import java.io.IOException;

import Utilitaires.ObjectType;

public class Oiseau extends Objet {

	public Oiseau(int _xInMap, int _yInMap)
	{
		super(_xInMap, _yInMap, ObjectType.OISEAU);
		try {
			this.ChangeSpriteTo("oiseau/oiseau1.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
