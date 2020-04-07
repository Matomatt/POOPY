package Engine;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import Data.SaveManager;
import Data.StringManager;
import Engine.Objets.Ballon;
import Settings.globalVar;



public class Fenetre extends JFrame 
{
	private static final long serialVersionUID = 8164118974463460991L;
	public Fenetre ()
	{
		// Initialisation de la fenetre.
		this.setTitle("Snoopy");
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally+6, globalVar.tileHeight*(globalVar.nbTilesVertically+1)+32); // Se refere aux settings
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

		this.add(new Menu(this));
		
		
		this.validate();		
	}
	
	
	
	
	// Correspond au chargement d'une sauvergarde, est appelé par menu
	public void loadgame(String a) throws IOException
	{
		this.add(new Partie(a,this));
		this.revalidate();
	}
	
	// Correspond au chargement d'un niveau , est appelé par menu
	public void loadlv (String mdp) throws IOException
	{
		File pwData = new File("./Saves/passwordListDontOpenVerySecret.txt");
		
		BufferedReader br = new BufferedReader(new FileReader(pwData));
		
		String st;
		//On lit toutes les lignes du fichier texte
		while ( (st = br.readLine()) != null)
		{
			System.out.println(st);
			if (st.contains(mdp)) break;
		}
	
		int lv = StringManager.ParseLineToInt(br.readLine()).get(0);
		System.out.println(lv);
		
		this.add( new Partie(this, lv));
		
		br.close();
		
		this.revalidate();
	}
	
	// correspond au lancement d'une nouvelle partie, est appelé par menu
	public void start() throws IOException
	{
		this.add(new Partie("", this));
		this.revalidate();
	}
	
	// renvoi la classe menu, est appelé par partie. 
	public void menu()
	{
		System.out.println("yolo menu yo"+this.getComponentCount());
		//this.removeAll();
		this.revalidate();
		
		this.add(new Menu(this));
		this.revalidate();
	}
	
	

	
}
