import java.util.Random;
import java.util.Scanner;
import java.lang.Thread;
public class Game implements Runnable{
	
	Cell[][] Cells;
	Scanner scan = Program.scan;
	int x;
	int y;
	int XofRandom;
	int YofRandom;
	boolean gameover = false;
	int numBombs;
	int bombsDefused = 0;
	Random r = new Random();
	
	public Game(int x, int y,int numBombs) {
		this.numBombs = numBombs;
		//The Cells are generated
		Cells = new Cell[x][y];
		for (int yKoordinate = 0; yKoordinate < Cells.length; yKoordinate++) {

			for (int xKoordinate = 0 ;xKoordinate < Cells[yKoordinate].length; xKoordinate++) {
				Cells[yKoordinate][xKoordinate] = new Cell();
			}
		}
		
		//Cells are Randomly selected as Bombs
		for (int i = 0; i < numBombs; i++) {
			XofRandom = r.nextInt(x);
			YofRandom = r.nextInt(y);
			if(Cells[YofRandom][XofRandom].Bomb == true) {
				// if the randomly selected Cell is already a bomb
				// the loop is executed again
				i--;
			}
			else {
				Cells[YofRandom][XofRandom].Bomb = true;
			}
		}
	}

	@Override
	public void run() {
		int bombsDefused = 0;
		do {
			renderCells();
			
			//if gameover == true the gameover screen will be displayed and the gameloop will stop.
			if(gameover == true) {
				System.out.println("- GAMEOVER -");
				scan.nextLine();
				break;
			}
			//if the number of defused bombs equals the number of bombs generated
			// the victory screen is displayed and the gameloop will stop.
			else if (bombsDefused == numBombs) {
				System.out.println("- YOU WIN -");
				scan.nextLine();
				break;
			}
			// if not the user is taking his next turn.
			else {
				nextTurn();
			}
		} while (true);
	}
	
	
	/**Enables the user to either flag or dig a cell by using x and y coordinates from user input.
	 * This only changes the Data and does not render any cells.
	 */
	private void nextTurn() {
		System.out.println("Flag(P) Dig(D)");
		switch (scan.nextLine()) {
		case "p":
		case "P":
			System.out.print("X:");
			x = getIntfromInput(0, Cells[0].length,"The specified Coordinate does not exist.\\nTry again:");
			System.out.print("Y:");
			y = getIntfromInput(0, Cells.length,"The specified Coordinate does not exist.\nTry again:");
			//Flagging a flagged Cell removes the Flag and ends the turn
			if(Cells[y][x].Flagged == true) {Cells[y][x].Flagged = false; break;}
			Cells[y][x].Flagged = true;
			break;
		case "D":
		case "d":
			System.out.print("X:");
			x = getIntfromInput(0, Cells[0].length - 1,"The specified Coordinate does not exist.\nTry again:");
			System.out.print("Y:");
			y = getIntfromInput(0, Cells.length - 1,"The specified Coordinate does not exist.\nTry again:");
			//Digging a flagged Cell is not possible and ends the turn
			if(Cells[y][x].Flagged == true) {break;}
			//Digging an open Cell is not possible and ends the turn
			else if(Cells[y][x].Hidden == false) {break;}
			openCell(x,y);
		default:
			break;
		}
	
	}
	
	
	/**Draws the output of  
	 * Cell.looks() : char
	 * for every Cell in the Cells Array to the Commandline. 
	 */
	private void renderCells() {
		// The Cells are surrounded by Integers for ease of orientation in the Cells Array.
		System.out.print(' ');
		for(int xKoordinate = 0; xKoordinate < Cells[0].length; xKoordinate++) {
			//Draws the Indices of the X-Axis
			System.out.print(xKoordinate);
		}
		System.out.println();

		
		//The Cells are Iterated through a for loop and then Drawn to the commandline
		for (int yKoordinate = 0; yKoordinate < Cells.length; yKoordinate++) {
			//Draws the Indices of the Y-Axis
			System.out.print(yKoordinate);

			for (Cell feld : Cells[yKoordinate]) {
				System.out.print(feld.look());
				// When a bomb('O') is drawn the gameover flag is set to true.
				if(feld.look() == 'O')
					gameover = true;
				//if a bomb is flagged the bombsDefused Counter is incremented.
				else if (feld.Flagged == true && feld.Bomb == true) {
					bombsDefused++;
				}
			}
			System.out.println();
		}
	}
	
	/**Takes the next input from the System.in stream and 
	 * Converts it to an integer as long as the Input does not match the specifications and 
	 * finally returns that Input as an Integer.
	 * @param min The smallest Integer accepted as input
	 * @param max The largest Integer accepted as input
	 * @param message The message that is displayed if the integer is not within the specified bounds
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
				System.out.print("Error: The entered number is not a valid integer.\nTry again:");
			}
		} while (true);
	}
	
	//TODO Algorithm to iterate through all neighboring cells in a circle and open them as well.
	private void openCell(int x, int y) {
		//First count the bombs around the selected Cell
		for (int tmpY = y - 1; tmpY < y - 1; tmpY++) {
			for (int tmpX = x - 1; tmpX < x + 2; tmpX++) {
				if (tmpX == x && tmpY == y) {
					continue;
				}
				else if(Cells[tmpY][tmpX].Bomb == true){
					Cells[y][x].neighboringBombs++;		
				}
				
			}
			
		}
		//if there are no Bombs 
		//open all surrounding Cells
		if(Cells[y][x].neighboringBombs != 0) {
			for (int tmpY = y - 1; tmpY < y - 1; tmpY++) {
				for (int tmpX = x - 1; tmpX < x + 2; tmpX++) {
					if (tmpX == x && tmpY == y) {
						continue;
					}
					else if(Cells[tmpY][tmpX].Hidden == true){
						openCell(tmpX, tmpY);
					}
					
				}
				
			}
		}
		Cells[y][x].Hidden = false;
	}
}
