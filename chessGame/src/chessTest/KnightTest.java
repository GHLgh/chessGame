package chessTest;

import chessModel.ChessGame;
import chessModel.King;
import chessModel.Knight;
import chessModel.Piece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class KnightTest {
    private ChessGame chessBoard;
    private Knight knight;
    private King opponentKing;

    @Before
    public void setUp() throws Exception {
        knight = new Knight(1, 0, 1);
        opponentKing = new King(2, 2, -1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(1,0,knight);
        chessBoard.setGrid(2,2,opponentKing);
    }

    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /*
   this test check white king's possible moves under the following condition:
   2 WP  --  BK  --
   1 --  --  --  --
   0 --  WN  --  --
      0   1   2   3
    */
    @Test
    public void checkPossibleMoves() throws Exception {
        chessBoard.setGrid(0,2,new Piece(0,2,1));

        knight.checkPossibleMoves(chessBoard);
        assertEquals(2, knight.getPossibleMoves().size());
        for(int iterator = 0; iterator < 2; iterator++)
            System.out.printf("%d %d %d %d\n",knight.getPossibleMoves().get(iterator).startX,
                    knight.getPossibleMoves().get(iterator).startY, knight.getPossibleMoves().get(iterator).endX,
                    knight.getPossibleMoves().get(iterator).endY);

    }

    @Test
    public void checkPossibleMovesWhenCaptured() throws Exception {
        knight.setCaptured();

        knight.checkPossibleMoves(chessBoard);
        assertEquals(0, knight.getPossibleMoves().size());
    }

    @Test
    public void isValid() throws Exception {
        // when given location is out of bond
        assertEquals(knight.isValid(-1, -1, chessBoard), false);

        // when give location is not on the spot
        assertEquals(knight.isValid(0, 0, chessBoard), false);

        // when given location is itself
        assertEquals(knight.isValid(1, 0, chessBoard), false);

        // when given location can be reach
        assertEquals(knight.isValid(0, 2, chessBoard), true);

        // when the piece is captured
        knight.setCaptured();
        assertEquals(knight.isValid(0, 2, chessBoard), false);
    }

}