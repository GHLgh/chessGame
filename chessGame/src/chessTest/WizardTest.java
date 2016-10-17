package chessTest;

import chessModel.ChessGame;
import chessModel.King;
import chessModel.Piece;
import chessModel.Wizard;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/16/2016.
 */
public class WizardTest {
    private ChessGame chessBoard;
    private Wizard wizard;
    private King opponentKing;

    @Before
    public void setUp() throws Exception {
        wizard = new Wizard(2, 2, 1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(2,2,wizard);
    }
    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /*
   this test check white wizard's possible moves under the following condition (sliding to the left):
   4 --  --  BP  --  --
   3 --  --  --  BP  --
   2 --  --  WQ  --  WP
   1 --  --  --  --  --
   0 --  --  --  --  --
      0   1   2   3   4
    when it is wizard's odd number move, it should follow rock's logic
    */
    @Test
    public void checkPossibleMovesWhenOdd() throws Exception {
        chessBoard.setGrid(2,4,new Piece(2, 4, -1));
        chessBoard.setGrid(3,3,new Piece(3, 3, -1));
        chessBoard.setGrid(4,2,new Piece(4, 2, 1));

        //when it is wizard's odd number move, it should follow rock's logic
        wizard.checkPossibleMoves(chessBoard);

        assertEquals(7, wizard.getPossibleMoves().size());
        for(int iterator = 0; iterator < 7; iterator++)
            System.out.printf("%d %d %d %d\n",wizard.getPossibleMoves().get(iterator).startX,
                    wizard.getPossibleMoves().get(iterator).startY, wizard.getPossibleMoves().get(iterator).endX,
                    wizard.getPossibleMoves().get(iterator).endY);


    }

    /*
   this test check white wizard's possible moves under the following condition (sliding to the left):
   4 --  --  BP  --  --
   3 --  --  --  BP  --
   2 --  --  WQ  --  WP
   1 --  --  --  --  --
   0 --  --  --  --  --
      0   1   2   3   4
    */
    @Test
    public void checkPossibleMovesWhenEven() throws Exception {
        chessBoard.setGrid(2,4,new Piece(2, 4, -1));
        chessBoard.setGrid(3,3,new Piece(3, 3, -1));
        chessBoard.setGrid(4,2,new Piece(4, 2, 1));

        //when it is wizard's even number move, it should follow bishop's logic
        wizard.setHasMoved();
        wizard.checkPossibleMoves(chessBoard);

        assertEquals(7, wizard.getPossibleMoves().size());
        for(int iterator = 0; iterator < 7; iterator++)
            System.out.printf("%d %d %d %d\n",wizard.getPossibleMoves().get(iterator).startX,
                    wizard.getPossibleMoves().get(iterator).startY, wizard.getPossibleMoves().get(iterator).endX,
                    wizard.getPossibleMoves().get(iterator).endY);


    }

    @Test
    public void isValidWhenOdd() throws Exception {
    // when given location is out of bond
        assertEquals(wizard.isValid(-1, -1, chessBoard), false);

        // when give location is not on the diagonal direction
        assertEquals(wizard.isValid(4, 5, chessBoard), false);

        // when given location is itself
        assertEquals(wizard.isValid(2, 2, chessBoard), false);

        // when given location can be reach
        assertEquals(wizard.isValid(2, 0, chessBoard), true);

        // when given location is blocked
        chessBoard.setGrid(2,4,new Piece(2, 4, -1));
        assertEquals(wizard.isValid(2, 5, chessBoard), false);

        // when the piece is captured
        wizard.setCaptured();
        assertEquals(wizard.isValid(2, 0, chessBoard), false);
    }

    @Test
    public void isValidWhenEven() throws Exception {
        // set the wizard to even logic (bishop's logic)
        wizard.setHasMoved();
        wizard.checkPossibleMoves(chessBoard);

        // when given location is out of bond
        assertEquals(wizard.isValid(-1, -1, chessBoard), false);

        // when give location is not on the cross direction
        assertEquals(wizard.isValid(2, 0, chessBoard), false);

        // when given location is itself
        assertEquals(wizard.isValid(2, 2, chessBoard), false);

        // when given location can be reach
        assertEquals(wizard.isValid(0, 0, chessBoard), true);

        // when given location is blocked
        chessBoard.setGrid(3,3,new Piece(3, 3, -1));
        assertEquals(wizard.isValid(4, 4, chessBoard), false);

        // when the piece is captured
        wizard.setCaptured();
        assertEquals(wizard.isValid(0, 0, chessBoard), false);
    }

}