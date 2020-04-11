package Menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import Data.ImageManager;
import Engine.Niveau;
import Settings.globalVar;
import Utilitaires.ErrorMessage;
import View.Fenetre;

// La classe menu est un panneaux n'ayant pour but que de servir d'interface pour trigger les fonctions approprié dans Fenetre
public class Menu extends JPanel{
	private static final long serialVersionUID = 4533597996297148103L;

	private Fenetre fenetre;
	private JTextField loadlv;
	private JTextField loadgame;
	private JLabel loadlvlabel;
	private JLabel loadgamelabel;
	private JButton loadlvbutt;
	private JButton loadgamebutt;
	private JButton start;
	private JButton scoreButton;
	protected ArrayList<Niveau> niveaux;
	int actuallv=0;
	private JLabel sprite;
	
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
		scoreButton=new JButton("Leaderboard");
		scoreButton.addActionListener(new LeaderboardtListener());
		
		loadlvbutt.addActionListener(new LoadLvListener());
		loadgamebutt.addActionListener(new LoadGameListener());
		start.addActionListener(new StartListener());

		loadlv.setText("Mot de passe");
		loadgame.setText("Partie");


		this.add(loadlvlabel);
		this.add(loadlv);
		this.add(loadlvbutt);

		this.add(loadgamelabel);
		this.add(loadgame);
		this.add(loadgamebutt);

		this.add(start);
		this.add(scoreButton);

		fenetre=fen;
		this.setSize(fenetre.getSize().width, fenetre.getSize().height);
		this.setVisible(true);
		
		try {
			sprite = new JLabel( new ImageIcon(ImageManager.LoadImage("./Images/Menus/snoopytitile.png", globalVar.tileWidth*globalVar.nbTilesHorizontally, globalVar.tileHeight*globalVar.nbTilesVertically)) );
			sprite.setBounds(0, 0, globalVar.tileWidth*globalVar.nbTilesHorizontally, globalVar.tileHeight*(globalVar.nbTilesVertically+1));
			this.add(sprite);
		} catch (IOException e) {
			new ErrorMessage("Couldn't open snoopy title page...\n" + e.getLocalizedMessage());
		}
		
		this.validate();
	}

	// Gestion chargement d'une partie, appel la fonction analogue dans fenetre
	private  class LoadGameListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			loadg();
		}
	}

	private void loadg()
	{
		try {
			fenetre.remove(this);
			fenetre.loadgame(loadgame.getText());
		} catch (IOException e) {
			new ErrorMessage();
			new ErrorMessage(e.getLocalizedMessage());
			fenetre.remove(this);
			fenetre.menu();
		}
	}

	// Gestion chargement d'un niveau, appel la fonction analogue dans fenetre
	private class LoadLvListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			loadlv();
		}
	}

	private void loadlv()
	{
		System.out.println("Load from password");
		try {
			if (fenetre.loadlv(loadlv.getText()))
				fenetre.remove(this);
		} catch (IOException e) {
			new ErrorMessage();
			new ErrorMessage(e.getLocalizedMessage());
			fenetre.remove(this);
			fenetre.menu();
		}
	}
	
	// Lance le menu
	private class LeaderboardtListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			new LeaderBoard();
		}
	}

	// Gestion du lancement d'une partie, appel la fonction analogue dans fenetre
	private class StartListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			start();
		}
	}
	private void start()
	{
		fenetre.remove(this);
		try {
			fenetre.start();
		} catch (IOException e) {
			new ErrorMessage("Impossible de lancer une nouvelle partie...\n" + e.getLocalizedMessage());
		}
	}


}
