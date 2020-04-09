package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import Controller.InputManager;
import Engine.Niveau;
import Engine.Objets.DrawableObjet;
import Settings.globalVar;
import Utilitaires.JLabelText;
import Utilitaires.KeyType;

public class ViewNiveau extends JPanel {
	private static final long serialVersionUID = -1571384451040609831L;

	Niveau niveau;
	InputManager inputManager;
	
	int nbBallons;
	int nbTiles = globalVar.nbTilesHorizontally*globalVar.nbTilesVertically;
	
	ArrayList<JLabelObjet> ballons = new ArrayList<JLabelObjet>();
	
	Timer refreshTimer = new Timer( 1000/globalVar.FPS, new ActionListener() { public void actionPerformed(ActionEvent arg0) { Refresh(); } });
	
	JLabelText temps;
	JLabelText vieDisplayer;
	JLabelText pressSpaceLabel = new JLabelText("Press space to start...", 600, (int)(globalVar.tileHeight/1.5), Color.WHITE);
	
	
	
	@SuppressWarnings("serial")
	public ViewNiveau(Niveau _niveau)
	{
		this.setLayout(null);
		this.setBackground(new Color(213,210,204));
		this.setBounds(0, 0, globalVar.tileWidth*globalVar.nbTilesHorizontally, (globalVar.nbTilesVertically+1)*globalVar.tileHeight);
		
		niveau = _niveau;
		inputManager = new InputManager(niveau, this);
		add(inputManager);
		
		add(pressSpaceLabel);
		pressSpaceLabel.setLocation(this.getWidth()/3, this.getHeight()/2-pressSpaceLabel.getHeight()/2);
		
		temps = new JLabelText("Remaining Time: " + niveau.getseconde(), 300, globalVar.tileHeight/2, Color.BLUE);
		vieDisplayer = new JLabelText("Vies : "+ niveau.getvie(), 300, globalVar.tileHeight/2, Color.BLUE);
		temps.setLocation(globalVar.tileWidth/6, globalVar.tileHeight/6);
		vieDisplayer.setLocation(globalVar.tileWidth*10, globalVar.tileHeight/6);
		
		add(temps); add(vieDisplayer);
		this.add(new Overlay(0, 0, this.getWidth(), globalVar.tileHeight, new Color(255, 255, 255)));
		
		DrawableObjet o = niveau.getDrawableSnoopy();
		JLabelObjet toAddJLabel = new JLabelObjet("Snoopy", o.geti(), o.getj(), o.getX(), o.getY(), o.getSprite());
		this.add(toAddJLabel);
		
		ArrayList<DrawableObjet> listeBallonList = niveau.GetDrawableBallons();
		nbBallons = listeBallonList.size();
		
		int b=0;
		for (DrawableObjet drawableObjet : listeBallonList) {
			toAddJLabel = new JLabelObjet(("ballon"+b), drawableObjet.geti(), drawableObjet.getj(), drawableObjet.getX(), drawableObjet.getY(), drawableObjet.getSprite());
			this.add(toAddJLabel);
			ballons.add(toAddJLabel);
			b++;
		}
		
		for (int i=0; i < globalVar.nbTilesHorizontally; i++)
		{
			for (int j=0; j < globalVar.nbTilesVertically; j++ )
			{
				o = niveau.getObjetToDraw(i, j);
				if (o.getSprite() != null)
				{
					toAddJLabel = new JLabelObjet("bloc", i, j, o.getX(), o.getY(), o.getSprite());
					this.add(toAddJLabel);
				}
				
			}
			
		}
		
		this.setVisible(true);
		this.validate();
		
		if (globalVar.waitForSpaceWhenStartingLevel)
			pressSpaceLabel.setVisible(true);
		else {
			this.remove(pressSpaceLabel);
			pressSpaceLabel=null;
			refreshTimer.start();
		}
	}
	
	public void StartNiveau() 
	{
		niveau.StartAfterWait(true);
		
		pressSpaceLabel.setVisible(false);
		this.remove(pressSpaceLabel);
		pressSpaceLabel=null;
		
		refreshTimer.start();
	}

	protected void Refresh() {
		
		if (niveau.isEnded())
		{
			refreshTimer.stop();
			return;
		}
		
		for (int i=0; i<nbBallons; i++)
		{
			ballons.get(i).refresh(niveau.getDrawableBallon(i));
		}
		
		for (Component component : this.getComponents())
		{
			if (component.getName() == "bloc" )
			{
				JLabelObjet toRefreshJLabel = ((JLabelObjet) component);
				toRefreshJLabel.refresh(niveau.getObjetToDraw(toRefreshJLabel.i, toRefreshJLabel.j));
			}
			else if (component.getName() == "Snoopy" )
				((JLabelObjet) component).refresh(niveau.getDrawableSnoopy());
		}
		
		temps.setText("Remaining Time: " + niveau.getseconde());
		vieDisplayer.setText("Vies : "+ niveau.getvie());
	}
}
