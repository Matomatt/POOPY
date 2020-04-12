package Data;

public class NomScoreAssoc implements Comparable<NomScoreAssoc> {

	String name = "";
	int score = 0;
	
	public NomScoreAssoc(String _name, int _score) {
		name = _name;
		score = _score;
	}
	
	public int compareTo(NomScoreAssoc arg) {
		return getScore() > arg.getScore() ? -1 : getScore() < arg.getScore() ? 1 : 0;
	}

	public boolean equals(String _name) 
	{
        return getName().contentEquals(_name);
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
	
}
