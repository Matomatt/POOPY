package Controller;

import java.util.ArrayList;
import java.util.List;

import Utilitaires.KeyType;

public class KeysPressedList {
	private List<KeyHolder> list = new ArrayList<KeyHolder>();
	
	public KeysPressedList()
	{
		
	}
	
	public void add(KeyType _key)
	{
		if (!IsPressed(_key))
			list.add(new KeyHolder(_key));
	}
	
	public void remove(KeyType _key)
	{
		KeyHolder firingKey;
		if ((firingKey = GetKeyHolder(_key)) != null)
			list.remove(firingKey);
	}
	
	public boolean IsPressed(KeyType _key)
	{
		for (KeyHolder keyHolder : list) {
			if (keyHolder.key == _key)
				return true;
		}
		
		return false;
	}

	private KeyHolder GetKeyHolder(KeyType _key)
	{
		for (KeyHolder keyHolder : list) {
			if (keyHolder.key == _key)
				return keyHolder;
		}
		
		return null;
	}
	
	public boolean ActionReadyOf(KeyType _key)
	{
		KeyHolder firingKey;
		if ((firingKey = GetKeyHolder(_key)) != null)
			return !firingKey.cooldown;
		
		return false;
	}
	
	public ArrayList<KeyType> getReadyKeys()
	{
		ArrayList<KeyType> list2 = new ArrayList<KeyType>();
		
		for (KeyHolder key : list) {
			if (!key.cooldown)
				list2.add(key.key);
		}
		return list2;
	}
	
	public void FireKey(KeyType key)
	{
		if (IsPressed(key))
			GetKeyHolder(key).StartCooldown();
	}
	
	public void Kill()
	{
		for (KeyHolder keyHolder : list) {
			keyHolder.Kill();
		}
	}
}
