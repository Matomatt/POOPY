package Engine;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.BoxLayout;
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
			this.setLayout(new BorderLayout());
			this.setTitle("Save");
			partie.niveaux.get(0).StopAll();
			messJLabel=new JLabel("Entrer le nom du fichier de sauvegarde");
			this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally/4,globalVar.nbTilesVertically*globalVar.nbTilesVertically/3);
			chaine=new JTextField(30);
			ok=new JButton("ok");
			ok.addActionListener(new SaveListener());
			
			add(messJLabel,BorderLayout.NORTH);
			add(chaine,BorderLayout.CENTER);
			add(ok,BorderLayout.SOUTH);
			
			this.setAlwaysOnTop(true);
			this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally/3,(globalVar.nbTilesVertically*globalVar.tileHeight)/4);
			this.setVisible(true);
			
			if (partie.getnom() != null)
				chaine.setText(partie.getnom());
		}
			
		private class SaveListener implements ActionListener  //?
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					savegame();
				} catch (FileNotFoundException e1) {
					new ErrorMessage("Erreur lors de la sauvegarde...\n" + e1.getLocalizedMessage());
				} catch (UnsupportedEncodingException e2) {
					new ErrorMessage("Erreur lors de la sauvegarde...\n" + e2.getLocalizedMessage());
				}
			}
		}
		private void savegame() throws FileNotFoundException, UnsupportedEncodingException
		{
			partie.setnom(chaine.getText());
			partie.SavePartie();
			//partie.niveaux.get(0).KillAll();
			partie.menu();
			this.dispose();
		}
		public void close()
		{
			this.dispose();
		}
	}
