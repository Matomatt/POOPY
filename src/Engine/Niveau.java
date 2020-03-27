package Engine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import Settings.*;
import Data.*;
import Utilitaires.*;

public class Niveau extends JPanel {
	private static final long serialVersionUID = 5093936493506272943L;

	int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

    protected Snoopy POOPY;
    protected ArrayList<Ballon> ballons = new ArrayList<Ballon>();
    protected ArrayList<AnimatedSolidBloc> blocs = new ArrayList<AnimatedSolidBloc>();
    
    Timer movementsTimer;
    boolean synchronizedMovements = true;
    
	@SuppressWarnings("serial")
	public Niveau(String name) throws IOException
	{
		this.setLayout(null);
		
		//	this.addKeyListener(new keylistener());
		
		LoadObjects(MapDataManager.LoadMap(name+".txt"));
		
		if (POOPY == null)
		{
			System.out.println("Ce niveau ne contient pas Snoopy, impossible d'y jouer, retour au menu...");
			return;
		}
		
		System.out.println("Nombre d'objets dans le niveau : " + this.getComponentCount());
		this.requestFocus();
		this.setVisible(true);
		this.validate();
		
		//Synchronized Movements, gardez if !synchronized pour les collisions
		ActionListener movementsTaskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { movementsTimerTrigger(); } };
		
		movementsTimer = new Timer(1000/globalVar.CalculusFrequency, movementsTaskPerformer);
		movementsTimer.start();
		
		this.grabFocus();
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"MoveUp");
		this.getActionMap().put("MoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { MoveObject(POOPY,Direction.NORTH); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"MoveLeft");
		this.getActionMap().put("MoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { MoveObject(POOPY,Direction.WEST); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"MoveDown");
		this.getActionMap().put("MoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { MoveObject(POOPY,Direction.SOUTH); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"MoveRight");
		this.getActionMap().put("MoveRight", new AbstractAction() { public void actionPerformed(ActionEvent e) { MoveObject(POOPY,Direction.EAST); } });
		
		this.validate();
		
	}

	void LoadObjects(int[][] _map) 
	{
		
		map = _map;
		
		this.setBounds(0, 0, globalVar.tileWidth*globalVar.nbTilesHorizontally,  globalVar.tileHeight*globalVar.nbTilesVertically);
		System.out.println("screen size : " + globalVar.tileWidth*globalVar.nbTilesHorizontally + " " +  globalVar.tileHeight*globalVar.nbTilesVertically);
		
		for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    	{
	    		//System.out.print(map[i][j]);
	    		switch(map[i][j])
	    		{
	    		case 1:
	    			blocs.add(new AnimatedSolidBloc(i,j));
	    			this.add(blocs.get(blocs.size()-1));
	    			break;
	    		case 2:
	    			ballons.add(new Ballon(i,j, !synchronizedMovements));
	    			this.add(ballons.get(ballons.size()-1));
	    			map[i][j] = 0;
	    			break;
	    		case 9:
	    			POOPY = new Snoopy(i, j, !synchronizedMovements);
	    			map[i][j] = 0;
	    			this.add(POOPY);
	    			break;
	    		}
	    	}
	    	//System.out.println("");
	    }		
	}
	
	private void movementsTimerTrigger() 
	{
		MouvementBallons(true);
		if (synchronizedMovements)
			POOPY.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
	}
	
	private void MouvementBallons(boolean checkCollisions) 
	{
		for(int i = 0; i < ballons.size(); i++)
		{
			if (synchronizedMovements)
				ballons.get(i).MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			
			if (checkCollisions)
				for(int y=0; y < blocs.size(); y++)
					CollisionsBallon(ballons.get(i));
		}
	}

	private void CollisionsBallon(Ballon b) 
	{
		for(int y = 0; y < blocs.size(); y++)
			b.hitboxslow(blocs.get(y));
	}
	
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
		else if (o.Type() == ObjectType.SNOOPY && !o.IsMoving())
			((Snoopy)o).ChangeOrientationTo(d);
			
	}
	
	//returns true if the object is a movable object and can move and if there is nothing blocking the way
	private boolean PossibleToMove(Objet o, Direction d)
	{		
		//System.out.println("trying to go there : [" + o.NextCaseX(d) + ", " + o.NextCaseY(d) + "] " + ((Snoopy)o).CanMove(d) + " " + (map[o.NextCaseX(d)][o.NextCaseY(d)] == 0));
		return (o.CanMove(d) && map[o.NextCaseX(d)][o.NextCaseY(d)] == 0);
	}
	
	
	/*
	Action moveup = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e) 
		{
			MoveObject(POOPY,Direction.NORTH);
		}
	};	
	Action moveleft = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e) 
		{
			MoveObject(POOPY,Direction.WEST);
		}
	};	
	Action movedown = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e) 
		{
			MoveObject(POOPY,Direction.SOUTH);
		}
	};
	Action moveright = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e) 
		{
			MoveObject(POOPY,Direction.EAST);
		}
	};
	*/
	
}

