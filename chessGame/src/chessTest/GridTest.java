package chessTest;

import chessModel.ChessGame;
import chessModel.Piece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class GridTest {
    private ChessGame chessBoard;
    private Piece whitePiece1;
    private Piece whitePiece2;
    private Piece blackPiece1;

    /*
    this chessBoard looks like this after setUP():
    6 --  --  --  BP  --
    5 --  --  --  --  --
    4 --  WB  --  --  --
    3 --  --  WP  --  --
       0   1   2   3   4
     */
    @Before
    public void setUp() throws Exception {
        chessBoard = new ChessGame(8, 8, null);
        whitePiece1 = new Piece(2, 3, 1);
        whitePiece2 = new Piece(1, 4, 1);
        blackPiece1 = new Piece(3, 6, -1);
        chessBoard.setGrid(2,3,whitePiece1);
        chessBoard.setGrid(1,4,whitePiece2);
        chessBoard.setGrid(3,6,blackPiece1);
    }

    @After
    public void tearDown() throws Exception {
        setUp();
    }

    @Test
    public void setGrid() throws Exception {
        boolean result;
        Piece incomingPiece = whitePiece1;
        Piece previousPiece;

        // try to replace whitePiece2
        previousPiece = chessBoard.setGrid(1,4,incomingPiece);
       assertEquals(previousPiece, whitePiece2);
        assertEquals(chessBoard.getPiece(1, 4), incomingPiece);

        // try to replace blackPiece1
        previousPiece = chessBoard.setGrid(3,6,incomingPiece);
        assertEquals(blackPiece1, previousPiece);
        assertEquals(whitePiece1.getXLocation(), 3);
        assertEquals(whitePiece1.getYLocation(), 6);

        // try to replace an empty space
        previousPiece = chessBoard.setGrid(0,0,incomingPiece);
        assertEquals(previousPiece, null);
        assertEquals(whitePiece1.getXLocation(), 0);
        assertEquals(whitePiece1.getYLocation(), 0);
    }

    @Test
    public void setGridAsAttempt() throws Exception {
        boolean result;
        Piece incomingPiece = whitePiece1;

        // try to replace blackPiece1
        chessBoard.setGrid(3,6,incomingPiece);
        assertEquals(blackPiece1.getCaptured(), false);
        assertEquals(whitePiece1.getXLocation(), 3);
        assertEquals(whitePiece1.getYLocation(), 6);
    }

    @Test
    public void getGrid() throws Exception {
        // get from an occupied spot
        assertEquals(chessBoard.getGrid(2,3), whitePiece1);

        // get from an unoccupied spot
        assertEquals(chessBoard.getGrid(0,0), null);
    }

}