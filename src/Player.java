import java.util.List;
import java.util.Stack;

public class Player extends Token {
	Stack<BoardPiece[][]> undoStackG = new Stack<BoardPiece[][]>();
	Stack<BoardPiece[][]> undoStackY = new Stack<BoardPiece[][]>();
	BoardPiece greenTokens[][] = new BoardPiece[6][4];
	BoardPiece yellowTokens[][] = new BoardPiece[6][4];
	String name = "";
	int originalCount = 0;
	int setterCount = 0;

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
		// preCondition
		if (checkValidCreationSpot(board, player.name) == false) {
			System.out.println("Invalid Move\nCreation Spot is already taken");
			return false;
		}
		BoardPiece tokenToAdd = null;
		if (player.name.equals("yellow")) {
			tokenToAdd = find(player, letter);
			if (tokenToAdd != null) {
				board.board[7][7] = tokenToAdd;
				System.out.println("token added");
				System.out.println("Token color is " + tokenToAdd.col);
				return true;
			}
		}
		if (player.name.equals("green")) {
			tokenToAdd = find(player, letter);
			if (tokenToAdd != null) {
				board.board[2][2] = tokenToAdd;
				System.out.println("token added");
				System.out.println("Token color is " + tokenToAdd.col);
				return true;
			}
		}
		System.out.println("returning falseeeeeeeeeeeeee");
		return false;
	}

	public BoardPiece find(Player player, String letter) {
		BoardPiece returnToken = null;

		if (player.name.equals("yellow")) {
			for (int r = 0; r < yellowTokens.length; r++) {
				for (int c = 0; c < yellowTokens[0].length; c++) {
					if (yellowTokens[r][c] == null) {
						continue;
					}
					if (yellowTokens[r][c].name.equals(letter)) {
						returnToken = yellowTokens[r][c];
						yellowTokens[r][c] = null;
						System.out.println("All good in find returned token to add");
						return returnToken;
					}
				}
			}
		}

		if (player.name.equals("green")) {
			for (int r = 0; r < greenTokens.length; r++) {
				for (int c = 0; c < greenTokens[0].length; c++) {
					if (greenTokens[r][c] == null) {
						continue;
					}
					if (greenTokens[r][c].name.equals(letter)) {
						returnToken = greenTokens[r][c];
						greenTokens[r][c] = null;
						System.out.println("all gud return green token");
						return returnToken;
					}
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
					System.out.println("player is " + player.name);
					System.out.println("token is " + token.col);
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
			//if they go of the board, they go to the grave yard O_O
			if(row < 0 || row > 9 || col < 0 || col > 9) {
				return true;
			}
			board.board[row][col] = token;
			return true;
		}
		return false;
	}



	public void createRecordY() {
		BoardPiece[][] recordY = new BoardPiece[6][4];
		for (int r = 0; r < recordY.length; r++) {
			for (int c = 0; c < recordY[0].length; c++) {
				recordY[r][c] = yellowTokens[r][c];
			}
		}
		undoStackY.push(recordY);
	}

	public void createRecordG() {
		BoardPiece[][] recordG = new BoardPiece[6][4];
		for(int r = 0; r < recordG.length; r++) {
			for(int c = 0; c < recordG[0].length; c++) {
				recordG[r][c] = greenTokens[r][c];
			}
		}
		undoStackG.push(recordG);
	}

	public void setBoardY() {
		BoardPiece[][] original = undoStackY.pop(); // get rid of original
		BoardPiece[][] setterY = undoStackY.pop();
		counter(original, setterY);
		for (int r = 0; r < setterY.length; r++) {
			for (int c = 0; c < setterY[0].length; c++) {
				yellowTokens[r][c] = setterY[r][c];
			}
		}

	}

	public void setBoardG() {
		BoardPiece[][] original =undoStackG.pop();
		BoardPiece[][] setterG = undoStackG.pop();
		counter(original, setterG);
		for(int r = 0; r < setterG.length; r++) {
			for(int c = 0; c < setterG[0].length; c++) {
				greenTokens[r][c] = setterG[r][c];
			}
		}
	}

	public void counter(Token[][] original, Token[][] setter) {
		originalCount = 0;
		setterCount = 0;
		for(int r = 0; r < setter.length; r++) {
			for(int c = 0; c < setter[0].length; c++) {
				if(setter[r][c] instanceof BoardPiece) {
					setterCount++;
				}
				if(original[r][c] instanceof BoardPiece) {
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

	@Override
	public String toString() {
		return "" + name;
	}

}
