import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Player extends Token {
	private String name = "";
	private int originalCount = 0;
	private int setterCount = 0;
	private BoardPiece[][] tokens = new BoardPiece[6][4];
	private Stack<BoardPiece[][]> undoStack = new Stack<BoardPiece[][]>();
	private List<String> movesSoFar = new ArrayList<String>(); // contains both rotated and moved pieces
	private List<BoardPiece> everyMovement = new ArrayList<BoardPiece>();

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
		if (tokenToAdd != null) {
			if (player.name.equals("green")) {
				board.getBoard()[2][2] = tokenToAdd;
			} else {
				board.getBoard()[7][7] = tokenToAdd;
			}
			System.out.println("token added");
			return true;
		}
		System.out.println("returning falseeeeeeeeeeeeee");
		return false;
	}

	public boolean Hax(String lettera, String letterb, String letterc, Player player, Board board) {
		BoardPiece tokenToAddA = null, tokenToAddB = null, tokenToAddC = null;
		//tokenToAddA = find(player, "c");
		//tokenToAddB = find(player, "x");
		//tokenToAddC = find(player, "g");
		//BoardPiece tokenToAddD = find(player, "g");
		// BoardPiece tokenToAddD = find(player, "k");
		System.out.println("letter a is " + lettera);
		//board.board[7][7] = tokenToAddA;
		//board.board[9][7] = tokenToAddB;
		//board.board[8][7] = tokenToAddC;
		// board.board[7][8] = tokenToAddD;
		System.out.println("token added");
		return true;

	}

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
			if (board.getBoard()[2][2] instanceof BoardPiece) {
				return false;
			}
		}
		if (color.equals("yellow")) {
			if (board.getBoard()[7][7] instanceof BoardPiece) {
				return false;
			}
		}
		System.out.println("all good in creation spot");
		return true;
	}

	public void populateTokens(Player player, List<BoardPiece> t) {
		int i = 0;
		for (int r = 0; r < tokens.length; r++) {
			for (int c = 0; c < tokens[0].length; c++) {
				tokens[r][c] = t.get(i++);
				tokens[r][c].setCol(name);
			}
		}
	}

	public void checkForSpace(Player player, BoardPiece token, String dir, Board board) {
		System.out.println("CheckForSpace");
		int c = board.getX(token.getName());
		int r = board.getY(token.getName());
		int count = 0;
		if (dir.equals("up")) {
			if(r - 1 < 0) {
				board.getBoard()[r][c] = null;
			}
			else if (!(board.getBoard()[r - 1][c] instanceof BoardPiece) && !(r - 1 < 0)) {
				board.getBoard()[r][c] = null;
				r--;
				board.getBoard()[r][c] = token;
			} else { // requires shifting
				for (int i = r -1, j = 0; i >= 0; i--, j++) {
					if (board.getBoard()[i][c] instanceof BoardPiece && count == j) {
						count++;
					}
				}
				if (count != 0) {
					for (int i = r - count; i <= r; i++) {
						if(i - 1 < 0) {
							board.getBoard()[i][c] = null;
						}
						else {
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
}
