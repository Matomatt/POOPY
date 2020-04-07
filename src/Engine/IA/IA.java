package Engine.IA;

import Engine.NiveauForIA;

public class IA {

	NiveauForIA niveau;
	
	public IA(NiveauForIA _niveau)
	{
		niveau = _niveau;
	}
	
	public boolean FindPath()
	{
		while (!niveau.Won())
		{
			
		}
		return true;
	}
}
