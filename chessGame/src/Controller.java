import chessModel.Apprentice;
import chessModel.ChessGame;
import chessModel.Move;
import chessModel.Piece;
import chessView.GridButton;
import chessView.VisualBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by guanheng on 9/23/2016.
 */
public class Controller extends JFrame {

    // Variables for controller to operate
    private ChessGame chessBoard = null;
    private VisualBoard visualBoard = null;

    // Variables for recording information (starting grid and ending grid) to make a move in Model
    boolean pieceSelected;
    GridButton endGrid;
    GridButton startGrid;

    // Variables for simulating picking up a piece
    Icon selectedIcon;

    public Controller() {
        //initialize a standard board
        pieceSelected = false;
        setUpBoard(true);

        // add a listener for grids on the board (GridButton)
        // a collection of mouse listeners to simulate the process of making a move
        visualBoard.addPieceListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                GridButton selectedButton = (GridButton)e.getSource();
                selectedIcon = selectedButton.getIcon();
                Piece piece = chessBoard.getPiece(selectedButton.x, selectedButton.y);
                if(piece != null && piece.getPlayer() == chessBoard.getWhoseTurn()) {
                    showMovesHelper(piece, 0);
                    selectedButton.setIcon(null);
                    pieceSelected = true;
                    startGrid = selectedButton;
                }
            }

            public void mouseEntered(MouseEvent e) {
                GridButton selectedButton = (GridButton)e.getSource();
                Piece piece = chessBoard.getPiece(selectedButton.x, selectedButton.y);
                if(pieceSelected) {
                    endGrid = selectedButton;
                    selectedButton.setIcon(selectedIcon);
                }
                else if(piece != null && piece.getPlayer()
                        == chessBoard.getWhoseTurn() && !piece.getPossibleMoves().isEmpty())
                    selectedButton.setBackground(new Color(144, 238, 144));
            }

            public void mouseExited(MouseEvent e){
                GridButton selectedButton = (GridButton)e.getSource();
                if(pieceSelected) {
                    if(startGrid != selectedButton)
                        selectedButton.setIcon(selectedButton.gridIcon);
                    else
                        selectedButton.setIcon(null);
                }
                else
                    selectedButton.setBackground(selectedButton.gridColor);
            }

            public void mouseReleased(MouseEvent e) {
                GridButton selectedButton = (GridButton)e.getSource();
                selectedButton.setIcon(selectedIcon);

                if(pieceSelected) {
                    Piece piece = chessBoard.getPiece(selectedButton.x, selectedButton.y);
                    showMovesHelper(piece, 1);
                    // This if statement is serving merely for the purpose of better gaming experience
                    // such that it won't treat picking up and putting down the piece without moving as a/an (invalid) move
                    if (endGrid != null && endGrid != startGrid) {
                        boolean result  = chessBoard.makeAMove(selectedButton.x, selectedButton.y, endGrid.x, endGrid.y);
                        if(chessBoard.getToBePromoted() != null) {
                            if(!(chessBoard.getToBePromoted() instanceof Apprentice)) {
                                Object[] options = {new ImageIcon(this.getClass().getResource
                                        ("/icon/11.png")), new ImageIcon(this.getClass().getResource
                                        ("/icon/12.png")), new ImageIcon(this.getClass().getResource
                                        ("/icon/13.png")), new ImageIcon(this.getClass().getResource
                                        ("/icon/14.png"))};
                                int dialogResult = JOptionPane.showOptionDialog(null,
                                        "Please select a promoted piece",
                                        "Promotion", JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
                                chessBoard.setPromoteTo(dialogResult+1);
                            }
                            chessBoard.promotePiece();
                            chessBoard.checkCheck(chessBoard.getWhoseTurn()*-1);
                        }
                        updatePanel(result);
                    }
                    visualBoard.updateGrids(chessBoard);
                    int result = chessBoard.checkEndCondition();
                    if(result != 0){
                        endGame(result, false);
                    }
                    else
                        visualBoard.showCheck(chessBoard.getIsCheck());
                    pieceSelected = false;
                }
                startGrid = null;
                endGrid = null;
            }
        });

        // add listeners to player icon (GridButton)
        // certain player's icon will be enabled if it is his/her turn
        // when click on the enabled icon, all pieces that can move will be lighted up
        // icon background will be in red if the player is in check
        visualBoard.addWhiteListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(chessBoard.getWhoseTurn() != 1)
                    return;
                showPieceHelper(1, 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(chessBoard.getWhoseTurn() != 1)
                    return;
                showPieceHelper(1, 1);
            }
        });
        visualBoard.addBlackListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(chessBoard.getWhoseTurn() != -1)
                    return;
                showPieceHelper(-1, 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(chessBoard.getWhoseTurn() != -1)
                    return;
                showPieceHelper(-1, 1);
            }
        });

        // add a listener to restart (JButton)
        visualBoard.addRestartListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int player = chessBoard.getWhoseTurn();
                String requestPlayer = chessBoard.getName(player);
                String otherPlayer = chessBoard.getName(-1 * player);
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        requestPlayer + " requests to restart the game, does " + otherPlayer + " agree?",
                        "Restart the Game", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION){
                    endGame(2, false);
                }
                else
                    visualBoard.updateText(0, player);
            }
        });

        // add a listener to forfeit (JButton)
        visualBoard.addForfeitListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                endGame(chessBoard.getWhoseTurn() * -1, true);
            }
        });

        // add a listener to undo (JButton)
        visualBoard.addUndoListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(chessBoard.undoAMove()) {
                    visualBoard.setEnable(chessBoard.getWhoseTurn());
                    visualBoard.updateText(1, chessBoard.getWhoseTurn());
                    visualBoard.updateGrids(chessBoard);
                    visualBoard.showCheck(chessBoard.getIsCheck());
                }
                else{
                    visualBoard.updateText(1, 0);
                }
            }
        });

        // add a listener to new game (JMenu)
        visualBoard.addNewStandardGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Do you want to start a new standard game?",
                        "Create a New Standard Game", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                    setUpBoard(true);
            }
        });

        // add a listen to new custom game
        visualBoard.addNewCustomGameListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogResult = JOptionPane.showConfirmDialog(null,
                        "Do you want to start a new custom game?",
                        "Create a New Custom Game", JOptionPane.YES_NO_OPTION);
                if(dialogResult == JOptionPane.YES_OPTION)
                    setUpBoard(false);
            }
        });

    }

    /**
     * Helper function to setup the board
     * @param isStandard if it is a standard game
     */
    private void setUpBoard(boolean isStandard) {
        chessBoard = new ChessGame(isStandard);
        chessBoard.checkEndCondition();
        if(visualBoard == null)
            visualBoard = new VisualBoard(this, chessBoard);
        else
            visualBoard.updateGrids(chessBoard);
        visualBoard.setEnable(chessBoard.getWhoseTurn());
        visualBoard.setName(chessBoard.getName(1), chessBoard.getName(-1));
        visualBoard.setScore(chessBoard.getWhiteScore(), chessBoard.getBlackScore());
        visualBoard.showCheck(chessBoard.getIsCheck());
        visualBoard.resetText();
    }

    /**
     * A helper function to update the left panel
     * @param validMove if the move is valid
     */
    private void updatePanel(boolean validMove){
        int player = chessBoard.getWhoseTurn();
        visualBoard.setEnable(player);
        if(validMove)
            player = -1 * player;
        visualBoard.updateText(validMove, player);
    }

    /**
     * A helper function to end the game and start a new game (scores are kept)
     * @param winnerIndicator indicates who is the winner
     * @param isForfeit if a player forfeited
     */
    private void endGame(int winnerIndicator, boolean isForfeit){
        String endString = "Checkmate!";
        if(isForfeit)
            endString = chessBoard.getName(winnerIndicator* -1) + " forfeit!";
        if(winnerIndicator != 2) {
            endString = endString + " " + chessBoard.getName(winnerIndicator) + " wins!";
            chessBoard.incrementScore();
        }
        else
            endString = "Stalemate!";
        JOptionPane.showMessageDialog(null,
                endString,
                "Game End", JOptionPane.INFORMATION_MESSAGE);
        visualBoard.setScore(chessBoard.getWhiteScore(), chessBoard.getBlackScore());
        chessBoard.resetBoard();
        chessBoard.checkEndCondition();
        visualBoard.updateGrids(chessBoard);
        visualBoard.resetText();
        visualBoard.setEnable(chessBoard.getWhoseTurn());
        visualBoard.showCheck(chessBoard.getIsCheck());
    }

    /**
     * Helper function to light up / reset pieces that can move
     * @param player whose turn
     * @param indicator 0: light up the pieces; 1: set to normal
     */
    private void showPieceHelper(int player, int indicator){
        ArrayList<Piece> list = chessBoard.getPieces(player);
        for(int iterator = 0; iterator < list.size(); iterator++) {
            if (!list.get(iterator).getPossibleMoves().isEmpty()) {
                GridButton button = visualBoard.getButtons()[list.get(iterator).getXLocation()]
                        [list.get(iterator).getYLocation()];
                if(indicator == 1) {
                    button.setBackground(button.gridColor);
                    button.setBorderPainted(false);
                }
                else {
                    button.setBackground(new Color(144, 238, 144));
                    button.setBorderPainted(true);
                }
            }
        }
    }

    /**
     * Helper function to show all possible moves of a piece
     * @param piece which piece's possible moves to be shown
     * @param indicator 0: light up the grids; 1: set to normal
     */
    private void showMovesHelper(Piece piece, int indicator) {
        ArrayList<Move> possibleMoves = piece.getPossibleMoves();
        visualBoard.getButtons()[piece.getXLocation()][piece.getYLocation()].setBackground
                (visualBoard.getButtons()[piece.getXLocation()][piece.getYLocation()].gridColor);
        for(int iterator = 0; iterator < possibleMoves.size(); iterator++){
            Move move = possibleMoves.get(iterator);
            GridButton possibleDestination = visualBoard.getButtons()[move.endX][move.endY];
            if(indicator == 0) {
                possibleDestination.setBackground(new Color(144, 238, 144));
                possibleDestination.setBorderPainted(true);
            }
            else{
                possibleDestination.setBackground(possibleDestination.gridColor);
                possibleDestination.setBorderPainted(false);
            }
        }
    }

    /**
     * where the game start
     * @param args
     */
    public static void main(String[] args) {
        new Controller();
    }
}
