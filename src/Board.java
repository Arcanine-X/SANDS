import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Board {
	private Token[][] board = new Token[10][10];
	private Stack<Token[][]> undoStack = new Stack<Token[][]>();
	private List<Pair> reactions = new ArrayList<Pair>();
	private static final String SEPARATOR = "     "; // Separator between token and board, and board and token
	private static final String TLINE = "-------------------------"; // Token board line
	private static final String BLINE = "-------------------------------------------------------------"; // Board line
	private static final String EDGESEPARATOR = "                              "; // Distance between edge and board
	private static final String INVALID_SQUARE = "|\u2591\u2591\u2591\u2591\u2591";
	private Player green;
	private Player yellow;
	private boolean gameEnded = false;

	public void setGreen(Player green) {
		this.green = green;
	}

	public void setYellow(Player yellow) {
		this.yellow = yellow;
	}

	public boolean checkForReaction() {
		reactions.clear();
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length - 1; c++) {
				if (board[r][c] instanceof BoardPiece && board[r][c + 1] instanceof BoardPiece) {
					BoardPiece temp1 = (BoardPiece) board[r][c];
					BoardPiece temp2 = (BoardPiece) board[r][c + 1];
					if (temp1.getEast() == 1 || temp2.getWest() == 1) {
						reactions.add(new Pair(temp1, temp2, "hori"));
					}
				}
			}
		}

		for (int r = 0; r < board.length - 1; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece && board[r + 1][c] instanceof BoardPiece) {
					BoardPiece temp1 = (BoardPiece) board[r][c];
					BoardPiece temp2 = (BoardPiece) board[r + 1][c];
					if (temp1.getSouth() == 1 || temp2.getNorth() == 1) {
						reactions.add(new Pair(temp1, temp2, "vert"));
					}
				}
			}
		}
		//////// ########## Reaction with green player #############////////////////
		if (board[1][1] instanceof Player && board[1][2] instanceof BoardPiece) {
			BoardPiece temp1 = (BoardPiece) board[1][2];
			Player temp2 = (Player) board[1][1];
			if (temp1.getWest() == 1) {
				// reaction with player
				reactions.add(new Pair(temp1, temp2, "hori"));

			}
		}

		if (board[1][1] instanceof Player && board[2][1] instanceof BoardPiece) {
			BoardPiece temp1 = (BoardPiece) board[2][1];
			Player temp2 = (Player) board[1][1];

			if (temp1.getNorth() == 1) {
				// reaction with player
				reactions.add(new Pair(temp1, temp2, "vert"));

			}
		}

		////////// Reaction with yellow player #################/////////////////
		if (board[8][8] instanceof Player && board[7][8] instanceof BoardPiece) {
			BoardPiece temp1 = (BoardPiece) board[7][8];
			Player temp2 = (Player) board[8][8];
			if (temp1.getSouth() == 1) {
				// reaction with player
				reactions.add(new Pair(temp1, temp2, "vert"));

			}
		}

		if (board[8][8] instanceof Player && board[8][7] instanceof BoardPiece) {
			BoardPiece temp1 = (BoardPiece) board[8][7];
			Player temp2 = (Player) board[8][8];
			if (temp1.getEast() == 1) {
				// reaction with player
				reactions.add(new Pair(temp1, temp2, "hori"));

			}
		}
		return reactions.isEmpty() ? false : true;
	}

	public int getX(String letter) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					if (temp.getName().equals(letter)) {
						return c; // c is x
					}
				}
			}
		}
		return -1;
	}

	public int getY(String letter) {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					if (temp.getName().equals(letter)) {
						return r; // r is y
					}
				}
			}
		}
		return -1;
	}

	public Board() {
	}

	public void drawTopRowBoard(int r) {
		for (int c = 0; c < board[0].length; c++) {
			if (board[r][c] instanceof BoardPiece) {
				BoardPiece temp = (BoardPiece) board[r][c];
				System.out.print(getNorth(temp)); // Deal with North
			} else if ((r == 0 && c < 2)) {
				System.out.print(INVALID_SQUARE);
			} else if (r == 1 && c == 0) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 8 && c == 9)) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 9 && c > 7)) {
				System.out.print(INVALID_SQUARE);
			} else {
				System.out.print("|     ");
			}
		}
	}

	public void drawTopRowTokens(Player player, int r) {
		for (int c = 0; c < player.getTokens()[0].length; c++) {
			if (player.getTokens()[r][c] != null) {
				System.out.print(getNorth(player.getTokens()[r][c])); // Deal with North
			} else {
				System.out.print("|     ");
			}
		}
	}

	public void drawMiddleRowBoard(int r) {
		for (int i = 0; i < 10; i++) {
			if (board[r][i] instanceof Player) {
				System.out.print(r == 1 && i == 1 ? "|green" : "|yelow");
				//if (!gameEnded) {System.out.print(r == 1 && i == 1 ? "|green" : "|yelow");} // Draw Player
			} else if (board[r][i] instanceof BoardPiece) { // Logic for drawing the tokens in the array
				BoardPiece temp = (BoardPiece) board[r][i];
				System.out.print(getWest(temp) + temp.getName() + getEast(temp));
			} else if (r == 2 && i == 2 && !(board[2][2] instanceof Token)) { // Draw creation box for green
				System.out.print("| [ ] ");
			} else if (r == 7 && i == 7 && !(board[7][7] instanceof Token)) { // Draw creation box for yellow
				System.out.print("| [ ] ");
			} else if ((r == 1 && i == 0)) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 0 && i < 2)) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 8 && i == 9)) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 9 && i > 7)) {
				System.out.print(INVALID_SQUARE);
			} else {
				System.out.print("|     ");
			}
		}
	}

	public void drawMiddleRowTokens(Player player, int r) {
		if (r < player.getTokens().length) {
			for (int i = 0; i < player.getTokens()[0].length; i++) {
				if (player.getTokens()[r][i] != null) { // Logic for drawing the tokens in the array
					System.out.print(getWest(player.getTokens()[r][i]) + player.getTokens()[r][i].getName()
							+ getEast(player.getTokens()[r][i]));
				} else {
					System.out.print("|     ");
				}
			}
			System.out.print("|" + SEPARATOR);
		} else {
			System.out.print(EDGESEPARATOR);
		}
	}

	public void drawLastRowTokens(Player player, int r) {
		for (int i = 0; i < player.getTokens()[0].length; i++) {
			if (player.getTokens()[r][i] != null) {
				System.out.print(getSouth(player.getTokens()[r][i])); // Deal with South
			} else {
				System.out.print("|     ");
			}
		}
	}

	public void drawLastRowBoard(int r) {
		for (int i = 0; i < 10; i++) {
			if (board[r][i] instanceof BoardPiece) {
				BoardPiece temp = (BoardPiece) board[r][i];
				System.out.print(getSouth(temp)); // Deal with South
			} else if ((r == 1 && i == 0)) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 0 && i < 2)) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 8 && i == 9)) {
				System.out.print(INVALID_SQUARE);
			} else if ((r == 9 && i > 7)) {
				System.out.print(INVALID_SQUARE);
			} else {
				System.out.print("|     ");
			}
		}
	}

	public void redraw() {
		System.out.println("Game ended is " + gameEnded);
		if (gameEnded == false) {
			addPlayers(green, yellow);
		}
		addInvalidSquares();
		System.out.println("\n\n");
		System.out.println(SEPARATOR + "~~Green Tokens~~" + EDGESEPARATOR + "  ~~Game Board~~" + EDGESEPARATOR
				+ "   ~~Yellow Tokens~~");
		System.out.println(TLINE + SEPARATOR + BLINE + SEPARATOR + TLINE);
		for (int r = 0; r < board.length; r++) {
			// ~~~~~~~~~ TOP ROW ~~~~~~~~~~~~~~~
			// Top row of player tokens
			if (r < green.getTokens().length) {
				drawTopRowTokens(green, r);
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}
			// Top row for board
			drawTopRowBoard(r);
			// Top row for yellow tokens
			if (r < yellow.getTokens().length) {
				System.out.print("|" + SEPARATOR);
				drawTopRowTokens(yellow, r);
			}

			System.out.println("|");
			// ~~~~~~~~~~~~ MIDDLE ROW ~~~~~~~~~~
			// Middle row for green tokens
			drawMiddleRowTokens(green, r);
			// Middle row for board
			drawMiddleRowBoard(r);
			System.out.print("|" + SEPARATOR);
			// Middle row for yellow tokens
			drawMiddleRowTokens(yellow, r);
			System.out.println("");

			// ~~~~~~~ LAST ROW ~~~~~~~~~~~~~

			if (r < green.getTokens().length) {
				drawLastRowTokens(green, r);
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}
			drawLastRowBoard(r);
			if (r < yellow.getTokens().length) {
				System.out.print("|" + SEPARATOR);
				drawLastRowTokens(yellow, r);
			}
			System.out.println("|");
			if (r < yellow.getTokens().length) {
				System.out.print(TLINE + SEPARATOR + BLINE + SEPARATOR + TLINE);
				System.out.println();
			} else {
				System.out.print(EDGESEPARATOR + BLINE);
				System.out.println();
			}
		}
		green.diffs.clear();
		yellow.diffs.clear();
		green.updateGraveyard(board);
		yellow.updateGraveyard(board);
		pupulateGraveyard(green, green.diffs);
		pupulateGraveyard(yellow, yellow.diffs);
		drawGraveYard();
	}

	public void pupulateGraveyard(Player player, List<BoardPiece> deathList) {
		int size = deathList.size();
		for (int i = size; i < 24; i++) {
			deathList.add(null);
		}
		int i = 0;
		for (int r = 0; r < player.getGraveYard().length; r++) {
			for (int c = 0; c < player.getGraveYard()[0].length; c++) {
				player.getGraveYard()[r][c] = deathList.get(i++);
			}
		}
	}

	public void drawGraveYard() {
		System.out.println();
		System.out.println(SEPARATOR + SEPARATOR + "   ~~~Green GraveYard~~~" + EDGESEPARATOR + SEPARATOR
				+ "                   ~~~Yellow GraveYard~~~");
		System.out.print("-------------------------------------------------");
		System.out.print(SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR);
		System.out.println("-------------------------------------------------");
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 8; c++) {
				// Top row green
				if (green.getGraveYard()[r][c] != null) {
					System.out.print(getNorth(green.getGraveYard()[r][c])); // Deal with North
				} else {
					System.out.print("|     ");
				}
			}

			System.out.print("|" + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR);
			// Top row yellow
			for (int c = 0; c < 8; c++) {
				if (yellow.getGraveYard()[r][c] != null) {
					System.out.print(getNorth(yellow.getGraveYard()[r][c])); // Deal with North
				} else {
					System.out.print("|     ");
				}
			}

			System.out.println("|");

			// Middle Row green
			for (int i = 0; i < 8; i++) {
				if (green.getGraveYard()[r][i] != null) { // Logic for drawing the tokens in the array
					System.out.print(getWest(green.getGraveYard()[r][i]) + green.getGraveYard()[r][i].getName()
							+ getEast(green.getGraveYard()[r][i]));
				} else {
					System.out.print("|     ");
				}
			}

			System.out.print("|" + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR);

			// Middle Row yellow

			for (int i = 0; i < 8; i++) {
				if (yellow.getGraveYard()[r][i] != null) { // Logic for drawing the tokens in the array
					System.out.print(getWest(yellow.getGraveYard()[r][i]) + yellow.getGraveYard()[r][i].getName()
							+ getEast(yellow.getGraveYard()[r][i]));
				} else {
					System.out.print("|     ");
				}
			}

			System.out.println("|");

			// Last row yellow
			for (int i = 0; i < 8; i++) {
				if (green.getGraveYard()[r][i] != null) {
					System.out.print(getSouth(green.getGraveYard()[r][i])); // Deal with South
				} else {
					System.out.print("|     ");
				}
			}

			System.out.print("|" + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR);

			for (int i = 0; i < 8; i++) {
				if (yellow.getGraveYard()[r][i] != null) {
					System.out.print(getSouth(yellow.getGraveYard()[r][i])); // Deal with South
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			System.out.print("-------------------------------------------------");
			System.out.print(SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR + SEPARATOR);
			System.out.println("-------------------------------------------------");
		}
	}

	public String getNorth(BoardPiece b) {
		return (b.getNorth() == 0) ? "|     " : (b.getNorth() == 1) ? "|  |  " : "|  +  ";
	}

	public String getSouth(BoardPiece b) {
		return (b.getSouth() == 0) ? "|     " : (b.getSouth() == 1) ? "|  |  " : "|  +  ";
	}

	public String getEast(BoardPiece b) {
		return (b.getEast() == 0) ? "  " : (b.getEast() == 1) ? "- " : "+ ";
	}

	public String getWest(BoardPiece b) {
		return (b.getWest() == 0) ? "|  " : (b.getWest() == 1) ? "| -" : "| +";
	}

	public void createRecord() {
		Token[][] record = new Token[10][10];
		// Make a copy of the board
		for (int r = 0; r < record.length; r++) {
			for (int c = 0; c < record[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					BoardPiece newBP = new BoardPiece(temp.getName(), temp.getNorth(), temp.getEast(), temp.getSouth(),
							temp.getWest(), temp.getCol());
					record[r][c] = newBP;

				} else {
					record[r][c] = board[r][c];
				}
			}
		}
		undoStack.push(record);
	}

	public void setBoard() {
		Token[][] original = undoStack.pop(); // Need to pop out the current identicle version in the stack
		Token[][] setter = undoStack.pop();
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				board[r][c] = setter[r][c];
			}
		}
		System.out.println("Done loading");
	}

	public BoardPiece findMoveToken(Player player, String letter) {
		System.out.println("in findmoveToken");
		BoardPiece returnToken = null;
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					System.out.println("getting closer");
					BoardPiece temp = (BoardPiece) board[r][c];
					if (temp.getCol().equals(player.getName()) && temp.getName().equals(letter)) {
						returnToken = temp;
						System.out.println("Always a good sign");
						return returnToken;
					}
				}
			}
		}
		System.out.println("error returning null");
		return null;
	}

	public void killToken(String letter) {
		System.out.println("in findmoveToken");
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					if (temp.getName().equals(letter)) {
						board[r][c] = null;
						temp = null;
						System.out.println("Dead");
					}
				}
			}
		}
	}

	public void killPlayer(String player) {
		if (player.equals("green")) {
			gameEnded = true;
			board[1][1] = null;
		} else if (player.equals("yellow")) {
			System.out.println("done");
			System.out.println("yello shud be nullz");
			board[8][8] = null;
			gameEnded = true;
			redraw();
		} else {
			System.out.println("Something went wrong");
		}
		if(board[8][8] == null) {
			System.out.println("work pls");
		}
		else {
			System.out.println("Board 8 8 is " + (Player)board[8][8]);
		}
	}

	public BoardPiece findToken(String letter) {
		System.out.println("in findmoveToken");
		BoardPiece returnToken = null;
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					System.out.println("getting closer");
					BoardPiece temp = (BoardPiece) board[r][c];
					if (temp.getName().equals(letter)) {
						returnToken = temp;
						System.out.println("Always a good sign");
						return returnToken;
					}
				}
			}
		}
		System.out.println("error returning null");
		return null;
	}

	public void tryPushUp(String pusher) {
		int c = getX(pusher);
		int r = getY(pusher);
		int count = 0;
		if (r - 1 < 0) {
			board[r][c] = null;
		} else { // requires shifting
			for (int i = r - 1, j = 0; i >= 0; i--, j++) {
				if (board[i][c] instanceof BoardPiece && count == j) {
					count++;
				}
			}
			if (count != 0) {
				for (int i = r - count; i <= r; i++) {
					if (i - 1 < 0) {
						board[i][c] = null;
					} else {
						board[i - 1][c] = board[i][c];
					}
				}
				board[r - 1][c] = null;
			}
		}
	}

	public void tryPushDown(String pusher) {
		int c = getX(pusher);
		int r = getY(pusher);
		int count = 0;

		if (r + 1 > 9) {
			board[r][c] = null;
		} else {
			for (int i = r + 1, j = 0; i < board.length; i++, j++) {
				if (board[i][c] instanceof BoardPiece && count == j) {
					count++;
				}
			}
			if (count != 0) {
				for (int i = r + count; i >= r; i--) {
					if (i + 1 > 9) {
						board[i][c] = null;
					} else {
						board[i + 1][c] = board[i][c];
					}
				}
				board[r + 1][c] = null;
			}
		}
	}

	public void tryPushRight(String pusher) {
		int c = getX(pusher);
		int r = getY(pusher);
		int count = 0;
		if (c + 1 > 9) {
			board[r][c] = null;
		} else {
			for (int i = c + 1, j = 0; i < board.length; i++, j++) {
				if (board[r][i] instanceof BoardPiece && count == j) {
					count++;
				}
			}
			if (count != 0) {
				for (int i = c + count; i >= c; i--) {
					if (i + 1 > 9) {
						board[r][i] = null;
					} else {
						board[r][i + 1] = board[r][i];
					}
				}
				board[r][c + 1] = null;
			}
		}
	}

	public void tryPushLeft(String pusher) {
		int c = getX(pusher);
		int r = getY(pusher);
		int count = 0;
		if (c - 1 < 0) {
			board[r][c] = null;
		} else {
			for (int i = c - 1, j = 0; i >= 0; i--, j++) {
				if (board[r][i] instanceof BoardPiece && count == j) {
					count++;
				}
			}
			if (count != 0) {
				for (int i = c - count; i <= c; i++) {
					if (i - 1 < 0) {
						board[r][i] = null;
					} else {
						board[r][i - 1] = board[r][i];
					}
				}
				board[r][c - 1] = null;
			}
		}
	}

	public void addPlayers(Player green, Player yellow) {
		board[1][1] = green;
		board[8][8] = yellow;
	}

	public void addInvalidSquares() {
		board[0][0] = new InvalidSquare();
		board[0][1] = new InvalidSquare();
		board[1][0] = new InvalidSquare();
		board[8][9] = new InvalidSquare();
		board[9][8] = new InvalidSquare();
		board[9][9] = new InvalidSquare();
	}

	public void initialise() {
		// Initialize board to nulls
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				board[r][c] = null;
			}
		}
	}

	public List<Pair> getReactions() {
		return reactions;
	}

	public Stack<Token[][]> getUndoStack() {
		return undoStack;
	}

	public Token[][] getBoard() {
		return board;
	}

	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

}