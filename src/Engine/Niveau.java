package Engine;
import java.awt.*;
import java.io.FileNotFoundException;

import javax.swing.*;

import Settings.*;
import Data.*;
import java.util.*;

public class Niveau extends JFrame {
	private static final long serialVersionUID = 1L;
	
	int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

	Snoopy POOPY;
	
	public Niveau(String name) throws FileNotFoundException
	{
		int[][] _map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];
		
		_map = MapDataManager.LoadMap(name+".txt");
		
		Init(_map);
	}

	void Init(int[][] _map)
	{
		map = _map;
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally, globalVar.tileHeight*globalVar.nbTilesVertically);
		
		for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    	{
	    		//System.out.print(map[i][j]);
	    		switch(map[i][j])
	    		{
	    		case 1:
	    			this.add(new Objet(i,j));
	    			
	    			
	    			//Si t'arrives a faire en sorte que ce Jlabel se place bien dans la fentre t'es chaud (c'est censé faire le contour)
	    			JLabel newBloc = new JLabel(new ImageIcon("./Images/Sprites/default.png"));
	    			newBloc.setBounds(i*globalVar.tileWidth, j*globalVar.tileHeight, globalVar.tileWidth, globalVar.tileHeight);
	    			
	    			
	    			
	    			
	    			
	    			System.out.print(i*globalVar.tileWidth + " " + j*globalVar.tileHeight + " " );
	    			this.add(newBloc);
	    			break;
	    		case 9:
	    			POOPY = new Snoopy(i*globalVar.tileWidth, j*globalVar.tileHeight);
	    			map[i][j] = 0;
	    			this.add(POOPY);
	    			break;
	    		}
	    		
	    	}
	    	System.out.println("");
	    }
		
		this.setVisible(true);
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
