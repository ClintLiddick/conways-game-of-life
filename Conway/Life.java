package Conway;

import java.util.Scanner;

public class Life {
	public static void main(String[] args) {
		ConwayBoard board;
		if (args.length == 1) {
			board = new ConwayBoard(Integer.parseInt(args[0]));
		} else {
			board = new ConwayBoard();
		}
		Scanner scn = new Scanner(System.in);
		for (int i=0; i<10; i++) {
			board.printBoard();
			scn.nextLine(); // pause
			board.updateBoard();
			board.printBoard();
		}
	}
}