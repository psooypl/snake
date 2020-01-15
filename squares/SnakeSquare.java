package marcin.squares;

import java.awt.*;

public class SnakeSquare extends Square {

    private final static Color SNAKE_GREEN = new Color(0, 100, 20);
    public static int numOfSqr = 0;

    public SnakeSquare(int row, int col) {
        super(row,col);
        this.sqrColor = SNAKE_GREEN;
        this.numOfSqr++;
    }

}
