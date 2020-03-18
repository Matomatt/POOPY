
import java.util.*;


public class Niveau {
	protected int[][]map = new int[20][10];
	protected ArrayList<Objet> items; 
	protected Fenetre fenetre;
	public Niveau(int[][] _map)
	{
		items=new ArrayList<Objet>();
		map = _map;
		fenetre= new Fenetre(); 
	}
	
	public void addall(Objet item)
	{
	   items.add(item);
	   fenetre.add(item,items.size());
	   item.setmap(map);
	}
	
	
}
