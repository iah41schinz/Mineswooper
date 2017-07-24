import java.util.Scanner;
public class Program {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Geben sie die Spielfeld Größe an:");
		System.out.print("X:");
		int x = Integer.parseInt(scan.nextLine());
		System.out.print("Y:");
		int y = Integer.parseInt(scan.nextLine());
		System.out.println("Geben sie die Anzahl der Bomben an:");
		int AnzahlBomben = Integer.parseInt(scan.nextLine());
		Game game = new Game(x,y,AnzahlBomben);
		game.run();
		scan.close();
	}

	public static int getIntfromInput(int min, int max,String message) {
		int i;
		do {
			try {
				i = Integer.parseInt(scan.nextLine());
				if (i > min && i < max) {
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
