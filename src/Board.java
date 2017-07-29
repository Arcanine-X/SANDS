
public class Board {
	Token[][] board = new Token[10][10];


	public Board() {

	}

	public void redraw() {
		// draw a board
		System.out.println("-------------------------------------------------------------");
		for (int r = 0; r < board.length; r++) {
			// First Row
			for (int c = 0; c < board.length; c++) {
				if (board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					System.out.print(getNorth(temp));
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			// Middle Row
			for (int i = 0; i < 10; i++) {
				// Draw Player
				if (board[r][i] instanceof Player) {
					if (r == 1 && i == 1) {
						System.out.print("|green");
					} else {
						System.out.print("|yelow");
					}
				} else if (board[r][i] instanceof BoardPiece) { // Logic for drawing the tokens in the array
					BoardPiece temp = (BoardPiece) board[r][i];
					StringBuilder build = new StringBuilder();
					// Deal with west
					build.append("| ");
					if (temp.west == 0) {
						build.append(" ");
					} else if (temp.west == 1) {
						build.append("-");
					} else {
						build.append("+");
					}
					// Make sure the names in
					build.append(temp.name);
					// Deal with East
					if (temp.east == 0) {
						build.append("  ");
					} else if (temp.east == 1) {
						build.append("- ");
					} else {
						build.append("+ ");
					}
					System.out.print(build.toString());
				} else if (r == 2 && i == 2 && !(board[2][2] instanceof Token)) {// Draw creation box for green
					System.out.print("| [ ] ");
				} else if (r == 7 && i == 7 && !(board[7][7] instanceof Token)) {// Draw creation box for yellow
					System.out.print("| [ ] ");
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			// Last Row
			for (int i = 0; i < 10; i++) {
				//Deal with South
				if (board[r][i] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][i];
					System.out.print(getSouth(temp));
				} else {
					System.out.print("|     ");
				}
			}
			System.out.println("|");
			System.out.println("-------------------------------------------------------------");
		}
	}

	public String getNorth(BoardPiece b) {
		return (b.north == 0) ?  "|     " : (b.north==1) ? "|  |  " : "|  +  ";
	}

	public String getSouth(BoardPiece b) {
		return (b.south == 0) ?  "|     " : (b.south==1) ? "|  |  " : "|  +  ";
	}

	public String getEast(BoardPiece b) {
		return "";
	}

	public String getWest(BoardPiece b) {

		return "";
	}





	public BoardPiece findMoveToken(Player player, String letter) {
		System.out.println("in findmoveToken");
		BoardPiece returnToken = null;
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[0].length; c++) {
				if(board[r][c] instanceof BoardPiece) {
					BoardPiece temp = (BoardPiece) board[r][c];
					if(temp.col.equals(player.name) && temp.name.equals(letter)) {
						returnToken = temp;
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
