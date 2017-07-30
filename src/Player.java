import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Player extends Token {
	String name = "";
	int originalCount = 0;
	int setterCount = 0;
	BoardPiece[][] tokens = new BoardPiece[6][4];
	Stack<BoardPiece[][]> undoStack = new Stack<BoardPiece[][]>();
	List<String> movesSoFar = new ArrayList<String>();
	List<BoardPiece> rotatedPieces = new ArrayList<BoardPiece>();

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
	public boolean addToken(String letter, Player player, Board board) {
		if (checkValidCreationSpot(board, player.name) == false) {
			System.out.println("Invalid Move\nCreation Spot is already taken");
			return false;
		}
		BoardPiece tokenToAdd = null;
		tokenToAdd = find(player, letter);
		if(tokenToAdd != null) {
			if(player.name.equals("green")) {
				board.board[2][2] = tokenToAdd;
			}
			else {
				board.board[7][7] = tokenToAdd;
			}
			System.out.println("token added");
			return true;
		}
		System.out.println("returning falseeeeeeeeeeeeee");
		return false;
	}

	public BoardPiece find(Player player, String letter) {
		BoardPiece returnToken = null;
		for (int r = 0; r < tokens.length; r++) {
			for (int c = 0; c < tokens[0].length; c++) {
				if (tokens[r][c] == null) {
					continue;
				}
				if (tokens[r][c].name.equals(letter)) {
					returnToken = tokens[r][c];
					tokens[r][c] = null;
					System.out.println("All good in find returned token to add");
					return returnToken;
				}
			}
		}
		System.out.println("returning null");
		return null;
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
		System.out.println("all good in creation spot");
		return true;
	}

	public void populateTokens(List<BoardPiece> t) {
		int i = 0;
		for (int r = 0; r < tokens.length; r++) {
			for (int c = 0; c < tokens[0].length; c++) {
				tokens[r][c] = t.get(i++);
				tokens[r][c].col = name;
			}
		}
	}

	/**
	 * First finds the token, and if its found moves it in the appropriate direction
	 *
	 * @param player
	 * @param token
	 * @param direction
	 * @param board
	 * @return
	 */

	public boolean moveToken(Player player, BoardPiece token, String direction, Board board) {
		System.out.println("in move token");
		// Find the piece
		int row = 0, col = 0;
		boolean found = false;
		for (int r = 0; r < board.board.length; r++) {
			for (int c = 0; c < board.board.length; c++) {
				if (board.board[r][c] == token) {
					row = r;
					col = c;
					if (player.name.equals(token.col)) {
						System.out.println("Found token to move");

						found = true;
					}
				}
			}
		}
		if (found) {
			System.out.println(direction);
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
				System.out.println("error in movetoken()");
			}
			// if they go of the board, they go to the grave yard O_O
			if (row < 0 || row > 9 || col < 0 || col > 9) {
				return true;
			}
			board.board[row][col] = token;
			return true;
		}
		return false;
	}

	public void createRecord() {
		BoardPiece[][] record = new BoardPiece[6][4];
		for (int r = 0; r < record.length; r++) {
			for (int c = 0; c < record[0].length; c++) {
				record[r][c] = tokens[r][c];
			}
		}
		undoStack.push(record);
	}

	public void setBoard() {
		BoardPiece[][] original = undoStack.pop(); // get rid of original
		BoardPiece[][] setter = undoStack.pop();
		counter(original, setter);
		for (int r = 0; r < setter.length; r++) {
			for (int c = 0; c < setter[0].length; c++) {
				tokens[r][c] = setter[r][c];
			}
		}

	}

	/**
	 * Helper method for undo --- Counts the instances of a token on the board, and
	 * the number instances on the setter. If there are less instances on the setter
	 * that means undo took away a creation, and the player should be allowed to
	 * create another token
	 */
	public void counter(Token[][] original, Token[][] setter) {
		originalCount = 0;
		setterCount = 0;
		for (int r = 0; r < setter.length; r++) {
			for (int c = 0; c < setter[0].length; c++) {
				if (setter[r][c] instanceof BoardPiece) {
					setterCount++;
				}
				if (original[r][c] instanceof BoardPiece) {
					originalCount++;
				}
			}
		}
	}

	public int getOriginalCount() {
		System.out.println("Original Count is : " + originalCount);
		return originalCount;
	}

	public int getSetterCount() {
		System.out.println("Setter Count is : " + setterCount);
		return setterCount;
	}
}
