package Menus;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.concurrent.*;

public class GameOver extends JPanel

{
	private int score;
	public GameOver(int sco,int width,int height,int lv) 
	{
		this.setSize(width, height);
		score=sco;
		this.setBackground(Color.BLACK);
		
		JLabel game=new JLabel("GAME OVER: LV"+lv);
		game.setFont(new Font("DISPLAY",Font.PLAIN,40));
		game.setForeground(Color.RED);
		game.setLocation(width/3, height/4);
		game.setSize(450,100);
		JLabel scoring= new JLabel("Score:"+score);
		scoring.setFont(new Font("DISPLAY",Font.PLAIN,20));
		scoring.setForeground(Color.RED);
		scoring.setLocation(width/3, 3/4*height);
		scoring.setSize(300,100);
		add(game);
		add(scoring);
		this.setVisible(true);	
		
		this.validate();

	}
	
	public void visible()
	{
		this.setVisible(true);
		this.update(this.getGraphics());
		this.revalidate();
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
						e.printStackTrace();
		}
	}
}
