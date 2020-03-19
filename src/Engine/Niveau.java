package Engine;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

import javax.swing.*;

import Settings.*;
import Data.*;
import java.util.*;

public class Niveau extends JPanel {
	private static final long serialVersionUID = 1L;
	
	int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

    protected	Snoopy POOPY;
	
	public Niveau(String name) throws FileNotFoundException
	{
		int[][] _map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];
		
		_map = MapDataManager.LoadMap(name+".txt");
		
		Init(_map);
		this.addKeyListener(new keylistener());
	}

	void Init(int[][] _map)
	{
		map = _map;
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally, globalVar.tileHeight*globalVar.nbTilesVertically);
		
		for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    	{
	    		//System.out.print(map[i][j]);
	    		switch(map[i][j])
	    		{
	    		case 1:
	    			this.add(new Objet(i,j));
	    			
	    			
	    			//Si t'arrives a faire en sorte que ce Jlabel se place bien dans la fentre t'es chaud (c'est cens� faire le contour)
	    			JLabel newBloc = new JLabel(new ImageIcon("./Images/Sprites/default.png"));
	    			newBloc.setBounds(i*globalVar.tileWidth, j*globalVar.tileHeight, globalVar.tileWidth, globalVar.tileHeight);

	    			
	    			
	    			
	    			System.out.print(i*globalVar.tileWidth + " " + j*globalVar.tileHeight + " " );
	    			this.add(newBloc);
	    			break;
	    		case 9:
	    			POOPY = new Snoopy(i*globalVar.tileWidth, j*globalVar.tileHeight);
	    			map[i][j] = 0;
	    			this.add(POOPY);
	    			break;
	    		}
	    		
	    	}
	    	System.out.println("");
	    }
		System.out.println(" nombre de sprite   "+this.getComponentCount());
		this.setVisible(true);
		this.validate();
	}
	
	public boolean MainLoop()
	{
		boolean quit = false;
		do
		{
			
		}while(!quit);
		return true;
	}
	
	private class keylistener implements KeyListener
	{

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			System.out.println("touche"+e.getKeyCode());
			
		}

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
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

}
