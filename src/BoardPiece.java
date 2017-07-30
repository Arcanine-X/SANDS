import java.util.ArrayList;
import java.util.List;

public class BoardPiece extends Token{
	String name = "", col = "";
	int north, east, south, west;
	//0 for nothing, 1 for sword, 2 for shield
	public BoardPiece(String name,int north, int east, int south, int west, String col) {
		this.name = name;
		this.north = north;
		this.east = east;
		this.west = west;
		this.south = south;
		this.col = col;
	}

	@Override
	public String toString() {
		String n ="" , e = "" , s = "" , w = "";
		n = (north == 0) ? "" : (north == 1) ? "|" : "+";
		e = (east == 0) ? "" : (east == 1) ? "-" : "+";
		s = (south == 0) ? "" : (south == 0) ? "|" : "+";
		w = (west == 0) ? " " : (west == 1) ? "-" : "+";
		return " " + n + " \n" + w + name + e + "\n" + " " + s + " \n";
	}
}

