import java.awt.*;
import javax.swing.*;
import Settings.*;

public class Niveau extends JPanel {
	private static final long serialVersionUID = 1L;
	
	int[][]map = new int[20][10];
	
	
	public Niveau(int[][] _map)
	{
		map = _map;
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
	
}
