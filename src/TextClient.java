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
	static public Set<String> movement = new HashSet<String>(Arrays.asList("up", "down", "left", "right"));
	static public Set<Integer> rotations = new HashSet<Integer>(Arrays.asList(0, 90, 180, 270));
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
		player.Hax("d", "b", "c", player, board);
		success();
		if (board.checkForReaction()) {
			fight();
		}
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

		BoardPiece tokenToMove = board.findMoveToken(player, letter);
		if (tokenToMove == null) {
			System.out.println("Your token to move doesn't exist");
			return;
		}

		if (!movement.contains(direction) || letter.length() != 1) {
			System.out.println("Input error in moveToken");
			return;
		}
		if (checkIfAllowedToMove(player, letter) == false) {
			return;
		}
		// Check that they haven't rotated
		if (player.everyMovement.contains(tokenToMove)) {
			System.out.println("You have already rotated or moved this piece this turn.\nSo you cannot rotate");
			return;
		}
		// Check that they haven't already moved this piece
		if (player.moveToken(player, tokenToMove, direction, board) == true) {
			player.everyMovement.add(tokenToMove);
			success();
			if (board.checkForReaction()) {
				fight();
			}
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
		if (player.everyMovement.contains(itemToRotate)) {
			System.out.println("You have already moved or rotated this token this turn");
			System.out.println("You cannot rotate it again");
			return;
		}
		player.everyMovement.add(itemToRotate);
		// Have to add have this to for to ensure that you cannot move after undoing a
		// rotation.
		player.movesSoFar.add("" + rotation);
		success();
		if (board.checkForReaction()) {
			fight();
		}

		System.out.println("Successful rotation");
	}

	public static void confirmation() {

		try {
			String options = inputString("Would you like to continue with the reaction? Yes/Undo");
			String input[] = options.split(" ");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	/*
	public static void fight() {
		// Print out the reactions
		while (!board.reactions.isEmpty()) {
			try {
				System.out.println("Here are the possiable reactions:");
				for (Pair p : board.reactions) {
					System.out.println("There is a reaction between " + p.one.name + " and " + p.two.name);
				}
				if (board.reactions.size() > 1) {
					// In this case the user has to choose what reaction should occur first
					String fightOptions = inputString(
							"There are multiple reactions. Enter the two letters of which the interactions should occur between first (or undo) : ");
					String tokens[] = fightOptions.split(" ");
					if (tokens.length != 2) {
						System.out.println("Incorrect input");
						continue;
					}
					String a = tokens[0], b = tokens[1];
					Pair pair = null;
					for (Pair p : board.reactions) {
						if ((p.one.name.equals(a) && p.two.name.equals(b))
								|| (p.one.name.equals(b) && p.two.name.equals(a))) {
							System.out.println("Found pair");
							pair = p;
							break;
						}
					}
					if (pair == null) {
						System.out.println("Invalid input");
						continue;
					}
					if (pair.dir.equals("vert")) {
						System.out.println("vertical");
						verticalReaction(pair);
					}
					if (pair.dir.equals("hori")) {
						System.out.println("horizontal");
						horizontalReaction(pair);
					}
				} else { // Only one reaction
					if (board.reactions.get(0).dir.equals("hori")) {
						horizontalReaction(board.reactions.get(0));
					} else if (board.reactions.get(0).dir.equals("vert")) {
						verticalReaction(board.reactions.get(0));
					} else {
						System.out.println("error");
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
	*/

	public static void fight() {
		// Print out the reactions
		while (!board.reactions.isEmpty()) {
			board.redraw();
			try {
				System.out.println("Here are the possiable reactions:");
				for (Pair p : board.reactions) {
					System.out.println("There is a reaction between " + p.one.name + " and " + p.two.name);
				}
				String options = "";
				if (board.reactions.size() > 1) {
					options = inputString(
							"There are multiple reactions. Enter the two letters of which the interactions should occur between first (or undo) : ");
					String[] tokens = options.split(" ");
					if (options.startsWith("undo")) {
						undo(green);
						undo(yellow);
					} else {
						if (tokens.length != 2) {
							System.out.println("Incorrect input");
							continue;
						}
						String a = tokens[0], b = tokens[1];
						Pair pair = null;
						for (Pair p : board.reactions) {
							if ((p.one.name.equals(a) && p.two.name.equals(b))
									|| (p.one.name.equals(b) && p.two.name.equals(a))) {
								System.out.println("Found pair");
								pair = p;
								break;
							}
						}
						if (pair == null) {
							System.out.println("Invalid input");
							continue;
						}
						if (pair.dir.equals("vert")) {
							System.out.println("vertical");
							verticalReaction(pair);
						}
						if (pair.dir.equals("hori")) {
							System.out.println("horizontal");
							horizontalReaction(pair);
						}
					}
				} else {
					options = inputString("Would you like to continue with the reaction? Yes/Undo");
					String[] tokens = options.split(" ");
					if (options.startsWith("undo")) {
						// then undo
						undo(green);
						undo(yellow);
					} else if (options.startsWith("yes") || options.startsWith("y")) {
						// do the reaction
						if (board.reactions.get(0).dir.equals("hori")) {
							horizontalReaction(board.reactions.get(0));
						} else if (board.reactions.get(0).dir.equals("vert")) {
							verticalReaction(board.reactions.get(0));
						} else {
							System.out.println("error");
						}
					} else {
						System.out.println("Not a valid input, try again");
					}
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void reactionCompleted(Pair p) {
		board.redraw();
		System.out.println(p.toString());
		board.reactions.remove(p);
		board.createRecord();
		yellow.createRecord();
		green.createRecord();
		if (board.checkForReaction()) {
			board.redraw();
			fight();
		}
	}

	public static void horizontalReaction(Pair p) {
		System.out.println("Horizontal Reaction");
		BoardPiece one = p.one;
		BoardPiece two = p.two;
		if (one.east == 1 && two.west == 1) { // sword - sword
			board.killToken(one.name);
			board.killToken(two.name);
			System.out.println("1 " + one.name + " and " + two.name + " died, due to Sword vs Sword. ");
			reactionCompleted(p);
		} else if (one.east == 1 && two.west == 0) { // sword - nothing
			board.killToken(two.name);
			System.out.println("2 " + two.name + " died, due to " + one.name + "'s Sword, vs Nothing. ");
			reactionCompleted(p);
		} else if (one.east == 1 && two.west == 2) { // sword - shield
			board.tryPushLeft(two.name);
			System.out.println("3 " + one.name + " got pushed back from " + two.name + "'s shield");
			reactionCompleted(p);
		} else if (one.east == 0 && two.west == 1) { // nothing - sword
			board.killToken(one.name);
			System.out.println("4 " + one.name + " died from " + two.west + "'s sword");
			reactionCompleted(p);
		} else if (one.east == 2 && two.west == 1) { // shield - sword
			board.tryPushRight(one.name);
			System.out.println("5 " + two.name + " got pushed back from " + one.name + "'s shield");
			reactionCompleted(p);
		} else {
			System.out.println("Something went wrong in horizontal reactions");
		}
	}

	public static void verticalReaction(Pair p) {
		// Five possibale reactions, sword - sword, sword - nothing, nothing - sword,
		// shield - sword, sword - shield
		System.out.println("Vertical Reaction");
		BoardPiece one = p.one;
		BoardPiece two = p.two;
		if (one.south == 1 && two.north == 1) { // sword - sword
			board.killToken(one.name);
			board.killToken(two.name);
			System.out.println("1 " + one.name + " and " + two.name + " died, due to Sword vs Sword. ");
			reactionCompleted(p);
		} else if (one.south == 1 && two.north == 2) { // sword - shield
			board.tryPushUp(two.name);
			System.out.println("2 " + one.name + " got pushed back from " + two.name + "'s shield");
			reactionCompleted(p);
		} else if (one.south == 1 && two.north == 0) { // sword - nothing
			board.killToken(two.name);
			System.out.println("3 " + two.name + " died, due to " + one.name + "'s Sword, vs Nothing. ");
			reactionCompleted(p);
		} else if (one.south == 0 && two.north == 1) { // nothing - sword
			board.killToken(one.name);
			System.out.println("4 " + one.name + " died, due to Nothing vs Sword. ");
			reactionCompleted(p);
		} else if (one.south == 2 && two.north == 1) { // shield - sword
			board.tryPushDown(one.name);
			System.out.println("5 " + two.name + " got pushed back from " + one.name + "'s shield");
			reactionCompleted(p);
		} else {
			System.out.println("Something went wrong in vertical reactions");
		}
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
				// System.out.println(player.everyMovement.toString());
				String options = inputString(
						"[create <letter> <0/90/180/270> / rotate <letter> <0/90/180/270> / move <letter> <up/right/down/left> / pass");
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
		if (!player.movesSoFar.isEmpty()) {
			player.movesSoFar.remove(player.movesSoFar.size() - 1);
		}
		if (player.getSetterCount() > player.getOriginalCount()) {
			firstCreation = true;
		}
		if (!player.everyMovement.isEmpty()) {
			player.everyMovement.remove(player.everyMovement.size() - 1);
		}
		if (board.checkForReaction()) {
			fight();
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
		yellow.everyMovement.clear();
		green.everyMovement.clear();
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
		if (!player.movesSoFar.contains(letter)) {
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
		for (BoardPiece bp : greList) {
			bp.name = bp.name.toUpperCase();
		}
		board.initialise();
		board.addPlayers(green, yellow);
		board.createRecord();
		yellow.populateTokens(yellow, yelList);
		green.populateTokens(green, greList);
		yellow.createRecord();
		green.createRecord();
		board.setGreen(green);
		board.setYellow(yellow);
		board.redraw();
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
		list.add(new BoardPiece("e", 0, 0, 0, 0, ""));
		list.add(new BoardPiece("f", 1, 0, 0, 1, ""));
		list.add(new BoardPiece("g", 1, 1, 1, 1, ""));
		list.add(new BoardPiece("h", 1, 0, 2, 2, ""));
		list.add(new BoardPiece("i", 0, 2, 0, 0, ""));
		list.add(new BoardPiece("j", 1, 2, 1, 2, ""));
		list.add(new BoardPiece("k", 1, 2, 0, 1, ""));
		list.add(new BoardPiece("l", 1, 0, 0, 0, ""));
		list.add(new BoardPiece("m", 1, 2, 2, 0, ""));
		list.add(new BoardPiece("n", 0, 2, 2, 0, ""));
		list.add(new BoardPiece("o", 1, 0, 1, 2, ""));
		list.add(new BoardPiece("p", 1, 0, 2, 1, ""));
		list.add(new BoardPiece("q", 1, 0, 0, 2, ""));
		list.add(new BoardPiece("r", 1, 2, 0, 2, ""));
		list.add(new BoardPiece("s", 0, 2, 0, 2, ""));
		list.add(new BoardPiece("t", 1, 0, 1, 0, ""));
		list.add(new BoardPiece("u", 1, 0, 0, 1, ""));
		list.add(new BoardPiece("v", 1, 2, 0, 0, ""));
		list.add(new BoardPiece("w", 1, 2, 2, 2, ""));
		list.add(new BoardPiece("x", 0, 2, 2, 2, ""));
	}
}
