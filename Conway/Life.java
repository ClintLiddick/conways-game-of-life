package Conway;

public class Life {
	public static void main(String[] args) {
		ConwayBoard board;
		if (args.length == 1) {
			board = new ConwayBoard(Integer.parseInt(args[0]));
		} else {
			board = new ConwayBoard();
		}
		board.printBoard();
		board.markForDeath();
		System.out.println(board.showCells());
		board.killCells();
		System.out.println(board.showCells());
		board.printBoard();
	}
}