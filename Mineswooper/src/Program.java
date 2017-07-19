import java.util.Scanner;
public class Program {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
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

}
