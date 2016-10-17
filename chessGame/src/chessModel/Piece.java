package chessModel;

/**
 * A chessModel.Piece is a generalized form of chess pieces
 * which includes information about its location corresponding to chessboard, its legion and if it is captured. \n \n
 */

import java.util.ArrayList;

/**
 * note: Functions, checkPossibleMoves() and isValid(), are meant to be overwritten
 *       because a generalized piece does not have a specific logic to check movement logic
 *       or to verify if certain location is accessible by the piece. \n \n
 *       Created by Guanheng on 9/5/2016.
 */
public class Piece {
    // use 1 / -1 to separate different players ( white / black ) so that it is easier to implement "movement".
    protected int player;
    protected int type = -1;
    protected boolean captured;
    protected int x;
    protected int y;
    protected boolean hasMoved;
    protected boolean isCustomPiece; //TODO: check if necessary
    protected ArrayList<Move> possibleMoves;

    /**
     * constructor for pieces
     * @param x,y the location of the piece
     * @param player the player that the piece belongs to
     */
    public Piece(int x, int y, int player){
        this.player = player;
        this.captured = false;
        this.hasMoved = false;
        this.x = x;
        this.y = y;
        this.possibleMoves = new ArrayList<Move>();
    }

    /**
     * @return the value which represents the player (white/black legion) of the piece
     */
    public int getPlayer(){
        return player;
    }

    /**
     * @return true if the piece is captured, false otherwise
     */
    public boolean getCaptured(){
        return captured;
    }

    /**
     * set the piece as captured
     */
    public void setCaptured(){
        this.captured = true;
    }

    public void undoCaptured(){
        this.captured = false;
    }
    /**
     * @return true if the piece has moved, false otherwise
     */
    public boolean getHasMoved(){
        return hasMoved;
    }

    /**
     * set the piece as "has moved"
     */
    public void setHasMoved(){
        this.hasMoved = true;
    }

    public void undoHasMoved(){
        this.hasMoved = false;
    }

    /**
     * @return the x location of the piece
     */
    public int getXLocation(){
        return this.x;
    }

    /**
     * @return the y location of the piece
     */
    public int getYLocation(){
        return this.y;
    }

    /**
     * @return the type of the piece
     */
    public int getType(){
        return this.type;
    }

    /**
     * set the x and y of the piece \n \n
     * note:setLocation() will only be called in chessModel.Grid.setPiece()
     *      and the chessModel.Grid's x and y are passed as parameter
     *      therefore, x and y should be in bond
     * @param x,y the location that the piece is being set to
     */
    public void setLocation(int x, int y){
        this.x = x;
        this.y = y;
    }

    public ArrayList<Move> getPossibleMoves(){
        return this.possibleMoves;
    }

    /**
     * This function will add every possible moves to "move" arrayList in chessModel.ChessGame object
     * regarding piece logic and piece blocking.\n \n
     * note: this function will add moves satisfied piece logic and blocking, which may be illegal under "check".
     *       Therefore, further elimination ("attemptMove" method in chessModel.ChessGame class) is needed
     * @param chessBoard the chess board where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {

    }

    /**
     * This function will be called to check if a piece can reach the specific location.
     * And the location is designed to always be the location of opponent's king.
     * Therefore, passing non-king location will lead to false result, especially for sliding pieces
     * @param x,y the location that the piece is asked to reach
     * @param chessBoard the chessModel.ChessGame object that the piece is on
     * @return true if the location can be reached, false otherwise
     */
    public boolean isValid(int x, int y, ChessGame chessBoard){
        /**
         * Check if the move is valid (used in refining player's (ex: A's) possible moves).
         * In this case, x, y will be set as the location of A's king
         * and opponent's (ex: B's) pieces will be the objects to call this function.
         * note: passing chessModel.ChessGame object for compromising slicing pieces' logic
         *       because it is necessary to check if there are pieces blocking the slicing pieces
         */
        return false;
    }

    /**
     * This helper function will check if the destination is beyond reach for any general pieces.
     * Check if destination is out of board or if the piece is captured
     * @param toX,toY the coordinate of the destination
     * @param chessBoard the chessGame object where the piece is
     * @return true if the general rules are satisfied, false otherwise
     */
    protected boolean checkGeneralRules(int toX, int toY, ChessGame chessBoard){
        if(this.captured)
            return false;
        if(toX < 0 || toX >= chessBoard.getX() || toY < 0 || toY >= chessBoard.getY())
            return false;
        return true;
    }
}
