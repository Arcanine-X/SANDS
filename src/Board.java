import java.util.Stack;

public class Board {
	Token[][] board = new Token[10][10];
	Stack<Token[][]> undoStack = new Stack<Token[][]>();
	static final String SEPARATOR = "     ";
	static final String TLINE = "-------------------------";
	static final String BLINE = "-------------------------------------------------------------";
	static final String EDGESEPARATOR = "                              ";
	public char dm = 'a';

	public Board() {
	}


	public void createGreenBoard(Player player) {
		// draw a board
		System.out.println("-------------------------");
		for (int r = 0; r < player.tokens.length; r++) {
			// First Row
			for (int c = 0; c < player.tokens[0].length; c++) {
				if (player.tokens[r][c] != null) {
					System.out.print(getNorth(player.tokens[r][c])); // Deal with North
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			// Middle Row
			for (int i = 0; i < player.tokens[0].length; i++) {
				if (player.tokens[r][i] != null) { // Logic for drawing the tokens in the array
					System.out.print(
							getWest(player.tokens[r][i]) + player.tokens[r][i].name + getEast(player.tokens[r][i]));
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			// Last Row
			for (int i = 0; i < player.tokens[0].length; i++) {
				if (player.tokens[r][i] != null) {
					System.out.print(getSouth(player.tokens[r][i])); // Deal with South
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			System.out.println("-------------------------");
		}
	}

	/*public void redraw() {
		// draw a board
		System.out.println("-------------------------------------------------------------");
		for (int r = 0; r < board.length; r++) {
			// First Row
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					System.out.print(getNorth(temp)); // Deal with North
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			// Middle Row
			for (int i = 0; i < 10; i++) {
				if (board[r][i] instanceof Player) {
					System.out.print(r == 1 && i == 1 ? "|green" : "|yelow"); // Draw Player
				} else if (board[r][i] instanceof BoardPiece) { // Logic for drawing the tokens in the array
					BoardPiece temp = (BoardPiece) board[r][i];
					System.out.print(getWest(temp) + temp.name + getEast(temp));
				} else if (r == 2 && i == 2 && !(board[2][2] instanceof Token)) { // Draw creation box for green
					System.out.print("| [ ] ");
				} else if (r == 7 && i == 7 && !(board[7][7] instanceof Token)) { // Draw creation box for yellow
					System.out.print("| [ ] ");
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			// Last Row
			for (int i = 0; i < 10; i++) {
				if (board[r][i] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][i];
					System.out.print(getSouth(temp)); // Deal with South
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			System.out.println("-------------------------------------------------------------");
		}
	}*/

	public void redraw(Player green, Player yellow) {
		System.out.println("\n\n");
		System.out.println(SEPARATOR + "~~Green Tokens~~" + EDGESEPARATOR + "  ~~Game Board~~" + EDGESEPARATOR + "   ~~Yellow Tokens~~");
		System.out.println(TLINE + SEPARATOR + BLINE + SEPARATOR + TLINE);
		for (int r = 0; r < board.length; r++) {
			// Top row of player tokens
			if (r < green.tokens.length) {
				for (int c = 0; c < green.tokens[0].length; c++) {
					if (green.tokens[r][c] != null) {
						System.out.print(getNorth(green.tokens[r][c])); // Deal with North
					} else {
						System.out.print("|     ");
					}
				}
				//System.out.print("|" + SEPARATOR);
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}
			// Top row for board
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					System.out.print(getNorth(temp)); // Deal with North
				} else {
					System.out.print("|     ");
				}
			}



			//top row for yellow
			if (r < yellow.tokens.length) {
				System.out.print("|"+SEPARATOR);
				for (int c = 0; c < yellow.tokens[0].length; c++) {
					if (yellow.tokens[r][c] != null) {
						System.out.print(getNorth(yellow.tokens[r][c])); // Deal with North
					} else {
						System.out.print("|     ");
					}
				}
			}


			System.out.println("|");

			// ###########################~~~~ Middle Row
			// ~~~#################################//
			if (r < green.tokens.length) {
				for (int i = 0; i < green.tokens[0].length; i++) {
					if (green.tokens[r][i] != null) { // Logic for drawing the tokens in the array
						System.out.print(
								getWest(green.tokens[r][i]) + green.tokens[r][i].name + getEast(green.tokens[r][i]));
					} else {
						System.out.print("|     ");
					}
				}
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}

			// board mid

			for (int i = 0; i < 10; i++) {
				if (board[r][i] instanceof Player) {
					System.out.print(r == 1 && i == 1 ? "|green" : "|yelow"); // Draw Player
				} else if (board[r][i] instanceof BoardPiece) { // Logic for drawing the tokens in the array
					BoardPiece temp = (BoardPiece) board[r][i];
					System.out.print(getWest(temp) + temp.name + getEast(temp));
				} else if (r == 2 && i == 2 && !(board[2][2] instanceof Token)) { // Draw creation box for green
					System.out.print("| [ ] ");
				} else if (r == 7 && i == 7 && !(board[7][7] instanceof Token)) { // Draw creation box for yellow
					System.out.print("| [ ] ");
				} else {
					System.out.print("|     ");
				}
			}


			//System.out.print("|");
			//Mid row for yellow
			System.out.print("|"+SEPARATOR);
			if (r < yellow.tokens.length) {

				for (int i = 0; i < yellow.tokens[0].length; i++) {
					if (yellow.tokens[r][i] != null) { // Logic for drawing the tokens in the array
						System.out.print(
								getWest(yellow.tokens[r][i]) + yellow.tokens[r][i].name + getEast(yellow.tokens[r][i]));
					} else {
						System.out.print("|     ");
					}
				}
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}

			System.out.println("");

			// **********************************---Last
			// Row---**************************************

			if (r < green.tokens.length) {
				for (int i = 0; i < green.tokens[0].length; i++) {
					if (green.tokens[r][i] != null) {
						System.out.print(getSouth(green.tokens[r][i])); // Deal with South
					} else {
						System.out.print("|     ");
					}
				}
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}

			// board

			for (int i = 0; i < 10; i++) {
				if (board[r][i] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][i];
					System.out.print(getSouth(temp)); // Deal with South
				} else {
					System.out.print("|     ");
				}
			}

			if (r < yellow.tokens.length) {
				System.out.print("|"+SEPARATOR);
				for (int i = 0; i < yellow.tokens[0].length; i++) {
					if (yellow.tokens[r][i] != null) {
						System.out.print(getSouth(yellow.tokens[r][i])); // Deal with South
					} else {
						System.out.print("|     ");
					}
				}

			}


			System.out.println("|");

			if (r < yellow.tokens.length) {
				System.out.print(TLINE + SEPARATOR + BLINE + SEPARATOR + TLINE);
				System.out.println();
			} else {
				System.out.print(EDGESEPARATOR + BLINE);
				System.out.println();
			}
			// System.out.println("");

			//Last row for yellow



		}
	}

	public String getNorth(BoardPiece b) {
		return (b.north == 0) ? "|     " : (b.north == 1) ? "|  |  " : "|  +  ";
	}

	public String getSouth(BoardPiece b) {
		return (b.south == 0) ? "|     " : (b.south == 1) ? "|  |  " : "|  +  ";
	}

	public String getEast(BoardPiece b) {
		return (b.east == 0) ? "  " : (b.east == 1) ? "- " : "+ ";
	}

	public String getWest(BoardPiece b) {
		return (b.west == 0) ? "|  " : (b.west == 1) ? "| -" : "| +";
	}

	public void createRecord() {
		Token[][] record = new Token[10][10];
		// Make a copy of the board
		for (int r = 0; r < record.length; r++) {
			for (int c = 0; c < record[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					BoardPiece newBP = new BoardPiece(temp.name, temp.north, temp.east, temp.south, temp.west,
							temp.col);
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
					if (temp.col.equals(player.name) && temp.name.equals(letter)) {
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

	public void addPlayers(Player green, Player yellow) {
		board[1][1] = green;
		board[8][8] = yellow;
	}

	public void initialise() {
		// Initialize board to nulls
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				board[r][c] = null;
			}
		}
	}
}
