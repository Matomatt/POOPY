package Engine;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;
public class Partie extends JPanel {
	private static final long serialVersionUID = -1207758538944896774L;
	
	private Pause pause;
	private Fenetre fenetre;
	private int score;
	protected ArrayList<Niveau> niveaux;

	
	
	public Partie(String fileToLoad,Fenetre fen)
	{
		fenetre=fen;
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);
	
		
		this.validate();
	}
	
	// Start
	public Partie(Fenetre fene)
	{
		fenetre=fene;
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setLayout(null);
		niveaux= new ArrayList<Niveau>();
		try {	
			niveaux.add( new Niveau("level2", false,this));
		}
		 catch (IOException e) {
				e.printStackTrace();
			}
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
			niveaux.add( new Niveau("level"+numlv, false,this));
		}
		 catch (IOException e) {
				e.printStackTrace();
			}
		niveaux.get(0).setFocusable(true);
	
		this.add(niveaux.get(0));
		this.setVisible(true);
		this.validate();
		
	}
	
	protected void perdu()
	{
		this.removeAll();
		this.add(new GameOver(score,this.getWidth(),this.getHeight()));
		System.out.println("crash?");
		menu();
	}
	protected void addscore(int lvscore)
	{
		score+=lvscore;
	}
	
	public void next()
	{
		
	}
	protected void menu()
	{
		fenetre.remove(this);
		fenetre.add(new Menu(fenetre));
		fenetre.revalidate();
	}
	private void LoadPartie(String fileToLoad)
	{
		
	}
	public void pPressed()
	{
		pause.pPressed();
	}
}
