
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
				if (board[r][i] instanceof Player) {
					System.out.print(r == 1 && i == 1 ? "|green" : "|yelow");			// Draw Player
				} else if (board[r][i] instanceof BoardPiece) { 						// Logic for drawing the tokens in the array
					BoardPiece temp = (BoardPiece) board[r][i];
					StringBuilder build = new StringBuilder();
					build.append(getWest(temp));										// Deal with West
					build.append(temp.name);											// Deal with Name
					build.append(getEast(temp));										// Deal with East
					System.out.print(build.toString());
				} else if (r == 2 && i == 2 && !(board[2][2] instanceof Token)) {		// Draw creation box for green
					System.out.print("| [ ] ");
				} else if (r == 7 && i == 7 && !(board[7][7] instanceof Token)) {		// Draw creation box for yellow
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
		return (b.east == 0) ? "  " : (b.east == 1) ? "- " : "+ ";
	}

	public String getWest(BoardPiece b) {
		return (b.west == 0) ? "|  " : (b.west == 1) ? "| -" : "| +";
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
