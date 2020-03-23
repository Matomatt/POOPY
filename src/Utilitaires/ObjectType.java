package Utilitaires;

public enum ObjectType {
	SOLIDBLOC, BALLON, SNOOPY;
	
	public static String nameOf(ObjectType o)
	{
		switch (o)
		{
			case SOLIDBLOC: return "solidbloc";
			case BALLON: return "ballon";
			case SNOOPY: return "snoopy";
			default: return "none";
		}
	}
}
