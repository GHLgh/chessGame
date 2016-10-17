package chessView;

import chessModel.ChessGame;
import chessModel.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

/**
 * Citation: This program is adopted from the Hand Written Code example from CS242 Assignment1.1 page\n
 * link: https://wiki.illinois.edu/wiki/display/cs242/Assignment+1.1 \n \n
 * Created by guanheng on 9/16/2016.
 */

public class VisualBoard{

    GridButton[][] buttons;

    private JMenuItem newGame;
    private JMenuItem newCustomGame;

    private JButton restartButton;
    private JButton undoButton;
    private JButton forfeitButton;

    private GridButton whiteIcon;
    private GridButton blackIcon;
    private JLabel whiteName;
    private JLabel blackName;

    private JLabel score;
    private JTextArea currentStatus;

    /**
     * This is a constructor to set up the GUI of a standard board
     * @param chessBoard the chess board that is using
     */
    public VisualBoard(JFrame itsFrame, ChessGame chessBoard){
        /*try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }*/
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }

        JFrame window = new JFrame("Chess Game");
        window.setSize(chessBoard.getX()* 60 + 20 + 150, chessBoard.getY() * 60 + 60);

        setUpMenu(window);

        JPanel board = initializeBoardPanel();
        initializeButton(board);
        window.getContentPane().add(board, "East");

        JPanel controlPanel = initializePanel();
        window.getContentPane().add(controlPanel, "West");

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateGrids(chessBoard);
        //itsFrame = window;
    }

    /**
     * This function is used to initialize the panel on the left of the board
     * @return JPanel object of the left panel
     */
    private JPanel initializePanel() {
        JPanel board = new JPanel();
        board.setPreferredSize(new Dimension(150, 480));
        board.setLayout(null);

        initializeCommandButtons(board);
        initializePlayerSection(board);
        initializeInfoSection(board);

        return board;
    }

    /**
     * This function is used to initialize the text area for showing messages
     * @param board the panel that the section is on
     */
    private void initializeInfoSection(JPanel board) {
        score = new JLabel(0 + ":" + 0, JLabel.CENTER);
        score.setBounds(45, 230, 60, 20);
        board.add(score);

        currentStatus = new JTextArea(5, 20);
        currentStatus.append("Please make a move\n");
        currentStatus.setBounds(6, 330, 140, 140);
        currentStatus.setLineWrap(true);
        currentStatus.setEditable(false);
        board.add(currentStatus);
    }

    /**
     * This function is used to initialize the player status area
     * @param board the panel that the section is on
     */
    private void initializePlayerSection(JPanel board) {
        whiteIcon = new GridButton(-1,-1);
        whiteIcon.setIcon(new ImageIcon(this.getClass().getResource("/icon/10" + ".png")));
        whiteIcon.gridColor = new Color(209, 139, 71);
        whiteIcon.setBackground(whiteIcon.gridColor);
        whiteIcon.setOpaque(true);
        whiteIcon.setContentAreaFilled(true);
        whiteIcon.setBorderPainted(false);
        whiteIcon.setBounds(15, 160, 60, 60);
        board.add(whiteIcon);

        whiteName = new JLabel("Dr. Jekyll");
        whiteName.setBounds(80, 160, 60, 20);
        board.add(whiteName);

        blackIcon = new GridButton(-1,-1);
        blackIcon.setIcon(new ImageIcon(this.getClass().getResource("/icon/-10" + ".png")));
        blackIcon.gridColor = new Color(255, 206, 158);
        blackIcon.setBackground(blackIcon.gridColor);
        blackIcon.setBounds(75, 260, 60, 60);
        blackIcon.setOpaque(true);
        blackIcon.setContentAreaFilled(true);
        blackIcon.setBorderPainted(false);
        board.add(blackIcon);

        blackName = new JLabel("Mr. Hyde");
        blackName.setBounds(15, 300, 60, 20);
        board.add(blackName);
    }

    /**
     * This function is used to initialize the command area for restart, forfeit and undo
     * @param board the panel that the section is on
     */
    private void initializeCommandButtons(JPanel board) {
        restartButton = new JButton("Restart");
        restartButton.setBounds(5, 10, 140, 40);
        board.add(restartButton);

        forfeitButton = new JButton("Forfeit");
        forfeitButton.setBounds(5, 60, 140, 40);
        board.add(forfeitButton);

        undoButton = new JButton("Undo");
        undoButton.setBounds(5, 110, 140, 40);
        board.add(undoButton);
    }

    /**
     * This is a helper function to set up each grid which is represented as a JButton object in the board
     * @param myPanel the panel that the buttons will be added to
     */
    private void initializeButton(JPanel myPanel) {
        buttons = new GridButton[8][8];
        for(int xIterator = 0; xIterator < 8; xIterator++){
            for(int yIterator = 0; yIterator < 8; yIterator++) {
                GridButton button = new GridButton(xIterator, yIterator);
                button.setBackground(button.gridColor);
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setBorderPainted(false);
                button.setBounds(xIterator * 60, (7-yIterator)*60, 60, 60);
                buttons[xIterator][yIterator] = button;
                myPanel.add(buttons[xIterator][yIterator]);
            }
        }
        //buttons[0][0].setIcon(new ImageIcon(this.getClass().getResource("/icon/BB.png")));
        //button.addActionListener(this);
    }

    /**
     * A helper function to initialize the panel that is large enough to hold a standard board
     * @return JPanel object
     */
    private JPanel initializeBoardPanel() {
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(480,480));
        myPanel.setLayout(null);
        return myPanel;
    }

    /**
     * A helper function to set up menu which can be used to create a new game
     * @param window the JFrame object that the game is based on
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        newGame = new JMenuItem("New Game");
        newCustomGame = new JMenuItem("New Game with Custom Pieces");

        file.add(newGame);
        file.add(newCustomGame);
        menubar.add(file);
        window.setJMenuBar(menubar);
    }

    /**
     * A function to update the grids in the board which should be called when a move is made
     * @param chessBoard the chessModel.ChessGame object where the game is hosting.
     */
    public void updateGrids(ChessGame chessBoard){
        for(int xIterator = 0; xIterator < 8; xIterator++){
            for(int yIterator = 0; yIterator < 8; yIterator++) {
                Piece piece = chessBoard.getPiece(xIterator, yIterator);
                ImageIcon buttonIcon = null;
                if(piece != null)
                    buttonIcon = new ImageIcon(this.getClass().getResource
                            ("/icon/" + piece.getPlayer() + piece.getType() + ".png"));
                buttons[xIterator][yIterator].setIcon(buttonIcon);
                buttons[xIterator][yIterator].gridIcon = buttons[xIterator][yIterator].getIcon();
                //JButton buttonBuffer = buttons[xIterator][yIterator];
            }
        }
    }

    //========= Functions that provides controller the access to add listener to objects in View ===============
    public void addPieceListener(MouseAdapter a) {
        for(int xIterator = 0; xIterator < buttons.length; xIterator++){
            for(int yIterator = 0; yIterator < buttons[xIterator].length; yIterator++){
                buttons[xIterator][yIterator].addMouseListener(a);
            }
        }
    }

    public void addRestartListener(MouseAdapter a) {
        restartButton.addMouseListener(a);
    }

    public void addUndoListener(MouseAdapter a) {
        undoButton.addMouseListener(a);
    }

    public void addForfeitListener(MouseAdapter a) {
        forfeitButton.addMouseListener(a);
    }

    public void addWhiteListener(MouseAdapter a) {
        whiteIcon.addMouseListener(a);
    }

    public void addNewStandardGameListener(ActionListener a){
        newGame.addActionListener(a);
    }

    public void addNewCustomGameListener(ActionListener a){
        newCustomGame.addActionListener(a);
    }

    public void addBlackListener(MouseAdapter a) {
        blackIcon.addMouseListener(a);
    }

    /**
     * This function will show scores on View based on inputs it receives
     * @param white the score of white player
     * @param black the score of black player
     */
    public void setScore(int white, int black){
        score.setText(white + ":" + black);
    }

    /**
     * This function will return the GridButtons which construct the chess board
     * @return 2-D array of GridButton object
     */
    public GridButton[][] getButtons(){
        return this.buttons;
    }

    /**
     * This function will set if the player icon is active based on who is taking the turn
     * @param player the player who is taking the turn
     */
    public void setEnable(int player){
        if(player == 1){
            whiteIcon.setEnabled(true);
            blackIcon.setEnabled(false);
        }
        else{
            whiteIcon.setEnabled(false);
            blackIcon.setEnabled(true);
        }
    }

    /**
     * Set players' names in View
     * @param white player name
     * @param black player name
     */
    public void setName(String white, String black){
        whiteName.setText(white);
        blackName.setText(black);
    }

    /**
     * This function is used to update text area after a move is made
     * @param validity if it is a valid move
     * @param player who made the move
     */
    public void updateText(boolean validity, int player){
        String invalid = " an invalid";
        if(validity)
            invalid = " a valid";
        String playerName = whiteName.getText();
        if(player == -1)
            playerName = blackName.getText();
        if(currentStatus.getLineCount() > 4)
            currentStatus.setText(null);
        currentStatus.append(playerName + " made" + invalid + " move\n");
    }

    /**
     * This function is used to update text area when a command is performed
     * @param indicator 0:restart refused, other: undo
     * @param player the player who request the command
     */
    public void updateText(int indicator, int player){
        String name = whiteName.getText();
        if(player == -1)
            name = blackName.getText();

        if(currentStatus.getLineCount() > 4)
            currentStatus.setText(null);
        if(indicator == 0)
            currentStatus.append(name +  "'s request is refused, game continues\n");
        else if(player != 0)
            currentStatus.append(name + " undid the move\n");
        else
            currentStatus.append("Undo failed, no moves have been made\n");
    }

    /**
     * Reset the text area to the begining of the game
     */
    public void resetText(){
        currentStatus.setText("Please make a move\n");
    }

    /**
     * Change the background of player icon to indicate that the player is in check
     * @param player the player who is in check, 0 if no one is in check
     */
    public void showCheck(int player){
        whiteIcon.setBackground(whiteIcon.gridColor);
        blackIcon.setBackground(blackIcon.gridColor);
        if(player == 1)
            whiteIcon.setBackground(Color.red);
        else if(player == -1)
            blackIcon.setBackground(Color.red);
    }

 /*   public static void main(String[] args) {
        chessModel.ChessGame board = new chessModel.ChessGame();
        new chessView.VisualBoard(board);
    }*/

}
