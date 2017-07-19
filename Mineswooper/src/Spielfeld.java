
public class Spielfeld {

	public Spielfeld() {

			Bomb = false;
			Hidden = true;
	}
	public Spielfeld(boolean Bomb) {
		// TODO Auto-generated constructor stub
		this.Bomb = Bomb;
		Hidden = true;
	}
	
	public char look() {
		if (Hidden == true) {
			return '#';
		}
		else if (Bomb == true) {
			return 'O';
		}
		else {
			return ' ';
		}

	}
	boolean Hidden;
	boolean Bomb;
}
