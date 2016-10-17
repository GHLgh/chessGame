package chessTest;

import chessModel.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/9/2016.
 */
public class ChessGameTest {
    private ChessGame chessBoard;
    @Before
    public void setUp() throws Exception {
        chessBoard = new ChessGame(8, 8, null);
    }

    @After
    public void tearDown() throws Exception {
        setUp();
    }

    // This test will print out the standard board
    @Test
    public void initializeDefault() throws Exception {
        chessBoard = new ChessGame(true);
        printBoardHelper(chessBoard);
    }

    @Test
    public void initializeCustomBoard() throws Exception {
        chessBoard = new ChessGame(false);
        printBoardHelper(chessBoard);
    }

    @Test
    public void resetBoard() throws Exception{
        chessBoard = new ChessGame(true);
        chessBoard.checkEndCondition();
        chessBoard.makeAMove(0, 1, 0, 3);
        chessBoard.resetBoard();
        printBoardHelper(chessBoard);
    }

    private void printBoardHelper(ChessGame chessBoard){
        for(int yIterator = chessBoard.getY()-1; yIterator > -1; yIterator--) {
            for (int xIterator = 0; xIterator < chessBoard.getX(); xIterator++){
                if(chessBoard.getPiece(xIterator, yIterator) == null)
                    System.out.print("--");
                else{
                    if(chessBoard.getPiece(xIterator, yIterator).getPlayer() == -1)
                        System.out.print("B");
                    else
                        System.out.print("W");
                    if(chessBoard.getPiece(xIterator, yIterator) instanceof Rock)
                        System.out.print("R");
                    if(chessBoard.getPiece(xIterator, yIterator) instanceof Knight)
                        System.out.print("N");
                    if(chessBoard.getPiece(xIterator, yIterator) instanceof Bishop)
                        System.out.print("B");
                    if(chessBoard.getPiece(xIterator, yIterator) instanceof King)
                        System.out.print("K");
                    if(chessBoard.getPiece(xIterator, yIterator) instanceof Queen)
                        System.out.print("Q");
                    if(chessBoard.getPiece(xIterator, yIterator) instanceof Pawn) {
                        if(chessBoard.getPiece(xIterator, yIterator) instanceof Apprentice)
                            System.out.print("A");
                        else
                            System.out.print("P");
                    }
                    if(chessBoard.getPiece(xIterator, yIterator) instanceof Wizard){
                        System.out.print("W");
                    }
                }
                System.out.print(" ");
            }
            System.out.printf(" : %d\n", yIterator);
        }
        System.out.print("\n");
    }

    // This test checks if an empty board is proper initialized when custom board is not given
    @Test
    public void initializeTest() throws Exception {
        for(int yIterator = chessBoard.getY()-1; yIterator > -1; yIterator--) {
            for (int xIterator = 0; xIterator < chessBoard.getX(); xIterator++){
                assertEquals(chessBoard.getPiece(xIterator, yIterator), null);
            }
        }
    }

    // This test checks if an empty board is proper initialized
    // when size of custom board does not match
    @Test
    public void initializeTestWithInvalidBoard() throws Exception {
        int[][][] customBoard =
        {
                {{1, 1}, {1, 0}},
                {{-1, 2}, {-1, 0}}
        };

        chessBoard = new ChessGame(8, 8, customBoard);
        for(int yIterator = chessBoard.getY()-1; yIterator > -1; yIterator--) {
            for (int xIterator = 0; xIterator < chessBoard.getX(); xIterator++){
                assertEquals(chessBoard.getPiece(xIterator, yIterator), null);
            }
        }
    }

    // This test checks if an empty board is proper initialized
    // when size of custom board does match
    @Test
    public void initializeTestWithValidBoard() throws Exception {
        int[][][] customBoard =
                {
                        {{1, 1}, {1, 0}},
                        {{-1, 2}, {-1, 0}}
                };

        chessBoard = new ChessGame(2, 2, customBoard);
        printBoardHelper(chessBoard);

        // test with invalid piece
        int[][][] newCustomBoard =
                {
                        {{1, 7}, {1, 0}},
                        {{-1, 2}, {2, 0}}
                };

        chessBoard = new ChessGame(2, 2, newCustomBoard);
        printBoardHelper(chessBoard);

        // test with two many kings of the same legion
        // should treat only one of them as valid
        int[][][] anotherCustomBoard =
                {
                        {{1, 1}, {1, 0}},
                        {{-1, 0}, {-1, 0}}
                };

        chessBoard = new ChessGame(2, 2, anotherCustomBoard);
        printBoardHelper(chessBoard);
    }

    /* The case where the game should not end
    White's turn
    this test check white king's possible moves under the following condition (sliding to the left):
    6 --  --  --  BK  --
    5 --  --  --  --  --
    4 --  WK  WP  --  --
    3 --  --  --  --  --
       0   1   2   3   4
     */
    @Test
    public void checkEndCondition() throws Exception {
        King whiteKing = new King(1, 4, 1);
        Pawn whitePawn = new Pawn(2, 4, 1);
        King blackKing = new King(3, 6, -1);

        chessBoard.setGrid(1, 4, whiteKing);
        chessBoard.setGrid(2, 4, whitePawn);
        chessBoard.setGrid(3, 6, blackKing);
        chessBoard.add(whiteKing);
        chessBoard.add(whitePawn);
        chessBoard.add(blackKing);

        assertEquals(0, chessBoard.checkEndCondition());
    }

    /* The case where the game end with black being checkmated
    Black's turn
    this test check white king's possible moves under the following condition (sliding to the left):
    3 --  --  --  --  --
    2 --  --  --  --  --
    1 --  WQ  WK  --  --
    0 BK  WR  --  --  --
       0   1   2   3   4
     */
    @Test
    public void checkEndConditionCheckmate() throws Exception {
        King whiteKing = new King(2, 1, 1);
        Queen whiteQueen = new Queen(1, 1, 1);
        Rock whiteRock = new Rock(1, 0, 1);
        King blackKing = new King(0, 0, -1);

        chessBoard.setGrid(2, 1, whiteKing);
        chessBoard.setGrid(1, 1, whiteQueen);
        chessBoard.setGrid(1, 0, whiteRock);
        chessBoard.setGrid(0, 0, blackKing);
        chessBoard.add(whiteKing);
        chessBoard.add(whiteQueen);
        chessBoard.add(whiteRock);
        chessBoard.add(blackKing);

        chessBoard.setCheck(-1);
        chessBoard.setWhoseTurn(-1);
        assertEquals(1, chessBoard.checkEndCondition());
    }

    /* The case where the game end with stalemate
    Black's turn
    this test check white king's possible moves under the following condition (sliding to the left):
    3 --  --  --  --  --
    2 --  --  --  --  --
    1 --  WR  WK  --  --
    0 BK  --  --  --  --
       0   1   2   3   4
     */
    @Test
    public void checkEndConditionStalemate() throws Exception {
        King whiteKing = new King(2, 1, 1);
        Rock whiteRock = new Rock(1, 1, 1);
        King blackKing = new King(0, 0, -1);

        chessBoard.setGrid(2,1,whiteKing);
        chessBoard.setGrid(1,1,whiteRock);
        chessBoard.setGrid(0,0,blackKing);
        chessBoard.add(whiteKing);
        chessBoard.add(whiteRock);
        chessBoard.add(blackKing);

        chessBoard.setWhoseTurn(-1);
        assertEquals(2, chessBoard.checkEndCondition());
    }

    /*
    White's turn
    this test check white king's possible moves under the following condition (sliding to the left):
    3 --  --  --  --  --
    2 --  --  --  --  --
    1 --  --  --  BK  --
    0 WR  WK  --  --  --
       0   1   2   3   4
     */
    @Test
    public void makeAMove() throws Exception {
        King whiteKing = new King(1, 0, 1);
        Rock whiteRock = new Rock(0, 0, 1);
        King blackKing = new King(3, 1, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(3,1,blackKing);
        chessBoard.setGrid(0,0,whiteRock);
        chessBoard.add(whiteKing);
        chessBoard.add(whiteRock);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition()==0){
            assertEquals(2, whiteKing.getPossibleMoves().size());
            assertEquals(7, whiteRock.getPossibleMoves().size());
            // when making an invalid move
            assertEquals(false, chessBoard.makeAMove(1, 0, 2, 1));
            // when making a move that check the other player
            assertEquals(true, chessBoard.makeAMove(0, 0, 0, 1));
            assertEquals(-1, chessBoard.getIsCheck());
        }
    }

    /*
    White's turn
    this test check white king's possible moves under the following condition (sliding to the left):
    3 --  --  --  --  --
    2 --  --  --  --  --
    1 BR  --  --  BK  --
    0 --  WK  --  --  --
       0   1   2   3   4
     */
    @Test
    public void makeACaptureMove() throws Exception {
        King whiteKing = new King(1, 0, 1);
        Rock blackRock = new Rock(0, 1, -1);
        King blackKing = new King(3, 1, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(3,1,blackKing);
        chessBoard.setGrid(0,1,blackRock);
        chessBoard.add(whiteKing);
        chessBoard.add(blackRock);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition()==0){
            assertEquals(true, chessBoard.makeAMove(1, 0, 0, 1));
            assertEquals(true, blackRock.getCaptured());
        }
    }

    /*
    White's turn
    this test check if promotion occurs:
    7 --  --  --  --  --  --
    6 WP  WA  WP  WP  WP  WP
    5 --  --  --  --  --  --
    4 --  --  --  --  --  --
       0   1   2   3   4   5
     */
    @Test
    public void makeAPromotionMove0() throws Exception {
        Apprentice piece = new Apprentice(1, 6, 1);
        Pawn p1 = new Pawn(0, 6, 1);

        King whiteKing = new King(1, 0, 1);
        King blackKing = new King(7, 0, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(7,0,blackKing);
        chessBoard.setGrid(1,6,piece);
        chessBoard.setGrid(0,6,p1);
        chessBoard.add(whiteKing);
        chessBoard.add(piece);
        chessBoard.add(p1);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition() == 0){
            ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
            System.setIn(in);
            chessBoard.makeAMove(0, 6, 0, 7);
            chessBoard.setPromoteTo(1);
            chessBoard.promotePiece();
            assertEquals(false, chessBoard.getPiece(0, 7) instanceof Pawn);
            assertEquals(true, chessBoard.getPiece(0, 7) instanceof Queen);
        }
    }

    @Test
    public void makeAPromotionMove1() throws Exception {
        Apprentice piece = new Apprentice(1, 6, 1);
        Pawn p1 = new Pawn(0, 6, 1);

        King whiteKing = new King(1, 0, 1);
        King blackKing = new King(7, 0, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(7,0,blackKing);
        chessBoard.setGrid(1,6,piece);
        chessBoard.setGrid(0,6,p1);
        chessBoard.add(whiteKing);
        chessBoard.add(piece);
        chessBoard.add(p1);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition() == 0){
            chessBoard.makeAMove(1, 6, 1, 7);
            chessBoard.setPromoteTo(7);
            chessBoard.promotePiece();
            assertEquals(false, chessBoard.getPiece(1, 7 ) instanceof Apprentice);
            assertEquals(true, chessBoard.getPiece(1, 7 ) instanceof Wizard);
        }
    }

    @Test
    public void makeAPromotionMove2() throws Exception {
        Apprentice piece = new Apprentice(1, 6, 1);
        Pawn p1 = new Pawn(0, 6, 1);

        King whiteKing = new King(1, 0, 1);
        King blackKing = new King(7, 0, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(7,0,blackKing);
        chessBoard.setGrid(1,6,piece);
        chessBoard.setGrid(0,6,p1);
        chessBoard.add(whiteKing);
        chessBoard.add(piece);
        chessBoard.add(p1);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition() == 0){
            ByteArrayInputStream in = new ByteArrayInputStream("2".getBytes());
            System.setIn(in);
            chessBoard.makeAMove(0, 6, 0, 7);
            chessBoard.setPromoteTo(2);
            chessBoard.promotePiece();
            assertEquals(false, chessBoard.getPiece(0, 7) instanceof Pawn);
            assertEquals(true, chessBoard.getPiece(0, 7) instanceof Bishop);
        }
    }

    @Test
    public void makeAPromotionMove3() throws Exception {
        Apprentice piece = new Apprentice(1, 6, 1);
        Pawn p1 = new Pawn(0, 6, 1);

        King whiteKing = new King(1, 0, 1);
        King blackKing = new King(7, 0, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(7,0,blackKing);
        chessBoard.setGrid(1,6,piece);
        chessBoard.setGrid(0,6,p1);
        chessBoard.add(whiteKing);
        chessBoard.add(piece);
        chessBoard.add(p1);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition() == 0){
            ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
            System.setIn(in);
            chessBoard.makeAMove(0, 6, 0, 7);
            chessBoard.setPromoteTo(3);
            chessBoard.promotePiece();
            assertEquals(false, chessBoard.getPiece(0, 7) instanceof Pawn);
            assertEquals(true, chessBoard.getPiece(0, 7) instanceof Knight);
        }
    }

    @Test
    public void makeAPromotionMove4() throws Exception {
        Apprentice piece = new Apprentice(1, 6, 1);
        Pawn p1 = new Pawn(0, 6, 1);

        King whiteKing = new King(1, 0, 1);
        King blackKing = new King(7, 0, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(7,0,blackKing);
        chessBoard.setGrid(1,6,piece);
        chessBoard.setGrid(0,6,p1);
        chessBoard.add(whiteKing);
        chessBoard.add(piece);
        chessBoard.add(p1);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition() == 0){
            ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());
            System.setIn(in);
            chessBoard.makeAMove(0, 6, 0, 7);
            chessBoard.setPromoteTo(4);
            chessBoard.promotePiece();
            assertEquals(false, chessBoard.getPiece(0, 7) instanceof Pawn);
            assertEquals(true, chessBoard.getPiece(0, 7) instanceof Rock);
        }
    }

    @Test
    public void makeAPromotionMove5() throws Exception {
        Apprentice piece = new Apprentice(1, 6, 1);
        Pawn p1 = new Pawn(0, 6, 1);

        King whiteKing = new King(1, 0, 1);
        King blackKing = new King(7, 0, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(7,0,blackKing);
        chessBoard.setGrid(1,6,piece);
        chessBoard.setGrid(0,6,p1);
        chessBoard.add(whiteKing);
        chessBoard.add(piece);
        chessBoard.add(p1);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition() == 0){
            ByteArrayInputStream in = new ByteArrayInputStream("8".getBytes());
            chessBoard.makeAMove(0, 6, 0, 7);
            chessBoard.setPromoteTo(7);
            chessBoard.promotePiece();
            assertEquals(true, chessBoard.getPiece(0, 7) instanceof Pawn);
            assertEquals(true, chessBoard.getPiece(0, 7) instanceof Apprentice);
        }
    }

    @Test
    public void getPiece() throws Exception {
        Piece piece = new Piece(1, 1, 1);
        chessBoard.setGrid(1,1,piece);
        assertEquals(chessBoard.getPiece(1, 1), piece);
    }

    @Test
    public void getX() throws Exception {
        assertEquals(8, chessBoard.getX());
    }

    @Test
    public void getY() throws Exception {
        assertEquals(8, chessBoard.getY());
    }

    @Test
    public void getIsCheck() throws Exception {
        assertEquals(0, chessBoard.getIsCheck());
    }

    @Test
    public void incrementScore() throws Exception{
        chessBoard.incrementScore();
        assertEquals(0,chessBoard.getWhiteScore());
        chessBoard.setWhoseTurn(-1);
        chessBoard.incrementScore();
        assertEquals(1, chessBoard.getWhiteScore());
    }

    @Test
    public void undoMoves() throws Exception{
        assertEquals(false, chessBoard.undoAMove());

        King whiteKing = new King(1, 0, 1);
        Rock blackRock = new Rock(0, 1, -1);
        King blackKing = new King(3, 1, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(3,1,blackKing);
        chessBoard.setGrid(0,1,blackRock);
        chessBoard.add(whiteKing);
        chessBoard.add(blackRock);
        chessBoard.add(blackKing);

        if(chessBoard.checkEndCondition()==0){
            assertEquals(true, chessBoard.makeAMove(1, 0, 0, 1));
            assertEquals(true, chessBoard.undoAMove());
            assertEquals(1, whiteKing.getXLocation());
            assertEquals(0, whiteKing.getYLocation());
            assertEquals(false, blackRock.getCaptured());
            assertEquals(false, whiteKing.getHasMoved());
        }
    }

    @Test
    public void getWhoseTurn() throws Exception {
        assertEquals(1, chessBoard.getWhoseTurn());
    }

    @Test
    public void getWhiteScore() throws Exception {
        assertEquals(0, chessBoard.getWhiteScore());
    }

    @Test
    public void getBlackScore() throws Exception {
        assertEquals(0, chessBoard.getBlackScore());
    }

    @Test
    public void getPieces() throws Exception{
        King whiteKing = new King(1, 0, 1);
        Rock blackRock = new Rock(0, 1, -1);
        King blackKing = new King(3, 1, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(3,1,blackKing);
        chessBoard.setGrid(0,1,blackRock);
        chessBoard.add(whiteKing);
        chessBoard.add(blackRock);
        chessBoard.add(blackKing);

        assertEquals(1, chessBoard.getPieces(1).size());
        assertEquals(2, chessBoard.getPieces(-1).size());
    }

    @Test
    public void getName() throws Exception{
        assertEquals("Dr. Jekyll", chessBoard.getName(1));
        assertEquals("Mr. Hyde", chessBoard.getName(-1));
    }

    @Test
    public void getToBePromoted() throws Exception{
        assertEquals(null, chessBoard.getToBePromoted());

        Pawn p1 = new Pawn(0, 6, 1);

        King whiteKing = new King(1, 0, 1);
        King blackKing = new King(7, 0, -1);

        chessBoard.setGrid(1,0,whiteKing);
        chessBoard.setGrid(7,0,blackKing);
        chessBoard.setGrid(0,6,p1);
        chessBoard.add(whiteKing);
        chessBoard.add(p1);
        chessBoard.add(blackKing);

        chessBoard.checkEndCondition();
        chessBoard.makeAMove(0, 6, 0, 7);
        assertEquals(p1, chessBoard.getToBePromoted());
    }

    @Test
    public void setPromoteTo() throws Exception{
        assertEquals(0, chessBoard.getPromoteTo());
        chessBoard.setPromoteTo(2);
        assertEquals(2, chessBoard.getPromoteTo());
    }
}