package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 

import Settings.globalVar;

public class MapDataManager {
	
	public static int[][] LoadMap(String fileName) throws FileNotFoundException
	{
		int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];
		
		File mapData = new File("./Maps/" + fileName); 
		Scanner sc = new Scanner(mapData); 
		sc.locale();
		int nbTilesHor = Integer.parseInt(sc.next());
		int nbTilesVer = Integer.parseInt(sc.next());
		
	    for (int j=0; j<nbTilesVer; j++)
	    {
	    	for (int i=0; i<nbTilesHor; i++)
	    	{
	    		try {
	    			String newTile = sc.next();
	    			System.out.print(newTile);
	    			map[i][j] = Integer.parseInt(newTile);
	    		}
	    		catch(Exception e) { e.printStackTrace(); map[i][j] = -1; }
	    		
	    	}
	    	System.out.println("");
	    		
	    }
	    
	    sc.close();
	    
		return map;
	}
}
