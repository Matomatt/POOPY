package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

//import Pause;
import Settings.*;
import Data.*;
import Engine.Objets.*;
import Menus.SaveFichier;
import Utilitaires.*;

public class Niveau {
	protected String name;
	private int[][] map = new int[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];
	private Objet[][] mapObjets = new Objet[globalVar.nbTilesHorizontally][globalVar.nbTilesVertically];

	private Snoopy POOPY;
	private List<Ballon> ballons = new ArrayList<Ballon>();
	//private List<Integer> nonSolidObjects = new ArrayList<Integer>(); //Liste des objets qu'il est possible de traverser

	private boolean busy = false;
	boolean ended = false;
	private int vie;
	private int seconde = 60;

	private Timer calculationsTimer;
	private boolean synchronizedCalculations = true;
	private long lastTrigger = System.currentTimeMillis();
	
	private Partie partie;

	// Initialisation du niveau 
	public Niveau(String _name, Partie p, boolean loadEnCours) throws IOException  /// Rajouter partie au constructeur
	{
		partie = p;
		name = _name;

		System.out.println("New level : " + name);

		LoadObjects(MapDataManager.LoadMap(name+".txt"));
		
		if (loadEnCours)
		{
			List<Ballon> _ballons = SaveManager.LoadSaveNiveau(name);
			for (Ballon b : _ballons) AddBallon(b);

			if (POOPY == null)
			{
				POOPY = SaveManager.LoadSaveSnoopy(name);
				if (POOPY != null) POOPY.setSelfMoved(!synchronizedCalculations);
			}
		}
		
		if(this.name.contains("P")) name = name.split("P")[0];

		//Synchronized Movements, gardez if !synchronized pour les collisions
		calculationsTimer = new Timer(1000/globalVar.CalculusFrequency, new ActionListener() { public void actionPerformed(ActionEvent arg0)
		{ 
			calculationsTrigger(); setBusy();
		} });

		this.StopAll();
	}

	// Est appele a l'initialisation avant le press start
	private boolean PreStart() 
	{
		System.out.println("Let's a go ! ("+name+")");
		if (POOPY == null)
		{
			new ErrorMessage("Ce niveau ne contient pas Snoopy, impossible d'y jouer, retour au menu...");
			return false;
		}

		vie = partie.vies;

		return true;
	}

	public boolean Start(final boolean waitForIt)
	{
		if (!PreStart())
			return false;

		if (globalVar.waitForSpaceWhenStartingLevel)
			partie.time.pPressed();
		
		return true;
	}

	// est appelé lors du press de la barre espace, permet au niveau de commencé 
	public void StartAfterWait(boolean waitedForIt)
	{
		System.out.println("Start after wait");
		if (waitedForIt)
			partie.time.pPressed();
		this.Resume();
		POOPY.StartImmunity();
	}

	// Sers a actualisé le timer et a enlevé une vie a snoopy si celui ci descend en dessous de 0
	public void timergestion()
	{
		//temps.setText(new String("Remaining Time: " + seconde));
		if ((seconde-=1) <= 0)
			vieloose();
	}

	// Sers a terminer le niveau en cas de game over
	private boolean vieloose() //return true si plus de vie du tout
	{
		//vieDisplayer.setText(new String("Vies : "+vie));

		if ((vie-=1) <= 0)
		{
			KillAll();
			ended = true;
			partie.perdu();
			return true;
		}
		else if (globalVar.resetLevelWhenLosingLife) {
			ended = true;
			partie.resetNiveau();
		}

		return globalVar.resetLevelWhenLosingLife;
	}

	//change de niveau et modifie le score
	public void win()
	{
		KillAll();
		ended = true;
		partie.addscore(seconde*100);
		partie.next(false);//false because is not reset
	}

	// Est appelé dans l'initialisation 
	void LoadObjects(int[][] _map) 
	{
		for (int j=0; j<globalVar.nbTilesVertically; j++)
		{
			for (int i=0; i<globalVar.nbTilesHorizontally; i++)
			{
				//On recupere les parametres de l'objet s'il y en a
				int [] idParam = SeparateIdParam(_map[i][j]);

				switch(ObjectType.typeOfInt(idParam[0]))
				{
					case BALLON: AddBallon(new Ballon(i,j, idParam[1], !synchronizedCalculations)); break;
	
					case SNOOPY: POOPY = new Snoopy(i, j, !synchronizedCalculations); AddObjet(new Vide(i, j)); break;
	
					case VIDE: AddObjet(new Vide(i, j)); break;
	
					case BREAKABLEBLOC: AddObjet(new BreakableBloc(i,j)); break;
	
					case PIEGE: AddObjet(new Piege(i,j)); break;
	
					case MOVINGBLOC: AddObjet(new MovingBloc(i, j, !synchronizedCalculations)); break;
	
					case SOLIDBLOC: AddObjet(new AnimatedSolidBloc(i,j)); break;
	
					case APPARITION: AddObjet(new Apparition(i,j)); break;
	
					case TAPISROULANT: AddObjet(new TapisRoulant(i, j, Direction.directionOfId(idParam[1]))); break;
	
					case OISEAU: AddObjet(new Oiseau(i,j)); break;
	
					default: break;
				}
			}
		}		
	}
	
	// Lis le parametre associé au bloc
	public int[] SeparateIdParam(int id)
	{
		
		int[] idParam = {id, 0};

		if (id > 9)
		{
			int div = 10;

			while(id/div > 9)
				div*=10;

			idParam[1] = id - ((int)(id/div))*10;
			idParam[0] = (int)(id/div);
		}

		return idParam;
	}

	private void AddObjet(Objet o) {
		System.out.println("Adding " + ObjectType.nameOf(o.Type()));
		map[o.xInMap][o.yInMap] = ObjectType.mapIdOf(o.Type());
		mapObjets[o.xInMap][o.yInMap] = o;
	}

	private void AddBallon(Ballon b)
	{
		ballons.add(b);
		AddObjet(new Vide(b.xInMap, b.yInMap));
	}

	private void RemoveObjet(Objet o) {
		AddObjet(new Vide(o.xInMap, o.yInMap));
	}

	private Objet NextObjet(Objet o, Direction d)
	{
		if (mapObjets[o.NextCaseX(d)][o.NextCaseY(d)] != null)
			return mapObjets[o.NextCaseX(d)][o.NextCaseY(d)];
		return new Objet(-1, -1, ObjectType.none);
	}


	//Calculs effectues sur la frequence globalVar.CalculusFrequency
	//Return true si on doit tout kill
	private boolean calculationsTrigger()
	{
		while (isBusy()) { try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
		
		busy = true;
		
		if (CollisionsSnoopy()) //Si return true ca veut dire c'est la mort
			return true;

		//Contient les collisions donc a lance meme si les mouvements ne sont pas synchronisees (que la balle bouge sur son propre timer)
		//return true si poopy touch� donc vie perdu donc retour a 0
		if (MouvementBallons(true))
			return globalVar.resetLevelWhenLosingLife;

		if (synchronizedCalculations)
		{
			POOPY.doCalculations((double)(System.currentTimeMillis()-lastTrigger)/1000);

			for (Objet[] line : mapObjets) {
				for (Objet objet : line) objet.doCalculations((double)(System.currentTimeMillis()-lastTrigger)/1000);
			}
			
			//System.out.println((double)(System.currentTimeMillis()-lastTrigger));
			lastTrigger = System.currentTimeMillis();
		}
		

		boolean oiseauxTousAttrape = true;
		for (Objet[] line : mapObjets) {
			for (Objet objet : line)
			{
				boolean succesfull = false;

				switch (objet.actionReturned())
				{
					case CHANGEBLOCINMAP:
						AddObjet(new AnimatedSolidBloc(objet.xInMap, objet.yInMap));
						succesfull = true;
						break;
					case REMOVEIT:
						RemoveObjet(objet);
						break;
					default: break;
				}

				if (objet != null) objet.setReturnedActionSuccess(succesfull);

				if (objet.Type() == ObjectType.OISEAU) oiseauxTousAttrape = false;
			}
		}

		if (oiseauxTousAttrape)
		{
			System.out.println("Et c'est la wiiin");
			win();
			return true;
		}

		return false;
	}

	public boolean ExecuteKey(KeyType key)
	{
		while (isBusy()) { try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
		
		busy = true;
		if (CollisionsSnoopy())
			return false;
		
		if (!POOPY.IsMoving() && !POOPY.SpeedModified())
		{
			switch (key) {
				case UP: return MoveObject(POOPY,Direction.NORTH);
	
				case LEFT: return MoveObject(POOPY,Direction.WEST);
	
				case DOWN: return MoveObject(POOPY,Direction.SOUTH);
	
				case RIGHT: return MoveObject(POOPY,Direction.EAST);
	
				case SPACE: return SpacePressed();
	
				default: return false;
			}
		}
		return false;
	}
	
	//Move an object to the desired direction, returns true if the object moved or changed direction succesfully
	public boolean MoveObject(Objet o, Direction d)
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
		if (OutOfBound(o.NextCaseX(d), o.NextCaseY(d)))
			return false;

		if (NextObjet(o,d).Type() == ObjectType.APPARITION)
		{
			if(!((Apparition) NextObjet(o, d)).visible)
				return false;
		}

		return (o.CanMove(d) && !NextObjet(o, d).isSolid());
	}

	private boolean CollisionsSnoopy()
	{
		//Les collisions de snoopy ne sont triggered que quand il a finit son deplacement sur la case
		if (POOPY.IsMoving())
			return false;

		 //  OISEAUX  //
		//Si snoopy est sur une case oiseau on le retire de la map, des objets de la fenetre et de la liste ne contenant que les oiseaux
		if (map[POOPY.xInMap][POOPY.yInMap] == ObjectType.mapIdOf(ObjectType.OISEAU))
			RemoveObjet(mapObjets[POOPY.xInMap][POOPY.yInMap]);

		 //  PIEGES  //
		if (map[POOPY.xInMap][POOPY.yInMap] == ObjectType.mapIdOf(ObjectType.PIEGE))
		{
			vieloose();
			return true;
		}
		
		 //  TAPISROULANTS  //
		CollisionsTapis(POOPY);

		return false;
	}

	// Gere la barre Espace
	public boolean SpacePressed()
	{
		//System.out.println("Space pressed");
		int blocX = POOPY.NextCaseX(POOPY.orientation);
		int blocY = POOPY.NextCaseY(POOPY.orientation);

		if (OutOfBound(blocX, blocY))
			return false;

		switch (ObjectType.typeOfInt(map[blocX][blocY]))
		{
		case MOVINGBLOC:
			MovingBloc bloc = (MovingBloc) mapObjets[blocX][blocY];
			if (MoveObject(bloc, POOPY.orientation))
			{
				AddObjet(bloc);
				AddObjet(new Vide(blocX, blocY));
				return true;
			}
			break;
		case BREAKABLEBLOC:
			BreakableBloc breakableBloc = (BreakableBloc) mapObjets[blocX][blocY];
			if (breakableBloc.stopAnimation)
				breakableBloc.Break();
			break;
		default:
			break;
		}

		return false;
	}

	private boolean OutOfBound(int x, int y)
	{
		return (x < 0 || x >= globalVar.nbTilesHorizontally || y < 0 || y >= globalVar.nbTilesVertically);
	}

	// Actualise les mouvements du ballons fait le calcul de collisions 
	private boolean MouvementBallons(boolean checkCollisions) 
	{
		boolean needReset = false;
		for (Ballon ballon : ballons)
		{
			if (synchronizedCalculations)
				ballon.doCalculations((double)1/globalVar.CalculusFrequency);

			if (checkCollisions)
				needReset = CollisionsBallon(ballon);

			if (needReset)
				break;
		}
		return needReset;
	}
	
	// Est appelé dnas mouvement pour  check les hitbox
	private boolean CollisionsBallon(Ballon b) 
	{
		for (Objet[] line : mapObjets) {
			for (Objet objet : line) 
				if (objet.isSolid())
					b.hitboxslow(objet, true);
		}

		if(b.hitboxslow(POOPY, false) && !POOPY.immune)
		{
			System.out.println("Et c'est la loooose");

			POOPY.StartImmunity();

			return vieloose();
		}

		return false;
	}

	// Gere le deplacement forc� et l'acc�ration des objets quand ils passent sur un tapis roulant
	private void CollisionsTapis(AnimatedObject o)
	{
		if (map[o.xInMap][o.yInMap] == ObjectType.mapIdOf(ObjectType.TAPISROULANT) && !o.SpeedModified() && !POOPY.IsMoving())
		{
			TapisRoulant tapisRoulant = (TapisRoulant) mapObjets[o.xInMap][o.yInMap];

			if (!NextObjet(POOPY, tapisRoulant.orientation).isSolid())
			{
				if (!o.Move(tapisRoulant.orientation))
				{
					if (o.Move(tapisRoulant.orientation))
						o.IncreaseSpeed(tapisRoulant.orientation, 1.5);
				}
				else o.IncreaseSpeed(tapisRoulant.orientation, 1.5);
				System.out.println("go " + POOPY.IsMoving());
			}
		}
		else if (o.SpeedModified()) { o.ResetSpeed(); System.out.println("Reset"); } //The condition if Snoopy stopped moving has already been taken care of when calling this function
	}

	// lance la pause
	public void pause()  // Timer to stop 
	{
		partie.pPressed();
	}

	// Arete temporairement les timer est appeler dans partie ppressed 
	public void StopAll()
	{
		if (calculationsTimer != null)
			calculationsTimer.stop();
		if (POOPY != null)
			POOPY.Stop();

		for (Ballon ballon : ballons) { ballon.Stop(); }

		for (Objet[] line : mapObjets) {
			for (Objet objet : line)
			{
				if (objet != null)
					objet.Stop();
				else {
					System.out.println("null");
				}
			}
		}
	}

	// Relance les timers est aussi apelé dans partie ppressed
	public void Resume()
	{
		if (calculationsTimer != null)
			calculationsTimer.start();
		if (POOPY != null)
			POOPY.Resume();

		for (Objet[] line : mapObjets) {
			for (Objet objet : line)
				objet.Resume();
		}

		for (Ballon ballon : ballons) { ballon.Resume(); }
	}

	// Permet d'arreter les timers
	protected void KillAll()
	{
		calculationsTimer.removeActionListener(calculationsTimer.getActionListeners()[0]);
		calculationsTimer.stop();

		if (POOPY != null)
			POOPY.Kill();
		POOPY = null;

		for (Objet[] line : mapObjets) {
			for (Objet objet : line)
				objet.Kill();
		}
	}

	// Sert a la sauvegarde
	public void CallSavePartie() 
	{
		ended = true;
		StopAll();
		
		try {
			if (globalVar.enterNameWhenSavingWithS)
				new SaveFichier(partie);
			else
				partie.SavePartie();
		} 
		catch (FileNotFoundException e) { new ErrorMessage("Erreur de sauvegarde...\n" + e.getLocalizedMessage()); } 
		catch (UnsupportedEncodingException e) { new ErrorMessage("Erreur de sauvegarde...\n" + e.getLocalizedMessage()); }
	}

	protected void SaveThis(String _namePartie) throws FileNotFoundException, UnsupportedEncodingException
	{
		ended = true;
		
		boolean printSnoopyInMap = !(POOPY.IsMoving() || map[POOPY.xInMap][POOPY.yInMap] != 0);
		
		String fileName = name + "P" + _namePartie;
		PrintWriter saveFile = new PrintWriter("./Maps/" + fileName + ".txt", "UTF-8");

		System.out.println("Saving...");

		saveFile.println(globalVar.nbTilesHorizontally + " " + globalVar.nbTilesVertically);

		for (int j=0; j<globalVar.nbTilesVertically; j++)
		{
			String lineToPrint = "";
			for (int i=0; i<globalVar.nbTilesHorizontally; i++)
				lineToPrint+= ((printSnoopyInMap && POOPY.IsHere(i, j))?"8":((mapObjets[i][j] != null)?mapObjets[i][j].SavingInfo():"0")) + ((i >= globalVar.nbTilesHorizontally - 1)?"":" ");

			saveFile.println(lineToPrint);
		}

		if (!printSnoopyInMap)
		{
			saveFile.println("Snoopy");
			saveFile.println(POOPY.SavingInfo());
		}

		if (!ballons.isEmpty())
		{
			saveFile.println("Ballons");
			for (Ballon ballon : ballons)
				saveFile.println(ballon.SavingInfo());
		}

		saveFile.close();
	}

	public int getseconde()
	{
		return seconde;
	}

	public int getvie()
	{
		return vie;
	}

	public void setSeconde(int timeLeft) {
		seconde = timeLeft;
		//temps.setText(new String("Remaining Time: " + seconde));
	}

	public void setVies(int vies) {
		vie = vies;
		//vieDisplayer.setText(new String("Vies : "+vie));
	}
	
	public ArrayList<DrawableObjet> GetDrawableBallons()
	{
		ArrayList<DrawableObjet> list = new ArrayList<DrawableObjet>();
		
		for (Ballon ballon : ballons) {
			list.add(new DrawableObjet(ballon));
		}
		
		return list;
	}
	
	public DrawableObjet getDrawableBallon(int i)
	{
		if (i>=0 && i<ballons.size())
			return new DrawableObjet(ballons.get(i));
		return null;
	}
	
	public int getBallonX(int i) {
		if (i>=0 && i<ballons.size())
			return (int) ballons.get(i).getX();
		return -1;
	}
	
	public int getBallonY(int i) {
		if (i>=0 && i<ballons.size())
			return (int) ballons.get(i).getY();
		return -1;
	}
	
	public DrawableObjet getDrawableSnoopy()
	{
		return new DrawableObjet(POOPY);
	}
	
	public DrawableObjet getDrawable(Objet o)
	{
		return new DrawableObjet(o);
	}
	
	public ImageIcon getSpriteToDraw(int x, int y)
	{
		if (!OutOfBound(x, y))
			return mapObjets[x][y].getSprite();
		return null;
	}

	public DrawableObjet getObjetToDraw(int x, int y) {
		if (!OutOfBound(x, y))
			return new DrawableObjet(mapObjets[x][y]);
		return null;
	}

	public boolean isEnded() {
		return ended;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy() {
		this.busy = false;
	}

	
}


/*
private List<Oiseau> oiseaux = new ArrayList<Oiseau>();
private List<MovingBloc> movingBlocs = new ArrayList<MovingBloc>();
private List<Apparition> apparitions = new ArrayList<Apparition>();
private List<BreakableBloc> breakableBlocs = new ArrayList<BreakableBloc>();
private List<AnimatedSolidBloc> blocs = new ArrayList<AnimatedSolidBloc>();
private List<TapisRoulant> tapisRoulants = new ArrayList<TapisRoulant>();
 */

/*
private JLabelText pressSpaceLabel = new JLabelText("Press space to start...", 600, (int)(globalVar.tileHeight/1.5), Color.WHITE);

private JLabelText vieDisplayer;

private JLabelText temps;

private KeysPressedList keysPressedList = new KeysPressedList();
 */

/* Niveau()
this.setLayout(null);
this.setBackground(new Color(213,210,204));

pressSpaceLabel.setVisible(false);
add(pressSpaceLabel);

temps = new JLabelText("Remaining Time: " + seconde, 300, globalVar.tileHeight/2, Color.BLUE);

vieDisplayer=new JLabelText("Vies : "+vie, 300, globalVar.tileHeight/2, Color.BLUE);

this.add(POOPY);

System.out.println("Nombre d'objets dans le niveau : " + this.getComponentCount());

this.setVisible(false);
 */

/* PreStart()
temps.setLocation(globalVar.tileWidth/6, globalVar.tileHeight/6);
vieDisplayer.setLocation(globalVar.tileWidth*10, globalVar.tileHeight/6);

if (temps.getText() == null)
	temps.setText(new String("Remaining Time: " + seconde));
partie.add(temps);
partie.add(vieDisplayer);

this.setLocation(0, globalVar.tileHeight);
this.setVisible(true);
 */

/* Start()
if (waitForIt)
{
	partie.time.pPressed();
	this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false),"Start");
	this.getActionMap().put("Start", new AbstractAction() { public void actionPerformed(ActionEvent e) { StartAfterWait(waitForIt); } });

	pressSpaceLabel.setLocation(this.getWidth()/3, this.getHeight()/2-pressSpaceLabel.getHeight()/2);
	pressSpaceLabel.setVisible(true);
}
else
	StartAfterWait(waitForIt);

this.validate();
 */

/* StartAfterWait()
if (waitedForIt)
{
	pressSpaceLabel.setVisible(false);
	this.remove(pressSpaceLabel);
	pressSpaceLabel=null;
	this.getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false));
	//this.getActionMap().remove;
	partie.time.pPressed();
}


AddKeyBindings();
 */

/*
// Sers a l'initialisation de la classe Niveau 
	@SuppressWarnings("serial")
	public void AddKeyBindings()
	{
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
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false),"AutoWin");
		this.getActionMap().put("AutoWin", new AbstractAction() { public void actionPerformed(ActionEvent e) { win(); } });
	}
 */
