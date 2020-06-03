package Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import Settings.globalVar;
import Utilitaires.path;

public class MapDataManager {
	
	public static int[][] LoadMap(String fileName) throws IOException
	{
		File mapData = new File(path.get()+ "/Maps/" + fileName);
		
		BufferedReader br = new BufferedReader(new FileReader(mapData));
		
		List<Integer> parsedIntList = new ArrayList<Integer>(); //La liste qui va contenir tous les nombres lus du fichier
		
		String st;
		//On lit toutes les lignes du fichier texte
		while ((st = br.readLine()) != null) {
			
			String numberToParse = "";
			char ch[] = st.toCharArray();
			
			//On lit chaque caractère de la ligne un à un
			for(int i = 0; i<ch.length; i++)
			{
				//Si le caractère n'est pas un espace, c'est un chiffre qui compose le nombre à lire
				if (ch[i] != ' ')
					numberToParse += ch[i];
				
				//Si le caractère n'est pas un espace (ou que c'est le dernier de la ligne) ca veut dire que le nombre a fini d'être lu et il faut maintenant le convertir en integer
				if (ch[i] == ' ' || i == ch.length-1) {
					try {
						parsedIntList.add(Integer.parseInt(numberToParse));
					} catch (Exception e) {
						//e.printStackTrace();
						parsedIntList.add(-1);
					}
					numberToParse = "";
				}
			}
		}
		
		br.close();
	
		int nbTilesHor = parsedIntList.get(0);
		int nbTilesVer = parsedIntList.get(1);
		
		globalVar.nbTilesHorizontally = nbTilesHor;
		globalVar.nbTilesVertically = nbTilesVer;
		
		int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];
		
		//Si on n'a pas lu assez de nombres pour remplir la map le fichier n'est pas bon
		if (parsedIntList.size() < nbTilesHor*nbTilesVer+2)
		{
			System.out.println("Fichier de donnée de map incorrecte...");
			return map;
		}
		
	    for (int j=0; j<nbTilesVer; j++)
	    {
	    	for (int i=0; i<nbTilesHor; i++)
	    	{
	    		int index = j*nbTilesHor+i;
	    		//System.out.print(parsedIntList.get(index+2) + " ");
    			map[i][j] = parsedIntList.get(index+2);
	    		
	    	}
	    	//System.out.println("");
	    }
	    
		return map;
	}

}
