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
import java.util.concurrent.TimeUnit;

public class Partie extends JPanel {
	private static final long serialVersionUID = -1207758538944896774L;
	
	private String name;
	

	private Pause pause;
	private Fenetre fenetre;
	Time time;
	
	private int score = 0;
	protected int vies = 3;
	private int timeLeft = 0;
	private int unlockedLevels = 1;
	
	protected ArrayList<Niveau> niveaux = new ArrayList<Niveau>();
	
	public Partie(String partieToLoad, Fenetre _fenetre) throws IOException
	{
		name = partieToLoad;
			
		fenetre=_fenetre;
	
		
		if (name.isEmpty())
		{
			niveaux.add( new Niveau("level1", this, false));
			unlockedLevels = 1;
		}
		else LoadPartie(name);

		Init();
	}
	
	public Partie(Fenetre fene, int numlv ) throws IOException
	{
		fenetre=fene;
		
		unlockedLevels = numlv;
		
		niveaux= new ArrayList<Niveau>();
		
		niveaux.add( new Niveau("level"+numlv, this, false));

		unlockedLevels = numlv;
		Init();
		
	}
	
	private void resetNiveau()
	{
		
	}
	
	private void Init()
	{
		time = new Time(niveaux.get(0));
		
		niveaux.get(0).setFocusable(true);
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);
		
		this.add((pause = new Pause(this)));
		
		this.add(niveaux.get(0)); //Le premier c'est toujours le nievau a reprendre quand on charge une partie (nouvelle ou pas, mdp ou pas)
		
		int totallv = unlockedLevels;
		File f = new File("./Maps/" + "level"+ (totallv+1) + ".txt");
		
		while(f.exists() && !f.isDirectory())
		{
			totallv += 1;
			try { niveaux.add(new Niveau(new String("level"+ totallv), this, false)); } 
			catch (IOException e) { e.printStackTrace(); }
			
			f = new File("./Maps/" + "level"+ (totallv+1) + ".txt");
		}
		
		System.out.println("Nombre total de niveaux : " + totallv);
		
		this.setVisible(true);
		this.validate();
		
		time.pPressed();
		niveaux.get(0).Start();
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
		remove(niveaux.get(0));
		niveaux.remove(0);
		time.cancel();
		
		boolean troubleStarting = false;
		
		if(!niveaux.isEmpty())
		{
			do {
				add(niveaux.get(0));
				
				time=new Time(niveaux.get(0));
				unlockedLevels+=1;
				
				this.revalidate();
				this.update(getGraphics());
				
				troubleStarting = false;
				time.pPressed();
				if (!niveaux.get(0).Start())
				{
					niveaux.remove(0);
					troubleStarting = true;
				}
				
			} while (troubleStarting && !niveaux.isEmpty());
			
		}
		
		if (niveaux.isEmpty())
		{
			niveaux=null;
			this.removeAll();
			this.update(getGraphics());
			
			this.add(new WinPage(score, this.getWidth(), this.getHeight(), unlockedLevels));
			
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
		
			System.out.println("gg tu as gagné voici ton score : "+score);
			//you win 
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
		Niveau n = new Niveau(niveauEnCours, this, enCours);
		
		List<Integer> parsedLine = StringManager.ParseLineToInt(br.readLine());
		unlockedLevels = parsedLine.get(0);
		score = parsedLine.get(1);
		vies = parsedLine.get(2);
		timeLeft = parsedLine.get(3);
		
		n.setSeconde(timeLeft);
		n.setVies(vies);
		niveaux.add(n);
		
		br.close();
	}
	
	protected void SavePartie() throws FileNotFoundException, UnsupportedEncodingException
	{
		if (name == null)
			name = "default";
		
		niveaux.get(0).SaveThis(name);
		
		PrintWriter saveFile = new PrintWriter("./Saves/" + name + ".txt", "UTF-8");
		
		saveFile.println(niveaux.get(0).name);
		saveFile.println(niveaux.get(0).ended);
		saveFile.println(unlockedLevels + " " + score + " " + niveaux.get(0).getvie() + " " + niveaux.get(0).getseconde());
		saveFile.close();
		
		time.cancel();
		niveaux.removeAll(niveaux);
		niveaux=null;
		this.removeAll();
		
		System.out.println("Saved !");
		fenetre.dispose();
		System.exit(0);
	}
	
	javax.swing.Timer cooldownTimer = new javax.swing.Timer( 50, new ActionListener() { public void actionPerformed(ActionEvent arg0) { pPressedNext(); } });
	
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
			
		
		
		//this.update(this.getGraphics());
	}
	
	public void pPressedNext()
	{
		cooldownTimer.stop();
		pause.pPressed();
		time.pPressed();
		
		pause.grabFocus();
		this.revalidate();
	}
}
