import java.util.Random;
import java.util.Scanner;
import java.lang.Thread;
public class Game implements Runnable{
	
	Spielfeld[][] spielfelder;
	Scanner scan = Program.scan;
	int x;
	int y;
	int XofRandom;
	int YofRandom;
	boolean gameover = false;
	int AnzahlBomben;
	Random r = new Random();
	
	public Game(int x, int y,int AnzahlBomben) {
		this.AnzahlBomben = AnzahlBomben;
		//Das Spielfeld wird Generiert
		spielfelder = new Spielfeld[x][y];
		for (int yKoordinate = 0; yKoordinate < spielfelder.length; yKoordinate++) {

			for (int xKoordinate = 0 ;xKoordinate < spielfelder[yKoordinate].length; xKoordinate++) {
				spielfelder[yKoordinate][xKoordinate] = new Spielfeld();
			}
		}
		
		//Felder werden Zufällig zu Bomben
		for (int i = 0; i < AnzahlBomben; i++) {
			XofRandom = r.nextInt(x);
			YofRandom = r.nextInt(y);
			if(spielfelder[YofRandom][XofRandom].Bomb == true) {
				//Wenn das zufällige Feld bereits eine Bombe ist,
				//Wird die Schleife noch einmal ausgeführt
				i--;
			}
			else {
				spielfelder[YofRandom][XofRandom].Bomb = true;
			}
		}
	}

	@Override
	public void run() {
		int bombsDefused = 0;
		do {
			//Um das Spielfeld werden Koordinaten zur Orientierung geschrieben.
			System.out.print(' ');
			for(int xKoordinate = 0; xKoordinate < spielfelder[0].length; xKoordinate++) {
				//Koordinaten der X-Achse zeichnen
				System.out.print(xKoordinate);
			}
			System.out.println();

			
			//Das Spielfeld wird durch eine for-schleife Iteriert und in die Kommandozeile geschrieben.
			for (int yKoordinate = 0; yKoordinate < spielfelder.length; yKoordinate++) {
				//Koordineten der Y-Achse zeichnen
				System.out.print(yKoordinate);

				for (Spielfeld feld : spielfelder[yKoordinate]) {
					System.out.print(feld.look());
					//Wenn eine Bombe('O') aufgedeckt wird, ist gameover == true.
					if(feld.look() == 'O')
						gameover = true;
					else if (feld.Flagged == true && feld.Bomb == true) {
						bombsDefused++;
					}
				}
				System.out.println();
			}
			
			//Wenn gameover == true wird die Spielschleife beendet.
			if(gameover == true) {
				System.out.println("- GAMEOVER -");
				scan.nextLine();
				break;
			}
			else if (bombsDefused == AnzahlBomben) {
				System.out.println("- YOU WIN -");
				scan.nextLine();
				break;
			}
			//Wenn nicht, dann wird nach den nächsten Koordinaten gefragt.
			else {
				nextTurn();
			}
		} while (true);
	}
	
	
	private void nextTurn() {
		System.out.println("Flag(P) Dig(D)");
		switch (scan.nextLine()) {
		case "p":
		case "P":
			System.out.print("X:");
			x = getIntfromInput(0, spielfelder[0].length,"Das Feld liegt nicht innerhalb des Spielfeldes.\nVersuchen sie es nochmal");
			System.out.print("Y:");
			y = getIntfromInput(0, spielfelder.length,"Das Feld liegt nicht innerhalb des Spielfeldes.\nVersuchen sie es nochmal");
			spielfelder[y][x].Flagged = true;
			break;
		case "D":
		case "d":
			System.out.print("X:");
			x = getIntfromInput(0, spielfelder[0].length - 1,"Das Feld liegt nicht innerhalb des Spielfeldes.\nVersuchen sie es nochmal");
			System.out.print("Y:");
			y = getIntfromInput(0, spielfelder.length - 1,"Das Feld liegt nicht innerhalb des Spielfeldes.\nVersuchen sie es nochmal");
			spielfelder[y][x].Hidden = false;
		default:
			break;
		}
	
	}
	
	/**Takes the next input from the System.in Stream and 
	 * Converts it to an Integer as long as the Input does not match the specifications and 
	 * finally returns that Input as an Integer.
	 * @param min The Smallest Integer accepted as Input
	 * @param max The Largest Integer accepted as Input
	 * @param message The message that is displayed if the Integer is not within the specified bounds
	 * @return
	 */
	private int getIntfromInput(int min, int max,String message) {
		int i;
		do {
			try {
				i = Integer.parseInt(scan.nextLine());
				if (i >= min && i <= max) {
					return i;
				}
				else {
					System.out.println(message);
				}
			} catch (NumberFormatException e) {
				System.out.print("Error: Die Eingegebene Zahl ist kein gültiger Integer.\nVersuchen sie es nochmal");
			}
		} while (true);
	}
	
}
