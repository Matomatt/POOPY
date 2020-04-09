package Controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;


import Engine.Niveau;
import Utilitaires.Direction;
import Utilitaires.KeyType;

public class InputManager extends JPanel{
	
	private Niveau niveau;
	private KeysPressedList keysPressedList;
	private Timer timer;
	public InputManager(Niveau niv) 
	{
		this.setLayout(null);
		niveau = niv;
		this.setVisible(true);
		keysPressedList=new KeysPressedList();
		//this.setEnabled(true);
		timer=new Timer();
		AddKeyBindings();
		timer.schedule( new ExecuteKey(), 0, 10);
		this.validate();
	}

	private class ExecuteKey extends TimerTask
	{
		
		public void run()
		{
			runkey();
			//System.out.println("poupynotmooving");

		}
	}
	private void runkey()
	{
		KeyType keyType;
		System.out.println("size keypressedlist: "+keysPressedList.getReadyKeys().size());
		for(int i=0;i<keysPressedList.getReadyKeys().size();i++)
		{
			keyType= keysPressedList.getReadyKeys().get(i);
			if(niveau.ExecuteKey(keyType))
				keysPressedList.FireKey(keyType);
		}	
	}
	public void stop()
	{
		timer.cancel();
	}
	public void resume()
	{
		timer.schedule( new ExecuteKey(), 0, 10);
	}
//	private boolean execute(KeyType key) {
//		if (!niveau.PoupyMoving())
//		{
//			switch (key) {
//				case UP: return niveau.MoveObject(niveau.POOPY,Direction.NORTH);
//
//				case LEFT: return niveau.MoveObject(niveau.POOPY,Direction.WEST);
//	
//				case DOWN: return niveau.MoveObject(niveau.POOPY,Direction.SOUTH);
//	
//				case RIGHT: return niveau.MoveObject(niveau.POOPY,Direction.EAST);
//	
//				case SPACE: return niveau.SpacePressed();
//				
//				default:return false;
//			}
//		
//		}
//		return false;
//	}
	public void AddKeyBindings()
	{
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false),"MoveUp");
		this.getActionMap().put("MoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.UP); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),"StopMoveUp");
		this.getActionMap().put("StopMoveUp", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.UP); } });

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false),"MoveLeft");
		this.getActionMap().put("MoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.LEFT); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true),"StopMoveLeft");
		this.getActionMap().put("StopMoveLeft", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.LEFT); } });

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false),"MoveDown");
		this.getActionMap().put("MoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.DOWN); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true),"StopMoveDown");
		this.getActionMap().put("StopMoveDown", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.DOWN); } });

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),"MoveRight");
		this.getActionMap().put("MoveRight", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.RIGHT); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true),"StopMoveRight");
		this.getActionMap().put("StopMoveRight", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.RIGHT); } });

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false),"SnoopyDo");
		this.getActionMap().put("SnoopyDo", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.add(KeyType.SPACE); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true),"SnoopyStop");
		this.getActionMap().put("SnoopyStop", new AbstractAction() { public void actionPerformed(ActionEvent e) { keysPressedList.remove(KeyType.SPACE); } });

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false),"Save");
		this.getActionMap().put("Save", new AbstractAction() { public void actionPerformed(ActionEvent e) { niveau.CallSavePartie(); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false),"Pause");
		this.getActionMap().put("Pause", new AbstractAction() { public void actionPerformed(ActionEvent e) { niveau.pause(); } });
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false),"AutoWin");
		this.getActionMap().put("AutoWin", new AbstractAction() { public void actionPerformed(ActionEvent e) { niveau.win(); } });
	}

}
