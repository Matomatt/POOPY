package Menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import Data.SaveManager;
import Engine.Partie;
import Utilitaires.ErrorMessage;

public class ScoreRecorder extends SaveFichier{
	public ScoreRecorder(Partie partie )
	{
		super(partie);
		messJLabel.setText("Entrer le Nom du joueur");
		setTitle("Entrez votre non.");
	
		ok.removeActionListener(ok.getActionListeners()[0]);
		ok.addActionListener(new ScoreRecordListener());
	}
	private class ScoreRecordListener implements ActionListener  //?
	{
		public void actionPerformed(ActionEvent e)
		{
			try {
				SaveManager.UpdateLeaderboard(partie.getScore(), chaine.getText());
			} catch (IOException e1) {
				new ErrorMessage("Impossible de mettre a jour les scores... \n" + e1.getLocalizedMessage());
			}
			close();
		}
	}
	
}
