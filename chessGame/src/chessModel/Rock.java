package chessModel;

/**
 * A chessModel.Rock class \n \n
 * Created by guanheng on 9/7/2016.
 */

// rock is implemented as subclass of bishop because rock has similar logic as bishop's
// regardless the special move, only the directions are different
public class Rock extends SlidingPiece {
    public Rock(int x, int y, int player) {
        super(x, y, player);
        this.type = 4;
    }

    // similar to bishop's logic
    @Override
    /**
     * This function will add all the possible moves under rock's move logic
     * without considering check condition \n \n
     * A chessModel.Rock can move any grids in cross directions
     * to any grids that are not occupied by pieces from the same legion, if there are not any pieces in between.
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {
        this.possibleMoves.clear();
        if(this.captured)
            return;
        // temporary variable to control directions of rock pieces
        int[] xDirection = { 1, -1,  0,  0};
        int[] yDirection = { 0,  0,  1, -1};

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
        if(this.x == x && this.y == y)
            return false;
        return isValidCross(x, y, chessBoard);
    }
}
