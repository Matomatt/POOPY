package Engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Settings.globalVar;

public class SaveFichier extends JFrame
	{	
		private Partie partie;
		private JLabel messJLabel;
		private JTextField chaine;
		private JButton ok;
		
		public SaveFichier(Partie part)
		{
			partie=part;
			this.setTitle("Save");
			partie.niveaux.get(0).StopAll();
			messJLabel=new JLabel("Entrer le nom du fichier de sauvegarde");
			this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally/4,globalVar.nbTilesVertically*globalVar.nbTilesVertically/3);
			chaine=new JTextField(20);
			ok=new JButton("ok");
			ok.addActionListener(new SaveListener());
			
			add(messJLabel);
			add(chaine);
			add(ok);
			
			this.setAlwaysOnTop(true);
			this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally/3,globalVar.nbTilesVertically*globalVar.nbTilesVertically/2);
			this.setVisible(true);
			
			
		}
			
		private class SaveListener implements ActionListener  //?
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					savegame();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		private void savegame() throws FileNotFoundException, UnsupportedEncodingException
		{
			if(partie.getName().isEmpty())
			{
				partie.setnom(chaine.getText());
				partie.SavePartie();
			}
			else {
					partie.SavePartie();
				}
			partie.niveaux.get(0).KillAll();
			partie.menu();
			this.dispose();
		}
	}
