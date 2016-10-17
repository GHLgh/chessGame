package chessModel;

/**
 * A subclass of chessModel.Piece class where recursive calls are used to enumerate every possible move on one direction \n \n
 * Created by guanheng on 9/8/2016.
 */
public class SlidingPiece extends Piece {
    public SlidingPiece(int x, int y, int player) {
        super(x, y, player);
    }

    /**
     * This is a helper function for particular sliding piece. \n
     * Given a direction, it will inductively check possible moves towards that direction
     * until the move is blocked by the boundary of the board or other pieces
     * @param toX,toY the location that is going to be checked
     * @param xDirection,yDirection the direction that the check will move towards if (toX, toY) is a possible move
     * @param chessBoard the chessModel.ChessGame object where the piece is
     */
    protected void checkMovesHelper(int toX, int toY, int xDirection, int yDirection, ChessGame chessBoard){
        // check if out of boundary
        if(toX>=0 && toX < chessBoard.getX() && toY>=0 && toY < chessBoard.getY()){
            Piece destination = chessBoard.getPiece(toX,toY);
            if( destination == null || destination.getPlayer() != this.player) {
                this.possibleMoves.add(new Move(this.x, this.y, toX, toY, this.hasMoved, destination));
                // recursive call checkMovesHelper if there are not pieces blocking the sliding piece
                if(destination == null){
                    checkMovesHelper(toX + xDirection,toY + yDirection, xDirection, yDirection, chessBoard);
                }
            }
        }
    }

    /**
     * This helper function will check if the location given can be reached under diagonal movement rule \n \n
     * note: The function will not check if destination is out of bound or if the piece is captured.
     *       Such checks should be performed before calling this helper function
     * @param x,y the location to be reached
     * @param chessBoard the chessModel.ChessGame object where the piece is
     * @return true if location can be reached, false otherwise
     */
    protected boolean isValidDiagonal(int x, int y, ChessGame chessBoard){
        //base case
        if(this.x == x && this.y == y)
            // return true to indicate that recursion reach the end
            return true;

        // if the difference of x / y between bishop and destination is not equal,
        // then the sliding piece can not reach under diagonal movement rule
        if(Math.abs(this.x - x) != Math.abs(this.y - y))
            return false;

        /*
         * setting parameters for recursion
         * ex. if (this.x - x) < 0, which means the x destination is higher than the sliding piece
         *      therefore, when passing parameters, the next x destination will be (x + (-1)) which is one grid
         *      closer to the sliding piece
         * note: (this.x - x) == 0 has been tested in base case
         */
        int nextX = x + (this.x - x)/Math.abs(this.x - x);
        int nextY = y + (this.y - y)/Math.abs(this.y - y);
        if(isValidDiagonal(nextX, nextY, chessBoard)) {
            Piece destination = chessBoard.getPiece(x, y);
            if (destination == null || destination instanceof King)
                return true;
        }
        return false;
    }

    /**
     * This helper function will check if the location given can be reached under cross movement rule \n \n
     * note: the function will not check if destination is out of bound or if the piece is captured.
     *       such checks should be performed before calling this helper function
     * @param x,y the location to be reached
     * @param chessBoard the chessModel.ChessGame object where the piece is
     * @return true if location can be reached, false otherwise
     */
    protected boolean isValidCross(int x, int y, ChessGame chessBoard){
        //base case
        if(this.x == x && this.y == y)
            return true;

        // if both the difference of x / y between the sliding piece and destination are not equal to zero,
        // then the piece can not reach under cross movement rule
        if(Math.abs(this.x - x) != 0 && Math.abs(this.y - y) != 0)
            return false;

        /*
         * setting parameters for recursion
         * ex. if (this.x - x) < 0, which means the x destination is higher than the sliding piece
         *      therefore, when passing parameters, the next x destination will be (x + (-1)) which is one grid
         *      closer to the sliding piece
         * note: (this.x - x) == 0 has been tested in base case
         */
        int nextX, nextY;
        if((this.x -x) != 0)
            nextX = x + (this.x - x)/Math.abs(this.x - x);
        else
            nextX = this.x;
        if((this.y - y) != 0)
            nextY = y + (this.y - y)/Math.abs(this.y - y);
        else
            nextY = this.y;

        if(isValidCross(nextX, nextY, chessBoard)) {
            Piece destination = chessBoard.getPiece(x, y);
            if (destination == null || destination instanceof King)
                return true;
        }

        return false;
    }

    //================ Helper function for Test =========================================
    public void checkMoves(int x, int y, int dx, int dy, ChessGame chessBoard){
        this.checkMovesHelper(x, y, dx, dy, chessBoard);
    }

    public boolean isValid(int x, int y, ChessGame chessBoard, boolean isDiagonal){
        if(isDiagonal)
            return isValidDiagonal(x, y, chessBoard);
        else
            return isValidCross(x, y, chessBoard);
    }
}
