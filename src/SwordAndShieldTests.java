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

	//Test green rotate
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










}
