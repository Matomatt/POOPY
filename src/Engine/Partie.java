package Engine;
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
import Engine.Objets.Ballon;
import Engine.Objets.TapisRoulant;
import Utilitaires.Direction;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.Timer;

public class Partie extends JPanel {
	private static final long serialVersionUID = -1207758538944896774L;
	
	private String name;
	

	private Pause pause;
	private Fenetre fenetre;
	Time time;
	
	private int score = 0;
	protected int vies = 5;
	private int timeLeft = 0;
	private int unlockedLevels = 1;
	
	protected ArrayList<Niveau> niveaux = new ArrayList<Niveau>();
	
	public Partie(String partieToLoad, Fenetre _fenetre)
	{
		name = partieToLoad;
			
		fenetre=_fenetre;
	
		
		if (name.isEmpty())
		{
			try { niveaux.add( new Niveau("level2", this, false)); }
			catch (IOException e) { e.printStackTrace(); }
		}
		else {
			try {
				LoadPartie(name);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		time = new Time(niveaux.get(0));
		
		niveaux.get(0).setFocusable(true);
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);
		
		this.add((pause = new Pause(this)));
		
		this.add(niveaux.get(0));
		
		this.setVisible(true);
		this.validate();
	}
	public Partie(Fenetre fene, int numlv )
	{
		
		
		fenetre=fene;
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);

	
		unlockedLevels = numlv;
		
		niveaux= new ArrayList<Niveau>();
		try {	
			niveaux.add( new Niveau("level"+numlv, this,false));
		}
		 catch (IOException e) {
				e.printStackTrace();
			}
		niveaux.get(0).setFocusable(true);
	
		
		this.add(niveaux.get(0));
		
		time=new Time(niveaux.get(0));
		this.setVisible(true);
		
		this.validate();
		
	}
	
	protected void perdu()
	{
		time.cancel();
		niveaux.removeAll(niveaux);
		niveaux=null;
		this.removeAll();
		this.update(getGraphics());
		this.add(new GameOver(score, this.getWidth(), this.getHeight(), unlockedLevels));
		this.revalidate();
		this.update(getGraphics());
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
						e.printStackTrace();
		}
		this.update(getGraphics());
		niveaux=null;
	//	this.add(new GameOver(score,this.getWidth(),this.getHeight()));
		
	    this.removeAll();
		this.update(this.getGraphics());
		menu();
	}
	protected void addscore(int lvscore)
	{
		score+=lvscore;
	}
	
	public void next()
	{
		niveaux.get(0).getName();
	}
	
	protected void menu()
	{
		time.cancel();
		pause=null;
		time=null;
		//save avant ? 
		this.update(this.getGraphics());
		fenetre.remove(this);
		
		fenetre.menu();

	}
	private void LoadPartie(String fileToLoad) throws IOException
	{
		File partieData = new File("./Saves/" + fileToLoad + ".txt");
		
		BufferedReader br = new BufferedReader(new FileReader(partieData));
		
		String niveauEnCours = br.readLine();
		boolean enCours = Boolean.parseBoolean(br.readLine());
		niveaux.add(new Niveau(niveauEnCours, this, enCours));
		
		List<Integer> parsedLine = StringManager.ParseLineToInt(br.readLine());
		unlockedLevels = parsedLine.get(0);
		score = parsedLine.get(1);
		vies = parsedLine.get(2);
		timeLeft = parsedLine.get(3);
		
		br.close();
	}
	
	protected void SavePartie(String _name, String _level, boolean enCours) throws FileNotFoundException, UnsupportedEncodingException
	{
		name = _name;
		
		PrintWriter saveFile = new PrintWriter("./Saves/" + name + ".txt", "UTF-8");
		
		saveFile.println(_level);
		saveFile.println(enCours);
		saveFile.println(unlockedLevels + " " + score + " " + vies + " " + timeLeft);
		
		saveFile.close();
		
		System.out.println("Saved !");
	}
	public boolean pPressed()
	{
		pause.pPressed();
		time.pPressed();
		this.revalidate();
		return pause.isVisible();
	}
}
