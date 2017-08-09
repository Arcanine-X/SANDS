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


	public boolean equals(Object o) {
		if(o instanceof BoardPiece) {
			BoardPiece bp = (BoardPiece)o;
			if(!((BoardPiece) bp).name.equals(this.name)) {
				System.out.println("init 11");
				return false;
			}
			if(((BoardPiece) bp).north!=this.north) {
				System.out.println("init 22");
				return false;
			}
			if(((BoardPiece) bp).east!=this.east) {
				System.out.println("init 33");
				return false;
			}
			if(((BoardPiece) bp).south!=this.south) {
				System.out.println("init 44");
				return false;
			}
			if(((BoardPiece) bp).west!=this.west) {
				System.out.println("init 55");
				return false;
			}
			if(!((BoardPiece) bp).col.equals(this.col)) {
				System.out.println("init 66");
				return false;
			}
		}
		System.out.println("ideal");
		return true;
	}



	public String getName() {
		return name;
	}

	public String getCol() {
		return col;
	}

	public int getNorth() {
		return north;
	}

	public int getEast() {
		return east;
	}

	public int getSouth() {
		return south;
	}

	public int getWest() {
		return west;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public void setNorth(int north) {
		this.north = north;
	}

	public void setEast(int east) {
		this.east = east;
	}

	public void setSouth(int south) {
		this.south = south;
	}

	public void setWest(int west) {
		this.west = west;
	}

}
