package View;

import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Engine.Niveau;
import Engine.Objets.DrawableObjet;
import Settings.globalVar;

public class ViewNiveau extends JPanel {
	private static final long serialVersionUID = -1571384451040609831L;

	Niveau niveau;
	
	int nbBallons;
	int nbTiles = globalVar.nbTilesHorizontally*globalVar.nbTilesVertically;
	
	ArrayList<JLabelObjet> ballons = new ArrayList<JLabelObjet>();
	
	Timer refreshTimer = new Timer( 1000/globalVar.FPS, new ActionListener() { public void actionPerformed(ActionEvent arg0) { Refresh(); } });
	private int refreshCounter = 0;
	
	public ViewNiveau(Niveau _niveau)
	{
		niveau = _niveau;
		
		this.setLayout(null);
		this.setBounds(0, globalVar.tileHeight, globalVar.tileWidth*globalVar.nbTilesHorizontally, globalVar.nbTilesVertically*globalVar.tileHeight);
		
		DrawableObjet o = niveau.getDrawableSnoopy();
		JLabelObjet toAddJLabel = new JLabelObjet(o.geti(), o.getj(), o.getX(), o.getY(), o.getSprite());
		toAddJLabel.setName("Snoopy");
		this.add(toAddJLabel);
		
		ArrayList<DrawableObjet> listeBallonList = niveau.GetDrawableBallons();
		nbBallons = listeBallonList.size();
		
		int b=0;
		for (DrawableObjet drawableObjet : listeBallonList) {
			toAddJLabel = new JLabelObjet(drawableObjet.getX(), drawableObjet.getY(), drawableObjet.geti(), drawableObjet.getj(), drawableObjet.getSprite());
			toAddJLabel.setName("ballon"+b);
			this.add(toAddJLabel);
			ballons.add(toAddJLabel);
			b++;
		}
		
		for (int i=0; i < globalVar.nbTilesHorizontally; i++)
		{
			for (int j=0; j < globalVar.nbTilesVertically; j++ )
			{
				o = niveau.getObjetToDraw(i, j);
				toAddJLabel = new JLabelObjet(i, j, o.getX(), o.getY(), o.getSprite());
				toAddJLabel.setName("bloc");
				this.add(toAddJLabel);
			}
			
		}
		
		this.setVisible(true);
		this.validate();
		
		refreshTimer.start();
	}

	protected void Refresh() {
		
		if (niveau.isEnded())
		{
			refreshTimer.stop();
			return;
		}
		/*
		ArrayList<DrawableObjet> ballonsDrawable = niveau.GetDrawableBallons();
		for (int i=0; i<nbBallons; i++) {
			if (ballons.get(i).getX() != ballonsDrawable.get(i).getX() || ballons.get(i).getY() != ballonsDrawable.get(i).getY())
				ballons.get(i).setLocation(ballonsDrawable.get(i).getX(), ballonsDrawable.get(i).getY());
			ballons.get(i).revalidate();
		}
		*/
		
		for (int i=0; i<nbBallons; i++)
		{
			ballons.get(i).refresh(niveau.getDrawableBallon(i));
		}
		/*
		for (Component component : this.getComponents())
		{
			if (component.getName() == "bloc" && false)
			{
				JLabelObjet toRefreshJLabel = ((JLabelObjet) component);
				toRefreshJLabel.refresh(niveau.getObjetToDraw(toRefreshJLabel.i, toRefreshJLabel.j));
			}
			else if (component.getName() == "Snoopy" && false)
				((JLabelObjet) component).refresh(niveau.getDrawableSnoopy());
			else if (component.getName().contains("ballon") && false)
			{
				int index = component.getName().toCharArray()[6]-'0';
				ballons.get(index).refresh(niveau.getDrawableBallon(index));
			}
			
		}
		*/

		
		/*
		refreshCounter = (refreshCounter+1)%3;
		if (refreshCounter == 0)
			this.updateUI();
			*/
		//this.revalidate();
	}
}
