package chessModel;

/**
 * A chessModel.Bishop class \n \n
 * Created by guanheng on 9/7/2016.
 */

public class Bishop extends SlidingPiece {
    public Bishop(int x, int y, int player) {
        super(x, y, player);
        this.type = 2;
    }

    @Override
    /**
     * This function will add all the possible moves under bishop's move logic
     * without considering check condition \n \n
     * A chessModel.Bishop can move any grids in diagonal directions
     * to any grids that are not occupied by pieces from the same legion, if there are not any pieces in between.
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {
        this.possibleMoves.clear();
        if(this.captured)
            return;
        // temporary variable to control directions of bishop pieces
        int[] xDirection = { 1,  1, -1, -1};
        int[] yDirection = { 1, -1,  1, -1};

        // move toward top right conner
        for(int iterator = 0; iterator < xDirection.length; iterator++) {
            checkMovesHelper(this.x + xDirection[iterator], this.y + yDirection[iterator],
                    xDirection[iterator], yDirection[iterator], chessBoard);
        }
    }


    @Override
    /**
     * This function will check if the piece can reach the location given
     * @param x,y the location to be reached
     * @param chessBoard the chessModel.ChessGame object where the piece is
     * @return true if the location can be reached, false otherwise
     */
    public boolean isValid(int x, int y, ChessGame chessBoard) {
        if(!checkGeneralRules(x, y, chessBoard))
            return false;
        // when the destination is the bishop itself
        if(this.x == x && this.y == y)
            return false;
        return isValidDiagonal(x, y, chessBoard);
    }
}
