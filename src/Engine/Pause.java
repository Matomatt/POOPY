package Engine;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.*;

import Data.ImageManager;
import Settings.globalVar;






public class Pause extends JPanel{
	//private Fenetre fenetre;
	private JButton menu;
	private JButton resume;
	private JButton save;
	private Partie partie;
	private JLabel sprite;
	private boolean active=false;


	public Pause(Partie part)
	{
		//		Image background = Toolkit.getDefaultToolkit().createImage("Background.png");
		//	    this.drawImage(background, 0, 0, null);
		partie=part;
		menu= new JButton("Menu");
		save= new JButton ("Save & Quit");
		resume= new JButton ("Resume");
		menu.addActionListener(new MenuListener());
		save.addActionListener(new SaveListener());
		resume.addActionListener(new ResumeListener());
		this.add(menu);
		this.add(resume);
		this.add(save);

		this.setVisible(false);
		this.setEnabled(false);
		this.setOpaque(false);
		this.setSize(partie.getSize().width/2, partie.getSize().height/2);
		this.setLocation(partie.getSize().width/4,partie.getSize().height/4);
		
		try { sprite = new JLabel( new ImageIcon(ImageManager.LoadImage("./Images/menus/pause.png", this.getWidth(), this.getHeight())) );
	      sprite.setBounds(this.getWidth()/4, this.getHeight()/4, this.getWidth()/2, this.getHeight()/2);
	      this.add(sprite);}

		catch (IOException ex) { System.out.println("Couldn't open snoopy title page "); }

		this.validate();
	}



	private  class MenuListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			partie.menu();
		}
	}
	private  class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
		 new SaveFichier(partie);	
		}
	}
	private  class ResumeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			partie.pPressed();
			close();
		}
	}
	public void pPressed()
	{
		System.out.println("pPressed dans pause");
		if (active==true)
			close();
		else
			open();
	}
	private void close()
	{
		active=false;
		//this.update(getGraphics());
		this.setVisible(false);
		this.setEnabled(false);
		this.revalidate();
		//this.update(this.getGraphics());
	}
	private void open()
	{
		active=true;
		//this.update(getGraphics());
		this.paint(getGraphics());
		this.setVisible(true);
		this.setEnabled(true);
		this.revalidate();
		//this.update(this.getGraphics());
	}

}
