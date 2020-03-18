import Utilitaires.Direction;

public class Snoopy extends Objet {
	private static final long serialVersionUID = 1L;
	Direction orientation = Direction.NORTH;
	
	
	
	public Snoopy(double _x, double _y)
	{
		super(_x, _y);
		BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		add(picLabel);
	}
}
