package Engine;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Settings.*;
import Data.*;
import Utilitaires.*;
import java.util.*;

public class Niveau extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

    protected Objet POOPY;
	
	public Niveau(String name) throws FileNotFoundException
	{
		int[][] _map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];
		
		_map = MapDataManager.LoadMap(name+".txt");
		
		Init(_map);
	//	this.addKeyListener(new keylistener());
	}

	void Init(int[][] _map)
	{
		this.setLayout(null);
		map = _map;
		this.setBounds(0, 0, globalVar.tileWidth*globalVar.nbTilesHorizontally,  globalVar.tileHeight*globalVar.nbTilesVertically);
		System.out.println("screen size : " + globalVar.tileWidth*globalVar.nbTilesHorizontally + " " +  globalVar.tileHeight*globalVar.nbTilesVertically);
		Objet buffer;
		for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    	{
	    		//System.out.print(map[i][j]);
	    		switch(map[i][j])
	    		{
	    		case 1:
	    			buffer=new Objet(i,j, ObjectType.SOLIDBLOC);
	    			this.add(buffer);
	    			break;
	    		case 9:
	    			POOPY = new Snoopy(i, j);
	    			map[i][j] = 0;
	    			
	    			this.add(POOPY);
	    			//C'est comme �a qu'on fait pour bouger n'importe quel objet
	    			MoveObject(POOPY, Direction.EAST);
	    			MoveObject(POOPY, Direction.EAST);
	    			break;
	    		}
	    	}
	    	//System.out.println("");
	    }
		System.out.println("Nombre d'objets dans le niveau : " + this.getComponentCount());
		this.requestFocus();
		this.setVisible(true);
		this.validate();
		System.out.println(this.getSize());
		
	}
	
	public boolean MainLoop()
	{
		boolean quit = false;
		do
		{
			
		}while(!quit);
		return true;
	}
	
//	private class keylistener implements KeyListener
//	{
//
//		public void keyTyped(KeyEvent e) {
//			// TODO Auto-generated method stub
//			System.out.println("touche"+e.getKeyCode());
//			
//		}
//
//		public void keyPressed(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void keyReleased(KeyEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
	
	/*
	protected void mouvement(int moove) // add binding 
	{
		if(moove==0)
		{
			POOPY.up();
		}
		if(moove==1)
		{
			POOPY.right();
		}
		if(moove==2)
		{
			POOPY.left();
		}
		if(moove==3)
		{
			POOPY.down();
		}
	}
	*/
	
	//Move an object to the desired direction
	private void MoveObject(Objet o, Direction d)
	{
		//If the object is a movable object and can move and if there is nothing blocking the way
		if (PossibleToMove(o, d))
		{
			//Tampon tu connais
			int exX = o.xInMap; int exY = o.yInMap;
			//If the object is written on the map (like the movable solid bloc) move returns true
			if (o.Move(d))
			{
				//Exchanging the values in the map array
				int tmpMap = map[exX][exY];
				map[exX][exY] = map[o.xInMap][o.yInMap];
				map[o.xInMap][o.yInMap] = tmpMap;
			}
		}
		//Ok il peut pas bouger mais si c'est snoopy il veut ptet juste regarder du bon cot� tu sais pas
		else if (o.Type() == ObjectType.SNOOPY)
			((Snoopy)o).ChangeOrientationTo(d);
			
	}
	
	//returns true if the object is a movable object and can move and if there is nothing blocking the way
	private boolean PossibleToMove(Objet o, Direction d)
	{		
		System.out.println("trying to go there : [" + o.NextCaseX(d) + ", " + o.NextCaseY(d) + "] " + ((Snoopy)o).CanMove(d) + " " + (map[o.NextCaseX(d)][o.NextCaseY(d)] == 0));
		return (o.CanMove(d) && map[o.NextCaseX(d)][o.NextCaseY(d)] == 0);
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("touche"+e.getKeyCode());
		
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("touche"+e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		System.out.println("touche"+e.getKeyCode());
		
	}

}
