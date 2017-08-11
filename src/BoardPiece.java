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

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		result = prime * result + east;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + north;
		result = prime * result + south;
		result = prime * result + west;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardPiece other = (BoardPiece) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
			return false;
		if (east != other.east)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (north != other.north)
			return false;
		if (south != other.south)
			return false;
		if (west != other.west)
			return false;
		return true;
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
