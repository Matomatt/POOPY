import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import Settings.globalVar;
import Engine.Niveau;



public class Fenetre extends JFrame 
{
	private static final long serialVersionUID = 8164118974463460991L;
	protected ArrayList<Niveau> niveaux;
	int actuallv=0;
	
	
	
	public Fenetre ()
	{
		this.setTitle("Snoopy");
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally+18, globalVar.tileHeight*globalVar.nbTilesVertically+46);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		
		niveaux= new ArrayList<Niveau>();
		try {
			niveaux.add( new Niveau("level1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	niveaux.get(0).addKeyListener(niveaux.get(0));
		niveaux.get(0).setFocusable(true);
		this.add(niveaux.get(0));
	
		//niveau.get(0).MainLoop();
	//	niveau.add(new Niveau("level1.txt"));
//		try {
//			LaunchDefaultLevel();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			System.out.println("Moi teuteu, moi pas trouver la map du niveau par d�faut");
//		}
		//this.addKeyListener();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.validate();// sert a valider l'incorporation du nouveau jpanel
		
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE); /// SALLE MECHANT PLUS JAMAIS MA RAM OSCOUR
		
	}
	
	public void launch()
	{
		//niveaux.get(0).MainLoop();
	}
	
//	void keyTyped(KeyEvent e)
//	{
//		if (e.getKeyCode()==34)
//		{
//			System.out.println("YOLO");
//		}
//		 
//	}
	

	
}
