package Engine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;
public class Partie extends JPanel {
	private static final long serialVersionUID = -1207758538944896774L;
	
	private String name;
	
	private Pause pause=new Pause(this);
	private Fenetre fenetre;
	Time time;
	
	private int score = 0;
	private int vies = 3;
	private int timerLeft = 0;
	protected ArrayList<Niveau> niveaux = new ArrayList<Niveau>();
	
	public Partie(String partieToLoad, Fenetre _fenetre)
	{
		name = partieToLoad;
		
		fenetre=_fenetre;
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);

	
		if (name.isEmpty())
		{
			try { niveaux.add( new Niveau("level2", this, false)); }
			catch (IOException e) { e.printStackTrace(); }
		}

		
		time=new Time(niveaux.get(0));
		niveaux.get(0).setFocusable(true);

		this.add(niveaux.get(0));
		
		this.setVisible(true);
		this.validate();
	}
	public Partie(Fenetre fene, int numlv )
	{
		
		
		fenetre=fene;
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);
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
		this.removeAll();
		this.add(new GameOver(score,this.getWidth(),this.getHeight()));
		System.out.println("crash?");
		niveaux=null;
		time=null;
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
		//save avant ? 
		fenetre.menu();

	}
	private void LoadPartie(String fileToLoad)
	{
		
	}
	
	private void SavePartie(String _name) throws FileNotFoundException, UnsupportedEncodingException
	{
		name = _name;
		
		PrintWriter saveFile = new PrintWriter("./Saves/" + name + ".txt", "UTF-8");
		
		saveFile.println(name);
	}
	public void pPressed()
	{
		
		pause.pPressed();
		time.pPressed();
	}
}
