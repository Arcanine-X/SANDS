
public class Pair {
	private BoardPiece one;
	private BoardPiece two;
	private String dir = "";

	public Pair(BoardPiece one, BoardPiece two, String dir) {
		this.one = one;
		this.two = two;
		this.dir = dir;
	}

	@Override
	public String toString() {
		String returnString = "";
		if (dir.equals("hori")) {
			if (one.getEast() == 1 && two.getWest() == 0) { // sword v nothing
				returnString = two.getName() + " died, due to " + one.getName() + "'s Sword, vs Nothing.";
			} else if (one.getEast() == 1 && two.getWest() == 1) { // sword v sword
				returnString = one.getName() + " and " + two.getName() + " died, due to Sword vs Sword.";
			} else if (one.getEast() == 1 && two.getWest() == 2) { // sword v shield
				returnString = one.getName() + " got pushed back from " + two.getName() + "'s shield";
			} else if (one.getEast() == 0 && two.getWest() == 1) { // nothing v sword
				returnString = one.getName() + " died, due to " + two.getName() + "'s Sword, vs Nothing.";
			} else if (one.getEast() == 2 && two.getWest() == 1) { // shield v sword
				returnString = two.getName() + " got pushed back from " + one.getName() + "'s shield";
			} else { // something went wrong
				System.out.println("Something went wrong");
			}
		} else if (dir.equals("vert")) {
			if (one.getSouth() == 1 && two.getNorth() == 0) { // sword v nothing
				returnString = two.getName() + " died, due to " + one.getName() + "'s Sword, vs Nothing.";
			} else if (one.getSouth() == 1 && two.getNorth() == 1) { // sword v sword
				returnString = one.getName() + " and " + two.getName() + " died, due to Sword vs Sword.";
			} else if (one.getSouth() == 1 && two.getNorth() == 2) { // sword v shield
				returnString = one.getName() + " got pushed back from " + two.getName() + "'s shield";
			} else if (one.getSouth() == 0 && two.getNorth() == 1) { // nothing v sword
				returnString = one.getName() + " died, due to " + two.getName() + "'s Sword, vs Nothing.";
			} else if (one.getSouth() == 2 && two.getNorth() == 1) { // shield v sword
				returnString = two.getName() + " got pushed back from " + one.getName() + "'s shield";
			} else { // something went wrong
				System.out.println("Something went wrong");
			}
		}else { // something went wrong
			System.out.println("Something went wrong");
		}
		return returnString;
	}

	public BoardPiece getOne() {
		return one;
	} 

	public BoardPiece getTwo() {
		return two;
	}

	public String getDir() {
		return dir;
	}
}
