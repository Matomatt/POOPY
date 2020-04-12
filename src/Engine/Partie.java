package Engine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Data.StringManager;
import Menus.GameOver;
import Menus.Pause;
import Menus.WinPage;
import Settings.globalVar;
import Utilitaires.ErrorMessage;
import View.Fenetre;
import View.ViewNiveau;

import java.util.concurrent.TimeUnit;

// La classe partie contient le niveau et la pause. 
public class Partie extends JPanel {
	private static final long serialVersionUID = -1207758538944896774L;
	
	private String name = null;
	
	private Pause pause;
	private Fenetre fenetre;
	Time time;
	
	private int score = 0;
	protected int vies = globalVar.vieAuDepart;
	private int timeLeft = globalVar.timerAuDepart;
	private int unlockedLevels = globalVar.niveauAuDepart;
	
	public ArrayList<Niveau> niveaux = new ArrayList<Niveau>();
	
	// Chargement d'une Partie Sauvegardé
	public Partie(String partieToLoad, Fenetre _fenetre) throws IOException
	{
		name = partieToLoad;
			
		fenetre=_fenetre;
		
		if (name.isEmpty())
			AddFirstLevel(globalVar.niveauAuDepart);
		else 
			LoadPartie(name);

		Init();// charge les caractéristique graphique et initialise les variable autre que niveau 
	}
	
	// Chargement d'une parite au niveau indiqué 
	public Partie(Fenetre _fenetre, int numlv) throws IOException
	{
		fenetre = _fenetre;
		
		AddFirstLevel(numlv);
		
		Init();
	}
	
	//Ajoute le premier niveau a lancer pour une nouvelle partie (depuis mdp ou depuis "nouvelle partie")
	private void AddFirstLevel(int n) throws IOException
	{
		niveaux.add( new Niveau("level"+n, this, false));
		unlockedLevels = n;
		vies = globalVar.vieAuDepart;
		timeLeft = globalVar.timerAuDepart;
	}
	
	// charge les caractéristique graphique et initialise les variable autre que niveau 
	private void Init()
	{
		time = new Time(niveaux.get(0));
		
		//niveaux.get(0).setFocusable(true);
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);
		
		this.add((pause = new Pause(this)));
		
		RemplirNiveau();
		
		this.setVisible(true);
		this.validate();
		
		niveaux.get(0).setSeconde(timeLeft);
		niveaux.get(0).setVies(vies);
		
		this.add(new ViewNiveau(niveaux.get(0)));
		
		if (niveaux.get(0).Start(globalVar.waitForSpaceWhenStartingLevel))
		{
			time.pPressed();
			this.revalidate();
		}
			
		else {
			//this.remove(niveaux.get(0));
			menu();
		}
	}
	public int getScore()
	{
		return score;
	}
	//Initialis l'attribut niveau de la classe éponyme 
	private void RemplirNiveau()
	{
		int totallv = unlockedLevels;
		File f = new File("./Maps/" + "level"+ (totallv+1) + ".txt");
		
		while(f.exists() && !f.isDirectory())
		{
			totallv += 1;
			try { niveaux.add(new Niveau(new String("level"+ totallv), this, false)); } 
			catch (IOException e) { new ErrorMessage("Impossible d'ajouter le niveau "+totallv+"...\n" + e.getLocalizedMessage()); }
			
			f = new File("./Maps/" + "level"+ (totallv+1) + ".txt");
		}
		
		System.out.println("Nombre total de niveaux : " + totallv);
	}
	
	// est utilisé pour consérvé le nombre de vie et relancé le niveau quand snoopy perd un PV 
	void resetNiveau()
	{
		String levelToRestartName = niveaux.get(0).name;
		niveaux.get(0).KillAll();
		
		try {
			niveaux.add(1, new Niveau(levelToRestartName, this, false));
		} catch (IOException e) {
			new ErrorMessage("Erreur ! Impossible de recommencer le niveau...\n"+ e.getLocalizedMessage());
		}
		
		next(true);
	}
	
	//Passe au niveau suivant dans la liste des niveaux
	//isReset indique si on appelle cette fonction pour relancer le meme niveau apr�s une mort ou si on passe a un nouveau niveau suivant
	public void next(boolean isReset) 
	{
		vies = niveaux.get(0).getvie() + ((isReset)?0:1);
		this.removeAll();
		this.add(pause);
		niveaux.remove(0);
		time.cancel();
		
		boolean troubleStarting = false;
		
		if(!niveaux.isEmpty())
		{
			do {
				time=new Time(niveaux.get(0));
				unlockedLevels += ((isReset)?0:1);
				niveaux.get(0).setVies(vies);
				niveaux.get(0).setSeconde(globalVar.timerAuDepart);
				
				this.add(new ViewNiveau(niveaux.get(0)));
				
				troubleStarting = false;
				time.pPressed();
				if (!niveaux.get(0).Start(globalVar.waitForSpaceWhenStartingLevel))
				{
					this.removeAll();
					niveaux.remove(0);
					troubleStarting = true;
				}
				
				this.revalidate();
				this.update(getGraphics());
				
			} while (troubleStarting && !niveaux.isEmpty());
			
		}
		
		if (niveaux.isEmpty())
		{
			niveaux=null;
			this.removeAll();
			
			this.add(new WinPage(score, this.getWidth(), this.getHeight(), unlockedLevels));
			
			this.revalidate();
			this.update(getGraphics());
			
			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				new ErrorMessage("Alors y'a un probleme avec la pause de 3 secondes mais ce n'est pas de mon ressort...\n" + e.getLocalizedMessage());
			}
			
		    this.removeAll();
			menu();
		}
	}
	// Deconstruit la classe ses descendants ainsi que les timer, appel la fonction menu de fenetre
	public void menu()
	{
		time.cancel();
		pause=null;
		time=null;
		
		fenetre.remove(this);
		
		fenetre.menu();
	}
	
	// Affiche le game Over en cas de défaite
	protected void perdu()
	{
		time.cancel();
		niveaux.removeAll(niveaux);
		niveaux=null;
		this.removeAll();
		
		this.add(new GameOver(score, this.getWidth(), this.getHeight(), unlockedLevels));
		
		this.revalidate();
		this.update(getGraphics());
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			new ErrorMessage("On fait beaucoup de pause de 3 secondes je trouve...\n" + e.getLocalizedMessage());
		}
		
	    this.removeAll();
	    
		menu();
	}
	
	//Garde le score a jour a la fin de chaque niveau en cas de victoire
	protected void addscore(int lvscore)
	{
		score+=lvscore;
	}
	
	javax.swing.Timer cooldownTimer = new javax.swing.Timer( 50, new ActionListener() { public void actionPerformed(ActionEvent arg0) { pPressedNext(); } });
	
	// Gere la pause
	public void pPressed()
	{
		if (!pause.isVisible())
		{
			niveaux.get(0).StopAll();
			cooldownTimer.restart();
		}
		else
		{
			pPressedNext();
			niveaux.get(0).Resume();
		}
		
	}
	
	// est utilisé pour arreté le timer dans pause lors du premier appuie
	public void pPressedNext()
	{
		cooldownTimer.stop();
		pause.pPressed();
		time.pPressed();
		
		pause.grabFocus();
		this.revalidate();
	}
	
	// Est utilisé dans le constructeur pour chargé une partie
	private void LoadPartie(String fileToLoad) throws IOException
	{
		File partieData = new File("./Saves/" + fileToLoad + ".txt");
		
		BufferedReader br = new BufferedReader(new FileReader(partieData));
		
		String niveauEnCours = br.readLine();
		boolean enCours = Boolean.parseBoolean(br.readLine());
		Niveau n = new Niveau(niveauEnCours, this, enCours);
		
		List<Integer> parsedLine = StringManager.ParseLineToInt(br.readLine());
		unlockedLevels = parsedLine.get(0);
		score = parsedLine.get(1);
		vies = parsedLine.get(2);
		timeLeft = parsedLine.get(3);
		
		niveaux.add(n);
		
		br.close();
	}
	
	// Permet la sauvegarde d'un fichier  
	public void SavePartie() throws FileNotFoundException, UnsupportedEncodingException
	{
		if (name == null)
			name = "default";
		else if (name.isEmpty())
			name = "default";
		niveaux.get(0).SaveThis(name);
		
		PrintWriter saveFile = new PrintWriter("./Saves/" + name + ".txt", "UTF-8");
		
		saveFile.println(niveaux.get(0).name+"P"+name);
		saveFile.println(true);
		saveFile.println(unlockedLevels + " " + score + " " + niveaux.get(0).getvie() + " " + niveaux.get(0).getseconde());
		saveFile.close();
		
		time.cancel();
		niveaux.get(0).KillAll();
		niveaux.removeAll(niveaux);
		niveaux=null;
		this.removeAll();
		
		System.out.println("Saved !");
		//fenetre.dispose();
		//System.exit(0);
	}
	
	public String getnom()
	{
		return name;
	}
	
	public void setnom(String b)
	{
		name=b;
	}
}
