package chessView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by guanheng on 9/24/2016.
 */
public class GridButton extends JButton {
    public int x;
    public int y;
    public Color gridColor;
    public Icon gridIcon;

    public GridButton(int x, int y) {
        this.x = x;
        this.y = y;
        if ((x + y) % 2 == 1)
            this.gridColor = new Color(255, 206, 158);
        else
            this.gridColor = new Color(209, 139, 71);
    }
}
