package chessTest;

import chessModel.ChessGame;
import chessModel.King;
import chessModel.Piece;
import chessModel.Queen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class QueenTest {
    private ChessGame chessBoard;
    private Queen queen;
    private King opponentKing;

    @Before
    public void setUp() throws Exception {
        queen = new Queen(2, 2, 1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(2,2,queen);
    }
    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /*
    this test check white king's possible moves under the following condition (sliding to the left):
    4 --  --  BP  --  --
    3 --  --  --  BP  --
    2 --  --  WQ  --  WP
    1 --  --  --  --  --
    0 --  --  --  --  --
       0   1   2   3   4
     */
    @Test
    public void checkPossibleMoves() throws Exception {
        chessBoard.setGrid(2,4,new Piece(2, 4, -1));
        chessBoard.setGrid(3,3,new Piece(3, 3, -1));
        chessBoard.setGrid(4,2,new Piece(4, 2, 1));

        queen.checkPossibleMoves(chessBoard);

        assertEquals(14, queen.getPossibleMoves().size());
        for(int iterator = 0; iterator < 14; iterator++)
            System.out.printf("%d %d %d %d\n",queen.getPossibleMoves().get(iterator).startX,
                    queen.getPossibleMoves().get(iterator).startY, queen.getPossibleMoves().get(iterator).endX,
                    queen.getPossibleMoves().get(iterator).endY);
    }

    @Test
    public void checkPossibleMovesWhenCaptured() throws Exception {
        queen.setCaptured();
        queen.checkPossibleMoves(chessBoard);
        assertEquals(0, queen.getPossibleMoves().size());
    }

    @Test
    public void isValid() throws Exception {
// when given location is out of bond
        assertEquals(queen.isValid(-1, -1, chessBoard), false);

        // when give location is on the diagonal direction
        assertEquals(queen.isValid(4, 4, chessBoard), true);

        // when give location is on the cross direction
        assertEquals(queen.isValid(2, 4, chessBoard), true);

        // when give location is on none of the diagonal or cross directions
        assertEquals(queen.isValid(3, 4, chessBoard), false);

        // when given location is itself
        assertEquals(queen.isValid(2, 2, chessBoard), false);

        // when given location can be reach
        assertEquals(queen.isValid(4, 4, chessBoard), true);

        // when given location is blocked
        chessBoard.setGrid(3,3,new Piece(3,3,-1));
        assertEquals(queen.isValid(4, 4, chessBoard), false);

        // when the piece is captured
        queen.setCaptured();
        assertEquals(queen.isValid(4, 4, chessBoard), false);
    }

}