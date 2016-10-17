package chessTest;

import chessModel.ChessGame;
import chessModel.King;
import chessModel.Pawn;
import chessModel.Piece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class PawnTest {
    private ChessGame chessBoard;
    private Pawn pawn;
    private King opponentKing;
    @Before
    public void setUp() throws Exception {
        pawn = new Pawn(1, 0, 1);
        opponentKing = new King(1, 1, -1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(1,0,pawn);
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
    0 --  WP  --  --
       0   1   2   3
     */
    @Test
    public void checkPossibleMoves() throws Exception {
        pawn.checkPossibleMoves(chessBoard);
        assertEquals(3, pawn.getPossibleMoves().size());
        for(int iterator = 0; iterator < 3; iterator++)
            System.out.printf("%d %d %d %d\n",pawn.getPossibleMoves().get(iterator).startX,
                    pawn.getPossibleMoves().get(iterator).startY, pawn.getPossibleMoves().get(iterator).endX,
                    pawn.getPossibleMoves().get(iterator).endY);

    }

    @Test
    public void checkPossibleMovesWhenMoved() throws Exception {
        pawn.setHasMoved();
        pawn.checkPossibleMoves(chessBoard);
        assertEquals(2, pawn.getPossibleMoves().size());
        for(int iterator = 0; iterator < 2; iterator++)
            System.out.printf("%d %d %d %d\n",pawn.getPossibleMoves().get(iterator).startX,
                    pawn.getPossibleMoves().get(iterator).startY, pawn.getPossibleMoves().get(iterator).endX,
                    pawn.getPossibleMoves().get(iterator).endY);

    }

    /*
    this test check white king's possible moves under the following condition:
    2 --  --  --  --
    1 --  WP  BK  --
    0 --  WP  --  --
       0   1   2   3
     */
    @Test
    public void checkPossibleMovesWhenBlocked() throws Exception {
        chessBoard.setGrid(1,1,new Piece(1,1,1));
        pawn.checkPossibleMoves(chessBoard);
        assertEquals(1, pawn.getPossibleMoves().size());
        for(int iterator = 0; iterator < 1; iterator++)
            System.out.printf("%d %d %d %d\n",pawn.getPossibleMoves().get(iterator).startX,
                    pawn.getPossibleMoves().get(iterator).startY, pawn.getPossibleMoves().get(iterator).endX,
                    pawn.getPossibleMoves().get(iterator).endY);

    }

    @Test
    public void checkPossibleMovesWhenCaptured() throws Exception {
        pawn.setCaptured();

        pawn.checkPossibleMoves(chessBoard);
        assertEquals(0, pawn.getPossibleMoves().size());
    }

    @Test
    public void isValid() throws Exception {
        // when given location is out of bond
        assertEquals(pawn.isValid(-1, -1, chessBoard), false);

        // when give location is too far
        assertEquals(pawn.isValid(7, 7, chessBoard), false);

        // when given location is itself
        assertEquals(pawn.isValid(1, 0, chessBoard), false);

        // when given location can is diagonal but no opponent piece there
        assertEquals(pawn.isValid(0, 1, chessBoard), false);

        // when given location can is diagonal and there is  opponent piece there
        assertEquals(pawn.isValid(1, 1, chessBoard), true);

        // when given location can be reach
        assertEquals(pawn.isValid(1, 1, chessBoard), true);
        assertEquals(pawn.isValid(1, 2, chessBoard), true);

        // when given location is two / one step(s) away, but the pawn has moved
        pawn.setHasMoved();
        assertEquals(pawn.isValid(1, 2, chessBoard), false);
        assertEquals(pawn.isValid(1, 1, chessBoard), true);

        // when the piece is captured
        pawn.setCaptured();
        assertEquals(pawn.isValid(1, 1, chessBoard), false);
    }


}