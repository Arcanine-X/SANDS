import java.util.ArrayList;
import java.util.List;

public class Player extends Token {
	BoardPiece greenTokens[][] = new BoardPiece[6][4];
	BoardPiece yellowTokens[][] = new BoardPiece[6][4];
	int index = 0;
	String name = "";

	public Player(String name) {
		this.name = name;
	}

	/**
	 * Adds the a token on the board
	 *
	 * @param token
	 * @param color
	 * @param board
	 * @return
	 */
	public boolean addToken(BoardPiece token, String color, Board board) {
		if (checkValidCreationSpot(board, color) == false) {
			System.out.println("Invalid Move\nCreation Spot is already taken");
			return false;
		} else {
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
	 * Method that checks that the creation spot is avaliable or not to create more
	 * tokens for the given player
	 *
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

	/**
	 * Puts all the types of tokens into greens array of tokens
	 *
	 * @param tokens
	 */
	public void populateGreenTokens(List<BoardPiece> tokens) {
		int i = 0;
		for (int r = 0; r < greenTokens.length; r++) {
			for (int c = 0; c < greenTokens[0].length; c++) {
				greenTokens[r][c] = tokens.get(i++);
				greenTokens[r][c].col = "green";
				//System.out.println(greenTokens[r][c].toString());
			}
		}
	}

	/**
	 * Puts all the types of tokens into yellows array of tokens
	 *
	 * @param tokens
	 */
	public void populateYellowTokens(List<BoardPiece> tokens) {
		int i = 0;
		for (int r = 0; r < yellowTokens.length; r++) {
			for (int c = 0; c < yellowTokens[0].length; c++) {
				yellowTokens[r][c] = tokens.get(i++);
				yellowTokens[r][c].col = "yellow";
				//System.out.println(yellowTokens[r][c].toString());
			}
		}
	}

	/**
	 * First finds the token, and if its found moves it in the appropriate direction
	 * @param player
	 * @param token
	 * @param direction
	 * @param board
	 * @return
	 */

	public boolean moveToken(Player player, BoardPiece token, String direction, Board board) {
		// Find the piece
		int row = 0, col = 0;
		boolean found = false;
		for (int r = 0; r < board.board.length; r++) {
			for (int c = 0; c < board.board.length; c++) {
				if (board.board[r][c] == token) {
					row = r;
					col = c;
					found = true;
				}
			}
		}
		if (found) {
			board.board[row][col] = null;
			if (direction.equals("up")) {
				row--;
			} else if (direction.equals("right")) {
				col++;
			} else if (direction.equals("down")) {
				row++;
			} else if (direction.equals("left")) {
				col--;
			} else {
				System.out.println("error");
			}
			board.board[row][col] = token;
			return true;
		}
		return false;
	}


	public String toString() {
		return "" + name;
	}

}
