package chessTest;

import chessModel.Apprentice;
import chessModel.ChessGame;
import chessModel.King;
import chessModel.Piece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The test case for chessModel.Apprentice class. \n
 * Since its only different from chessModel.Pawn class is that it always has not move, therefore the test cases are highly duplicated
 * with chessModel.Pawn class's \n \n
 * Created by guanheng on 9/16/2016.
 */
public class ApprenticeTest {
    private ChessGame chessBoard;
    private Apprentice apprentice;
    private King opponentKing;
    @Before
    public void setUp() throws Exception {
        apprentice = new Apprentice(1, 0, 1);
        opponentKing = new King(1, 1, -1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(1,0,apprentice);
        chessBoard.setGrid(2,1,opponentKing);
    }
    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /*
    this test check white pawn's possible moves under the following condition:
    2 --  --  --  --
    1 --  --  BK  --
    0 --  WA  --  --
       0   1   2   3
     */
    @Test
    public void checkPossibleMoves() throws Exception {
        apprentice.checkPossibleMoves(chessBoard);
        assertEquals(3, apprentice.getPossibleMoves().size());
        for(int iterator = 0; iterator < 3; iterator++)
            System.out.printf("%d %d %d %d\n",apprentice.getPossibleMoves().get(iterator).startX,
                    apprentice.getPossibleMoves().get(iterator).startY, apprentice.getPossibleMoves().get(iterator).endX,
                    apprentice.getPossibleMoves().get(iterator).endY);

    }

    // Should be the only different test from chessModel.Pawn class tests
    @Test
    public void checkPossibleMovesWhenMoved() throws Exception {
        apprentice.setHasMoved();
        apprentice.checkPossibleMoves(chessBoard);
        assertEquals(3, apprentice.getPossibleMoves().size());
        for(int iterator = 0; iterator < 3; iterator++)
            System.out.printf("%d %d %d %d\n",apprentice.getPossibleMoves().get(iterator).startX,
                    apprentice.getPossibleMoves().get(iterator).startY, apprentice.getPossibleMoves().get(iterator).endX,
                    apprentice.getPossibleMoves().get(iterator).endY);

    }

    /*
    this test check white king's possible moves under the following condition:
    2 --  --  --  --
    1 --  WP  BK  --
    0 --  WA  --  --
       0   1   2   3
     */
    @Test
    public void checkPossibleMovesWhenBlocked() throws Exception {
        chessBoard.setGrid(1,1,new Piece(1,1,1));
        apprentice.checkPossibleMoves(chessBoard);
        assertEquals(1, apprentice.getPossibleMoves().size());
        for(int iterator = 0; iterator < 1; iterator++)
            System.out.printf("%d %d %d %d\n",apprentice.getPossibleMoves().get(iterator).startX,
                    apprentice.getPossibleMoves().get(iterator).startY, apprentice.getPossibleMoves().get(iterator).endX,
                    apprentice.getPossibleMoves().get(iterator).endY);

    }

    @Test
    public void checkPossibleMovesWhenCaptured() throws Exception {
        apprentice.setCaptured();

        apprentice.checkPossibleMoves(chessBoard);
        assertEquals(0, apprentice.getPossibleMoves().size());
    }

    @Test
    public void isValid() throws Exception {
        // when given location is out of bond
        assertEquals(apprentice.isValid(-1, -1, chessBoard), false);

        // when give location is too far
        assertEquals(apprentice.isValid(7, 7, chessBoard), false);

        // when given location is itself
        assertEquals(apprentice.isValid(1, 0, chessBoard), false);

        // when given location can is diagonal but no opponent piece there
        assertEquals(apprentice.isValid(0, 1, chessBoard), false);

        // when given location can is diagonal and there is  opponent piece there
        assertEquals(apprentice.isValid(1, 1, chessBoard), true);

        // when given location can be reach
        assertEquals(apprentice.isValid(1, 1, chessBoard), true);
        assertEquals(apprentice.isValid(1, 2, chessBoard), true);

        // when given location is two / one step(s) away, but the pawn has moved
        apprentice.setHasMoved();
        assertEquals(apprentice.isValid(1, 2, chessBoard), true);
        assertEquals(apprentice.isValid(1, 1, chessBoard), true);

        // when the piece is captured
        apprentice.setCaptured();
        assertEquals(apprentice.isValid(1, 1, chessBoard), false);
    }
}