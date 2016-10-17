package chessTest;

import chessModel.ChessGame;
import chessModel.King;
import chessModel.Piece;
import chessModel.Rock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class RockTest {
    private ChessGame chessBoard;
    private Rock rock;
    private King opponentKing;
    @Before
    public void setUp() throws Exception {
        rock = new Rock(1, 4, 1);
        chessBoard = new ChessGame(8, 8, null);
        chessBoard.setGrid(1,4,rock);
    }
    @After
    public void tearDown() throws Exception {
        setUp();
    }

    /*
    this test check white king's possible moves under the following condition (sliding to the left):
    6 --  WP  --  --  --
    5 --  --  --  --  --
    4 --  WR  --  BP  --
    3 --  --  --  --  --
       0   1   2   3   4
     */
    @Test
    public void checkPossibleMoves() throws Exception {
        chessBoard.setGrid(1,6,new Piece(1, 6, 1));
        chessBoard.setGrid(3,4,new Piece(3, 4, -1));

        rock.checkPossibleMoves(chessBoard);

        assertEquals(8, rock.getPossibleMoves().size());
        for(int iterator = 0; iterator < 8; iterator++)
            System.out.printf("%d %d %d %d\n",rock.getPossibleMoves().get(iterator).startX,
                    rock.getPossibleMoves().get(iterator).startY, rock.getPossibleMoves().get(iterator).endX,
                    rock.getPossibleMoves().get(iterator).endY);
    }

    @Test
    public void checkPossibleMovesWhenCaptured() throws Exception {
        rock.setCaptured();
        rock.checkPossibleMoves(chessBoard);
        assertEquals(0, rock.getPossibleMoves().size());
    }

    @Test
    public void isValid() throws Exception {
        // when given location is out of bond
        assertEquals(rock.isValid(-1, -1, chessBoard), false);

        // when give location is not on the diagonal direction
        assertEquals(rock.isValid(4, 5, chessBoard), false);

        // when given location is itself
        assertEquals(rock.isValid(1, 4, chessBoard), false);

        // when given location can be reach
        assertEquals(rock.isValid(4, 4, chessBoard), true);

        // when given location is blocked
        chessBoard.setGrid(3,4,new Piece(3,4,-1));
        assertEquals(rock.isValid(4, 4, chessBoard), false);

        // when the piece is captured
        rock.setCaptured();
        assertEquals(rock.isValid(4, 4, chessBoard), false);
    }

}