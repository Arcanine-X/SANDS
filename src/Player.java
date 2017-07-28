
public class Player extends Token {
	BoardPiece piecesPlayed[] = new BoardPiece[24];
	BoardPiece piecesAvaliable[][] = new BoardPiece[6][4];
	int index = 0;
	String name = "";

	public Player(String name) {
		this.name = name;
	}

	public boolean addToken(BoardPiece token, String color, Board board) {
		if(checkValidCreationSpot(board, color)==false){
			System.out.println("Invalid Move\nCreation Spot is already taken");
			return false;
		}
		else {
			if (color.equals("green")) {
				if (checkValidCreationSpot(board, color)) {
					board.board[2][2] = token;
					return true;
				}
			} else {
				if (checkValidCreationSpot(board, color)) {
					board.board[7][7] = token;
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkValidCreationSpot(Board board, String color) {
		if (color.equals("green")) {
			if (board.board[2][2] instanceof BoardPiece) {
				return false;
			}
		}
		if (color.equals("yellow")) {
			if (board.board[7][7] instanceof BoardPiece) {
				return false;
			}
		}
		return true;
	}

	public void populatePieces() {

	}

	public void placeBoardPiece(BoardPiece piece) {
		piecesPlayed[index] = piece;
		index++;
	}

	public String toString() {
		return "" + name;
	}

}
