package Engine;
import java.awt.Color;
import java.awt.Font;
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
//import java.util.Timer;
import java.util.List;
import java.util.TimerTask;
import java.io.File;

import javax.naming.InitialContext;
import javax.swing.*;

//import Pause;
import Settings.*;
import Data.*;
import Engine.Objets.AnimatedObject;
import Engine.Objets.AnimatedSolidBloc;
import Engine.Objets.Apparition;
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
	
	protected String name;
	private int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

	private Snoopy POOPY;
	private List<Apparition> apparitions = new ArrayList<Apparition>();
	private List<Ballon> ballons = new ArrayList<Ballon>();
	private List<BreakableBloc> breakableBlocs = new ArrayList<BreakableBloc>();
	private List<MovingBloc> movingBlocs = new ArrayList<MovingBloc>();
	private List<AnimatedSolidBloc> blocs = new ArrayList<AnimatedSolidBloc>();
	private List<TapisRoulant> tapisRoulants = new ArrayList<TapisRoulant>();
	private List<Oiseau> oiseaux = new ArrayList<Oiseau>();
	private List<Integer> nonSolidObjects = new ArrayList<Integer>(); //Liste des objets qu'il est possible de traverser
	
	protected boolean ended = false;
	private int vie;
	private JLabel vieDisplayer;
//    private Java.util.Timer lvtimer;

    private int seconde=60;
    private JLabel temps;
	
	private Timer movementsTimer;
	private boolean synchronizedMovements = true;
    
	private KeysPressedList keysPressedList = new KeysPressedList();
	private Partie partie;

	public Niveau(String _name, Partie p, boolean loadEnCours) throws IOException  /// Rajouter partie au constructeur
	{
		this.setLayout(null);
		//this.setOpaque(true);
		this.setBackground(new Color(213,210,204));
		
		partie = p;
		name = _name;
		vie = partie.vies;
		
		System.out.println("New level : " + name);
		
		temps=new JLabel(new String("Remaining Time: " + seconde));
		temps.setFont(new Font("DISPLAY",Font.PLAIN, globalVar.tileHeight/2));
		temps.setSize(300, globalVar.tileHeight/2+3);
		temps.setForeground(Color.BLUE);
		temps.setVisible(true);
		
		vieDisplayer=new JLabel(new String("Vies : "+vie));
		vieDisplayer.setFont(new Font("DISPLAY",Font.PLAIN, globalVar.tileHeight/2));
		vieDisplayer.setSize(300, globalVar.tileHeight/2+3);
		vieDisplayer.setForeground(Color.BLUE);
		vieDisplayer.setVisible(true);
		
		nonSolidObjects.add(0);
		LoadObjects(MapDataManager.LoadMap(name+".txt"));
		
		if (loadEnCours)
		{
			List<Ballon> _ballons = SaveManager.LoadSaveNiveau(name);
			for (Ballon b : _ballons) AddBallon(b);
		}
		
		if(this.name.contains("P")) name = name.split("P")[0];
		
		System.out.println("Nombre d'objets dans le niveau : " + this.getComponentCount());
		
		//Synchronized Movements, gardez if !synchronized pour les collisions
		ActionListener movementsTaskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{ 
				if (movementsTimerTrigger())
					if (POOPY != null)
						System.out.println("Faudrait reset lï¿½ si on a le time");
			} };
		
		movementsTimer = new Timer(1000/globalVar.CalculusFrequency, movementsTaskPerformer);
		
		movementsTimer.start();
		
		this.setVisible(false);
		this.StopAll();
	}
	
	
	
	
	
	
	@SuppressWarnings("serial")
	public boolean Start()
	{
		System.out.println("Let's a go ! ("+name+")");
		if (POOPY == null)
		{
			System.out.println("Ce niveau ne contient pas Snoopy, impossible d'y jouer, retour au menu...");
			return false;
		}
		System.out.println("Let's a go !");
		
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
		
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false),"Save");
		this.getActionMap().put("Save", new AbstractAction() { public void actionPerformed(ActionEvent e) { CallSavePartie(); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false),"Pause");
		this.getActionMap().put("Pause", new AbstractAction() { public void actionPerformed(ActionEvent e) { pause(); } });
		
		
		temps.setLocation(globalVar.tileWidth/6, globalVar.tileHeight/6);
		vieDisplayer.setLocation(globalVar.tileWidth*10, globalVar.tileHeight/6);
		
		partie.add(temps);
		partie.add(vieDisplayer);
		
		this.setLocation(0, globalVar.tileHeight);
		this.setVisible(true);
		this.validate();
		
		this.Resume();
		
		return true;
	}

	public void timergestion()
	{
		seconde-=1;
		temps.setText(new String("Remaining Time: " + seconde));
		//System.out.println("seconde"+seconde);
		if (seconde<=0)
		{
			vieloose();
		}
		
	}
	
	private boolean vieloose() //return true si plus de vie du tout
	{
		vie-=1;
		vieDisplayer.setText(new String("Vies : "+vie));
		
		if (vie<=0)
		{
			KillAll();
			ended = true;
			partie.perdu();
			return true;
		}
		
		return globalVar.resetLevelWhenLosingLife;
	}
	private void win()
	{
		KillAll();
		ended = true;
		partie.addscore(seconde*100);
		partie.next();
	}
	
	private void KillAll()
	{
		movementsTimer.removeActionListener(movementsTimer.getActionListeners()[0]);
		movementsTimer.stop();
		
		if (POOPY != null)
			POOPY.Kill();
		POOPY = null;
		for (Ballon ballon : ballons) { ballon.Kill(); }
		for (BreakableBloc breakableBloc : breakableBlocs) { breakableBloc.Kill();}
		for (MovingBloc movingBloc : movingBlocs) { movingBloc.Kill();  }
		for (AnimatedSolidBloc bloc : blocs) { bloc.Kill(); }
		for (TapisRoulant tapisRoulant : tapisRoulants) { tapisRoulant.Kill();}
		keysPressedList.Kill();
		
		this.removeAll();
		partie.remove(temps);
		partie.remove(vieDisplayer);
		
		this.getInputMap().clear();
		this.getActionMap().clear();
		
		System.gc();
	}
	
	private void pause()  // Timer to stop 
	{
		partie.pPressed();
	}

	public void StopAll()
	{
		movementsTimer.stop();
		if (POOPY != null)
			POOPY.Pause();
		for (Ballon ballon : ballons) { ballon.Pause(); }
		for (BreakableBloc breakableBloc : breakableBlocs) { breakableBloc.Pause(); }
		for (MovingBloc movingBloc : movingBlocs) { movingBloc.Pause(); }
		for (AnimatedSolidBloc bloc : blocs) { bloc.Pause(); }
		for (TapisRoulant tapisRoulant : tapisRoulants) { tapisRoulant.Pause(); }
	}
	
	public void Resume()
	{
		movementsTimer.start();
		if (POOPY != null)
			POOPY.Resume();
		for (Ballon ballon : ballons) { ballon.Resume(); }
		for (BreakableBloc breakableBloc : breakableBlocs) { breakableBloc.Resume(); }
		for (MovingBloc movingBloc : movingBlocs) { movingBloc.Resume(); }
		for (AnimatedSolidBloc bloc : blocs) { bloc.Resume(); }
		for (TapisRoulant tapisRoulant : tapisRoulants) { tapisRoulant.Resume(); }
		vieDisplayer.setText(new String("Vies : "+vie));
		temps.setText(new String("Remaining Time: " + seconde));
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
	    		int [] idParam = SeparateIdParam(map[i][j]);
	    		map[i][j] = idParam[0];
	    		
	    		int id = idParam[0];
	    		int param = idParam[1];
	    		
	    		switch(ObjectType.typeOfInt(id))
	    		{
	    		case BALLON:
	    			AddBallon(new Ballon(i,j, param, !synchronizedMovements));
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
	    		//On recupere les parametres de l'objet s'il y en a

	    		int [] idParam = SeparateIdParam(map[i][j]);
	    		map[i][j] = idParam[0];
	    		
	    		int id = idParam[0];
	    		int param = idParam[1];
	    		
	    		switch(ObjectType.typeOfInt(id))
	    		{
	    		case BREAKABLEBLOC:
	    			breakableBlocs.add(new BreakableBloc(i,j));
	    			this.add(breakableBlocs.get(breakableBlocs.size()-1));
	    			break;
	    		case PIEGE:
	    			this.add(new Piege(i,j));
	    			if(!nonSolidObjects.contains(id))
	    				nonSolidObjects.add(id);
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
	    			this.add(new Apparition(i,j));
	    			break;
	    		case TAPISROULANT:
	    			tapisRoulants.add(new TapisRoulant(i, j, ((param == 1)?Direction.NORTH:((param == 2)?Direction.SOUTH:((param == 3)?Direction.EAST:Direction.WEST)))));
	    			this.add(tapisRoulants.get(tapisRoulants.size()-1));
	    			if(!nonSolidObjects.contains(id))
	    				nonSolidObjects.add(id);
	    			break;
	    		case OISEAU:
	    			oiseaux.add(new Oiseau(i,j));
	    			this.add(oiseaux.get(oiseaux.size()-1));
	    			if(!nonSolidObjects.contains(id))
	    				nonSolidObjects.add(id);
	    			break;
				default:
					break;
	    		}
	    	}
	    }		
	}
	
	public int[] SeparateIdParam(int id)
	{
		int param = 0;
		if (id > 9)
		{
			int div = 10;
			
			while(id/div > 0)
				div*=10;
			div/=10;
			
			param = id - ((int)(id/div))*10;
			id = (int)(id/div);
		}
		int[] idParam = {id, param};
		return idParam;
	}
	
	public void AddBallon(Ballon b)
	{
		ballons.add(b);
		this.add(b);
	}
	
	//Calculs effectues sur la frequence globalVar.CalculusFrequency
	//Return true si on doit tout kill
	private boolean movementsTimerTrigger() 
	{
		if (CollisionsSnoopy()) //Si return true ca veut dire c'est la mort
			return globalVar.resetLevelWhenLosingLife;
		
		ExecuteKeys(); //Si une touche a ete appuyee et est donc en attente on l'execute
		
		//Contient les collisions donc a lance meme si les mouvements ne sont pas synchronisees (que la balle bouge sur son propre timer)
		//return true si poopy touchï¿½ donc vie perdu donc retour a 0
		if (MouvementBallons(true))
			return globalVar.resetLevelWhenLosingLife;
		
		if (synchronizedMovements)
		{
			POOPY.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			for (MovingBloc movingBloc : movingBlocs) {
				movingBloc.MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			}
		}
		
		if (globalVar.movingBlocMoveOnce)
		{
			//Passage d'un movingbloc en solidbloc après son déplacement
			MovingBloc m = null;
			for (MovingBloc movingBloc : movingBlocs)
			{
				if (movingBloc.moved && !movingBloc.IsMoving())
				{
					map[movingBloc.xInMap][movingBloc.yInMap] = ObjectType.mapIdOf(ObjectType.SOLIDBLOC);
					blocs.add(new AnimatedSolidBloc(movingBloc.xInMap, movingBloc.yInMap));
					this.add(blocs.get(blocs.size()-1));
					m = movingBloc;
				}
			}
			if (m!=null) {
				this.remove(m);
				movingBlocs.remove(m);
			}
		}
		
			
		
		return false;
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
		if(o.NextCaseX(d) < 0 || o.NextCaseX(d) >= globalVar.nbTilesHorizontally || o.NextCaseY(d) < 0 || o.NextCaseY(d) >= globalVar.nbTilesVertically)
			return false;
		return (o.CanMove(d) && nonSolidObjects.contains(map[o.NextCaseX(d)][o.NextCaseY(d)]));
	}
	
	private boolean CollisionsSnoopy()
	{
		//Les collisions de snoopy ne sont triggered que quand il a finit son dï¿½placement sur la case
		if (POOPY.IsMoving())
			return false;
		
		  ///////////////
		 //  OISEAUX  //
		///////////////
		
		Oiseau catchedOiseau = null;

		//On regarde tous les oiseaux pour voir si snoopy ne se trouverai pas sur la case de l'un deux
		for (Oiseau oiseau : oiseaux) {
			if (((Objet) POOPY).SameTileAs((Objet)oiseau)) catchedOiseau = oiseau;
		}
		
		//Si il en a attrape un, on le retire de la map, des objets de la fenetre et de la liste ne contenant que les oiseaux
		if (catchedOiseau != null)
		{
			map[catchedOiseau.xInMap][catchedOiseau.yInMap] = 0;
			this.remove(catchedOiseau);
			oiseaux.remove(catchedOiseau);
			POOPY.RefreshSprite();
			
			//Si la liste est vide ca veut dire que snoopy a attrape tous les oiseaux et qu'il a fini le niveau
			if(oiseaux.isEmpty())
			{
				System.out.println("Et c'est la wiiin");
				win();
				return true;
			}
		}
		
		  /////////////////////
		 //  TAPISROULANTS  //
		/////////////////////
		
		CollisionsTapis(POOPY);
		
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
			
			return vieloose();
		}
		
		return false;
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
	
	private boolean MouvementBallons(boolean checkCollisions) 
	{
		for(int i = 0; i < ballons.size(); i++)
		{
			if (synchronizedMovements)
				ballons.get(i).MoveTowardsTarget((double)1/globalVar.CalculusFrequency);
			
			if (checkCollisions)
				for(int y=0; y < blocs.size(); y++)
					if (CollisionsBallon(ballons.get(i)))
						return true;
		}
		return false;
	}

	private boolean CollisionsBallon(Ballon b) 
	{
		for(int y = 0; y < blocs.size(); y++)
			b.hitboxslow(blocs.get(y), true);
		for(int y = 0; y < movingBlocs.size(); y++)
			b.hitboxslow(movingBlocs.get(y), true);

		if(b.hitboxslow(POOPY, false) && !POOPY.immune)
		{
			System.out.println("Et c'est la loooose");
		
			POOPY.StartImmunity();
			
			return vieloose();
		}
		
		return false;
	}
	
	private void CollisionsTapis(AnimatedObject o)
	{
		if (map[o.xInMap][o.yInMap] == ObjectType.mapIdOf(ObjectType.TAPISROULANT) && !o.SpeedModified())
		{
			for (TapisRoulant tapisRoulant : tapisRoulants) {
				if (tapisRoulant.xInMap == o.xInMap && tapisRoulant.yInMap == o.yInMap)
				{
					if (nonSolidObjects.contains(map[o.NextCaseX(tapisRoulant.orientation)][o.NextCaseY(tapisRoulant.orientation)]))
					{
						o.Move(tapisRoulant.orientation);
						o.IncreaseSpeed(tapisRoulant.orientation, 1.5);
					}
				}
			}
		}
		else if (o.SpeedModified()) o.ResetSpeed();
	}
	
	public int getseconde()
	{
		return seconde;
	}
	
	public int getvie()
	{
		return vie;
	}
	
	protected void CallSavePartie() {
		StopAll();
		try {
			partie.SavePartie();
			KillAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void SaveThis(String _namePartie) throws FileNotFoundException, UnsupportedEncodingException
	{
		//ENTREZ NOM PARTIE SI NON EXISTANT
		String fileName = name + "P" + _namePartie;
		PrintWriter saveFile = new PrintWriter("./Maps/" + fileName + ".txt", "UTF-8");
		
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

	public void setSeconde(int timeLeft) {
		seconde = timeLeft;
	}

	public void setVies(int vies) {
		vie = vies;
	}
}

	
