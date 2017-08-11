import org.junit.*;

import static org.junit.Assert.*;

public class SwordAndShieldTests {

	// Test yellow token creation
	@Test
	public void testValidYellowCreate() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String options = "create w 90";
		game.createToken(yellow, options);
		BoardPiece testPiece = (BoardPiece) game.getBoard().getBoard()[7][7];
		assertEquals("w", testPiece.getName());
	}

	// Test green token creation
	@Test
	public void testValidGreenCreate() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		String options = "create C 90";
		game.createToken(green, options);
		BoardPiece testPiece = (BoardPiece) game.getBoard().getBoard()[2][2];
		assertEquals("C", testPiece.getName());
	}

	// Test yellow token move
	@Test
	public void testValidYellowTokenMove() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String create = "create c 90";
		String move = "move c up";
		game.createToken(yellow, create);
		game.moveToken(yellow, move);
		BoardPiece testPiece = (BoardPiece) game.getBoard().getBoard()[6][7];
		assertEquals("c", testPiece.getName());
	}

	// Test yellow token move in each direction
	@Test
	public void testValidYellowTokenMoveAllDirections() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player player = game.getYellow();
		BoardPiece one = player.find(player, "e");
		BoardPiece two = player.find(player, "s");
		BoardPiece three = player.find(player, "c");
		BoardPiece four = player.find(player, "x");
		game.getBoard().getBoard()[4][4] = one;
		assertEquals(one, game.getBoard().getBoard()[4][4]);
		game.getBoard().getBoard()[5][5] = two;
		assertEquals(two, game.getBoard().getBoard()[5][5]);
		game.getBoard().getBoard()[5][4] = three;
		assertEquals(three, game.getBoard().getBoard()[5][4]);
		game.getBoard().getBoard()[4][5] = four;
		assertEquals(four, game.getBoard().getBoard()[4][5]);
		game.moveToken(player, "move e up");
		game.moveToken(player, "move s right");
		game.moveToken(player, "move c down");
		game.moveToken(player, "move x left");
		assertEquals(one, game.getBoard().getBoard()[3][4]);
		assertEquals(two, game.getBoard().getBoard()[5][6]);
		assertEquals(three, game.getBoard().getBoard()[6][4]);
		assertEquals(four, game.getBoard().getBoard()[4][4]);
	}

	// Test green move token
	@Test
	public void testValidGreenMove() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		String create = "create C 90";
		String move = "move C up";
		game.createToken(green, create);
		game.moveToken(green, move);
		BoardPiece testPiece = (BoardPiece) game.getBoard().getBoard()[1][2];
		assertEquals("C", testPiece.getName());
	}

	// Test valid yellow rotate
	@Test
	public void testValidYellowRotate() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String create = "create l 0";
		String rotate = "rotate l 90";
		game.createToken(yellow, create);
		game.rotateToken(yellow, rotate);
		BoardPiece testPiece = (BoardPiece) game.getBoard().getBoard()[7][7];
		BoardPiece newPiece = new BoardPiece("l", 0, 1, 0, 0, "yellow");
		assertTrue(testPiece.equals(newPiece));
	}

	// Test valid green rotate
	@Test
	public void testValidGreenRotate() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		String create = "create K 0";
		String rotate = "rotate K 90";
		game.createToken(green, create);
		game.rotateToken(green, rotate);
		BoardPiece testPiece = (BoardPiece) game.getBoard().getBoard()[2][2];
		BoardPiece newPiece = new BoardPiece("K", 1, 1, 2, 0, "green");
		assertTrue(testPiece.equals(newPiece));
	}

	// Test undo
	@Test
	public void testValidUndo() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String create = "create c 90";
		game.createToken(yellow, create);
		game.undo(yellow);
		BoardPiece testPiece = (BoardPiece) game.getBoard().getBoard()[7][7];
		assertTrue(testPiece == null);
	}

	// Test undo
	// TODO Complete thisssssssssssssssssssssss
	@Test
	public void testValidUndo_2() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		Player green = game.getGreen();
		String create = "create c 90";
		String create2 = "create s 0";
		String move = "move c up";
		String move2 = "move s left";
		game.createToken(yellow, create);
		game.moveToken(yellow, move);
		game.reset(yellow, game.getBoard());
		game.reset(green, game.getBoard());
		game.createToken(yellow, create2);
		game.moveToken(yellow, move2);
		game.undo(yellow);
		game.undo(yellow);
		game.createToken(yellow, create2);
		game.moveToken(yellow, move2);
		game.undo(yellow);
		// BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[7][7];
	}

	// Test Sword v Sword reaction
	@Test
	public void testValidReaction_1() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		Player green = game.getGreen();
		String create = "create t 0";
		String create2 = "create d 0";
		String move = "move t up";
		game.createToken(yellow, create);
		game.moveToken(yellow, move);
		game.reset(yellow, game.getBoard());
		game.reset(green, game.getBoard());
		game.createToken(yellow, create2);
		game.getBoard().checkForReaction();
		BoardPiece t = game.getBoard().findToken("t");
		BoardPiece d = game.getBoard().findToken("d");
		assertTrue(t != null);
		assertTrue(d != null);
		game.verticalReaction(yellow, game.getBoard().getReactions().get(0));
		t = game.getBoard().findToken("t");
		d = game.getBoard().findToken("d");
		assertTrue(t == null);
		assertTrue(d == null);
	}

	// Test Sword v Shield reaction (Vertical)
	@Test
	public void testValidReaction_2() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "c");
		BoardPiece two = yellow.find(yellow, "t");
		game.getBoard().getBoard()[4][4] = one;
		game.getBoard().getBoard()[5][4] = two;
		game.getBoard().checkForReaction();
		game.verticalReaction(yellow, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[4][4].equals(one));
		assertTrue(game.getBoard().getBoard()[5][4] == null);
		assertTrue(game.getBoard().getBoard()[6][4].equals(two));
	}

	// Test Sword v Nothing reaction (Vertical)
	@Test
	public void testValidReaction_3() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "e");
		BoardPiece two = yellow.find(yellow, "t");
		game.getBoard().getBoard()[4][4] = one;
		game.getBoard().getBoard()[5][4] = two;
		game.getBoard().checkForReaction();
		assertTrue(game.getBoard().getBoard()[4][4].equals(one));
		game.verticalReaction(yellow, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[4][4] == null);
		assertTrue(game.getBoard().getBoard()[5][4].equals(two));
	}

	// Test Sword V Nothing reaction (Horizontal)
	@Test
	public void testValidReaction_4() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "e");
		BoardPiece two = yellow.find(yellow, "g");
		game.getBoard().getBoard()[4][4] = one;
		game.getBoard().getBoard()[4][5] = two;
		assertTrue(game.getBoard().getBoard()[4][4].equals(one));
		game.getBoard().checkForReaction();
		game.horizontalReaction(yellow, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[4][4] == null);
		assertTrue(game.getBoard().getBoard()[4][5].equals(two));
	}

	// Test Sword V Shield reaction (Horizontal)
	@Test
	public void testValidReaction_5() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "c");
		BoardPiece two = yellow.find(yellow, "g");
		game.getBoard().getBoard()[4][4] = one;
		game.getBoard().getBoard()[4][5] = two;
		game.getBoard().checkForReaction();
		game.horizontalReaction(yellow, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[4][5] == null);
		assertTrue(game.getBoard().getBoard()[4][4].equals(one));
		assertTrue(game.getBoard().getBoard()[4][6].equals(two));
	}

	// Test Sword v Sword reaction (Horizontal)
	@Test
	public void testValidReaction_6() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "g");
		BoardPiece two = yellow.find(yellow, "k");
		game.getBoard().getBoard()[4][4] = one;
		game.getBoard().getBoard()[4][5] = two;
		assertTrue(game.getBoard().getBoard()[4][4].equals(one));
		assertTrue(game.getBoard().getBoard()[4][5].equals(two));
		game.getBoard().checkForReaction();
		game.horizontalReaction(yellow, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[4][4] == null);
		assertTrue(game.getBoard().getBoard()[4][5] == null);
	}

	// Vertical Reaction with green player
	@Test
	public void testValidReaction_7() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "t");
		game.getBoard().getBoard()[2][1] = one;
		assertTrue(game.getBoard().getBoard()[2][1].equals(one));
		game.getBoard().checkForReaction();
		game.verticalReaction(yellow, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[1][1] == null);
		assertTrue(game.isGameEnd() == true);
	}

	// Vertical Reaction with yellow player
	@Test
	public void testValidReaction_8() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "t");
		game.getBoard().getBoard()[7][8] = one;
		assertTrue(game.getBoard().getBoard()[7][8].equals(one));
		game.getBoard().checkForReaction();
		game.verticalReaction(yellow, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[8][8] == null);
		assertTrue(game.isGameEnd() == true);
	}

	// Horizontal Reaction with green player
	@Test
	public void testValidReaction_9() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		BoardPiece one = green.find(green, "U");
		game.getBoard().getBoard()[1][2] = one;
		assertTrue(game.getBoard().getBoard()[1][2].equals(one));
		game.getBoard().checkForReaction();
		game.horizontalReaction(green, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[1][1] == null);
		assertTrue(game.isGameEnd() == true);
	}

	// Horizontal Reaction with yellow player
	@Test
	public void testValidReaction_10() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		BoardPiece one = green.find(green, "G");
		game.getBoard().getBoard()[7][8] = one;
		assertTrue(game.getBoard().getBoard()[7][8].equals(one));
		game.getBoard().checkForReaction();
		game.horizontalReaction(green, game.getBoard().getReactions().get(0));
		assertTrue(game.getBoard().getBoard()[8][8] == null);
		assertTrue(game.isGameEnd() == true);
	}

	@Test
	public void testValidGreenTellowTokenMoveAllDirections() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player player = game.getGreen();
		BoardPiece one = player.find(player, "E");
		BoardPiece two = player.find(player, "S");
		BoardPiece three = player.find(player, "C");
		BoardPiece four = player.find(player, "X");
		game.getBoard().getBoard()[4][4] = one;
		assertEquals(one, game.getBoard().getBoard()[4][4]);
		game.getBoard().getBoard()[5][5] = two;
		assertEquals(two, game.getBoard().getBoard()[5][5]);
		game.getBoard().getBoard()[5][4] = three;
		assertEquals(three, game.getBoard().getBoard()[5][4]);
		game.getBoard().getBoard()[4][5] = four;
		assertEquals(four, game.getBoard().getBoard()[4][5]);
		game.moveToken(player, "move e up");
		game.moveToken(player, "move s right");
		game.moveToken(player, "move c down");
		game.moveToken(player, "move x left");
		assertEquals(one, game.getBoard().getBoard()[3][4]);
		assertEquals(two, game.getBoard().getBoard()[5][6]);
		assertEquals(three, game.getBoard().getBoard()[6][4]);
		assertEquals(four, game.getBoard().getBoard()[4][4]);
	}

	@Test
	public void testValidPushing_1() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		BoardPiece one = yellow.find(yellow, "e");
		BoardPiece two = yellow.find(yellow, "s");
		BoardPiece three = yellow.find(yellow, "c");
		BoardPiece four = yellow.find(yellow, "x");
		BoardPiece five = yellow.find(yellow, "i");
		game.getBoard().getBoard()[3][9] = one;
		game.getBoard().getBoard()[4][9] = two;
		game.getBoard().getBoard()[5][9] = three;
		game.getBoard().getBoard()[6][9] = four;
		game.getBoard().getBoard()[7][9] = five;
		game.getBoard().redraw();
		game.success();
		game.moveToken(yellow, "move e down");
		assertTrue(game.getBoard().getBoard()[3][9] == null);
		assertTrue(game.getBoard().getBoard()[4][9].equals(one));
		assertTrue(game.getBoard().getBoard()[5][9].equals(two));
		assertTrue(game.getBoard().getBoard()[6][9].equals(three));
		assertTrue(game.getBoard().getBoard()[7][9].equals(four));
		game.undo(yellow);
		game.moveToken(yellow, "move i up");
		game.getBoard().redraw();
		assertTrue(game.getBoard().getBoard()[2][9].equals(one));
		assertTrue(game.getBoard().getBoard()[3][9].equals(two));
		assertTrue(game.getBoard().getBoard()[4][9].equals(three));
		assertTrue(game.getBoard().getBoard()[5][9].equals(four));
		assertTrue(game.getBoard().getBoard()[6][9].equals(five));
		assertTrue(game.getBoard().getBoard()[7][9] == null);

	}

	@Test
	public void testValidPushingBoundaries() {

	}

}
