package Menus;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Engine.Partie;
import Settings.globalVar;
import Utilitaires.ErrorMessage;

public class SaveFichier extends JFrame
{
	private static final long serialVersionUID = 521387102919772058L;
	
	protected Partie partie;
	protected JLabel messJLabel;
	protected JTextField chaine;
	protected JButton ok;

	public SaveFichier(Partie part, boolean resumeLevelIfClosed)
	{
		this.setLocation(300, 100);
		partie=part;
		if (!resumeLevelIfClosed)
			Init();
		
		addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	        	partie.niveaux.get(0).Resume();
	        	super.windowClosing(e);
	        }
	    });
		
		Init();
	}
	
	public SaveFichier(Partie part)
	{
		partie=part;
		Init();
	}
	
	private void Init()
	{
		this.setLayout(new BorderLayout());
		this.setTitle("Save");
		this.setSize(globalVar.tileWidth*globalVar.nbTilesHorizontally/3,(globalVar.nbTilesVertically*globalVar.tileHeight)/4);
		
		messJLabel=new JLabel("Entrer le nom du fichier de sauvegarde");
		chaine=new JTextField(30);
		ok=new JButton("ok");
		ok.addActionListener(new SaveListener());

		add(messJLabel,BorderLayout.NORTH);
		add(chaine,BorderLayout.CENTER);
		add(ok,BorderLayout.SOUTH);

		this.setAlwaysOnTop(true);
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
		partie.menu();
		this.dispose();
	}
	
	public void close()
	{
		this.dispose();
	}
}
