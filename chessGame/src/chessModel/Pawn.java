package chessModel;

/**
 * A chessModel.Pawn class \n \n
 * Created by guanheng on 9/7/2016.
 */
public class Pawn extends Piece {
    // a boolean to determine if pawn can move two squares forward.

    public Pawn(int x, int y, int player) {
        super(x, y, player);
        this.type = 5;
    }

    @Override
    /**
     * This function will add all the possible moves under pawn's move logic
     * without considering check condition \n \n
     * A chessModel.Pawn can move one grid forward or two grids forward if it has not moved before
     * to any grids that are not occupied by any pieces. \n \n
     * It can only move to a grid which is diagonally in front of it when the grid is occupied by an opponent piece.
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {
        this.possibleMoves.clear();
        if(this.captured)
            return;

        //any kind of movements of pawn needs to move forward
        int toY = this.y + this.player;
        if(toY >= chessBoard.getY() || toY < 0){
            return;
        }

        int toX = this.x - 1;
        // if diagonal direction is inside boundary
        checkDiagonalHelper(toX, toY, chessBoard);
        toX += 2;
        checkDiagonalHelper(toX, toY, chessBoard);

        // does not check if it is out of bound here because toX, toY must be in bond when control reaches here
        toX = this.x;
        Piece destination = chessBoard.getPiece(toX,toY);
        if(destination == null) {
            this.possibleMoves.add(new Move(this.x, this.y, toX, toY, this.hasMoved, null));
            // when the pawn has not moved before, check if it can move two block
            if(!(this.hasMoved)){
                toY += this.player;
                if(toY >= 0 && toY < chessBoard.getY()){
                    destination = chessBoard.getPiece(toX,toY);
                    if( destination == null) {
                        Move possibleMove = new Move(this.x, this.y, toX, toY, this.hasMoved, null);
                        possibleMove.specialMove = 1;
                        this.possibleMoves.add(possibleMove);
                    }
                }
            }
        }
    }

    /**
     * This is a helper function to check if the pawn can move to diagonal direction
     * @param toX,toY the coordinate of destination
     * @param chessBoard the chessModel.ChessGame object where the pawn is
     */
    private void checkDiagonalHelper(int toX, int toY, ChessGame chessBoard){
        // check if out of boundary
        if(toX>=0 && toX < chessBoard.getX()){
            Piece destination = chessBoard.getPiece(toX,toY);
            if( destination != null && destination.getPlayer() != this.player) {
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
        if(!checkGeneralRules(x, y, chessBoard))
            return false;
        // when parameter "x" is on diagonal direction
        if (this.x + 1 == x || this.x - 1 == x) {
            if ((this.y + this.player) == y
                    && chessBoard.getPiece(x, y) != null && chessBoard.getPiece(x, y).getPlayer() != this.player) {
                return true;
            }
        }
        else if(this.x == x){
            if(((this.y + this.player) == y && chessBoard.getPiece(x, y) == null)
                    || ((this.y + 2 * this.player) == y && chessBoard.getPiece(x, y) == null && !this.hasMoved))
                return true;
        }
        return false;
    }
}