package Utilitaires;

public enum ObjectType {
	VIDE, BREAKABLEBLOC, MOVINGBLOC, PIEGE, SOLIDBLOC, TAPISROULANT, APPARITION, BALLON, OISEAU, SNOOPY, none;
	
	public static String nameOf(ObjectType o)
	{
		switch (o)
		{
			case VIDE: return "vide";
			case BREAKABLEBLOC: return "breakablebloc";
			case SOLIDBLOC: return "solidbloc";
			case PIEGE: return "piege";
			case MOVINGBLOC: return "movingbloc";
			case APPARITION: return "apparition";
			case TAPISROULANT: return "tapisroulant";
			case BALLON: return "ballon";
			case SNOOPY: return "snoopy";
			case OISEAU: return "oiseau";
			default: return "none";
		}
	}
	
	public static ObjectType typeOfInt(int n)
	{
		switch (n)
		{
			case 0: return VIDE;
			case 1: return BREAKABLEBLOC;
			case 2: return MOVINGBLOC;
			case 3: return PIEGE;
			case 4: return SOLIDBLOC;
			case 5: return APPARITION;
			case 6: return TAPISROULANT;
			case 7: return BALLON;
			case 8: return SNOOPY;
			case 9: return OISEAU;
			default: return none;
		}
	}
	public static int mapIdOf(ObjectType o)
	{
		switch (o)
		{
			case VIDE: return 0;
			case BREAKABLEBLOC: return 1;
			case MOVINGBLOC: return 2;
			case PIEGE: return 3;
			case SOLIDBLOC: return 4;
			case APPARITION: return 5;
			case TAPISROULANT: return 6;
			case BALLON: return 7;
			case SNOOPY: return 8;
			case OISEAU: return 9;
			default: return 0;
		}
	}
}
