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
import java.io.File;

import javax.swing.*;

//import Pause;
import Settings.*;
import Data.*;
import Engine.Objets.AnimatedSolidBloc;
import Engine.Objets.Ballon;
import Engine.Objets.BreakableBloc;
import Engine.Objets.MovingBloc;
import Engine.Objets.Objet;
import Engine.Objets.Oiseau;
import Engine.Objets.Piege;
import Engine.Objets.Snoopy;
import Engine.Objets.TapisRoulant;
import Utilitaires.*;

public class Niveau extends JPanel {
	private static final long serialVersionUID = 5093936493506272943L;
	
	private static String namePartie = "1";
	private static String name;
	//private Pause pause;
	private int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

	private Snoopy POOPY;
	private List<Ballon> ballons = new ArrayList<Ballon>();
	private List<BreakableBloc> breakableBlocs = new ArrayList<BreakableBloc>();
	private List<MovingBloc> movingBlocs = new ArrayList<MovingBloc>();
	private List<AnimatedSolidBloc> blocs = new ArrayList<AnimatedSolidBloc>();
	private List<TapisRoulant> tapisRoulants = new ArrayList<TapisRoulant>();
	private List<Oiseau> oiseaux = new ArrayList<Oiseau>();
	private List<Integer> nonSolidObjects = new ArrayList<Integer>(); //Liste des objets qu'il est possible de traverser
    
	private Timer movementsTimer;
	private boolean synchronizedMovements = true;
    
	private KeysPressedList keysPressedList = new KeysPressedList();
    
	@SuppressWarnings("serial")
	public Niveau(String _name, String _namePartie, boolean loadEnCours) throws IOException  /// Rajouter partie au constructeur
	{
		this.setLayout(null);
		name = _name;
		namePartie = _namePartie;
		
		//this.addKeyListener(new keylistener());
		nonSolidObjects.add(0);
		LoadObjects(MapDataManager.LoadMap(name+".txt"));
		
		if (POOPY == null)
		{
			System.out.println("Ce niveau ne contient pas Snoopy, impossible d'y jouer, retour au menu...");
			return;
		}
		
		if (loadEnCours)
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
		/*
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"),"MoveUp");
		this.getActionMap().put("MoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 1; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"),"MoveLeft");
		this.getActionMap().put("MoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 2; keyTimer.restart(); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"),"MoveDown");
		this.getActionMap().put("MoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { waitingKey = 3; keyTimer.restart(); } });
		*/
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false),"MoveUp");
		this.getActionMap().put("MoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.UP); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),"StopMoveUp");
		this.getActionMap().put("StopMoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.UP); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false),"MoveLeft");
		this.getActionMap().put("MoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.LEFT); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true),"StopMoveLeft");
		this.getActionMap().put("StopMoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.LEFT); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false),"MoveDown");
		this.getActionMap().put("MoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.DOWN); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),"StopMoveDown");
		this.getActionMap().put("StopMoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.DOWN); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),"MoveRight");
		this.getActionMap().put("MoveRight", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.RIGHT); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true),"StopMoveRight");
		this.getActionMap().put("StopMoveRight", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.RIGHT); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false),"SnoopyDo");
		this.getActionMap().put("SnoopyDo", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.SPACE); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true),"SnoopyStop");
		this.getActionMap().put("SnoopyStop", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.SPACE); } });
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),"Save");
		this.getActionMap().put("Save", new AbstractAction() { public void actionPerformed(ActionEvent e) { 
			try {
				SaveThis();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, true),"Pause");
		this.getActionMap().put("Pause", new AbstractAction() { public void actionPerformed(ActionEvent e) { pause(); } });
				
		
		
		
		
		this.validate();
		
	}
	
	private void pause()  // Timer to stop 
	{
		
		//partie.pPressed();
		
		try {
			//C'est qu'une 
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Stop timer (va falloir foreach toutes les listes et trigger leur fonction pause, y'aurait pas plus simple que le faire à la mano ?)
		
	}
	private void resume()
	{
		
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
	    		case BREAKABLEBLOC:
	    			breakableBlocs.add(new BreakableBloc(i,j));
	    			this.add(breakableBlocs.get(breakableBlocs.size()-1));
	    			break;
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
	    		case APPARITION:
	    			break;
	    		case TAPISROULANT:
	    			tapisRoulants.add(new TapisRoulant(i, j, Direction.NORTH));
	    			this.add(tapisRoulants.get(tapisRoulants.size()-1));
	    			if(!nonSolidObjects.contains(map[i][j]))
	    				nonSolidObjects.add(map[i][j]);
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
		ExecuteKeys(); //Si une touche a ete appuyee et est donc en attente on l'execute
		
		MouvementBallons(true); //Contient les collisions donc a lance meme si les mouvements ne sont pas synchronisees (que la balle bouge sur son propre timer)
		
		if (synchronizedMovements)
		{
			POOPY.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			for (MovingBloc movingBloc : movingBlocs) {
				movingBloc.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			}
		}
			
		CollisionsSnoopy();
		
		for (TapisRoulant tapisRoulant : tapisRoulants) {
			CollisionsTapis(tapisRoulant);
		}
	}
	
	private void ExecuteKeys()
	{
		if (keysPressedList.ActionReadyOf(KeyType.UP)) if (MoveObject(POOPY,Direction.NORTH)) keysPressedList.FireKey(KeyType.UP);
		if (keysPressedList.ActionReadyOf(KeyType.LEFT)) if (MoveObject(POOPY,Direction.WEST)) keysPressedList.FireKey(KeyType.LEFT);
		if (keysPressedList.ActionReadyOf(KeyType.DOWN)) if (MoveObject(POOPY,Direction.SOUTH)) keysPressedList.FireKey(KeyType.DOWN);
		if (keysPressedList.ActionReadyOf(KeyType.RIGHT)) if (MoveObject(POOPY,Direction.EAST)) keysPressedList.FireKey(KeyType.RIGHT);
		if (keysPressedList.ActionReadyOf(KeyType.SPACE)) if (SpacePressed()) keysPressedList.FireKey(KeyType.UP);
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
		{
			/*
			String bip = "bip.mp3";
			Media hit = new Media(new File(bip).toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
			*/
			System.out.println("Et c'est la looooose");
		}
			
		
	}
	
	private boolean SpacePressed()
	{
		System.out.println("Space pressed");
		int blocX = POOPY.NextCaseX(POOPY.orientation);
		int blocY = POOPY.NextCaseY(POOPY.orientation);
		
		switch (ObjectType.typeOfInt(map[blocX][blocY]))
		{
		case MOVINGBLOC:
			for (MovingBloc movingBloc : movingBlocs) {
				if (movingBloc.xInMap == blocX && movingBloc.yInMap == blocY)
				{
					System.out.println("MovingBloc found");
					if (MoveObject(movingBloc, POOPY.orientation))
					{
						map[movingBloc.xInMap][movingBloc.yInMap]  = map[blocX][blocY];
						map[blocX][blocY] = 0;
						return true;
					}
				}
			}
			break;
		case BREAKABLEBLOC:
			for (BreakableBloc breakableBloc : breakableBlocs) {
				if (breakableBloc.xInMap == blocX && breakableBloc.yInMap == blocY)
				{
					System.out.println("breakableBloc found");
					if (breakableBloc.stopAnimation)
						breakableBloc.Break();
					map[blocX][blocY] = 0;
				}
			}
			break;
		default:
			break;
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
	
	private void CollisionsTapis(TapisRoulant t)
	{
		for (MovingBloc movingBloc : movingBlocs) {
			
		}
	}
	
	private void SaveThis() throws FileNotFoundException, UnsupportedEncodingException
	{
		//ENTREZ NOM PARTIE SI NON EXISTANT
		PrintWriter saveFile = new PrintWriter("./Maps/" + name + "P" + namePartie + ".txt", "UTF-8");
		
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

