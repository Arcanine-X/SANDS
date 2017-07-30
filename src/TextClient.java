import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TextClient {

	public static List<BoardPiece> yelList = new ArrayList<>();
	public static List<BoardPiece> greList = new ArrayList<>();
	static Board board = new Board();
	// Two sets that have all the possiable movements and rotations for simplicity
	// of code
	static public Set<String> movement = new HashSet<String>(Arrays.asList("up","down","left","right"));
	static public Set<Integer> rotations = new HashSet<Integer>(Arrays.asList(0,90,180,270));
	static boolean firstCreation = true;
	static Player yellow;
	static Player green;

	/**
	 * Get string from System.in
	 */
	private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (1 == 1) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}

	public static void createToken(Player player, String options) {
		if (!firstCreation) {
			System.out.println("You have already created a token this turn. You cannot create another one");
			return;
		}
		if (player == null || options == null) {
			throw new NullPointerException();
		}
		String[] tokens = options.split(" ");
		if (tokens.length != 3) {
			System.out.println("The format is incorrect. It should be create <letter> <0/90/180/270>");
		}
		String letter = tokens[1];
		int rotation = Integer.parseInt(tokens[2]);

		if (!rotations.contains(rotation) || letter.length() != 1) {
			System.out.println("Input error in create token");
			return;
		}
		if (player.addToken(letter, player, board) == false) {
			System.out.println("Something went wrong in create token");
			return;
		}
		BoardPiece item = board.findMoveToken(player, letter);
		int num = (rotation == 0) ? 0 : (rotation == 90) ? 1 : (rotation == 180) ? 2 : 3;
		while (num > 0) {
			rotator(item, rotation);
			num--;
		}
		firstCreation = false;
		System.out.println("Made a copy");
		success();
	}

	public static void moveToken(Player player, String options) {
		if (player == null || options == null) {
			throw new NullPointerException();
		}
		String[] tokens = options.split(" ");
		if (tokens.length != 3) {
			System.out.println("Move input error");
		}
		String letter = tokens[1];
		String direction = tokens[2];
		if (checkIfAllowedToMove(player, letter) == false) {
			return;
		}
		if (!movement.contains(direction) || letter.length() != 1) {
			System.out.println("Input error in moveToken");
			return;
		}
		BoardPiece tokenToMove = board.findMoveToken(player, letter);
		if (tokenToMove == null) {
			System.out.println("Your token to move doesn't exist");
			return;
		}
		if (player.moveToken(player, tokenToMove, direction, board) == true) {
			success();
		} else {
			System.out.println("Something went wrong in moveToken");
			return;
		}
	}

	public static void rotateToken(Player player, String options) {
		if (player == null || options == null) {
			throw new NullPointerException();
		}
		String[] tokens = options.split(" ");
		if (tokens.length != 3) {
			System.out.println("Rotation input error");
		}
		String letter = tokens[1];
		int rotation = Integer.parseInt(tokens[2]);
		if (!rotations.contains(rotation) || letter.length() != 1) {
			System.out.println("Input error in rotation");
			return;
		}
		BoardPiece itemToRotate = board.findMoveToken(player, letter);


		if (itemToRotate == null) {
			System.out.println("Your rotation piece doesn't exist");
			return;
		}
		int num = (rotation == 0) ? 0 : (rotation == 90) ? 1 : (rotation == 180) ? 2 : 3;
		while (num > 0) {
			rotator(itemToRotate, rotation);
			num--;
		}
		success();
		System.out.println("Successful rotation");
	}


	public static void rotator(BoardPiece item, int rotation) {
		int tn = item.north, te = item.east, ts = item.south, tw = item.west;
		item.north = tw;
		item.east = tn;
		item.south = te;
		item.west = ts;
	}

	public static void playerOptions(Player player) {
		while (1 == 1) {
			try {
				String options = inputString(
						"[create <letter> <0/90/180/270> / rotate <letter> <1-4> / move <letter> <up/right/down/left> / pass");
				if (options.startsWith("create")) {
					createToken(player, options);
				} else if (options.startsWith("rotate")) {
					rotateToken(player, options);
				} else if (options.startsWith("move")) {
					moveToken(player, options);
				} else if (options.startsWith("pass")) {
					System.out.println("Next persons turn");
					reset(player, board);
					return;
				} else if (options.startsWith("undo")) {
					undo(player);
				} else {
					System.out.println("Invalid option....");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void undo(Player player) {
		System.out.println("Undoing");
		board.setBoard(); // undo board
		yellow.setBoard();
		green.setBoard();
		green.createRecord();
		yellow.createRecord();
		board.createRecord(); // create new record
		// undo lists which ensure a player can only move something once per turn
		if (!green.movesSoFar.isEmpty()) {
			green.movesSoFar.remove(green.movesSoFar.size() - 1);
		}
		if (!yellow.movesSoFar.isEmpty()) {
			yellow.movesSoFar.remove(yellow.movesSoFar.size() - 1);
		}
		if (player.name.equals("green")) {
			if (green.getSetterCount() > green.getOriginalCount()) {
				firstCreation = true;
			}
		}
		if (player.name.equals("yellow")) {
			if (yellow.getSetterCount() > yellow.getOriginalCount()) {
				firstCreation = true;
			}
		}
		board.redraw();
	}

	public static void success() {
		board.createRecord();
		green.createRecord();
		yellow.createRecord();
		board.redraw();
	}

	public static void reset(Player player, Board board) {// after passing need to reset stuff
		yellow.movesSoFar.clear();
		green.movesSoFar.clear();
		firstCreation = true;
		yellow.undoStack.clear();
		green.undoStack.clear();
		board.undoStack.clear();
		green.createRecord();
		yellow.createRecord();
		board.createRecord();
	}

	/**
	 * Checks if the move is valid in terms of how many times its been moved in a
	 * turn
	 *
	 * @param player
	 * @param letter
	 * @return
	 */
	public static boolean checkIfAllowedToMove(Player player, String letter) {
		if(!player.movesSoFar.contains(letter)) {
			player.movesSoFar.add(letter);
			return true;
		}
		System.out.println("You have already moved this piece this turn");
		System.out.println("You can move another existing piece or move it next turn");
		return false;
	}

	public static void main(String[] args) {
		// Create players
		green = new Player("green");
		yellow = new Player("yellow");
		int turn = 0;
		gerneratePieces(yelList);
		gerneratePieces(greList);
		board.initialise();
		board.addPlayers(green, yellow);
		board.redraw();
		board.createRecord();
		yellow.populateTokens(yelList);
		green.populateTokens(greList);
		yellow.createRecord();
		green.createRecord();
		System.out.println("~*~*~ Sword & Shield ~*~*~");
		while (1 == 1) {// loop forever
			System.out.println("\n********************");
			System.out.println("***** TURN " + turn + " *******");
			System.out.println("********************\n");
			if (turn % 2 == 0) {
				System.out.println("It is yellows turn!");
				playerOptions(yellow);
				board.redraw();

			} else {
				System.out.println("It is greens turn!");
				playerOptions(green);
				board.redraw();
			}
			turn++;
		}
	}

	public static void gerneratePieces(List<BoardPiece> list) {
		list.add(new BoardPiece("a", 1, 2, 1, 1, ""));
		list.add(new BoardPiece("b", 1, 0, 1, 1, ""));
		list.add(new BoardPiece("c", 2, 2, 2, 2, ""));
		list.add(new BoardPiece("d", 1, 0, 0, 0, ""));
		// empty tile??
		//
		list.add(new BoardPiece("e", 0, 0, 0, 0, ""));
		list.add(new BoardPiece("f", 1, 0, 0, 1, ""));
		list.add(new BoardPiece("g", 1, 1, 1, 1, ""));
		list.add(new BoardPiece("h", 1, 0, 2, 2, ""));
		list.add(new BoardPiece("i", 0, 2, 0, 0, ""));
		//
		list.add(new BoardPiece("j", 1, 2, 1, 2, ""));
		list.add(new BoardPiece("k", 1, 2, 0, 1, ""));
		list.add(new BoardPiece("l", 1, 0, 0, 0, ""));
		list.add(new BoardPiece("m", 1, 2, 2, 0, ""));
		list.add(new BoardPiece("n", 0, 2, 2, 0, ""));
		//
		list.add(new BoardPiece("o", 1, 0, 1, 2, ""));
		list.add(new BoardPiece("p", 1, 0, 2, 1, ""));
		list.add(new BoardPiece("q", 1, 0, 0, 2, ""));
		list.add(new BoardPiece("r", 1, 2, 0, 2, ""));
		list.add(new BoardPiece("s", 0, 2, 0, 2, ""));
		// e
		list.add(new BoardPiece("t", 1, 0, 1, 0, ""));
		list.add(new BoardPiece("u", 1, 0, 0, 1, ""));
		list.add(new BoardPiece("v", 1, 2, 0, 0, ""));
		list.add(new BoardPiece("w", 1, 2, 2, 2, ""));
		list.add(new BoardPiece("x", 0, 2, 2, 2, ""));
	}
}
