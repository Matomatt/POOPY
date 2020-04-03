package Engine;

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
		{
			list.add(new KeyHolder(_key));
			System.out.println("adding key");
		}
			
	}
	
	public void remove(KeyType _key)
	{
		System.out.println("removing key");
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
	
	public void FireKey(KeyType key)
	{
		GetKeyHolder(key).StartCooldown();
	}
}
