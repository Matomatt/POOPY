package Engine;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import Data.SaveManager;
import Settings.globalVar;



public class Fenetre extends JFrame 
{
	private static final long serialVersionUID = 8164118974463460991L;
	public Fenetre ()
	{
		this.setTitle("Snoopy");
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally+18, globalVar.tileHeight*globalVar.nbTilesVertically+46);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
//		pause=null;
//		niveaux= new ArrayList<Niveau>();
//		try {
//			//niveaux.add( new Niveau("level1", false));
//			//niveaux.add(new Niveau("level1P1", true));
//			niveaux.add( new Niveau("level2", false));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	//	niveaux.get(0).addKeyListener(niveaux.get(0));
//		niveaux.get(0).setFocusable(true);
//		this.add(niveaux.get(0));
		//niveau.get(0).MainLoop();
	//	niveau.add(new Niveau("level1.txt"));
//		try {
//			LaunchDefaultLevel();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			System.out.println("Moi teuteu, moi pas trouver la map du niveau par d�faut");
//		}
		this.add(new Menu(this));
		//this.addKeyListener();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.validate();// sert a valider l'incorporation du nouveau jpanel
		
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE); /// SALLE MECHANT PLUS JAMAIS MA RAM OSCOUR
		
	}
	
	public void launch()
	{
		//niveaux.get(0).MainLoop();
	}
//	public Pause getpause()
//	{
//		return pause;
//	}
//	public void addPause()
//	{
//		pause=new Pause(this);
//		add(pause);
//	}
//	public void suppPause()
//	{
//		remove(pause);
//		pause=null;
//	}
//	void keyTyped(KeyEvent e)
//	{
//		if (e.getKeyCode()==34)
//		{
//			System.out.println("YOLO");
//		}
//		 
//	}
	public void loadgame(String a)
	{
		if (a.equals("lv2")==true)
		{
			System.out.println("code ok ");
			this.add( new Partie(this,2));
		}
		if (a.equals("lv3")==true)
		{
			this.add(new Partie(this,3));
		}
	}
	
	public void loadlv (String a)
	{
		
	}
	
	public void start()
	{
		this.add(new Partie(this));
		this.revalidate();
	}
	
	

	
}
