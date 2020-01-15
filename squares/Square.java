package marcin.squares;

import java.awt.*;

public class Square {

    public static final int SQUARE_SIZE = 20;
    protected Color sqrColor;
    protected int row;
    protected int col;
    protected Point position;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
        this.position = new Point(col, row);
    }

    public int getRow() {
        return row;
    }


    public int getCol() {
        return col;
    }


    public Color getSqrColor() {
        return sqrColor;
    }

    public void setSqrColor(Color sqrColor) {
        this.sqrColor = sqrColor;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        this.row = (int) position.getY();
        this.col = (int) position.getX();
    }

    public boolean compSqrPos(Square sqr) {

        return this.col == sqr.col && this.row == sqr.row;
    }


}
