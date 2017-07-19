import java.util.Random;
import java.util.Scanner;
import java.lang.Thread;
public class Game implements Runnable{
	
	Spielfeld[][] spielfelder;
	Scanner scan = new Scanner(System.in);
	int x;
	int y;
	int XofRandom;
	int YofRandom;
	boolean gameover = false;
	Random r = new Random();
	
	public Game(int x, int y,int AnzahlBomben) {
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
				}
				System.out.println();
			}
			
			//Wenn gameover == true wird die Spielschleife beendet.
			if(gameover == true) {
				System.out.println("- GAMEOVER -");
				scan.nextLine();
				break;
			}
			//Wenn nicht, dann wird nach den nächsten Koordinaten gefragt.
			else {
				System.out.println("Geben sie die Koordinaten an");
				System.out.print("X:");
				x = Integer.parseInt(scan.nextLine());
				System.out.print("Y:");
				y = Integer.parseInt(scan.nextLine());
				if (x > spielfelder[0].length|| y > spielfelder.length - 1 || x < 0 || y < 0) {
					do {
						System.out.println("Das Feld liegt nicht innerhalb des Spielfeldes\nVersuchen sie es nochmal");
						System.out.print("X:");
						x = Integer.parseInt(scan.nextLine());
						System.out.print("Y:");
						y = Integer.parseInt(scan.nextLine());
					} while (x > spielfelder[0].length|| y > spielfelder.length - 1 || x < 0 || y < 0);
				}
				spielfelder[y][x].Hidden = false;	
			}
		} while (true);
	}
}
