
public class Cell {

	

	public Cell() {

			Bomb = false;
			Hidden = true;
	}
	
	public Cell(boolean Bomb) {
		// TODO Auto-generated constructor stub
		this.Bomb = Bomb;
		Hidden = true;
	}
	
	
	/**Returns a char depending on the value of the attributes the Object is holding.
	 * @return
	 */
	public char look() {
		if (Flagged == true) {
			return 'P';
		}
		else if (Hidden == true) {
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
	boolean Flagged;
}
