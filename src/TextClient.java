import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextClient {
	private static Board board;
	private static SwordAndShieldGame game;

	/**
	 * Get string from System.in
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

	public static void playerOptions(Player player) {
        while (true) {
            System.out.println("Stack size is " + game.getBoard().getUndoStack().size());
            try {
                if (board.checkForReaction()) {
                    fight(player);
                }
                String options = "";
                if(game.getBoard().getUndoStack().size() == 1){
                    game.setFirstCreation(true);
                     options = inputString("[create <letter> <0/90/180/270>  / pass");
                     if(options.startsWith("create")){
                         game.createToken(player, options);
                     }else if(options.startsWith("pass")){
                         game.setFirstCreation(false);
                         game.success();
                     }else{
                         System.out.println("invalid option");
                     } 
                }
                System.out.println("Stack size is " + game.getBoard().getUndoStack().size());
                options = "";
                if(game.getBoard().getUndoStack().size()>1){
                     options = inputString("[rotate <letter> <0/90/180/270> / move <letter> <up/right/down/left> / pass");
                     if (options.startsWith("rotate")) {
                        game.rotateToken(player, options);
                    } else if (options.startsWith("move")) {
                        game.moveToken(player, options);
                    } else if (options.startsWith("pass")) {
                        System.out.println("Next persons turn");
                        game.reset(player, board);
                        return;
                    } else if (options.startsWith("undo")) {
                        System.out.println("in undo");
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
			board.redraw();
			try {
				System.out.println("Here are the possiable reactions:");
				for (Pair p : board.getReactions()) {
					System.out.println(
							"There is a reaction between " + p.getOne().getName() + " and " + p.getTwo().getName());
				}
				String options = "";
				if (board.getReactions().size() > 1) {
					options = inputString(
							"There are multiple reactions. Enter the two letters of which the interactions should occur between first (or undo) : ");
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
						for (Pair p : board.getReactions()) {
							if ((p.getOne().getName().equals(a) && p.getTwo().getName().equals(b))
									|| (p.getOne().getName().equals(b) && p.getTwo().getName().equals(a))) {
								System.out.println("Found pair");
								pair = p;
								break;
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
					if (options.startsWith("undo")) {
						// then undo
						game.undo(player);
					} else if (options.startsWith("yes") || options.startsWith("y")) {
						// do the reaction
						if (board.getReactions().get(0).getDir().equals("hori")) {
							game.horizontalReaction(player, board.getReactions().get(0));
						} else if (board.getReactions().get(0).getDir().equals("vert")) {
							game.verticalReaction(player, board.getReactions().get(0));
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

	public static void main(String[] args) {
		game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		Player green = game.getGreen();
		board = game.getBoard();
		int turn = 0;
		System.out.println("~*~*~ Sword & Shield ~*~*~");
		while (true) {// loop forever
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
}
