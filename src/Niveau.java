import java.awt.*;
import javax.swing.*;
import Settings.*;
import java.util.*;

public class Niveau extends JFrame {
	private static final long serialVersionUID = 1L;
	
	int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

	protected ArrayList<Objet> items; 
	protected Fenetre fenetre;
	
	public Niveau(int[][] _map)
	{
		items=new ArrayList<Objet>();
		map = _map;
		fenetre= new Fenetre();
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally, globalVar.tileHeight*globalVar.nbTilesVertically);
	}
	
	public boolean MainLoop()
	{
		boolean quit = false;
		do
		{
			
		}while(!quit);
		return true;
	}
	
	public void addall(Objet item)
	{
	   items.add(item);
	   this.add(item);
	   item.setmap(map);
	}
	
	
}
