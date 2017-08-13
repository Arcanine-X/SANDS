import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class contains the code for interfacing with the Sword and Shield game. It also
 * contains much of the game logic for controlling how the user can interact.
 * @author Chin Patel
 *
 */

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
			//get a input from system in
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}


	/**
	 * Method takes a user input, and accordingly passes the input to other functions. If the
	 * the stack size is 1, then it means that its the start of the turn, and the user can create or pass. Otherwise if the stack size is greater than
	 * 1 then the user must have created or passed, and so the user can only move, rotate or pass. Any invalid in
	 * @param player
	 */
	private static void playerOptions(Player player) {
		while (true) {
			try {
				if (game.isGameEnd()) { // Check if the game has ended yet
					return;
				}
				if (board.checkForReaction()) { // Check for reactions
					fight(player);
				}
				if (game.isGameEnd()) { // Need to check if game has ended after reactions
					return;
				}
				String options = "";
				if (game.getBoard().getUndoStack().size() == 1) {// If stack size is = 1, then they haven't created or passed yet
					game.setFirstCreation(true);
					options = inputString("[create <letter> <0/90/180/270>  / pass").toLowerCase().trim();
					if (options.split(" ")[0].equals("create")) {
						game.createToken(player, options);
					} else if (options.split(" ")[0].equals("pass")) {
						game.setFirstCreation(false);
						game.success(); // Create record so the user can undo back to the create phase
					} else {
						System.out.println("Invalid option");
					}
				}
				if (board.checkForReaction()) { // Again check for reactions and if the game has ended or not
					fight(player);
				}
				if (game.isGameEnd()) {
					return;
				}
				options = "";
				if (game.getBoard().getUndoStack().size() > 1) { // If stack size is greater then 1, then they have either created or passed already
					options = inputString(
							"[rotate <letter> <0/90/180/270> / move <letter> <up/right/down/left> / pass").toLowerCase().trim();
					if (options.split(" ")[0].equals("rotate")) {
						game.rotateToken(player, options);
					} else if (options.split(" ")[0].equals("move")) {
						game.moveToken(player, options);
					} else if (options.split(" ")[0].equals("pass")) {
						System.out.println("Next persons turn");
						game.reset(player, board);
						return;
					} else if (options.split(" ")[0].equals("undo")) {
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

	/**
	 * This method is called when there is a reaction. If there is only one reaction the user is forced to do that reaction,
	 * or undo. Otherwise if there are multiple reactions, then it will loop through the list of reactions and print
	 * all of them out for the user. It then takes an input of what the reaction should occur between, and goes through the list of pairs of reaction
	 * and finds the reaction. With this pair of reaction, it checks the direction of the reaction and calls the appropriate method,
	 * either vertical or horizontal reaction, which deals with the rest.
	 * @param player
	 */
	private static void fight(Player player) {
		while (!board.getReactions().isEmpty()) { // Want to keep looping until no reactions are left
			if (game.isGameEnd()) { // If a player has died, then end the game
				return;
			}
			try {
				System.out.println("Here are the possiable reactions:");
				for (Pair p : board.getReactions()) {
					if (p.getPlayer() != null) { // if there's a player reaction print out player name and token reacting with it
						System.out.println("There is a reaction between " + p.getOne().getName() + " and "
								+ p.getPlayer().getName());
					} else {
						System.out.println( // print out two board piece names that are reacting together
								"There is a reaction between " + p.getOne().getName() + " and " + p.getTwo().getName());
					}
				}
				String options = "";
				if (board.getReactions().size() > 1) { //in this case the user has to pick the pair of reactions
					options = inputString(
							"There are multiple reactions.\n Please Note : Capital letters matter \nEnter the two letters of which the interactions should occur between first eg (a b) or undo : ");
					String[] tokens = options.split(" ");
					if (tokens[0].equals("undo")) {
						game.undo(player);
						board.redraw();
					} else {
						if (tokens.length != 2) { // input should contain at least 2 characters
							System.out.println("Incorrect input");
							continue;
						}
						String a = tokens[0], b = tokens[1];
						Pair pair = null;
						if (a.length() > 1 || b.length() > 1) { // check if they are trying to react with the player
							for (Pair p : board.getReactions()) {
								if (p.getPlayer() != null) {
									// Check for permutations of input eg if there's a reaction between a and b check for (a-b, b-a)
									if ((p.getPlayer().getName().equals(b) && p.getOne().getName().equals(a)) ||(
											p.getPlayer().getName().equals(a) && p.getOne().getName().equals(b))){
										pair = p;
										break; //if we find the pair then break
									}
								}
							}
						} else { //otherwise loop through the pairs to try and find the pair of reactions
							for (Pair p : board.getReactions()) {
								if ((p.getOne().getName().equals(a) && p.getTwo().getName().equals(b)) // check for both permutations of user input
										|| (p.getOne().getName().equals(b) && p.getTwo().getName().equals(a))) {
									pair = p;
									break;
								}
							}
						}
						if (pair == null) { // Shouldn't ever happen
							System.out.println("Invalid input");
							continue;
						}
						if (pair.getDir().equals("vert")) { // vertical reaction
							game.verticalReaction(player, pair);
						}
						if (pair.getDir().equals("hori")) { // horizontal reaction
							game.horizontalReaction(player, pair);
						}
					}
				} else { // in this case there is only one reaction so the user can only continue or undo
					options = inputString("Would you like to continue with the reaction? Yes/Undo").toLowerCase().trim();
					if (options.equals("undo")) {
						game.undo(player);
						board.redraw();
					} else if (options.equals("yes") || options.equals("y")) {
						if (board.getReactions().get(0).getDir().equals("hori")) { // horizontal reaction
							game.horizontalReaction(player, board.getReactions().get(0));
						} else if (board.getReactions().get(0).getDir().equals("vert")) { // vertical reaction
							game.verticalReaction(player, board.getReactions().get(0));
						} else {
							System.out.println("Invalid reaction"); // shouldn't ever happen
						}
					} else {
						System.out.println("Not a valid input, try again"); // shouldn't ever happen
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
		System.out.println("~*~*~ Sword & Shield Game~*~*~");
		System.out.println("Welcome to the Sword and Shield Game");
		System.out.println("The game is simple");
		System.out.println("One player will be green, and the other player will be incorectlly spelt yellow");
		System.out.println("Your aim is to kill the other player, by putting a sword to his head");
		System.out.println("Swords are indicated with a '|' or '-'");
		System.out.println("Shields are indicated with a +");
		System.out.println("Reactions will occur when two pieces are adjacent to each other and one has sword");
		System.out.println("In the affair of honor, swords v swords, both tokens will die");
		System.out.println("If one is reluctant to die, swords v shields, the token with the sword will be pushed back one");
		System.out.println("If one poor soul is having a unfortunate day, sword v nothing, the token with nothing will be eliminated");
		System.out.println("At the start of your turn you can either create or pass, and then you can move rotate or pass again");
		System.out.println("Have fun :)");
		int turn = 1;
		while (!game.isGameEnd()) {// loop until game is ended
			System.out.println("\n********************");
			System.out.println("***** TURN " + turn + " *******");
			System.out.println("********************\n");
			if (turn % 2 == 0) {
				System.out.println("It is greens turn!");
				playerOptions(green);
				board.redraw();
			} else {
				System.out.println("It is yellows turn!");
				playerOptions(yellow);
				board.redraw();

			}
			turn++;
		}
		board.setGameEnded(true); // doesn't redraw the players
		String winner = board.getBoard()[1][1] == null ? "Yellow" : "Green"; // get the winner
		System.out.println("After a long battle " + winner + " has taken victory");
		System.out.println("Thanks for playing");
		System.out.println("=========================================================================\n\n\n\n");
	}
}
