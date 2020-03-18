import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.*;

import Settings.globalVar;
import Engine.Niveau;



public class Fenetre extends JFrame 
{
	private static final long serialVersionUID = 8164118974463460991L;

	public Fenetre ()
	{
		this.setTitle("Snoopy");
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally, globalVar.tileHeight*globalVar.nbTilesVertically);
		this.setVisible(true);
		this.setLayout(null);
		this.validate();
		
		try {
			LaunchDefaultLevel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Moi teuteu, moi pas trouver la map du niveau par défaut");
		}
	}
	
	private void LaunchDefaultLevel() throws FileNotFoundException
	{
		Niveau defaultLevel = new Niveau("level1");
		defaultLevel.MainLoop();
	}
}
