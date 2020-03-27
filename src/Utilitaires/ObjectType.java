package Utilitaires;

public enum ObjectType {
	SOLIDBLOC, BALLON, OISEAU, SNOOPY;
	
	public static String nameOf(ObjectType o)
	{
		switch (o)
		{
			case SOLIDBLOC: return "solidbloc";
			case BALLON: return "ballon";
			case OISEAU: return "oiseau";
			case SNOOPY: return "snoopy";
			default: return "none";
		}
	}
}
