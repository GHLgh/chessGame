package chessModel;

/**
 * A chessModel.Knight class \n \n
 * Created by guanheng on 9/7/2016.
 */
public class Knight extends Piece {
    public Knight(int x, int y, int player) {
        super(x, y, player);
        this.type = 3;
    }

    @Override
    /**
     * This function will add all the possible moves under knight's move logic
     * without considering check condition \n \n
     * A chessModel.Knight can move two grids vertically and one grid horizontally, or two grids horizontally and one grid vertically
     * to any grids that are not occupied by pieces from the same legion
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {
        this.possibleMoves.clear();
        if(this.captured)
            return;

        int toX, toY;

        int[] xShift = {-2, -2,  2,  2, -1, -1,  1,  1};
        int[] yShift = {-1,  1, -1,  1, -2,  2, -2,  2};
        for(int iterator = 0; iterator < xShift.length; iterator++){
            toX = this.x + xShift[iterator];
            toY = this.y + yShift[iterator];
            checkMovesHelper(toX, toY, chessBoard);
        }

    }

    /**
     * This is a helper function to check if a movement under knight's logic can be made
     * @param toX,toY the destination of the movement
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    private void checkMovesHelper(int toX, int toY, ChessGame chessBoard){
        // check if out of boundary
        if(toX>=0 && toX < chessBoard.getX() && toY>=0 && toY < chessBoard.getY()){
            Piece destination = chessBoard.getPiece(toX,toY);
            if( destination == null || destination.getPlayer() != this.player) {
                this.possibleMoves.add(new Move(this.x, this.y, toX, toY, this.hasMoved, destination));
            }
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
        if(this.captured)
            return false;
        if(((Math.abs(this.x - x) == 2 && Math.abs(this.y - y) == 1)||(Math.abs(this.x - x) == 1 && Math.abs(this.y - y) == 2))
                && !(x < 0 || x >= chessBoard.getX() || y < 0 || y >= chessBoard.getY())){
            return true;
        }
        return false;
    }
}
