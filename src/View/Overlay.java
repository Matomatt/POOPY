package View;

import java.awt.Color;

import javax.swing.JPanel;

public class Overlay extends JPanel {
	private static final long serialVersionUID = 77457602714500662L;

	public Overlay(int x, int y, int w, int h, Color color)
	{
		this.setBounds(x, y, w, h);
		this.setBackground(color);
		this.setVisible(true);
		this.validate();
	}
}
