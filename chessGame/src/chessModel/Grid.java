package chessModel;

/**
 * A chessModel.Grid class to store information for grids in chess board \n \n
 * Created by guanheng on 9/5/2016.
 */
public class Grid {
    private Piece currentPiece;
    private int x;
    private int y;

    public Grid(int x, int y){
        currentPiece = null;
        this.x = x;
        this.y = y;

    }

    /**
     * This function will replace the piece on grid by incoming piece
     * @param incomingPiece the piece which is going to replace the piece on grid
     * @return previous piece on the grid, null if it does not exist.
     */
    public Piece setGrid(Piece incomingPiece){
        Piece currentPiece = this.currentPiece;
        this.currentPiece = incomingPiece;
        if(this.currentPiece != null)
            this.currentPiece.setLocation(this.x, this.y);
        return currentPiece;
    }

    /**
     * @return the piece on the grid, null if the grid is empty
     */
    public Piece getGrid(){
        return currentPiece;
    }
}
