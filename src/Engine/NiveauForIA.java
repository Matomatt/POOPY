package Engine;

public class NiveauForIA  {

	Niveau niveau;
	
	public NiveauForIA(Niveau _niveau)
	{
		niveau = _niveau;
	}
	
	public boolean Won()
	{
		return niveau.ended;
	}
	
}
