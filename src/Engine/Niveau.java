package Engine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected ArrayList<Oiseau> oiseaux = new ArrayList<Oiseau>();
    
    Timer movementsTimer;
    boolean synchronizedMovements = false;
    
    Timer keyTimer = new Timer(800, new ActionListener() { public void actionPerformed(ActionEvent arg0) { ExecuteKey(waitingKey, true); } });
    int waitingKey = 0;
    
	@SuppressWarnings("serial")
	public Niveau(String name) throws IOException
	{
		this.setLayout(null);
		
		//this.addKeyListener(new keylistener());
		
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
		this.getActionMap().put("MoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 1; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"MoveLeft");
		this.getActionMap().put("MoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 2; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"MoveDown");
		this.getActionMap().put("MoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 3; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"MoveRight");
		this.getActionMap().put("MoveRight", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 4; keyTimer.restart(); } });
		
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
	    		case 7:
	    			ballons.add(new Ballon(i,j, !synchronizedMovements));
	    			this.add(ballons.get(ballons.size()-1));
	    			map[i][j] = 0;
	    			break;
	    		case 8:
	    			POOPY = new Snoopy(i, j, !synchronizedMovements);
	    			this.add(POOPY);
	    			map[i][j] = 0;
	    			break;
	    		}
	    	}
	    	//System.out.println("");
	    }
		
		for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    	{
	    		//System.out.print(map[i][j]);
	    		switch(map[i][j])
	    		{
	    		case 4:
	    			blocs.add(new AnimatedSolidBloc(i,j));
	    			this.add(blocs.get(blocs.size()-1));
	    			break;
	    		case 9:
	    			oiseaux.add(new Oiseau(i,j));
	    			this.add(oiseaux.get(oiseaux.size()-1));
	    			break;
	    		}
	    	}
	    	//System.out.println("");
	    }		
	}
	
	private void movementsTimerTrigger() 
	{
		ExecuteKey(waitingKey, false);
		
		MouvementBallons(true);
		
		if (synchronizedMovements)
			POOPY.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
		
		if (!POOPY.IsMoving())
			CollisionsSnoopy();
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
	
	private void CollisionsSnoopy()
	{
		Oiseau catchedOiseau = null;
		for (Oiseau oiseau : oiseaux) {
			if (((Objet) POOPY).MemeCaseQue((Objet)oiseau))
			{
				catchedOiseau = oiseau;
				break;
			}
		}
		if (catchedOiseau != null)
		{
			this.remove(catchedOiseau);
			oiseaux.remove(catchedOiseau);
		}
	}
	
	//Move an object to the desired direction
	private boolean MoveObject(Objet o, Direction d)
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
				return true;
			}
		}
		//Ok il peut pas bouger mais si c'est snoopy il veut ptet juste regarder du bon cot� tu sais pas
		else if (o.Type() == ObjectType.SNOOPY && !o.IsMoving())
				return ((Snoopy)o).ChangeOrientationTo(d);
			
		return false;
			
	}
	
	//returns true if the object is a movable object and can move and if there is nothing blocking the way
	private boolean PossibleToMove(Objet o, Direction d)
	{	
		Integer l[] = {0, 9};
		List<Integer> nonSolidObjects = Arrays.asList(l);
		//System.out.println("trying to go there : [" + o.NextCaseX(d) + ", " + o.NextCaseY(d) + "] " + ((Snoopy)o).CanMove(d) + " " + (map[o.NextCaseX(d)][o.NextCaseY(d)] == 0));
		return (o.CanMove(d) && nonSolidObjects.contains(map[o.NextCaseX(d)][o.NextCaseY(d)]));
	}
	
	private void ExecuteKey(int key, boolean timerTriggered)
	{
		boolean successful = false;
		switch(key)
		{
		case 1 : successful = MoveObject(POOPY,Direction.NORTH); break;
		case 2 : successful = MoveObject(POOPY,Direction.WEST); break;
		case 3 : successful = MoveObject(POOPY,Direction.SOUTH); break;
		case 4 : successful = MoveObject(POOPY,Direction.EAST); break;
		}
		
		if(successful || timerTriggered)
		{
			keyTimer.stop();
			waitingKey = 0;
		}
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

