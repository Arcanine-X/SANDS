import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * This class represents the player, and is a type of a token. The player class
 * holds it own collection of tokens, and their own grave yard.
 *
 * @author Chin Patel
 *
 */

public class Player extends Token {
	private String name = "";
	private int originalCount = 0;
	private int setterCount = 0;
	private BoardPiece[][] tokens = new BoardPiece[6][4];
	private BoardPiece[][] graveYard = new BoardPiece[3][8];
	private Stack<BoardPiece[][]> undoStack = new Stack<BoardPiece[][]>();
	private List<String> movesSoFar = new ArrayList<String>(); // contains both rotated and moved pieces
	private List<BoardPiece> everyMovement = new ArrayList<BoardPiece>();
	private List<BoardPiece> listOfTokens = new ArrayList<BoardPiece>();
	List<BoardPiece> differences = new ArrayList<BoardPiece>(); 

	/**
	 * Constructor which takes the player name, (yellow/green).
	 *
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * This method adds the token to the board. It checks if the creation spot is
	 * not already taken. If it is it will return false, otherwise try to find the
	 * token in the players set of token, if it exists it will add the token to the
	 * board, and return true. Otherwise it will return false.
	 *
	 * @param letter
	 *            --- letter of the token which is intended to be added
	 * @param player
	 *            --- the player in whose token we are adding
	 * @param board
	 *            --- the current board
	 * @return
	 */
	public boolean addToken(String letter, Player player, Board board) {
		if (checkValidCreationSpot(board, player.name) == false) { // Check if the creation spot is already taken or not
			System.out.println("Invalid Move\nCreation Spot is already taken");
			return false;
		}
		BoardPiece tokenToAdd = null;
		tokenToAdd = find(player, letter);
		if (tokenToAdd != null) {
			if (player.name.equals("green")) { // Add it to the correct creation spot
				board.getBoard()[2][2] = tokenToAdd;
			} else {
				board.getBoard()[7][7] = tokenToAdd;
			}
			return true;
		}
		return false;
	}

	public boolean Hax(Player player, Board board) {
		BoardPiece one = find(player, "e");
		BoardPiece two = find(player, "t");
		BoardPiece three = find(player, "c");
		BoardPiece four = find(player, "g");
		BoardPiece five = find(player, "i");
		board.getBoard()[3][9] = one;
		board.getBoard()[4][9] = two;
		board.getBoard()[5][9] = three;
		board.getBoard()[7][8] = four;
		board.getBoard()[7][9] = five;
		System.out.println("token added");
		return true;

	}

	/**
	 * This method goes through the players set of tokens and tries to find the
	 * letter, which corresponds to the token which it returns.
	 *
	 * @param player
	 * @param letter
	 * @return
	 */
	// TODO --- make private and remove player??
	public BoardPiece find(Player player, String letter) {
		BoardPiece returnToken = null;
		for (int r = 0; r < tokens.length; r++) {
			for (int c = 0; c < tokens[0].length; c++) {
				if (tokens[r][c] == null) {
					continue;
				}
				if (tokens[r][c].getName().equals(letter)) {
					returnToken = tokens[r][c];
					tokens[r][c] = null;
					return returnToken;
				}
			}
		}
		return null;
	}

	/**
	 * Helper method that checks if the creation spot is available to create more
	 * tokens or not for the given player. Returns true/false accordingly.
	 *
	 * @param board
	 * @param color
	 *            --- color representing the player (i.e yellow/green)
	 * @return
	 */
	public boolean checkValidCreationSpot(Board board, String color) {
		if (color.equals("green")) { // greens creation spot
			if (board.getBoard()[2][2] instanceof BoardPiece) {
				return false;
			}
		}
		if (color.equals("yellow")) { // yellows creation spot
			if (board.getBoard()[7][7] instanceof BoardPiece) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method which fills up each players tokens from the hard coded list of tokens.
	 *
	 * @param player
	 * @param toks
	 *            --- hard coded tokens
	 */
	public void populateTokens(Player player, List<BoardPiece> toks) {
		int i = 0; // index for the tokens list
		for (int r = 0; r < tokens.length; r++) {
			for (int c = 0; c < tokens[0].length; c++) {
				tokens[r][c] = toks.get(i++);
				tokens[r][c].setCol(name);
			}
		}
	}

	// TODO - split into 4 methods
	public void tryMoveToken(Player player, BoardPiece token, String dir, Board board) {
		int c = board.getX(token.getName());
		int r = board.getY(token.getName());
		int count = 0;
		if (dir.equals("up")) {
			if (r - 1 < 0) {
				board.getBoard()[r][c] = null;
			} else if (!(board.getBoard()[r - 1][c] instanceof BoardPiece) && !(r - 1 < 0)) {
				board.getBoard()[r][c] = null;
				r--;
				board.getBoard()[r][c] = token;
			} else { // requires shifting
				for (int i = r - 1, j = 0; i >= 0; i--, j++) {
					if (board.getBoard()[i][c] instanceof BoardPiece && count == j) {
						count++;
					}
				}
				if (count != 0) {
					for (int i = r - count; i <= r; i++) {
						if (i - 1 < 0) {
							board.getBoard()[i][c] = null;
						} else {
							board.getBoard()[i - 1][c] = board.getBoard()[i][c];
						}
					}
					board.getBoard()[r][c] = null;
				}
			}
		} else if (dir.equals("down")) {
			if (r + 1 > 9) {
				board.getBoard()[r][c] = null;
			} else if (!(board.getBoard()[r + 1][c] instanceof BoardPiece) && !(r + 1 > 9)) {
				board.getBoard()[r][c] = null;
				r++;
				board.getBoard()[r][c] = token;
			} else {
				for (int i = r + 1, j = 0; i < board.getBoard().length; i++, j++) {
					if (board.getBoard()[i][c] instanceof BoardPiece && count == j) {
						count++;
					}
				}
				if (count != 0) {
					for (int i = r + count; i >= r; i--) {
						if (i + 1 > 9) {
							board.getBoard()[i][c] = null;
						} else {
							board.getBoard()[i + 1][c] = board.getBoard()[i][c];
						}
					}
					board.getBoard()[r][c] = null;
				}
			}
		} else if (dir.equals("left")) {
			if (c - 1 < 0) {
				board.getBoard()[r][c] = null;
			} else if (!(board.getBoard()[r][c - 1] instanceof BoardPiece) && !(c - 1 < 0)) {
				board.getBoard()[r][c] = null;
				c--;
				board.getBoard()[r][c] = token;
			} else {
				for (int i = c - 1, j = 0; i >= 0; i--, j++) {
					if (board.getBoard()[r][i] instanceof BoardPiece && count == j) {
						count++;
					}
				}
				if (count != 0) {
					for (int i = c - count; i <= c; i++) {
						if (i - 1 < 0) {
							board.getBoard()[r][i] = null;
						} else {
							board.getBoard()[r][i - 1] = board.getBoard()[r][i];
						}
					}
					board.getBoard()[r][c] = null;
				}
			}
		} else if (dir.equals("right")) {
			if (c + 1 > 9) {
				board.getBoard()[r][c] = null;
			} else if (!(board.getBoard()[r][c + 1] instanceof BoardPiece) && !(c + 1 > 9)) {
				board.getBoard()[r][c] = null;
				c++;
				board.getBoard()[r][c] = token;
			} else {
				for (int i = c + 1, j = 0; i < board.getBoard().length; i++, j++) {
					if (board.getBoard()[r][i] instanceof BoardPiece && count == j) {
						count++;
					}
				}
				if (count != 0) {
					for (int i = c + count; i >= c; i--) {
						if (i + 1 > 9) {
							board.getBoard()[r][i] = null;
						} else {
							board.getBoard()[r][i + 1] = board.getBoard()[r][i];
						}
					}
					board.getBoard()[r][c] = null;
				}
			}
		} else {
			System.out.println("error");
		}
	}

	/**
	 * Creates a copy of the players tokens, and pushes it in the stack. This stack
	 * is popped when the user wants to undo.
	 */
	public void createRecord() {
		BoardPiece[][] record = new BoardPiece[6][4];
		for (int r = 0; r < record.length; r++) {
			for (int c = 0; c < record[0].length; c++) {
				record[r][c] = tokens[r][c];
			}
		}
		undoStack.push(record);
	}

	/**
	 * Sets the board to the previous version of the players tokens. This method is
	 * called every time after the user wants to undo, to copy over the old contents
	 * to the latest player tokens.
	 */
	public void setBoard() {
		BoardPiece[][] original = undoStack.pop(); // get rid of original
		BoardPiece[][] setter = undoStack.pop(); // this is the old copy we want to use
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

	/**
	 * This method figures out what should be in the grave yard. This is done by
	 * going through the players tokens, and through the boards to figure out which
	 * tokens are missing from the original list of 24 tokens. These missing tokens
	 * are then added to the differences list.
	 *
	 * @param board
	 */
	public void updateGraveyard(Token[][] board) {
		// TODO check for names specifically maybe? not sure
		// check differences between board and players tokens and add them to graveyard
		List<BoardPiece> boardTokens = new ArrayList<BoardPiece>();
		List<BoardPiece> playerTokens = new ArrayList<BoardPiece>();
		// get all the tokens that are on the board
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				if (board[r][c] instanceof BoardPiece) {
					boardTokens.add((BoardPiece) board[r][c]);
				}
			}
		}
		// get all the tokens that are in the players 2D array of tokens
		for (int r = 0; r < tokens.length; r++) {
			for (int c = 0; c < tokens[0].length; c++) {
				if (tokens[r][c] instanceof BoardPiece) {
					playerTokens.add((BoardPiece) tokens[r][c]);
				}
			}
		}
		// find what's missing from the board and players tokens
		for (BoardPiece bp : listOfTokens) {
			if (!boardTokens.contains(bp) && !playerTokens.contains(bp)) {
				differences.add(bp);
			}
		}
	}

	/**
	 * Clears the grave yards by setting everything in it to null.
	 */
	public void clearGraveYards() {
		for (int r = 0; r < graveYard.length; r++) {
			for (int c = 0; c < graveYard[0].length; c++) {
				graveYard[r][c] = null;
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

	public String getName() {
		return name;
	}

	public List<String> getMovesSoFar() {
		return movesSoFar;
	}

	public List<BoardPiece> getEveryMovement() {
		return everyMovement;
	}

	public BoardPiece[][] getTokens() {
		return tokens;
	}

	public Stack<BoardPiece[][]> getUndoStack() {
		return undoStack;
	}

	public BoardPiece[][] getGraveYard() {
		return graveYard;
	}

	public void setListOfTokens(List<BoardPiece> listOfTokens) {
		this.listOfTokens = listOfTokens;
	}
}
