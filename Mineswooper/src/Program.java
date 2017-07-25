import java.util.Scanner;
public class Program {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Specify the games size");
		System.out.print("X:");
		int x = Integer.parseInt(scan.nextLine());
		System.out.print("Y:");
		int y = Integer.parseInt(scan.nextLine());
		System.out.println("Specify the number of bombs:");
		int numBombs = Integer.parseInt(scan.nextLine());
		Game game = new Game(x,y,numBombs);
		game.run();
		scan.close();
	}
}
