package Data;

public class NomScoreAssoc implements Comparable<NomScoreAssoc> {

	String name = "";
	int score = 0;
	
	public NomScoreAssoc(String _name, int _score) {
		name = _name;
		score = _score;
	}
	
	public int compareTo(NomScoreAssoc arg) {
		return score > arg.score ? -1 : score < arg.score ? 1 : 0;
	}

	public boolean equals(String _name) 
	{
        return name.contentEquals(_name);
	}
	
}
