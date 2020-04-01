package Data;

import java.util.ArrayList;
import java.util.List;

public class StringManager {
	public static List<Integer> ParseLineToInt(String line) {
		List<Integer> parsedIntList = new ArrayList<Integer>();
		
		String numberToParse = "";
		char ch[] = line.toCharArray();
		
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
		
		return parsedIntList;
	}
}
