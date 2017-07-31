import java.util.Scanner;
public class Program {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		Game game = new Game();
		game.run();
		scan.close();
	}
}
