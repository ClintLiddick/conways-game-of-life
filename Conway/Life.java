package Conway;

import java.util.Scanner;

public class Life {
	public static void main(String[] args) {
		ConwayBoard board;
		int generations = 10;
		switch (args.length) {
			case 2:
				generations = Integer.parseInt(args[1]);
			case 1:
				board = new ConwayBoard(Integer.parseInt(args[0]));
				break;
			default:
				board = new ConwayBoard();
		}

		Scanner scn = new Scanner(System.in);
		board.printBoard();
		for (int i=0; i<generations; i++) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) { }
			board.updateBoard();
			board.printBoard();
		}
	}
}