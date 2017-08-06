import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Board {
	Token[][] board = new Token[10][10];
	Stack<Token[][]> undoStack = new Stack<Token[][]>();
	List<Pair> reactions = new ArrayList<Pair>();
	private static final String SEPARATOR = "     "; //Separator between token and board, and board and token
	private static final String TLINE = "-------------------------"; //Token board line
	private static final String BLINE = "-------------------------------------------------------------"; //Board line
	private static final String EDGESEPARATOR = "                              "; //Distance between edge and board
	private Player green;
	private Player yellow;


	public void setGreen(Player green) {
		this.green = green;
	}
	public void setYellow(Player yellow) {
		this.yellow = yellow;
	}

	public boolean checkForReaction() {
		reactions.clear();
		/*
		 * Need to check for reactions up down left and right... so two sets of double four loops....
		 */
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[0].length-1; c++) {
				if(board[r][c] instanceof BoardPiece && board[r][c+1] instanceof BoardPiece) {
					BoardPiece temp1 = (BoardPiece) board[r][c];
					BoardPiece temp2 = (BoardPiece) board[r][c+1];
					if(temp1.east == 1 || temp2.west == 1) {
						reactions.add(new Pair(temp1,temp2,"hori", this));
					}
				}
			}
		}

		for(int r = 0; r < board.length-1; r++) {
			for(int c = 0; c < board[0].length; c++) {
				if(board[r][c] instanceof BoardPiece && board[r+1][c] instanceof BoardPiece) {
					BoardPiece temp1 = (BoardPiece) board[r][c];
					BoardPiece temp2 = (BoardPiece) board[r+1][c];
					if(temp1.south == 1 || temp2.north == 1) {
						reactions.add(new Pair(temp1,temp2,"vert", this));
					}
				}
			}
		}
		if(!reactions.isEmpty()) {
			System.out.println("oka");
		}
		return reactions.isEmpty() ? false : true;
	}



	public int getX(String letter) {
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[0].length; c++) {
				if(board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece)board[r][c];
					if(temp.name.equals(letter)) {
						return c; //c is x
					}
				}
			}
		}
		return -1;
	}

	public int getY(String letter) {
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[0].length; c++) {
				if(board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece)board[r][c];
					if(temp.name.equals(letter)) {
						return r; //r is y
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
			} else {
				System.out.print("|     ");
			}
		}
	}

	public void drawTopRowTokens(Player player, int r) {
		for (int c = 0; c < player.tokens[0].length; c++) {
			if (player.tokens[r][c] != null) {
				System.out.print(getNorth(player.tokens[r][c])); // Deal with North
			} else {
				System.out.print("|     ");
			}
		}
	}

	public void drawMiddleRowBoard(int r) {
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
	}

	public void drawMiddleRowTokens(Player player, int r) {
		if (r < player.tokens.length) {
			for (int i = 0; i < player.tokens[0].length; i++) {
				if (player.tokens[r][i] != null) { // Logic for drawing the tokens in the array
					System.out.print(
							getWest(player.tokens[r][i]) + player.tokens[r][i].name + getEast(player.tokens[r][i]));
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
		for (int i = 0; i < player.tokens[0].length; i++) {
			if (player.tokens[r][i] != null) {
				System.out.print(getSouth(player.tokens[r][i])); // Deal with South
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
			} else {
				System.out.print("|     ");
			}
		}
	}




	public void redraw() {
		System.out.println("\n\n");
		System.out.println(SEPARATOR + "~~Green Tokens~~" + EDGESEPARATOR + "  ~~Game Board~~" + EDGESEPARATOR + "   ~~Yellow Tokens~~");
		System.out.println(TLINE + SEPARATOR + BLINE + SEPARATOR + TLINE);
		for (int r = 0; r < board.length; r++) {
			//~~~~~~~~~ TOP ROW ~~~~~~~~~~~~~~~
			// Top row of player tokens
			if (r < green.tokens.length) {
				drawTopRowTokens(green, r);
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}
			// Top row for board
			drawTopRowBoard(r);
			// Top row for yellow tokens
			if (r < yellow.tokens.length) {
				System.out.print("|"+SEPARATOR);
				drawTopRowTokens(yellow, r);
			}
			System.out.println("|");
			//~~~~~~~~~~~~ MIDDLE ROW ~~~~~~~~~~
			// Middle row for green tokens
			drawMiddleRowTokens(green, r);
			// Middle row for board
			drawMiddleRowBoard(r);
			System.out.print("|"+SEPARATOR);
			//Middle row for yellow tokens
			drawMiddleRowTokens(yellow, r);
			System.out.println("");

			//~~~~~~~ LAST ROW ~~~~~~~~~~~~~

			if (r < green.tokens.length) {
				drawLastRowTokens(green, r);
				System.out.print("|" + SEPARATOR);
			} else {
				System.out.print(EDGESEPARATOR);
			}
			drawLastRowBoard(r);
			if (r < yellow.tokens.length) {
				System.out.print("|"+SEPARATOR);
				drawLastRowTokens(yellow, r);
			}
			System.out.println("|");
			if (r < yellow.tokens.length) {
				System.out.print(TLINE + SEPARATOR + BLINE + SEPARATOR + TLINE);
				System.out.println();
			} else {
				System.out.print(EDGESEPARATOR + BLINE);
				System.out.println();
			}
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

	public void killToken(String letter) {
		System.out.println("in findmoveToken");
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[0].length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					if (temp.name.equals(letter)) {
						board[r][c] = null;
						temp = null;
						System.out.println("Dead");
					}
				}
			}
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
					if (temp.name.equals(letter)) {
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
		for(int i = r -1, j = 0; i>= 0; i--, j++) {
			if(board[i][c] instanceof BoardPiece && count == j) {
				count++;
			}
		}

		if(count!=0) {
			System.out.println("Count is : " + count);
			for(int i = r - count; i < r; i++) {
				board[i-1][c] = board[i][c];
			}
			board[r-1][c] = null;
		}
		System.out.println("all done");
	}

	public void tryPushDown(String pusher) {
		int c = getX(pusher);
		int r = getY(pusher);
		int count = 0;
		for(int i = r + 1, j = 0; i < board.length; i++, j++) {
			if(board[i][c] instanceof BoardPiece && count == j) {
				count++;
			}
		}

		if(count!=0) {
			System.out.println("count is : " + count);
			for(int i = r + count; i > r; i --) {
				System.out.println("in here");
				board[i+1][c] = board[i][c];
			}
			board[r+1][c] = null;
		}
	}

	public void tryPushRight(String pusher) {
		int c = getX(pusher);
		int r = getY(pusher);
		System.out.println("Pusher pushing right is : " + pusher);
		int count = 0;
		System.out.println("cant get in for loop?");
		System.out.println("c is " + c);
		for(int i = c + 1, j = 0; i < board.length; i++, j++) {
			System.out.println("first four loop");
			if(board[r][i] instanceof BoardPiece && count == j) {
				count++;
			}
		}
		if(count!=0) {
			System.out.println("count is : " + count);
			for(int i = c + count; i > c; i--) {
				board[r][i+1] = board[r][i];

			}
			board[r][c+1] = null;
			System.out.println("successful push");
		}
	}

	public void tryPushLeft(String pusher) {
		int c = getX(pusher);
		int r = getY(pusher);
		System.out.println("Pusher pushing left is : " + pusher);
		int count = 0;
		System.out.println("cant get in for loop?");
		System.out.println("c is " + c);
		for(int i = c - 1, j = 0; i >=0; i--, j++) {
			if(board[r][i] instanceof BoardPiece && count == j) {
				count++;
			}
		}
		if(count!=0) {
			System.out.println("count is : " + count);
			for(int i = c - count; i < c; i++) {
				board[r][i-1] = board[r][i];
			}
			board[r][c-1] = null;
			System.out.println("successfull");
		}
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
