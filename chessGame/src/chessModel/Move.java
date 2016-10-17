package chessModel;

/**
 * This is a class for storing movement information.\n
 * It serves for two purpose: acting as an element in moment history and as an element of possible moves. \n
 * It stores the piece that is captured by the movement to lookup and restore the piece on undo \n
 * And it stores if it is the piece's first move such that special move can be performed after undo the move.\n \n
 * Created by guanheng on 9/24/2016.
 */
public class Move{
    public int startX;
    public int startY;
    public int endX;
    public int endY;
    public boolean hasMoved;
    public Piece pieceCaptured;
    public int isCheck;
    public int specialMove; // 0: normal move; 1: pawn first move; 2: promotion; 3: castling (TODO)

    public Move(int startX, int startY, int endX, int endY, boolean hasMoved, Piece pieceCaptured){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.hasMoved = hasMoved;
        this.pieceCaptured = pieceCaptured;
        this.isCheck = 0;
        this.specialMove = 0;
    }
}
