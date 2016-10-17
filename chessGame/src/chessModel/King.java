package chessModel;

/**
 * A chessModel.King class \n \n
 * Created by guanheng on 9/7/2016.
 */
public class King extends Piece {

    public King(int x, int y, int player){
        super(x, y, player);
        this.type = 0;
    }

    @Override
    /**
     * This function will add all the possible moves under king's move logic
     * without considering check condition \n \n
     * A chessModel.King can move one grid in any direction
     * to any grids that are not occupied by pieces from the same legion
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {
        this.possibleMoves.clear();
        if(this.captured)
            return;
        for(int xShift = -1; xShift < 2; xShift++){
            for(int yShift = -1; yShift < 2; yShift++){
                // condition that is neither out of bond nor does not move
                if((x+xShift)>=0 && (x+xShift)<chessBoard.getX() && (y+yShift)>=0
                        && (y+yShift)<chessBoard.getY()){
                    Piece destination = chessBoard.getPiece(x+xShift,y+yShift);
                    if( destination == null || destination.getPlayer() != this.player) {
                        this.possibleMoves.add(new Move(this.x, this.y, this.x + xShift, this.y + yShift,
                                this.hasMoved, destination));
                    }
                }
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
        if(!checkGeneralRules(x, y, chessBoard))
            return false;
        // check if destination is occupied by the pieces from the same legion (including pointing to itself)
        if(Math.abs(x - this.x) > 1 || Math.abs(y - this.y) > 1 || chessBoard.getPiece(x, y).getPlayer() == this.player)
            return false;
        else
            return true;
    }
}
