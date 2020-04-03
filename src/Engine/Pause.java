package Engine;
import java.awt.event.*;

import javax.swing.*;






public class Pause extends JPanel{
	//private Fenetre fenetre;
	private JButton menu;
	private JButton resume;
	private JButton save;
	private Partie partie;
	private boolean active=false;
	public Pause(Partie part)
	{
//		Image background = Toolkit.getDefaultToolkit().createImage("Background.png");
//	    this.drawImage(background, 0, 0, null);
		partie=part;
		menu= new JButton("Menu");
		save= new JButton ("Save & Quit");
		resume= new JButton ("Resume");
		//menu.addActionListener(new MenuListener());
		save.addActionListener(new SaveListener());
		resume.addActionListener(new ResumeListener());
		this.add(menu);
		this.add(resume);
		this.add(save);
		this.setVisible(true);
		this.setSize(partie.getSize().width/2, partie.getSize().height/2);
		this.setLocation(partie.getSize().width/4,partie.getSize().height/4);
		this.validate();
	}

	
//	private  class MenuListener implements ActionListener  // juste fermer la partie 
//	{
//		public void actionPerformed(ActionEvent e)
//		{
//			//fenetre.menu();
//			//
//		//	 SwingUtilities.getWindowAncestor(this).remove(this);
//		}
//	}

	private  class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
				// Ajouter une fonction de sauvegarde dans fenetre 
			
			
			partie.menu();
		}
	}
	private  class ResumeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// Resume la pause dans niveau 
		//	 SwingUtilities.getWindowAncestor(this).remove(this);
		     close();
		}
	}
	public void pPressed()
	{
		if (active)
			close();
		else
			open();
	}
	private void close()
	{
		active=false;
		this.setVisible(false);
		this.setEnabled(false);
	}
	private void open()
	{
		active=true;
		this.setVisible(true);
		this.setVisible(false);
	}

}
