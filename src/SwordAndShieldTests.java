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
		BoardPiece testPiece = (BoardPiece)game.getBoard().board[7][7];
		assertEquals("w", testPiece.getName());
	}

	//Test green token creation
	@Test
	public void testValidGreenCreate(){
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getGreen();
		String options = "create C 90";
		game.createToken(yellow, options);
		BoardPiece testPiece = (BoardPiece)game.getBoard().board[2][2];
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
		BoardPiece testPiece = (BoardPiece)game.getBoard().board[6][7];
		assertEquals("c", testPiece.getName());
	}




}
