package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class Menu extends JPanel{

	private Fenetre fenetre;
	private JTextField loadlv;
	private JTextField loadgame;
	private JLabel loadlvlabel;
	private JLabel loadgamelabel;
	private JButton loadlvbutt;
	private JButton loadgamebutt;
	private JButton start;
	
	protected ArrayList<Niveau> niveaux;
	int actuallv=0;
	
	
	public Menu(Fenetre fen)
	{
		// Partie Graphique
		loadlv=new JTextField(20);
		loadgame=new JTextField(20);
		loadlvlabel=new JLabel("Entrer le code niveau");
		loadgamelabel=new JLabel("Entrer le nom du fichier");
		loadlvbutt=new JButton("Validé");
		loadgamebutt=new JButton("Validé");
		start=new JButton("Nouvelle Partie");
		
		loadlvbutt.addActionListener(new LoadLvListener());
		loadgamebutt.addActionListener(new LoadGameListener());
		start.addActionListener(new StartListener());
		
		this.add(loadlvlabel);
		this.add(loadlv);
		this.add(loadlvbutt);
		
		this.add(loadgamelabel);
		this.add(loadgame);
		this.add(loadgamebutt);
		
		this.add(start);
		
		
		
		fenetre=fen;
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setVisible(true);
		this.validate();
		
		
	}
	
	// Gestion Load game;
	private  class LoadGameListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			loadg();
		}
	}
	private void loadg()
	{
		fenetre.remove(this);
		fenetre.loadgame(loadgame.getText());
		fenetre.revalidate();
	}
	//Gestion Load LV
	private class LoadLvListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			loadlv();
		}
	}
	private void loadlv()
	{
		fenetre.remove(this);
		fenetre.loadlv(loadlv.getText());
		fenetre.revalidate();
	}
	// Gestion start 
	private class StartListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			start();
		}
	}
	private void start()
	{
		fenetre.remove(this);;
		fenetre.start();
		fenetre.revalidate();
	}
	
	
}