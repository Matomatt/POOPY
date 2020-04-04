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
		this.setVisible(false);
		this.setEnabled(false);
		this.setSize(partie.getSize().width/2, partie.getSize().height/2);
		this.setLocation(partie.getSize().width/4,partie.getSize().height/4);
		this.validate();
	}

	


	private  class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			partie.menu();
		}
	}
	private  class ResumeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
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
		this.setVisible(false);
		this.setEnabled(false);
		this.revalidate();
	}
	private void open()
	{
		active=true;
		this.setVisible(true);
		this.setEnabled(true);
		this.revalidate();

	}

}
