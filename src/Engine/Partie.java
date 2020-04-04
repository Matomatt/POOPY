package Engine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.*;
public class Partie extends JPanel {
	private static final long serialVersionUID = -1207758538944896774L;
	
	private String name;
	
	private Pause pause;
	private Fenetre fenetre;
	
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
			try { niveaux.add( new Niveau("level2", name, false)); }
			catch (IOException e) { e.printStackTrace(); }
		}
		
		niveaux.get(0).setFocusable(true);
		this.add(niveaux.get(0));
		
		this.setVisible(true);
		this.validate();
	}
	
	public void next()
	{
		niveaux.get(0).getName();
	}
	
	protected void menu()
	{
		fenetre.add(new Menu(fenetre));
		fenetre.remove(this);
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
	}
}
