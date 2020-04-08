package View;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Engine.Objets.DrawableObjet;

public class JLabelObjet extends JLabel {
	private static final long serialVersionUID = 7616670564695743177L;
	
	int i; int j;
	
	public JLabelObjet(int _i, int _j, int _x, int _y, ImageIcon sprite)
	{
		super(sprite);
		i=_i; j=_j;
		this.setBounds(_x, _y, sprite.getIconWidth(), sprite.getIconHeight());
		this.setVisible(true);
		this.validate();
	}
	
	public void refresh(DrawableObjet o)
	{
		this.setIcon(o.getSprite());
		this.setLocation(o.getX(), o.getY());
		this.revalidate();
	}
}
