import java.util.ArrayList;
import java.util.List;

public class Player extends Token {
	BoardPiece piecesPlayed[] = new BoardPiece[24];
	BoardPiece piecesGreenAvaliable[][] = new BoardPiece[6][4];
	BoardPiece piecesYellowAvaliable[][] = new BoardPiece[6][4];
	int index = 0;
	String name = "";

	public Player(String name) {
		this.name = name;
	}


	/**
	 * Adds the a token on the board
	 * @param token
	 * @param color
	 * @param board
	 * @return
	 */
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

	/**
	 * Method that checks that the creation spot is avaliable or not to create
	 * more tokens for the given player
	 * @param board
	 * @param color
	 * @return
	 */
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

	public void populateGreenTokens() {

	}

	public void populateYellowTokens(List tokens) {
		for(int r = 0; r < piecesYellowAvaliable[0].length; r++) {
			for(int c = 0; c < piecesYellowAvaliable.length; c++) {
				piecesYellowAvaliable[r][c] = (BoardPiece) tokens.get(r + (tokens.size() * r));
			}
		}
	}



	public boolean moveToken(Player player, BoardPiece token, String direction, Board board) {
		//Find the piece
		int row =0, col = 0;
		boolean found = false;
		for(int r = 0; r < board.board.length; r++) {
			for(int c = 0; c < board.board.length; c++) {
				if(board.board[r][c] == token){
					row = r;
					col = c;
					found = true;
				}
			}
		}
		if(found) {
			board.board[row][col] = null;
			if(direction.equals("up")) {
				row--;
			}
			else if(direction.equals("right")) {
				col++;
			}
			else if(direction.equals("down")) {
				row++;
			}
			else if(direction.equals("left")) {
				col--;
			}
			else {
				System.out.println("error");
			}
			board.board[row][col] = token;
			return true;
		}
		return false;
	}



	public void placeBoardPiece(BoardPiece piece) {
		piecesPlayed[index] = piece;
		index++;
	}

	public String toString() {
		return "" + name;
	}

}
