import org.junit.*;

import static org.junit.Assert.*;
public class SwordAndShieldTests {

	@Test
	public void testYellowCreate(){
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getYellow();
		String options = "create w 90";
		game.createToken(yellow, options);
		BoardPiece testPiece = (BoardPiece)game.getBoard().board[7][7];
		assertEquals("w", testPiece.name);
	}


	@Test
	public void testGreenCreate(){
		SwordAndShieldGame game = new SwordAndShieldGame();
		Player yellow = game.getGreen();
		String options = "create C 90";
		game.createToken(yellow, options);
		BoardPiece testPiece = (BoardPiece)game.getBoard().board[2][2];
		assertEquals("C", testPiece.name);
	}




}
