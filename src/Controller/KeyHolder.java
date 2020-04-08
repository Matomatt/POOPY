package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import Settings.globalVar;
import Utilitaires.KeyType;

public class KeyHolder {
	KeyType key;
	
	boolean cooldown = false;
	Timer cooldownTimer = new Timer( globalVar.keyCooldownTime, new ActionListener() { public void actionPerformed(ActionEvent arg0) { EndCooldown(); } });
	
	public KeyHolder(KeyType _key)
	{
		key = _key;
	}
	
	public void StartCooldown()
	{
		cooldown = true;
		cooldownTimer.restart();
	}
	private void EndCooldown()
	{
		cooldown = false;
		cooldownTimer.stop();
	}
	
	public void Kill()
	{
		cooldownTimer.stop();
		cooldownTimer = null;
	}
}
