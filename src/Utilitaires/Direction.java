package Utilitaires;

public enum Direction {
	NORTH, SOUTH, EAST, WEST, NW, NE, SE, SW, none;
	
	public static String nameOf(Direction d)
	{
		switch(d)
		{
			case NORTH: return "NORTH";
			case SOUTH: return "SOUTH";
			case EAST: return "EAST";
			case WEST: return "WEST";
			default: return "none";
		}
	}
	
	public static int idOf(Direction d)
	{
		switch(d)
		{
			case NORTH: return 1;
			case SOUTH: return 2;
			case EAST: return 3;
			case WEST: return 4;
			default: return 5;
		}
	}
	
	public static Direction directionOfId(int i)
	{
		switch(i)
		{
			case 1: return NORTH;
			case 2: return SOUTH;
			case 3: return EAST;
			case 4: return WEST;
			default: return none;
		}
	}
}
