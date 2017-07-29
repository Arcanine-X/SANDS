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
		//north
		if(north == 0) {
			n= "";
		}
		else if(north == 1) {
			n = "|";
		}
		else {
			n = "+";
		}
		//east
		if(east == 0) {
			e= "";
		}
		else if(east == 1) {
			e = "-";
		}
		else {
			e = "+";
		}
		//south
		if(south == 0) {
			s= "";
		}
		else if(south == 1) {
			s = "|";
		}
		else {
			s = "+";
		}
		//west
		if(west == 0) {
			w= " ";
		}
		else if(west == 1) {
			w = "-";
		}
		else {
			w = "+";
		}
		return " " + n + " \n" + w + name + e + "\n" + " " + s + " \n";
	}


}
