package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Engine.Objets.Ballon;
import Engine.Objets.Snoopy;
import Utilitaires.path;

public class SaveManager {
	
	public static List<Ballon> LoadSaveNiveau(String fileName) throws IOException
	{
		File mapData = new File(path.get()+ "/Maps/" + fileName + ".txt");
		
		BufferedReader br = new BufferedReader(new FileReader(mapData));
		
		List<Ballon> ballons = new ArrayList<Ballon>();
		
		String st;
		//On lit toutes les lignes du fichier texte
		while ( (st = br.readLine()) != null)
		{
			if (st.contains("Ballons")) break;
		}
		
		while ((st = br.readLine()) != null) 
		{
			List<Integer> parsedLine = StringManager.ParseLineToInt(st);
			ballons.add(new Ballon((double)parsedLine.get(0), (double)parsedLine.get(1), parsedLine.get(2), false));
		}
		
		br.close();
		
		return ballons;
	}

	public static Snoopy LoadSaveSnoopy(String fileName) throws IOException 
	{
		File mapData = new File(path.get()+ "/Maps/" + fileName + ".txt");
		
		BufferedReader br = new BufferedReader(new FileReader(mapData));
		
		String st;
		while ( (st = br.readLine()) != null) if (st.contains("Snoopy")) break;
		
		if (st==null)
		{
			br.close();
			return null;
		}
		
		if (!st.contains("Snoopy"))
		{
			br.close();
			return null;
		}
		
		List<Integer> l = StringManager.ParseLineToInt(br.readLine());
		
		br.close();
		
		if (l.size() < 11)
			return null;
		
		Snoopy tmpSnoopy = new Snoopy(l.get(0), l.get(1), l.get(2), l.get(3), l.get(4), l.get(5), l.get(6), l.get(7), l.get(8), l.get(9), l.get(10));
		
		return tmpSnoopy;
	}
	
	public static void UpdateLeaderboard(int score, String name) throws IOException
	{
		ArrayList<NomScoreAssoc> leaderboard = GetLeaderboard();
		AddScoreInLeaderboard(score, name, leaderboard);
		PrintLeaderboard(leaderboard);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public static void AddScoreInLeaderboard(int score, String name, ArrayList<NomScoreAssoc> leaderboard)
	{
		if (leaderboard.contains(name))
		{
			leaderboard.get(leaderboard.indexOf(name)).score = score;
		}
		else {
            leaderboard.add(new NomScoreAssoc(name,score));
        }

	}
	
	public static ArrayList<NomScoreAssoc> GetLeaderboard() throws IOException
	{
		ArrayList<NomScoreAssoc> leaderboard = new ArrayList<NomScoreAssoc>();
		
		File saveFile = new File(path.get()+ "/Saves/leaderboard.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(saveFile));
		
		String st;
		while ( (st = br.readLine()) != null)
		{
			leaderboard.add(new NomScoreAssoc(st, StringManager.ParseLineToInt(br.readLine()).get(0)));
		}
		
		br.close();
		
		return leaderboard;
	}
	
	public static void PrintLeaderboard(ArrayList<NomScoreAssoc> leaderboard) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter saveFile = new PrintWriter(path.get()+ "/Saves/leaderboard.txt", "UTF-8");
		
		Collections.sort(leaderboard);
		if (leaderboard.size() > 10)
			leaderboard.remove(leaderboard.size()-1);
		
		for (NomScoreAssoc nomScoreAssoc : leaderboard) {
			saveFile.println(nomScoreAssoc.getName());
			saveFile.println(nomScoreAssoc.getScore());
		}
		
		saveFile.close();
	}
}
