package Engine;
import Utilitaires.Direction;

public class Snoopy extends Objet {
	private static final long serialVersionUID = 1L;
	Direction orientation = Direction.NORTH;
	
	
	public Snoopy(double _x, double _y)
	{
		super(_x, _y);
	}
	
	
	//Pensez à rajouter l'animamtion du sprite 
	public void up() // le déplacement se fait après une vérif et est call dans niveau 
	{
		orientation= Direction.NORTH;
	 	yInMap-=1; 
	}
	public void right() 
	{
		orientation= Direction.EAST;
		xInMap+=1; 
	}
	
	public void left() 
	{
		orientation= Direction.WEST;
		xInMap-=1; 
	}
	public void down()
	{
		orientation= Direction.WEST;
		yInMap+=1; 
	}
	
	
	
}
