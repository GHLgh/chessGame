package chessModel;

/**
 * A custom chessModel.Wizard class \n
 * A chessModel.Wizard piece can only appear in the game through the promotion of an chessModel.Apprentice piece. \n
 * A chessModel.Wizard piece's move logic will switch between chessModel.Rock's and chessModel.Bishop every time it moves \n \n
 * Created by guanheng on 9/15/2016.
 */
public class Wizard extends SlidingPiece {
    private int isTransformed = 0;
    public Wizard(int x, int y, int player) {
        super(x, y, player);
        this.type = 7;
    }

    @Override
    /**
     * This function will add all the possible moves under wizard's move logic
     * without considering check condition \n \n
     * A chessModel.Wizard can move based on either chessModel.Bishop's logic or chessModel.Rock's
     * to any grids that are not occupied by pieces from the same legion, if there are not any pieces in between \n
     * depending on the "isTransformed" variable. \n \n
     * note: The variable will be changed when player has made a move with the wizard piece (after MakeAMove() is called)
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard){
        // check if transformation is needed
        if(this.hasMoved){
            this.hasMoved = false;
            this.isTransformed = (this.isTransformed+1)%2;
        }
        this.possibleMoves.clear();
        if(this.captured)
            return;

        // temporary variable to control directions of wizard pieces
        int[][] xDirection = {{ 1, -1,  0,  0},{ 1,  1, -1, -1}};
        int[][] yDirection = {{ 0,  0,  1, -1}, { 1, -1,  1, -1}};

        // move toward top right conner
        for(int iterator = 0; iterator < xDirection[0].length; iterator++) {
            checkMovesHelper(this.x + xDirection[this.isTransformed][iterator],
                    this.y + yDirection[this.isTransformed][iterator],
                    xDirection[this.isTransformed][iterator],
                    yDirection[this.isTransformed][iterator], chessBoard);
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

        if(this.isTransformed == 1)
            return isValidDiagonal(x, y, chessBoard);
        else
            return isValidCross(x, y, chessBoard);
    }
}
