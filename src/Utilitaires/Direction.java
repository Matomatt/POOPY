package Utilitaires;

public enum Direction {
	NORTH, SOUTH, EAST, WEST, NW, NE, SE, SW;
	
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
}
