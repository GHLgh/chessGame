package chessTest;

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
public class KingTest {
    private ChessGame chessBoard;
    private King king;
    private King opponentKing;
    @Before
    public void setUp() throws Exception {
        king = new King(1, 0, 1);
        opponentKing = new King(1, 1, -1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(1,0,king);
        chessBoard.setGrid(1,1,opponentKing);
    }
    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /*
    this test check white king's possible moves under the following condition:
    2 --  --  --  --
    1 --  BK  --  --
    0 WP  WK  --  --
       0   1   2   3
     */
    @Test
    public void checkPossibleMoves() throws Exception {
        chessBoard.setGrid(0,0,new Piece(0,0,1));

        king.checkPossibleMoves(chessBoard);
        assertEquals(4, king.getPossibleMoves().size());
        for(int iterator = 0; iterator < 4; iterator++)
            System.out.printf("%d %d %d %d\n",king.getPossibleMoves().get(iterator).startX,
                    king.getPossibleMoves().get(iterator).startY, king.getPossibleMoves().get(iterator).endX,
                    king.getPossibleMoves().get(iterator).endY);

    }

    @Test
    public void checkPossibleMovesWhenCaptured() throws Exception {
        king.setCaptured();

        king.checkPossibleMoves(chessBoard);
        assertEquals(0, king.getPossibleMoves().size());
    }

    @Test
    public void isValid() throws Exception {
        // when given location is out of bond
        assertEquals(king.isValid(-1, -1, chessBoard), false);

        // when give location is too far
        assertEquals(king.isValid(7, 7, chessBoard), false);

        // when given location is itself
        assertEquals(king.isValid(1, 0, chessBoard), false);

        // when given location can be reach
        assertEquals(king.isValid(1, 1, chessBoard), true);

        // when the piece is captured
        king.setCaptured();
        assertEquals(king.isValid(1, 1, chessBoard), false);
    }

}