package chessModel;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class for operating a chess game.\n \n
 * note: For custom board, player of the piece and type of the piece will be represented in integer \n
 * 1:white, -1:black; 0:chessModel.King, 1:chessModel.Queen, 2:chessModel.Bishop, 3:chessModel.Knight, 4:chessModel.Rock, 5:chessModel.Pawn
 */
public class ChessGame {
    /**
     * A class for store possible movements for each turn
     */

    Grid[][] grid;
    private int x;
    private int y;

    // An array to store piece information for a standard board
    // and it will be replaced if user pass in a custom board
    // First integer represents the player of the piece ( 1:white, -1:black)
    // Second integer represents the type of the piece
    // ( 0:King, 1:Queen, 2:Bishop, 3:Knight, 4:Rock, 5:Pawn, 6:Apprentice, 7:Wizard)
    private int[][][] board =
    {
            {{ 1,4},{ 1,3},{ 1,2},{ 1, 1},{ 1,0},{ 1,2},{ 1,3},{ 1,4}},
            {{ 1,5},{ 1,5},{ 1,5},{ 1, 5},{ 1,5},{ 1,5},{ 1,5},{ 1,5}},
            {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
            {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
            {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
            {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
            {{-1,5},{-1,5},{-1,5},{-1, 5},{-1,5},{-1,5},{-1,5},{-1,5}},
            {{-1,4},{-1,3},{-1,2},{-1, 1},{-1,0},{-1,2},{-1,3},{-1,4}},
    };

    // A 8x8 board with custom pieces
    private int[][][] customboard =
            {
                    {{ 1,4},{ 1,3},{ 1,2},{ 1, 1},{ 1,0},{ 1,2},{ 1,3},{ 1,4}},
                    {{ 1,5},{ 1,6},{ 1,5},{ 1, 5},{ 1,5},{ 1,5},{ 1,6},{ 1,5}},
                    {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
                    {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
                    {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
                    {{ 0,0},{ 0,0},{ 0,0},{ 0, 0},{ 0,0},{ 0,0},{ 0,0},{ 0,0}},
                    {{-1,5},{-1,6},{-1,5},{-1, 5},{-1,5},{-1,5},{-1,6},{-1,5}},
                    {{-1,4},{-1,3},{-1,2},{-1, 1},{-1,0},{-1,2},{-1,3},{-1,4}},
            };

    // Lists of pieces for each player such that it is easy to retrieve specific piece
    // and enumerate its possible moves
    ArrayList<Piece> whitePieces;
    ArrayList<Piece> blackPieces;

    // Storing information for promotion
    Piece toBePromoted = null;
    int promoteTo = 0;

    // Storing players' chessModel.King pieces for checking game end condition
    Piece whiteKing = null;
    Piece blackKing = null;

    // Storing players' scores
    int whiteScore;
    int blackScore;

    // Storing players' name
    String whiteName = "Dr. Jekyll";
    String blackName = "Mr. Hyde";

    // isCheck: 1 - white is being checked; 1 - black; 0 - neither player is being checked
    int isCheck;
    int whoseTurn;

    // This will be used to store all possible movements that player can make in this turn.
    ArrayList<Move> moveHistory;

    /**
     * Constructor for a standard board. \n \n
     * note: set bottom-left of the board as (0,0)
     * @param isStandardGame boolean to indicate if the 8x8 board is hosting a standard game or custom game
     */
    public ChessGame(boolean isStandardGame) {
        x = 8;
        y = 8;
        grid = new Grid[x][y];
        this.moveHistory = new ArrayList<Move>();
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        isCheck = 0;
        whoseTurn = 1;
        whiteScore = 0;
        blackScore = 0;
        if(isStandardGame == false)
            board = customboard;
        initializeBoard();
    }

    /**
     * This function is used for creating a custom board. \n \n
     * note: An empty board will be created if user does not provide a board
     *       or the board does not match the size that user provides.
     *       A grid will be empty if the piece on that grid is not an valid piece.
     *       In the situation where a legion(white/black) has too many kings,
     *       only one of them will be treated as valid piece \n \n
     * note: Custom pieces are not implemented in this function.
     *       Set up custom pieces manually if needed
     * @param inputX,inputY the value to set up the width and length of the board.
     * @param customBoard an array that user provides for constructing the board,
     *                    first index represents the row; second index represents column;
     *                    third index should contain two element, index 0:player of the piece, index 1:type of the piece
     */
    public ChessGame(int inputX, int inputY, int[][][] customBoard){
        x = inputX;
        y = inputY;
        this.moveHistory = new ArrayList<Move>();
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        isCheck = 0;
        whoseTurn = 1;
        whiteScore = 0;
        blackScore = 0;
        grid = new Grid[x][y];

        board = checkCustomBoard(customBoard);
        initializeBoard();
    }

    /**
     * This function is used to reset the board with current setting
     */
    public void resetBoard(){
        this.moveHistory = new ArrayList<Move>();
        this.whitePieces = new ArrayList<Piece>();
        this.blackPieces = new ArrayList<Piece>();
        isCheck = 0;
        whoseTurn = 1;
        whiteKing = null;
        blackKing = null;
        initializeBoard();
    }

    /**
     * This function is used to increment the score based on who is the winner
     */
    public void incrementScore(){
        if(whoseTurn == 1)
            blackScore++;
        else
            whiteScore++;
    }

    /**
     * A helper function to check if the board provided by user is an valid board
     * @param customBoard, the piece information that user provides
     * @return customBoard if it is an valid board, null otherwise
     */
    private int[][][] checkCustomBoard(int[][][] customBoard){
        if(customBoard != null){
            if(customBoard.length != this.y)
                return null;
            else{
                for(int rowIterator = 0; rowIterator < this.y; rowIterator++){
                    if(customBoard[rowIterator].length != this.x)
                        return null;
                    for(int columnIterator = 0; columnIterator < this.x; columnIterator++){
                        if(customBoard[rowIterator][columnIterator].length != 2)
                            return null;
                    }
                }
            }
        }
        return customBoard;
    }

    /**
     * This initialing function is constructing a chess board
     * and assigning pieces to the board
     * by default, it will set up the board as following:
     *
     * R N B K Q B N R     - - - - - - - -
     * P P P P P P P P     - - - - - - - -
     * - - - - - - - -     P P P P P P P P
     * - - - - - - - -     R N B K Q B N R
     */
    private void initializeBoard(){
        //setting board
        for(int xIterator = 0; xIterator < this.x; xIterator++){
            for(int yIterator = 0; yIterator < this.y; yIterator++){
                grid[xIterator][yIterator] = new Grid(xIterator, yIterator);
            }
        }

        // create an empty board if the board information does not match the setting
        if(board == null)
            return;

        // use 1 / -1 to separate different players ( white / black )
        //setting pieces for white
        initializeHelper(board);
    }

    /**
     * Helper function to set pieces to grids of board and to add them to white/blackPieces list.
     * @param board, the piece information for constructing the board.
     */
    private void initializeHelper(int[][][] board){
        ArrayList<Piece> list;
        for(int yIterator = 0; yIterator < this.y; yIterator++){
            for(int xIterator = 0; xIterator < this.x; xIterator++){
                Piece piece;
                if(Math.abs(board[yIterator][xIterator][0]) == 1) {
                    if(board[yIterator][xIterator][0] == 1)
                        list = this.whitePieces;
                    else
                        list = this.blackPieces;

                    switch (board[yIterator][xIterator][1]) {
                        case 0:
                            piece = new King(xIterator, yIterator, board[yIterator][xIterator][0]);
                            if(piece.getPlayer() == 1 && whiteKing == null)
                                whiteKing = piece;
                            else if(piece.getPlayer() == -1 && blackKing == null)
                                blackKing = piece;
                            else
                                piece = null;
                            break;
                        case 1:
                            piece = new Queen(xIterator, yIterator, board[yIterator][xIterator][0]);
                            break;
                        case 2:
                            piece = new Bishop(xIterator, yIterator, board[yIterator][xIterator][0]);
                            break;
                        case 3:
                            piece = new Knight(xIterator, yIterator, board[yIterator][xIterator][0]);
                            break;
                        case 4:
                            piece = new Rock(xIterator, yIterator, board[yIterator][xIterator][0]);
                            break;
                        case 5:
                            piece = new Pawn(xIterator, yIterator, board[yIterator][xIterator][0]);
                            break;
                        case 6:
                            piece = new Apprentice(xIterator, yIterator, board[yIterator][xIterator][0]);
                            break;
                        default:
                            piece = null;
                    }
                    if(piece != null)
                        list.add(piece);
                }
                else
                    piece = null;
                grid[xIterator][yIterator].setGrid(piece);
            }
        }
    }

    /**
     * This function should be called at the beginning of player's turn
     * and it will check if end condition is satisfied
     */
    public int checkEndCondition(){
        boolean availableMoves = false;
        ArrayList<Piece> pieceList;
        if(whoseTurn == 1)
            pieceList = this.whitePieces;
        else
            pieceList = this.blackPieces;

        setPossibleMoves(pieceList);

        // use for loop to go through every possible move
        // if attemptMove return false, remove the move from possibleMoves
        for(int pieceIterator = 0; pieceIterator < pieceList.size(); pieceIterator++){
            ArrayList<Move> piecePossibleMoves = pieceList.get(pieceIterator).getPossibleMoves();
            evaluatePossibleMoves(piecePossibleMoves);
            if(!piecePossibleMoves.isEmpty())
                availableMoves = true;
        }
        // if there is no possible moves, the game comes to an end.
        if(!availableMoves){
            if(isCheck == whoseTurn){
                String winner;
                if(whoseTurn == 1)
                    return -1;
                else
                    return 1;
            }
            else{
                return 2;
            }
        }
        return 0;
    }

    private void evaluatePossibleMoves(ArrayList<Move> possibleMoves) {
        for(int iterator = 0; iterator < possibleMoves.size(); iterator++) {
            boolean result = attemptMove(possibleMoves.get(iterator));
            if (!result) {
                possibleMoves.remove(iterator);
                iterator--;
            }
        }
    }

    /**
     * Helper function to enumerate every possible moves merely based on piece logic and blocking
     */
    private void setPossibleMoves(ArrayList<Piece> list){
        for(int iterator = 0; iterator < list.size(); iterator++){
            (list.get(iterator)).checkPossibleMoves(this);
        }
    }

    /**
     * Helper function to refine possibleMoves list
     * by eliminating moves that will lead player's king under check.
     * @param move, the object contains start location and end location of a possible move
     */
    private boolean attemptMove(Move move){
        // attempt to make the move, and then determine if self king is being checked
        // undo the move and return the result in boolean

        Piece startPiece = grid[move.startX][move.startY].getGrid();
        Piece endPiece = grid[move.endX][move.endY].getGrid();
        boolean validity = true;

        grid[move.startX][move.startY].setGrid(null);
        grid[move.endX][move.endY].setGrid(startPiece);
        if(endPiece != null)
            endPiece.setCaptured();

        ArrayList<Piece> list;
        Piece opponentKing;
        if(whoseTurn == 1) {
            list = this.blackPieces;
            opponentKing = whiteKing;
        }
        else {
            list = this.whitePieces;
            opponentKing = blackKing;
        }
        for(int iterator = 0; iterator < list.size(); iterator++){
            Piece tempPiece = list.get(iterator);
            validity = !(tempPiece.isValid(opponentKing.getXLocation(), opponentKing.getYLocation(), this));

            // if there are opponent pieces which can reach player's king, validity will be true,
            // and the attempted move will not be valid
            if(!validity)
                break;
        }

        grid[move.startX][move.startY].setGrid(startPiece);
        grid[move.endX][move.endY].setGrid(endPiece);
        if(endPiece != null)
            endPiece.undoCaptured();

        return validity;
    }

    /**
     * This function will take a start location and a end location from player and perform the move if it is valid.
     * @param startX,startY,endX,endY the values of the move that player tries to make
     * @return true if the move is valid, false otherwise
     */
    public boolean makeAMove(int startX, int startY, int endX, int endY){
        //Check if the piece to move has possible moves and if it is the player's turn
        // (otherwise the possible moves need to be cleaned up after making a move) TODO: explain more
        Piece pieceToMove = this.getPiece(startX, startY);
        if(pieceToMove != null && pieceToMove.getPlayer() == whoseTurn
                && !pieceToMove.getPossibleMoves().isEmpty()) {
            ArrayList<Move> possibleMoves = pieceToMove.getPossibleMoves();
            for (int iterator = 0; iterator < possibleMoves.size(); iterator++) {
                Move possibleMove = possibleMoves.get(iterator);

                // if the move requested match one of the move in possible moves list
                // consider the move is valid and return true as success
                if (possibleMove.startX == startX && possibleMove.startY == startY
                        && possibleMove.endX == endX && possibleMove.endY == endY) {
                    Piece startPiece = grid[startX][startY].getGrid();
                    Piece endPiece = grid[endX][endY].getGrid();

                    grid[startX][startY].setGrid(null);
                    grid[endX][endY].setGrid(startPiece);
                    startPiece.setHasMoved();
                    if (endPiece != null)
                        endPiece.setCaptured();
                    // check if promotion satisfied if the moving piece is in chessModel.Pawn class
                    if (startPiece instanceof Pawn) {
                        if ((startPiece.getPlayer() == 1 && startPiece.getYLocation() == (this.y - 1))
                                || (startPiece.getPlayer() == -1 && startPiece.getYLocation() == 0)) {
                            possibleMove.specialMove = 2;
                            toBePromoted = startPiece;
                        }
                    }

                    possibleMove.isCheck = this.isCheck;

                    checkCheck(whoseTurn);

                    whoseTurn = -1 * whoseTurn;
                    moveHistory.add(possibleMove);

                    return true;
                }
            }
        }
        System.out.print("Invalid move!");
        return false;
    }

    public void checkCheck(int player){
        ArrayList<Piece> list;
        Piece opponentKing;
        boolean validity = false;
        if (player == -1) {
            list = this.blackPieces;
            opponentKing = whiteKing;
        } else {
            list = this.whitePieces;
            opponentKing = blackKing;
        }
        for (int iterator = 0; iterator < list.size(); iterator++) {
            Piece tempPiece = list.get(iterator);
            validity = tempPiece.isValid(opponentKing.getXLocation(), opponentKing.getYLocation(), this);

            // if there are opponent pieces which can reach player's king, validity will be true,
            // and the attempted move will not be valid
            if (validity) {
                break;
            }
        }
        if (validity)
            this.isCheck = -1 * player;
        else
            this.isCheck = 0;
    }

    /**
     * A function to handle the promotion process
     */
    public void promotePiece(){
        Piece piece = toBePromoted;
        Piece promotedPiece;
        int x = piece.getXLocation();
        int y = piece.getYLocation();
        int player = piece.getPlayer();
        if(piece instanceof Apprentice)
            promotedPiece = new Wizard(x, y, player);
        else{
            /*Scanner reader = new Scanner(System.in);  // Reading from System.in
            System.out.println("Enter a number (1:Queen, 2:Bishop, 3:Knight, 4:Rock, other: Apprentice): ");
            int userInput = reader.nextInt();*/
            switch (promoteTo) {
                case 1:
                    promotedPiece = new Queen(x, y, player);
                    break;
                case 2:
                    promotedPiece = new Bishop(x, y, player);
                    break;
                case 3:
                    promotedPiece = new Knight(x, y, player);
                    break;
                case 4:
                    promotedPiece = new Rock(x, y, player);
                    break;
                default:
                    promotedPiece = new Apprentice(x, y, player);
            }
        }

        // replace the piece with promoted piece
        grid[x][y].setGrid(null);
        grid[x][y].setGrid(promotedPiece);
        piece.setCaptured();

        if(player == 1)
            whitePieces.add(promotedPiece);
        else
            blackPieces.add(promotedPiece);

        toBePromoted = null;
    }

    /**
     * A function to undo the previous move until there is no move to be undone
     * @return true on success, false otherwise
     */
    public boolean undoAMove(){
        if(moveHistory.isEmpty())
            return false;


        Move moveToUndo = moveHistory.remove(moveHistory.size()-1);

        Piece undoPiece = this.grid[moveToUndo.endX][moveToUndo.endY].getGrid();

        // check if the piece was promoted in the last step
        // if true, then replace the piece with one of the captured pawns in the list (due to the setup of the promotion process)
        // and continue the undo process
        if(moveToUndo.specialMove == 2){
            ArrayList<Piece> list = whitePieces;
            if(undoPiece.getPlayer() == -1)
                list = blackPieces;
            for(int iterator = 0; iterator < list.size(); iterator++) {
                if (undoPiece instanceof Wizard && list.get(iterator) instanceof Apprentice
                        && list.get(iterator).getCaptured() == true) {
                    list.remove(undoPiece);
                    undoPiece = list.get(iterator);
                    undoPiece.undoCaptured();
                    break;
                }
                else if(list.get(iterator) instanceof Pawn && !(list.get(iterator) instanceof Apprentice)
                        && list.get(iterator).getCaptured() == true){
                    list.remove(undoPiece);
                    undoPiece = list.get(iterator);
                    undoPiece.undoCaptured();
                    break;
                }
            }
        }

        this.grid[moveToUndo.startX][moveToUndo.startY].setGrid(undoPiece);
        if(moveToUndo.hasMoved == false)
            undoPiece.undoHasMoved();

        this.grid[moveToUndo.endX][moveToUndo.endY].setGrid(moveToUndo.pieceCaptured);
        if(moveToUndo.pieceCaptured != null)
            moveToUndo.pieceCaptured.undoCaptured();

        this.isCheck = moveToUndo.isCheck;
        whoseTurn = -1 * whoseTurn;

        checkEndCondition();
        return true;
    }

    /**
     * A getter function to retrieve the piece information from a specific grid
     * @param x,y the location of the grid
     * @return a chessModel.Piece object which is on the grid, null if the grid is empty
     */
    public Piece getPiece(int x, int y){
        return grid[x][y].getGrid();
    }

    /**
     * @return the size of board in x direction
     */
    public int getX(){
        return this.x;
    }

    /**
     * @return the size of board in y direction
     */
    public int getY(){
        return this.y;
    }

    /**
     * Return the check status of the game
     * @return -1 if black is checked, 1 if white is checked, 0 if neither player is checked
     */
    public int getIsCheck(){
        return this.isCheck;
    }

    /**
     * Return which player's turn it is
     * @return -1 if it is black's turn, 1 if it is white's turn
     */
    public int getWhoseTurn(){
        return this.whoseTurn;
    }

    /**
     * Return white's score
     * @return value of white's score
     */
    public int getWhiteScore(){
        return this.whiteScore;
    }

    /**
     * Return black's score
     * @return value of black's score
     */
    public int getBlackScore(){
        return this.blackScore;
    }

    /**
     * A getter function to retrieve the piece to be promoted
     * @return a chessModel.Piece object which is on the grid, null if the grid is empty
     */
    public Piece getToBePromoted(){
        return this.toBePromoted;
    }

    /**
     * This function is used to obtain a list of a player's pieces
     * @param player whose piece list to be returned
     * @return an ArrayList of Piece object
     */
    public ArrayList<Piece> getPieces(int player){
        if(player == 1)
            return this.whitePieces;
        else
            return this.blackPieces;
    }

    /**
     * Return the name of the player
     * @param player whose name to be returned
     * @return a String contains player's name
     */
    public String getName(int player){
        if(player == 1)
            return this.whiteName;
        else
            return this.blackName;
    }

    public void setPromoteTo(int type){
        this.promoteTo = type;
    }

    //=============================== Helper Function for Tests =======================================
    public Piece setGrid(int x, int y, Piece piece){
        return this.grid[x][y].setGrid(piece);
    }

    public Piece getGrid(int x, int y){
        return this.grid[x][y].getGrid();
    }

    public void add(Piece piece){
        if(piece.getPlayer() == 1) {
            this.whitePieces.add(piece);
            if(piece instanceof King)
                whiteKing = piece;
        }
        else {
            this.blackPieces.add(piece);
            if(piece instanceof King)
                blackKing = piece;
        }
    }

    public void setCheck(int value){
        this.isCheck = value;
    }

    public void setWhoseTurn(int value){
        this.whoseTurn = value;
    }

    public int getPromoteTo(){
        return this.promoteTo;
    }
}