package chessModel;

/**
 * A chessModel.Queen Class \n \n
 * Created by guanheng on 9/7/2016.
 */
public class Queen extends SlidingPiece {
    public Queen(int x, int y, int player) {
        super(x, y, player);
        this.type = 1;
    }

    @Override
    /**
     * This function will add all the possible moves under queen's move logic
     * without considering check condition \n \n
     * A chessModel.Queen can move any grids in cross directions or in diagonal directions
     * to any grids that are not occupied by pieces from the same legion, if there are not any pieces in between.
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {
        this.possibleMoves.clear();
        if(this.captured)
            return;
        // temporary variable to control directions of bishop pieces
        // basically the directions are bishop's directions plus rock's directions
        int[] xDirection = { 1,  1, -1, -1,  1, -1,  0,  0};
        int[] yDirection = { 1, -1,  1, -1,  0,  0,  1, -1};

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
        if(isValidCross(x, y, chessBoard) || isValidDiagonal(x, y, chessBoard))
            return true;
        else
            return false;
    }
}
