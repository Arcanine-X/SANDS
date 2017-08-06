
public class Pair {
	Board board;
	BoardPiece one;
	BoardPiece two;
	String dir = "";
	public Pair(BoardPiece one, BoardPiece two, String dir, Board board) {
		this.one = one;
		this.two = two;
		this.dir = dir;
		this.board = board;
	}

	@Override
	public String toString() {
		if(dir.equals("vert")) {


		}
		else { //dir is hori

		}
		return "";
	}

}
