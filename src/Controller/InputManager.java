package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;


import Engine.Niveau;
import Settings.globalVar;
import Utilitaires.KeyType;
import View.ViewNiveau;

public class InputManager extends JPanel{
	private static final long serialVersionUID = -1000453229612833299L;
	
	private Niveau niveau;
	ViewNiveau view;
	
	private KeysPressedList keysPressedList;
	KeyStroke keyToWaitFor = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false);
	
	private Timer timer;
	
	
	@SuppressWarnings("serial")
	public InputManager(Niveau niv, ViewNiveau _view) 
	{
		this.setLayout(null);
		niveau = niv;
		view = _view;
		
		this.requestFocus();
		this.setVisible(true);
		
		keysPressedList=new KeysPressedList();

		timer=new Timer();
		timer.schedule( new ExecuteKey(), 0, 10);
		
		if (globalVar.waitForSpaceWhenStartingLevel)
		{
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyToWaitFor,"waitSpacePress");
			this.getActionMap().put("waitSpacePress", new AbstractAction() { public void actionPerformed(ActionEvent e) { StartNiveau(); } });
		}
		else
			AddKeyBindings();
		
		this.validate();
	}
	
	private void StartNiveau()
	{
		this.getInputMap().remove(keyToWaitFor);
		AddKeyBindings();
		view.StartNiveau();
	}
	private class ExecuteKey extends TimerTask
	{
		
		public void run()
		{
			runkey();
		}
	}
	
	private void runkey()
	{
		if (niveau.isEnded() || niveau.isBusy())
			return;
		ArrayList<KeyType> keysReadyList = keysPressedList.getReadyKeys();
		for (KeyType keyType : keysReadyList) {
			KeyType tmpKeyType = keyType;
			if(niveau.ExecuteKey(tmpKeyType))
				keysPressedList.FireKey(tmpKeyType);
			niveau.setBusy();
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
	
	@SuppressWarnings("serial")
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
