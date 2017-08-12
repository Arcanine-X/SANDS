import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextClient {
	private static Board board;
	private static SwordAndShieldGame game;

	/**
	 * Get a input from system in
	 * @param msg
	 * @return
	 */
	private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}

	/**
	 * Method takes asks the user for a input, and accordingly passes the input to other functions. If the
	 * the stack size is 1, then it means that its the start of the turn, and the user can create or pass. Otherwise if the stack size is greater than
	 * 1 then the user must have created or passed, and so the user can only move, rotate or pass. Any invalid in
	 * @param player
	 */
	private static void playerOptions(Player player) {
		while (true) {
			try {
				if (game.isGameEnd()) {
					return;
				}
				if (board.checkForReaction()) {
					fight(player);
				}
				String options = "";
				if (game.getBoard().getUndoStack().size() == 1) {
					game.setFirstCreation(true);
					options = inputString("[create <letter> <0/90/180/270>  / pass");
					if (options.startsWith("create")) {
						game.createToken(player, options);
					} else if (options.startsWith("pass")) {
						game.setFirstCreation(false);
						game.success();
					} else {
						System.out.println("invalid option");
					}
				}
				if (game.isGameEnd()) {
					return;
				}
				if (board.checkForReaction()) {
					fight(player);
				}
				if (game.isGameEnd()) {
					return;
				}
				options = "";
				if (game.getBoard().getUndoStack().size() > 1) {
					options = inputString(
							"[rotate <letter> <0/90/180/270> / move <letter> <up/right/down/left> / pass");
					if (options.startsWith("rotate")) {
						game.rotateToken(player, options);
					} else if (options.startsWith("move")) {
						game.moveToken(player, options);
					} else if (options.startsWith("pass")) {
						System.out.println("Next persons turn");
						game.reset(player, board);
						return;
					} else if (options.startsWith("undo")) {
						game.undo(player);
					} else {
						System.out.println("Invalid option....");
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void fight(Player player) {
		while (!board.getReactions().isEmpty()) {
			if (game.isGameEnd()) {
				return;
			}
			board.redraw();
			try {
				System.out.println("Here are the possiable reactions:");
				for (Pair p : board.getReactions()) {
					if (p.getPlayer() != null) {
						System.out.println("There is a reaction between " + p.getOne().getName() + " and "
								+ p.getPlayer().getName());
					} else {
						System.out.println(
								"There is a reaction between " + p.getOne().getName() + " and " + p.getTwo().getName());
					}
				}
				String options = "";
				if (board.getReactions().size() > 1) {
					options = inputString(
							"There are multiple reactions.\n Enter the two letters of which the interactions should occur between first eg (a b) or undo : ");
					String[] tokens = options.split(" ");
					if (options.startsWith("undo")) {
						game.undo(player);
					} else {
						if (tokens.length != 2) {
							System.out.println("Incorrect input");
							continue;
						}
						String a = tokens[0], b = tokens[1];
						Pair pair = null;
						if (a.length() > 1 || b.length() > 1) {
							for (Pair p : board.getReactions()) {
								if (p.getPlayer() != null) {
									System.out.println(p.getPlayer().getName());
									if ((p.getPlayer().getName().equals(b) && p.getOne().getName().equals(a)) ||(
											p.getPlayer().getName().equals(a) && p.getOne().getName().equals(b))){
										pair = p;
										break;
									}
								}
							}
						} else {
							for (Pair p : board.getReactions()) {
								if ((p.getOne().getName().equals(a) && p.getTwo().getName().equals(b))
										|| (p.getOne().getName().equals(b) && p.getTwo().getName().equals(a))) {
									System.out.println("Found pair");
									pair = p;
									break;
								}
							}
						}
						if (pair == null) {
							System.out.println("Invalid input");
							continue;
						}
						if (pair.getDir().equals("vert")) {
							System.out.println("vertical");
							game.verticalReaction(player, pair);
						}
						if (pair.getDir().equals("hori")) {
							System.out.println("horizontal");
							game.horizontalReaction(player, pair);
						}
					}
				} else {
					options = inputString("Would you like to continue with the reaction? Yes/Undo");
					options = options.toLowerCase();
					options = options.trim();
					if (options.equals("undo")) {
						game.undo(player);
					} else if (options.equals("yes") || options.equals("y")) {
						if (board.getReactions().get(0).getDir().equals("hori")) {
							game.horizontalReaction(player, board.getReactions().get(0));
						} else if (board.getReactions().get(0).getDir().equals("vert")) {
							game.verticalReaction(player, board.getReactions().get(0));
						} else {
							System.out.println("Invalid reaction");
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

	public static void main(String[] args) {
		game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		Player green = game.getGreen();
		board = game.getBoard();
		int turn = 0;
		System.out.println("~*~*~ Sword & Shield ~*~*~");
		while (!game.isGameEnd()) {// loop forever
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
		board.setGameEnded(true);
		String winner = board.getBoard()[1][1] ==null ? "Yellow" : "Green";
		System.out.println("The winner is " + winner + "!");
		System.out.println("Thanks for playing");
		System.out.println("=========================================================================\n\n\n\n");

	}
}
