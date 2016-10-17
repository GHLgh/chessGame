package chessTest;

import chessModel.Bishop;
import chessModel.ChessGame;
import chessModel.King;
import chessModel.Piece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class BishopTest {
    private ChessGame chessBoard;
    private Bishop bishop;
    private King opponentKing;
    @Before
    public void setUp() throws Exception {
        bishop = new Bishop(1, 4, 1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(1,4,bishop);
    }
    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /*
    this test check white king's possible moves under the following condition (sliding to the left):
    6 --  --  --  BP  --
    5 --  --  --  --  --
    4 --  WB  --  --  --
    3 --  --  WP  --  --
       0   1   2   3   4
     */
    @Test
    public void checkPossibleMoves() throws Exception {
        chessBoard.setGrid(2,3,new Piece(2, 3, 1));
        chessBoard.setGrid(3,6,new Piece(3, 6, -1));

        bishop.checkPossibleMoves(chessBoard);

        assertEquals(4, bishop.getPossibleMoves().size());
        for(int iterator = 0; iterator < 4; iterator++)
            System.out.printf("%d %d %d %d\n",bishop.getPossibleMoves().get(iterator).startX,
                    bishop.getPossibleMoves().get(iterator).startY, bishop.getPossibleMoves().get(iterator).endX,
                    bishop.getPossibleMoves().get(iterator).endY);
    }

    @Test
    public void checkPossibleMovesWhenCaptured() throws Exception {
        bishop.setCaptured();
        bishop.checkPossibleMoves(chessBoard);
        assertEquals(0, bishop.getPossibleMoves().size());
    }

    @Test
    public void isValid() throws Exception {
        // when given location is out of bond
        assertEquals(bishop.isValid(-1, -1, chessBoard), false);

        // when give location is not on the diagonal direction
        assertEquals(bishop.isValid(4, 4, chessBoard), false);

        // when given location is itself
        assertEquals(bishop.isValid(1, 4, chessBoard), false);

        // when given location can be reach
        assertEquals(bishop.isValid(3, 6, chessBoard), true);

        // when given location is blocked
        chessBoard.setGrid(2,5,new Piece(2,5,-1));
        assertEquals(bishop.isValid(3, 6, chessBoard), false);

        // when the piece is captured
        bishop.setCaptured();
        assertEquals(bishop.isValid(3, 6, chessBoard), false);
    }

}