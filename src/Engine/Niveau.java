package Engine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import Settings.*;
import Data.*;
import Utilitaires.*;

public class Niveau extends JPanel {
	private static final long serialVersionUID = 5093936493506272943L;
	private static final String idPartie = "1";
	private String name;

	private int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

	private Snoopy POOPY;
	private ArrayList<Ballon> ballons = new ArrayList<Ballon>();
	private ArrayList<AnimatedSolidBloc> blocs = new ArrayList<AnimatedSolidBloc>();
	private ArrayList<MovingBloc> movingBlocs = new ArrayList<MovingBloc>();
	private ArrayList<Oiseau> oiseaux = new ArrayList<Oiseau>();
	private List<Integer> nonSolidObjects = new ArrayList<Integer>(); //Liste des objets qu'il est possible de traverser
    
	private Timer movementsTimer;
	private boolean synchronizedMovements = true;
    
	private Timer keyTimer = new Timer(500, new ActionListener() { public void actionPerformed(ActionEvent arg0) { waitingKey=0; keyTimer.stop(); } });
	private int waitingKey = 0;
    
	@SuppressWarnings("serial")
	public Niveau(String _name, boolean load) throws IOException
	{
		this.setLayout(null);
		name = _name;
		
		//this.addKeyListener(new keylistener());
		nonSolidObjects.add(0);
		LoadObjects(MapDataManager.LoadMap(name+".txt"));
		
		if (POOPY == null)
		{
			System.out.println("Ce niveau ne contient pas Snoopy, impossible d'y jouer, retour au menu...");
			return;
		}
		
		if (load)
		{
			List<Ballon> _ballons = SaveManager.LoadSaveNiveau(name);
			for (Ballon b : _ballons) {
				AddBallon(b);
			}
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
		
		//When key pressed add to list + timer, when key released take off list, when key action is done succesfully start timer, when wanting to do a key action, make sure timer for this key is out (avoid doing twice the same thing when only wanted once but key still pressed for a few ms)
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"MoveUp");
		this.getActionMap().put("MoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 1; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"MoveLeft");
		this.getActionMap().put("MoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 2; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"MoveDown");
		this.getActionMap().put("MoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 3; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"),"MoveRight");
		this.getActionMap().put("MoveRight", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 4; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false),"SnoopyDo");
		this.getActionMap().put("SnoopyDo", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 5; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),"Save");
		this.getActionMap().put("Save", new AbstractAction() { public void actionPerformed(ActionEvent e) { 
			try {
				SaveThis();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} } });
		
		this.validate();
		
	}

	void LoadObjects(int[][] _map) 
	{
		
		map = _map;
		
		this.setBounds(0, 0, globalVar.tileWidth*globalVar.nbTilesHorizontally,  globalVar.tileHeight*globalVar.nbTilesVertically);
		System.out.println("screen size : " + globalVar.tileWidth*globalVar.nbTilesHorizontally + " " +  globalVar.tileHeight*globalVar.nbTilesVertically);
		
		//On charge les ballons et Snoopy
		for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    	{
	    		switch(ObjectType.typeOfInt(map[i][j]))
	    		{
	    		case BALLON:
	    			AddBallon(new Ballon(i,j, !synchronizedMovements));
	    			map[i][j] = 0;
	    			break;
	    		case SNOOPY:
	    			POOPY = new Snoopy(i, j, !synchronizedMovements);
	    			this.add(POOPY);
	    			map[i][j] = 0;
	    			break;
				default:
					break;
	    		}
	    	}
	    }
		
		//On charge le reste
		for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    	{
	    		switch(ObjectType.typeOfInt(map[i][j]))
	    		{
	    		case PIEGE:
	    			this.add(new Piege(i,j));
	    			if(!nonSolidObjects.contains(map[i][j]))
	    				nonSolidObjects.add(map[i][j]);
	    			break;
	    		case MOVINGBLOC:
	    			movingBlocs.add(new MovingBloc(i, j, !synchronizedMovements));
	    			this.add(movingBlocs.get(movingBlocs.size()-1));
	    			break;
	    		case SOLIDBLOC:
	    			blocs.add(new AnimatedSolidBloc(i,j));
	    			this.add(blocs.get(blocs.size()-1));
	    			break;
	    		case OISEAU:
	    			oiseaux.add(new Oiseau(i,j));
	    			this.add(oiseaux.get(oiseaux.size()-1));
	    			if(!nonSolidObjects.contains(map[i][j]))
	    				nonSolidObjects.add(map[i][j]);
	    			break;
				default:
					break;
	    		}
	    	}
	    }		
	}
	
	public void AddBallon(Ballon b)
	{
		ballons.add(b);
		this.add(b);
	}
	
	//Calculs effectues sur la frequence globalVar.CalculusFrequency
	private void movementsTimerTrigger() 
	{
		ExecuteKey(waitingKey); //Si une touche a ete appuyee et est donc en attente on l'execute
		
		MouvementBallons(true); //Contient les collisions donc a lance meme si les mouvements ne sont pas synchronisees (que la balle bouge sur son propre timer)
		
		if (synchronizedMovements)
		{
			POOPY.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			for (MovingBloc movingBloc : movingBlocs) {
				movingBloc.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			}
		}
			
		
		CollisionsSnoopy();
	}
	
	private void ExecuteKey(int key)
	{
		boolean successful = false;
		switch(key)
		{
		case 1 : successful = MoveObject(POOPY,Direction.NORTH); break;
		case 2 : successful = MoveObject(POOPY,Direction.WEST); break;
		case 3 : successful = MoveObject(POOPY,Direction.SOUTH); break;
		case 4 : successful = MoveObject(POOPY,Direction.EAST); break;
		case 5 : successful = SpacePressed(); break;
		default: successful = true;
		}
		
		//Si l'action a bien ete realise ou que le cooldown de l'hysteresis es tarrive a son terme on reinitialise la touche en attente et le timer
		if(successful)
		{
			keyTimer.stop();
			waitingKey = 0;
		}
	}
	
	//Move an object to the desired direction, returns true if the object moved or changed direction succesfully
	private boolean MoveObject(Objet o, Direction d)
	{
		//If the object is a movable object and can move and if there is nothing blocking the way
		if (PossibleToMove(o, d))
			return o.Move(d);
		
		//Ok il peut pas bouger mais si c'est snoopy il veut ptet juste regarder du bon cote tu sais pas
		else if (o.Type() == ObjectType.SNOOPY && !o.IsMoving())
				return ((Snoopy)o).ChangeOrientationTo(d);
			
		return false;
			
	}
	
	//returns true if the object is a movable object and can move and if there is nothing blocking the way
	private boolean PossibleToMove(Objet o, Direction d)
	{
		return (o.CanMove(d) && nonSolidObjects.contains(map[o.NextCaseX(d)][o.NextCaseY(d)]));
	}
	
	private void CollisionsSnoopy()
	{
		//La seule collision de snoopy c'est avec les oiseaux et lesp pieges en dehors d'etre bloque par la map (gerer dans les MoveObject)
		//Et snoopy n'attrape un oiseau ou on ne compte le piege que s'il est a l'arret (vient de passer par la case)
		if (POOPY.IsMoving())
			return;
		
		  ///////////////
		 //  OISEAUX  //
		///////////////
		
		Oiseau catchedOiseau = null;
		//On regarde tous les oiseaux pour voir si snoopy ne se trouverai pas sur la case de l'un deux
		for (Oiseau oiseau : oiseaux) {
			if (((Objet) POOPY).SameTileAs((Objet)oiseau))
			{
				catchedOiseau = oiseau;
				break;
			}
		}
		
		//Si il en a attrape un, on le retire de la map, des objets de la fenetre et de la liste ne contenant que les oiseaux
		if (catchedOiseau != null)
		{
			map[catchedOiseau.xInMap][catchedOiseau.yInMap] = 0;
			this.remove(catchedOiseau);
			oiseaux.remove(catchedOiseau);
			POOPY.RefreshSprite();
		}
		
		//Si la liste est vide ca veut dire que snoopy a attrape tous les oiseaux et qu'il a fini le niveau
		if(oiseaux.isEmpty())
		{
			System.out.println("Et c'est la wiiin");
		}
		
		  //////////////
		 //  PIEGES  //
		//////////////
		
		if (map[POOPY.xInMap][POOPY.yInMap] == ObjectType.mapIdOf(ObjectType.PIEGE))
			System.out.println("Et c'est la looooose");
		
	}
	
	private boolean SpacePressed()
	{
		System.out.println("Space pressed");
		int movingblocX = POOPY.NextCaseX(POOPY.orientation);
		int movingblocY = POOPY.NextCaseY(POOPY.orientation);
		
		if (map[movingblocX][movingblocY] == ObjectType.mapIdOf(ObjectType.MOVINGBLOC))
		{
			for (MovingBloc movingBloc : movingBlocs) {
				if (movingBloc.xInMap == movingblocX && movingBloc.yInMap == movingblocY)
				{
					System.out.println("MovingBloc found");
					if (MoveObject(movingBloc, POOPY.orientation))
					{
						map[movingBloc.xInMap][movingBloc.yInMap]  = map[movingblocX][movingblocY];
						map[movingblocX][movingblocY] = 0;
						return true;
					}
				}
			}
		}
		
		return false;
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
			b.hitboxslow(blocs.get(y), true);
		for(int y = 0; y < movingBlocs.size(); y++)
			b.hitboxslow(movingBlocs.get(y), true);
		if(b.hitboxslow(POOPY, false))
			System.out.println("Et c'est la loooose");
	}
	
	private void SaveThis() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter saveFile = new PrintWriter("./Maps/" + name + "P" + idPartie + ".txt", "UTF-8");
		
		System.out.println("Saving...");
		
		saveFile.println(globalVar.nbTilesHorizontally + " " + globalVar.nbTilesVertically);
		
	    for (int j=0; j<globalVar.nbTilesVertically; j++)
	    {
	    	String lineToPrint = "";
	    	for (int i=0; i<globalVar.nbTilesHorizontally; i++)
	    		lineToPrint+= (((((Objet)POOPY).IsHere(i, j))?8:map[i][j]) + ((i >= globalVar.nbTilesHorizontally - 1)?"":" "));
	    	
	    	saveFile.println(lineToPrint);
	    }
	    
	    if (!ballons.isEmpty())
	    {
	    	saveFile.println("Ballons");
	    	for (Ballon ballon : ballons)
		    	saveFile.println(ballon.SavingInfo());
	    }
	    
	    
	    saveFile.close();
	}
}

