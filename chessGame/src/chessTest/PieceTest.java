package chessTest;

import chessModel.Piece;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by guanheng on 9/8/2016.
 */
public class PieceTest {
    private static Piece piece;
    private static int x = 5;
    private static int y = 3;
    private static int player = 1;

    @org.junit.Before
    public void setUp() throws Exception {
        piece = new Piece(x, y, player);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        piece = new Piece(x, y, player);
    }

    // To test if a piece object is set up properly
    @org.junit.Test
    public void initialize() throws Exception {
        int pieceX = piece.getXLocation();
        assertEquals(x, pieceX);

        int pieceY = piece.getYLocation();
        assertEquals(y, pieceY);

        int piecePlayer = piece.getPlayer();
        assertEquals(player, piecePlayer);

        boolean pieceCaptured = piece.getCaptured();
        assertEquals(false, pieceCaptured);
    }

    @org.junit.Test
    public void getPlayer() throws Exception {
        int piecePlayer = piece.getPlayer();
        assertEquals(player, piecePlayer);
    }

    @org.junit.Test
    public void getCaptured() throws Exception {
        boolean pieceCaptured = piece.getCaptured();
        assertEquals(false, pieceCaptured);
    }

    @org.junit.Test
    public void setCaptured() throws Exception {
        piece.setCaptured();
        boolean pieceCaptured = piece.getCaptured();
        assertEquals(true, pieceCaptured);
    }

    @org.junit.Test
    public void getHasMoved() throws Exception {
        boolean pieceHasMoved = piece.getHasMoved();
        assertEquals(false, pieceHasMoved);
    }

    @org.junit.Test
    public void setHasMoved() throws Exception {
        piece.setHasMoved();
        boolean pieceHasMoved = piece.getHasMoved();
        assertEquals(true, pieceHasMoved);
    }

    @org.junit.Test
    public void getXLocation() throws Exception {
        int pieceX = piece.getXLocation();
        assertEquals(x, pieceX);
    }

    @org.junit.Test
    public void getYLocation() throws Exception {
        int pieceY = piece.getYLocation();
        assertEquals(y, pieceY);
    }
    @org.junit.Test
    public void getType() throws Exception {
        int type = piece.getType();
        assertEquals(-1, type);
    }

    @org.junit.Test
    public void setLocation() throws Exception {
        int targetX = 6;
        int targetY = 7;
        piece.setLocation(targetX, targetY);
        int pieceX = piece.getXLocation();
        int pieceY = piece.getYLocation();
        assertEquals(targetX, pieceX);
        assertEquals(targetY, pieceY);
    }

    @org.junit.Test
    public void checkUndo() throws Exception{
        piece.setCaptured();
        piece.setHasMoved();
        piece.undoCaptured();
        piece.undoHasMoved();
        assertEquals(false, piece.getHasMoved());
        assertEquals(false, piece.getHasMoved());
    }

}