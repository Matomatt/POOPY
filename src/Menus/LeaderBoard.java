package Menus;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;

import Data.NomScoreAssoc;
import Data.SaveManager;
import Utilitaires.ErrorMessage;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class LeaderBoard extends JFrame{
    private static final long serialVersionUID = 1L;
	private ArrayList<JLabel> nom ;
	private ArrayList<JLabel> score ;
	
	public LeaderBoard()
	{
		
		nom=new ArrayList<JLabel>();
		score=new ArrayList<JLabel>();
		this.setTitle("LeaderBoard");
		this.setLayout(new GridLayout(10,2));
		this.fill();
		
		for(int i=0;i<10;i++)
		{
			if (i<nom.size())
			{
				add(new JLabel(" "+(i+1)+")  "+nom.get(i).getText()+" "));
				add(score.get(i));
			}
			else {
				add(new JLabel(""));
				add(new JLabel(""));
			}
		}
		
		this.setSize(300, 500);
		this.setVisible(true);
		this.validate();
	}
	private void fill()
	{
		ArrayList<NomScoreAssoc> leaderboard = new ArrayList<NomScoreAssoc>();
		try {
			leaderboard = SaveManager.GetLeaderboard();
		} catch (IOException e) {
			new ErrorMessage("Impossible de recuperer le leaderboard..." + e.getLocalizedMessage());
		}
		
		for (NomScoreAssoc nomScoreAssoc : leaderboard) {
			nom.add(new JLabel(nomScoreAssoc.getName()));
			score.add(new JLabel("" + nomScoreAssoc.getScore()));
		}
	}
	
}
