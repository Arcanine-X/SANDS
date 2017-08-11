import org.junit.*;

import static org.junit.Assert.*;
public class SwordAndShieldTests {

	//Test yellow token creation
	@Test
	public void testValidYellowCreate(){
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String options = "create w 90";
		game.createToken(yellow, options);
		BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[7][7];
		assertEquals("w", testPiece.getName());
	}

	//Test green token creation
	@Test
	public void testValidGreenCreate(){
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		String options = "create C 90";
		game.createToken(green, options);
		BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[2][2];
		assertEquals("C", testPiece.getName());
	}

	//Test yellow token move
	@Test
	public void testValidYellowTokenMove() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String create = "create c 90";
		String move = "move c up";
		game.createToken(yellow, create);
		game.moveToken(yellow, move);
		BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[6][7];
		assertEquals("c", testPiece.getName());
	}

	//Test green move token
	@Test
	public void testValidGreenMove() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		String create = "create C 90";
		String move = "move C up";
		game.createToken(green, create);
		game.moveToken(green, move);
		BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[1][2];
		assertEquals("C", testPiece.getName());
	}

	//Test valid yellow rotate
	@Test
	public void testValidYellowRotate() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String create = "create l 0";
		String rotate = "rotate l 90";
		game.createToken(yellow, create);
		game.rotateToken(yellow, rotate);
		BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[7][7];
		BoardPiece newPiece = new BoardPiece("l", 0, 1, 0, 0, "yellow");
		assertTrue(testPiece.equals(newPiece));
	}

	//Test valid green rotate
	@Test
	public void testValidGreenRotate() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player green = game.getGreen();
		String create = "create K 0";
		String rotate = "rotate K 90";
		game.createToken(green, create);
		game.rotateToken(green, rotate);
		BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[2][2];
		BoardPiece newPiece = new BoardPiece("K", 1, 1, 2, 0, "green");
		assertTrue(testPiece.equals(newPiece));
	} 
	
	//Test undo
	@Test
	public void testValidUndo() {
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String create = "create c 90";
		game.createToken(yellow, create);
		game.undo(yellow);
		BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[7][7];
		assertTrue(testPiece == null);
	}
	
	//Test undo
	//TODO Complete thisssssssssssssssssssssss
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
		//BoardPiece testPiece = (BoardPiece)game.getBoard().getBoard()[7][7];
	}

	//Test Sword v Sword reaction
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
		assertTrue(t!=null);
		assertTrue(d!=null);
		game.verticalReaction(yellow, game.getBoard().getReactions().get(0));
		t = game.getBoard().findToken("t");
		d = game.getBoard().findToken("d");
		assertTrue(t == null);
		assertTrue(d == null);
	} 
	










}
