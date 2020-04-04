package Engine;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.concurrent.*;

public class GameOver extends JPanel

{
	private int score;
	public GameOver(int sco,int width,int height) 
	{
		this.setSize(width, height);
		score=sco;
		this.setBackground(new Color(255,255,255));
		this.setLayout(null);
		JLabel game=new JLabel("GAME OVER");
		game.setFont(new Font("DISPLAY",Font.PLAIN,40));
		game.setForeground(Color.RED);
		game.setLocation(width/3, height/4);
		JLabel scoring= new JLabel("Score:"+score);
		scoring.setFont(new Font("DISPLAY",Font.PLAIN,20));
		scoring.setForeground(Color.RED);
		scoring.setLocation(width/3, 3/4*height);

		add(game);
		add(scoring);
		this.setVisible(true);
		this.validate();
		/*
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
						e.printStackTrace();
		}*/
	}
}
