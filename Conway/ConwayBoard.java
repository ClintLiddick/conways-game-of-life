package Conway;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

class ConwayBoard {
	private static final int BOARD_WIDTH = 80;
	private static final int BOARD_HEIGHT = 30;	
	private List<Cell> board;

	ConwayBoard(int seed_cells) {
		this.board = new LinkedList<Cell>();
		// seed random cells
		Random rand = new Random();
		for (int i=0; i<seed_cells; i++) {
			Cell newCell = new Cell(rand.nextInt(BOARD_WIDTH),
									rand.nextInt(BOARD_HEIGHT));
			this.insertCell(newCell);
		}
	}

	ConwayBoard() {
		this.board = new LinkedList<Cell>();
		// default seed
		this.insertCell(new Cell(30,15));
		this.insertCell(new Cell(31,15));
		this.insertCell(new Cell(30,16));
		this.insertCell(new Cell(32,15));
		this.insertCell(new Cell(30,19));		
	}

	// inserts a cell into the board array in sorted order
	private void insertCell(Cell newCell) {
		ListIterator<Cell> itr = this.board.listIterator();
		Cell curr;
		int index;
		if (itr.hasNext()) {
			curr = itr.next();
			index = 0;
			while (newCell.compareTo(curr) > 0) {
				if (itr.hasNext()) {
					curr = itr.next();
					index++;
				} else {
					index++;
					break;
				}
			}
			board.add(index,newCell); // add before current cursor
		} else {
			board.add(newCell); // no Cells to begin with
		}
		
		connectAdjCells(newCell);
	}

	private void connectAdjCells(Cell newCell) {
		Iterator<Cell> itr = board.listIterator();
		Cell curr; // at least 1 cell in list.
		while (itr.hasNext()) {
			curr = itr.next();
			if (!curr.equals(newCell) && 
				Math.abs(curr.x - newCell.x) <= 1 && 
				Math.abs(curr.y - newCell.y) <= 1) {
				curr.adj.add(newCell);
				newCell.adj.add(curr);
			}
		}
	}

	public void markForDeath() {
		for (Cell c : board) {
			int size = c.adj.size();
			if (size > 3 || size < 2) {
				c.state = CellState.DYING;
			}
		}
	}

	public void killCells() {
		ListIterator<Cell> itr1 = board.listIterator();
		while (itr1.hasNext()) {
			Cell c = itr1.next();
			if (c.state == CellState.DYING) {
				itr1.remove();
			}
			ListIterator<Cell> itr2 = c.adj.listIterator();
			while (itr2.hasNext()) {
				Cell linked = itr2.next();
				if (linked.state == CellState.DYING)
					itr2.remove();
			}
		}
	}

	public void printBoard() {
		System.out.println(this.toString());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		// int x = 0, y = 0;
		ListIterator<Cell> itr = this.board.listIterator();
		Cell curr;
		if (itr.hasNext()) curr = itr.next();
		else curr = null;
		for (int y=0; y<BOARD_HEIGHT; y++) {
			for (int x=0; x<BOARD_WIDTH; x++) {
				if (curr != null && curr.y == y && curr.x == x) {
					sb.append("O");
					if (itr.hasNext()) curr = itr.next();
					else curr = null;
				} else {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public String showCells() {
		StringBuilder sb = new StringBuilder();
		for (Cell c : board) {
			sb.append(c + "\n");
		}
		return sb.toString();
	}
}