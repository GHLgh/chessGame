package chessTest;

import chessModel.ChessGame;
import chessModel.King;
import chessModel.Piece;
import chessModel.SlidingPiece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class SlidingPieceTest {
    private ChessGame chessBoard;
    private SlidingPiece piece;
    private King opponentKing;
    @Before
    public void setUp() throws Exception {
        piece = new SlidingPiece(4, 0, 1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(4,0,piece);
    }
    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /* because this is only a helper function, it can be tested by setting a direction for the piece to sliding
    this test check white king's possible moves under the following condition (sliding to the left):
    2 --  --  --  --  --
    1 --  --  --  --  --
    0 --  --  --  --  WS
       0   1   2   3   4
     */
    @Test
    public void checkMovesHelper() throws Exception {
        piece.checkMoves(3, 0, -1, 0, chessBoard);

        assertEquals(4, piece.getPossibleMoves().size());
        for(int iterator = 0; iterator < 4; iterator++)
            System.out.printf("%d %d %d %d\n",piece.getPossibleMoves().get(iterator).startX,
                    piece.getPossibleMoves().get(iterator).startY, piece.getPossibleMoves().get(iterator).endX,
                    piece.getPossibleMoves().get(iterator).endY);
    }

    /*
    this test check white king's possible moves under the following condition (sliding to the left):
    2 --  --  --  --  --
    1 --  --  --  --  --
    0 --  BP  --  --  WS
       0   1   2   3   4
     */
    @Test
    public void checkMovesHelperWhenBlocked() throws Exception {
        // blocked by enemy
        chessBoard.setGrid(1,0,new Piece(0,0,-1));
        piece.checkMoves(3, 0, -1, 0, chessBoard);

        assertEquals(3, piece.getPossibleMoves().size());
        for(int iterator = 0; iterator < 3; iterator++)
            System.out.printf("%d %d %d %d\n",piece.getPossibleMoves().get(iterator).startX,
                    piece.getPossibleMoves().get(iterator).startY, piece.getPossibleMoves().get(iterator).endX,
                    piece.getPossibleMoves().get(iterator).endY);
    }

    /*
    this test check white king's possible moves under the following condition (sliding to the left):
    2 --  --  --  --  --
    1 --  --  --  --  --
    0 --  WP  --  --  WS
       0   1   2   3   4
     */
    @Test
    public void checkMovesHelperWhenBlocked2() throws Exception {
        // blocked by friendly piece
        chessBoard.setGrid(1,0,new Piece(0,0,1));
        piece.checkMoves(3, 0, -1, 0, chessBoard);

        assertEquals(2, piece.getPossibleMoves().size());
        for(int iterator = 0; iterator < 2; iterator++)
            System.out.printf("%d %d %d %d\n",piece.getPossibleMoves().get(iterator).startX,
                    piece.getPossibleMoves().get(iterator).startY, piece.getPossibleMoves().get(iterator).endX,
                    piece.getPossibleMoves().get(iterator).endY);
    }

    // note: for the following two test, because the function is structured to check if the piece can reach
    //       the other king. For this reason, the functions will return false if there are any pieces between
    //       the piece and destination regardless if the blocking pieces can be captured

    @Test
    public void isValidDiagonal() throws Exception {
        // when give location is not on the diagonal direction
        assertEquals(piece.isValid(5, 0, chessBoard, true), false);

        // when given location is itself
        // note: this will return true in this function because it is set to be the base case
        //       when this.x == x && this.y == y.
        //       And this will be treated as false in particular sliding piece
        //       because this function will be wrapped with a outer function
        assertEquals(piece.isValid(4, 0, chessBoard, true), true);

        // when given location can be reach
        assertEquals(piece.isValid(6, 2, chessBoard, true), true);

        // when given location is blocked
        chessBoard.setGrid(5,1, new Piece(5,1,-1));
        assertEquals(piece.isValid(6, 2, chessBoard, true), false);
    }

    @Test
    public void isValidCross() throws Exception {
        // when give location is not on the cross direction
        assertEquals(piece.isValid(5, 1, chessBoard,false), false);

        // when given location is itself
        // note: this will return true in this function because it is set to be the base case
        //       when this.x == x && this.y == y.
        //       And this will be treated as false in particular sliding piece
        //       because this function will be wrapped with a outer function
        assertEquals(piece.isValid(4, 0, chessBoard,false), true);

        // when given location can be reach
        assertEquals(piece.isValid(6, 0, chessBoard,false), true);

        // when given location is blocked
        chessBoard.setGrid(5,0,new Piece(5,0,-1));
        assertEquals(piece.isValid(6, 0, chessBoard, false), false);
    }

}