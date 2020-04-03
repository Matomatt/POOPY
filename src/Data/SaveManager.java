package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Engine.Niveau;
import Engine.Objets.Ballon;
import Settings.globalVar;
import Utilitaires.ParametresPartie;

public class SaveManager {

	public static ParametresPartie LoadPartie(String fileName) 
	{
		ParametresPartie params = new ParametresPartie(0, 0, "");
		
		return params;
	}
	
	public static List<Ballon> LoadSaveNiveau(String fileName) throws IOException
	{
		File mapData = new File("./Maps/" + fileName + ".txt");
		
		BufferedReader br = new BufferedReader(new FileReader(mapData));
		
		List<Ballon> ballons = new ArrayList<Ballon>();
		
		String st;
		//On lit toutes les lignes du fichier texte
		while ( !(st = br.readLine()).contains("Ballons") && st != null) System.out.println(st);
		
		while ((st = br.readLine()) != null) 
		{
			List<Integer> parsedLine = StringManager.ParseLineToInt(st);
			ballons.add(new Ballon((double)parsedLine.get(0), (double)parsedLine.get(1), parsedLine.get(2), false));
		}
		
		br.close();
		
		return ballons;
	}
}
