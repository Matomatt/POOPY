package Utilitaires;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class JLabelText extends JLabel {

    private static final long serialVersionUID = 1L;

	public JLabelText(String text, int width, int fontSize, Color color)
	{
		super(new String(text));
		this.setFont(new Font("DISPLAY",Font.PLAIN, fontSize));
		this.setSize(width, fontSize+3);
		this.setForeground(color);
		this.setVisible(true);
	}
}
