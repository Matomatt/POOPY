package View;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Engine.Objets.DrawableObjet;
import Settings.globalVar;

public class JLabelObjet extends JLabel {
	private static final long serialVersionUID = 7616670564695743177L;
	
	int i; int j;
	
	public JLabelObjet(int _i, int _j, int _x, int _y, ImageIcon sprite)
	{
		super(sprite);
		this.setLayout(new GridLayout());
		i=_i; j=_j;
		this.setLocation(_x, _y);
		this.setSize(getPreferredSize());
		this.setVisible(true);
		this.validate();
	}
	
	public void refresh(DrawableObjet o)
	{
		/*
		if (!(this.getIcon() == null && o.getSprite() == null))
			this.setIcon(o.getSprite());
		*/
		if (this.getX() != o.getX() || this.getY() != o.getY())
			this.setLocation(o.getX(), o.getY());
		//this.revalidate();
		//if (this.getName().contains("ballon"))
		//	paint(this.getGraphics());
		//if (this.getIcon() != null)
			
	}
}
