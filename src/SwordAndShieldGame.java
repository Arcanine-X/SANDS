import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SwordAndShieldGame {
	private Player green;
	private Player yellow;
	private Board board;
	private List<BoardPiece> yelList = new ArrayList<>();
	private List<BoardPiece> greList = new ArrayList<>();
	private Set<String> movement = new HashSet<String>(Arrays.asList("up", "down", "left", "right"));
	private Set<Integer> rotations = new HashSet<Integer>(Arrays.asList(0, 90, 180, 270));
	private boolean firstCreation = true;


	public SwordAndShieldGame() {
		green = new Player("green");
		yellow = new Player("yellow");
		board = new Board();
		initialiseGame();
	}

	public void initialiseGame(){
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
	}

	public boolean checkIfAllowedToMove(Player player, String letter) {
		if (!player.movesSoFar.contains(letter)) {
			player.movesSoFar.add(letter);
			return true;
		}
		System.out.println("You have already moved this piece this turn");
		System.out.println("You can move another existing piece or move it next turn");
		return false;
	}

	public void moveToken(Player player, String options) {
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
		// Find the piece to move

		player.checkForSpace(player, tokenToMove, direction, board);
		player.everyMovement.add(tokenToMove);
		success();
	}

	public void rotateToken(Player player, String options) {
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
		System.out.println("Successful rotation");
	}

	public void createToken(Player player, String options) {
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



	public void undo(Player player) {
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
			return;
		}
		board.redraw();
	}


	public void reactionCompleted(Player player, Pair p) {
		board.redraw();
		System.out.println(p.toString());
		board.reactions.remove(p);
		board.createRecord();
		yellow.createRecord();
		green.createRecord();
		if (board.checkForReaction()) {
			board.redraw();
			return;
		}
	}

	public void success() {
		board.createRecord();
		green.createRecord();
		yellow.createRecord();
		board.redraw();
	}

	public void reset(Player player, Board board) {// after passing need to reset stuff
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

	public void rotator(BoardPiece item, int rotation) {
		int tn = item.north, te = item.east, ts = item.south, tw = item.west;
		item.north = tw;
		item.east = tn;
		item.south = te;
		item.west = ts;
	}



	public void gerneratePieces(List<BoardPiece> list) {
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


	public void horizontalReaction(Player player, Pair p) {
		System.out.println("Horizontal Reaction");
		BoardPiece one = p.one;
		BoardPiece two = p.two;
		if (one.east == 1 && two.west == 1) { // sword - sword
			board.killToken(one.name);
			board.killToken(two.name);
			System.out.println("1 " + one.name + " and " + two.name + " died, due to Sword vs Sword. ");
			reactionCompleted(player,p);
		} else if (one.east == 1 && two.west == 0) { // sword - nothing
			board.killToken(two.name);
			System.out.println("2 " + two.name + " died, due to " + one.name + "'s Sword, vs Nothing. ");
			reactionCompleted(player,p);
		} else if (one.east == 1 && two.west == 2) { // sword - shield
			board.tryPushLeft(two.name);
			System.out.println("3 " + one.name + " got pushed back from " + two.name + "'s shield");
			reactionCompleted(player,p);
		} else if (one.east == 0 && two.west == 1) { // nothing - sword
			board.killToken(one.name);
			System.out.println("4 " + one.name + " died from " + two.west + "'s sword");
			reactionCompleted(player,p);
		} else if (one.east == 2 && two.west == 1) { // shield - sword
			board.tryPushRight(one.name);
			System.out.println("5 " + two.name + " got pushed back from " + one.name + "'s shield");
			reactionCompleted(player,p);
		} else {
			System.out.println("Something went wrong in horizontal reactions");
		}
	}

	public void verticalReaction(Player player, Pair p) {
		// Five possibale reactions, sword - sword, sword - nothing, nothing - sword,
		// shield - sword, sword - shield
		System.out.println("Vertical Reaction");
		BoardPiece one = p.one;
		BoardPiece two = p.two;
		if (one.south == 1 && two.north == 1) { // sword - sword
			board.killToken(one.name);
			board.killToken(two.name);
			System.out.println("1 " + one.name + " and " + two.name + " died, due to Sword vs Sword. ");
			reactionCompleted(player, p);
		} else if (one.south == 1 && two.north == 2) { // sword - shield
			board.tryPushUp(two.name);
			System.out.println("2 " + one.name + " got pushed back from " + two.name + "'s shield");
			reactionCompleted(player, p);
		} else if (one.south == 1 && two.north == 0) { // sword - nothing
			board.killToken(two.name);
			System.out.println("3 " + two.name + " died, due to " + one.name + "'s Sword, vs Nothing. ");
			reactionCompleted(player, p);
		} else if (one.south == 0 && two.north == 1) { // nothing - sword
			board.killToken(one.name);
			System.out.println("4 " + one.name + " died, due to Nothing vs Sword. ");
			reactionCompleted(player, p);
		} else if (one.south == 2 && two.north == 1) { // shield - sword
			board.tryPushDown(one.name);
			System.out.println("5 " + two.name + " got pushed back from " + one.name + "'s shield");
			reactionCompleted(player, p);
		} else {
			System.out.println("Something went wrong in vertical reactions");
		}
	}


	public Player getGreen() {
		return green;
	}
	public Player getYellow() {
		return yellow;
	}

	public Board getBoard() {
		return board;
	}
}
