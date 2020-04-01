package Utilitaires;

public enum ObjectType {
	BREAKABLEBLOC, MOVINGBLOC, PIEGE, SOLIDBLOC, BALLON, OISEAU, SNOOPY, none;
	
	public static String nameOf(ObjectType o)
	{
		switch (o)
		{
			case BREAKABLEBLOC: return "breakablebloc";
			case SOLIDBLOC: return "solidbloc";
			case PIEGE: return "piege";
			case MOVINGBLOC: return "movingbloc";
			case BALLON: return "ballon";
			case OISEAU: return "oiseau";
			case SNOOPY: return "snoopy";
			default: return "none";
		}
	}
	
	public static ObjectType typeOfInt(int n)
	{
		switch (n)
		{
			case 1: return BREAKABLEBLOC;
			case 2: return MOVINGBLOC;
			case 3: return PIEGE;
			case 4: return SOLIDBLOC;
			case 7: return BALLON;
			case 9: return OISEAU;
			case 8: return SNOOPY;
			default: return none;
		}
	}
	public static int mapIdOf(ObjectType o)
	{
		switch (o)
		{
			case BREAKABLEBLOC: return 1;
			case MOVINGBLOC: return 2;
			case PIEGE: return 3;
			case SOLIDBLOC: return 4;
			case BALLON: return 7;
			case OISEAU: return 9;
			case SNOOPY: return 8;
			default: return 0;
		}
	}
}
