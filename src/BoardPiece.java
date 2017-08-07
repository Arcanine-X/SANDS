import java.util.ArrayList;
import java.util.List;

public class BoardPiece extends Token {
	private String name = "";
	private String col = "";
	private int north;
	private int east;
	private int south;
	private int west;

	// 0 for nothing, 1 for sword, 2 for shield
	public BoardPiece(String name, int north, int east, int south, int west, String col) {
		this.name = name;
		this.north = north;
		this.east = east;
		this.west = west;
		this.south = south;
		this.col = col;
	}

	@Override
	public String toString() {
		String n = "", e = "", s = "", w = "";
		n = (north == 0) ? "" : (north == 1) ? "|" : "+";
		e = (east == 0) ? "" : (east == 1) ? "-" : "+";
		s = (south == 0) ? "" : (south == 0) ? "|" : "+";
		w = (west == 0) ? " " : (west == 1) ? "-" : "+";
		return " " + n + " \n" + w + name + e + "\n" + " " + s + " \n";
	}

	String getName() {
		return name;
	}

	String getCol() {
		return col;
	}

	int getNorth() {
		return north;
	}

	int getEast() {
		return east;
	}

	int getSouth() {
		return south;
	}

	int getWest() {
		return west;
	}

	void setName(String name) {
		this.name = name;
	}

	void setCol(String col) {
		this.col = col;
	}

	void setNorth(int north) {
		this.north = north;
	}

	void setEast(int east) {
		this.east = east;
	}

	void setSouth(int south) {
		this.south = south;
	}

	void setWest(int west) {
		this.west = west;
	}

}
