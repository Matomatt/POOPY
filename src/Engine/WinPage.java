package Engine;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Data.ImageManager;
import Settings.globalVar;

import java.util.concurrent.*;

public class WinPage extends JPanel

{
	private int score;
	private JLabel sprite;
	public WinPage(int sco,int width,int height,int lv) 
	{
		this.setSize(width, height);
		score=sco;
	//	this.setBackground(Color.yellow);
		

		JLabel game=new JLabel("You Win: LV"+lv);
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
		try { sprite = new JLabel( new ImageIcon(ImageManager.LoadImage("./Images/Sprites/poupywin.png", globalVar.nbTilesHorizontally*globalVar.tileWidth, this.getHeight())) );
	      sprite.setBounds(0, globalVar.nbTilesHorizontally*globalVar.tileWidth, 0, globalVar.nbTilesVertically*globalVar.tileHeight);
	      this.add(sprite);}
		
		catch (IOException ex) { System.console().writer().println("Couldn't open win page "); }
		this.setVisible(true);	
		
		this.validate();

	}
}