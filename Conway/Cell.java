package Conway;

import java.util.ArrayList;
import java.util.List;

class Cell implements Comparable<Cell> {
	CellState state;
	List<Cell> adj;
	int x, y; // (x,y) coordinates

	Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.state = CellState.DEAD;
		adj = new ArrayList<Cell>(8);
	}

	int numAliveAdjacent() {
		int alive = 0;
		for (Cell c : adj) {
			if (c.state == CellState.ALIVE || c.state == CellState.DYING)
				alive++;
		}
		return alive;
	}

	public int compareTo(Cell o) {
		int x = 0;
		if (this.y > o.y || (this.y == o.y && this.x > o.x))
			x = 1;
		else if (this.y < o.y || (this.y == o.y && this.x < o.x))
			x = -1;
		return x;
	}

	public boolean equals(Object obj) {
		Cell o = (Cell) obj;
		return this.x == o.x && this.y == o.y;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("("+x+","+y+") "+state+", Adj: ");
		for (Cell c : this.adj) {
			sb.append("("+c.x+","+c.y+") ");
		}
		return sb.toString();
	}
}

enum CellState { GROWING, ALIVE, DYING, DEAD };