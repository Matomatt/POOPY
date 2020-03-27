package Utilitaires;

public enum ObjectType {
	SOLIDBLOC, MOVINGBLOC, BALLON, OISEAU, SNOOPY, none;
	
	public static String nameOf(ObjectType o)
	{
		switch (o)
		{
			case SOLIDBLOC: return "solidbloc";
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
			case 1: return MOVINGBLOC;
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
			case MOVINGBLOC: return 1;
			case SOLIDBLOC: return 4;
			case BALLON: return 7;
			case OISEAU: return 9;
			case SNOOPY: return 8;
			default: return 0;
		}
	}
}
