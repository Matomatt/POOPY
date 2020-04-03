package Engine;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
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
			niveaux.add( new Niveau("level1", false));
		}
		 catch (IOException e) {
				e.printStackTrace();
			}
		niveaux.get(0).setFocusable(true);

		this.add(niveaux.get(0));
		this.setVisible(true);
		this.validate();
		
	}
	
	public void next()
	{
		
	}
	protected void menu()
	{
		fenetre.add(new Menu(fenetre));
		fenetre.remove(this);
	}
	private void LoadPartie(String fileToLoad)
	{
		//
	}
	public void pPressed()
	{
		pause.pPressed();
	}
}
