package Menus;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.Box.Filler;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LeaderBoard extends JFrame{
	private ArrayList<JLabel> nom ;
	private ArrayList<JLabel> score ;
	public LeaderBoard()
	{
		nom=new ArrayList<JLabel>();
		score=new ArrayList<JLabel>();
		this.setTitle("LeaderBoard");
		this.setLayout(new GridLayout());
		this.fill();
		for(int i=0;i<nom.size();i++)
		{
			add(nom.get(0));
			add(score.get(0));
		}
		
		this.setSize(300, 500);
		this.setVisible(true);
		this.validate();
	}
	private void fill()
	{
		// Remplir ici depuis le fichier
	}
	
}