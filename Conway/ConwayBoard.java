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

	ConwayBoard(int width, int height) {
		this.board = new LinkedList<Cell>();
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				this.insertCell(new Cell(x,y));
			}
		}
	}

	ConwayBoard(int seed_cells) {
		this(BOARD_WIDTH,BOARD_HEIGHT);
		// seed random cells
		Random rand = new Random();
		for (int i=0; i<seed_cells; i++) {
			int index = rand.nextInt(board.size());
			this.board.get(index).state = CellState.ALIVE;
		}
	}

	ConwayBoard() {
		this(BOARD_WIDTH,BOARD_HEIGHT);
		// default seed
		// this.insertCell(new Cell(30,15));
		// this.insertCell(new Cell(31,15));
		// this.insertCell(new Cell(30,16));
		// this.insertCell(new Cell(32,15));
		// this.insertCell(new Cell(30,19));
		this.board.get(200).state = CellState.ALIVE;		
		this.board.get(201).state = CellState.ALIVE;		
		this.board.get(202).state = CellState.ALIVE;		
		// this.board.get(280).state = CellState.ALIVE;		
	}

	public void updateBoard() {
		this.markForBirth();
		this.markForDeath();
		this.killCells();
		this.spawnCells();
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

	private void markForDeath() {
		for (Cell c : board) {
			if (c.state == CellState.ALIVE) {
				int alive = c.numAliveAdjacent();
				if (alive > 3 || alive < 2) {
					c.state = CellState.DYING;
				}
			}
		}
	}

	private void killCells() {
		// ListIterator<Cell> itr1 = board.listIterator();
		// while (itr1.hasNext()) {
		for (Cell c : this.board) {
			// Cell c = itr1.next();
			if (c.state == CellState.DYING)
				c.state = CellState.DEAD;
			// ListIterator<Cell> itr2 = c.adj.listIterator();
			// while (itr2.hasNext()) {
			// 	Cell linked = itr2.next();
			// 	if (linked.state == CellState.DYING)
			// 		;
			// }
		}
	}

	private void markForBirth() {
		for (Cell c : this.board) {
			if (c.state == CellState.DEAD && c.numAliveAdjacent() == 3) {
				c.state = CellState.GROWING;
			}
		}
	}

	private void spawnCells() {
		for (Cell c : this.board) {
			if (c.state == CellState.GROWING)
				c.state = CellState.ALIVE;
		}
	}

	public void printBoard() {
		System.out.println(this.toString());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		ListIterator<Cell> itr = this.board.listIterator();
		Cell curr;
		int index = -1;
		while(itr.hasNext()) {
			curr = itr.next();
			index++;
			if (curr.state == CellState.ALIVE)
				sb.append("O");
			else
				sb.append(" ");

			if (index % 80 == 0)
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

enum Patterns {BLINKER, TOAD, BEACON, PULSAR, GLIDER, LWSS};
