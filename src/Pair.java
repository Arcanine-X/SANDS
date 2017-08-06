
public class Pair {
	Board board;
	BoardPiece one;
	BoardPiece two;
	String dir = "";

	public Pair(BoardPiece one, BoardPiece two, String dir) {
		this.one = one;
		this.two = two;
		this.dir = dir;
	}

	@Override
	public String toString() {
		String returnString = "";
		if (dir.equals("hori")) {
			if (one.east == 1 && two.west == 0) { // sword v nothing
				returnString = two.name + " died, due to " + one.name + "'s Sword, vs Nothing. ";
			} else if (one.east == 1 && two.west == 1) { // sword v sword
				returnString = one.name + " and " + two.name + " died, due to Sword vs Sword. ";
			} else if (one.east == 1 && two.west == 2) { // sword v shield
				returnString = one.name + " got pushed back from " + two.name + "'s shield";
			} else if (one.east == 0 && two.west == 1) { // nothing v sword
				returnString = one.name + " died, due to " + two.name + "'s Sword, vs Nothing. ";
			} else if (one.east == 2 && two.west == 1) { // shield v sword
				returnString = two.name + " got pushed back from " + one.name + "'s shield";
			} else { // something went wrong
				System.out.println("Something went wrong");
			}
		} else if (dir.equals("vert")) {
			if (one.south == 1 && two.north == 0) { // sword v nothing
				returnString = two.name + " died, due to " + one.name + "'s Sword, vs Nothing. ";
			} else if (one.south == 1 && two.north == 1) { // sword v sword
				returnString = one.name + " and " + two.name + " died, due to Sword vs Sword. ";
			} else if (one.south == 1 && two.north == 2) { // sword v shield
				returnString = one.name + " got pushed back from " + two.name + "'s shield";
			} else if (one.south == 0 && two.north == 1) { // nothing v sword
				returnString = one.name + " died, due to " + two.name + "'s Sword, vs Nothing. ";
			} else if (one.south == 2 && two.north == 1) { // shield v sword
				returnString = two.name + " got pushed back from " + one.name + "'s shield";
			} else { // something went wrong
				System.out.println("Something went wrong");
			}
		}else { // something went wrong
			System.out.println("Something went wrong");
		}
		return returnString;
	}
}
