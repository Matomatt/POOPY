package Menus;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Data.ImageManager;
import Settings.globalVar;
import Utilitaires.ErrorMessage;
import Utilitaires.path;

public class WinPage extends JPanel
{
	private static final long serialVersionUID = 790327203544610088L;
	
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
		try { sprite = new JLabel( new ImageIcon(ImageManager.LoadImage(path.getImagePath("Menus/poupywin.png"), globalVar.nbTilesHorizontally*globalVar.tileWidth, this.getHeight())) );
	      sprite.setBounds(0, 0, globalVar.nbTilesHorizontally*globalVar.tileWidth, (globalVar.nbTilesVertically+1)*globalVar.tileHeight);
	      this.add(sprite);}
		
		catch (IOException e) { new ErrorMessage("Couldn't open win page...\n" + e.getLocalizedMessage()); }
		this.setVisible(true);	
		
		this.validate();

	}
}