package chessModel;

/**
 * Created by guanheng on 9/16/2016.
 */
public class Apprentice extends Pawn {
    public Apprentice(int x, int y, int player) {
        super(x, y, player);
        this.type = 6;
    }

    @Override
    /**
     * This function will add all the possible moves under apprentice's move logic
     * without considering check condition \n \n
     * An chessModel.Apprentice can move as a chessModel.Pawn does except it can transform itself into a stage when it has not moved yet.\n
     * In other words, it can always satisfy the prerequisite of moving two grid forward.
     * @param chessBoard chessModel.ChessGame object where the piece is
     */
    public void checkPossibleMoves(ChessGame chessBoard) {
        this.hasMoved = false;
        super.checkPossibleMoves(chessBoard);
    }

    public boolean isValid(int x, int y, ChessGame chessBoard) {
        this.hasMoved = false;
        return super.isValid(x, y, chessBoard);
    }
}
