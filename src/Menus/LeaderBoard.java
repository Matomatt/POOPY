package Menus;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;

import Data.NomScoreAssoc;
import Data.SaveManager;
import Utilitaires.ErrorMessage;

import javax.swing.JFrame;
import javax.swing.JLabel;

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
			add(nom.get(i));
			add(score.get(i));
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
