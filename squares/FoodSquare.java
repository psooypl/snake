package marcin.squares;

import java.awt.*;

public class FoodSquare extends Square {

    public static int foodNum = 0;
    private boolean bonus;

    public FoodSquare(int row, int col) {
        super(row, col);

        foodNum++;

        if (foodNum % 5 == 0) {
            super.setSqrColor(Color.YELLOW);
        } else
            super.setSqrColor(Color.RED);

    }


    public void changeColorIfBonus(boolean bonus) {
        if (bonus) {
            super.setSqrColor(Color.YELLOW);
        } else {
            super.setSqrColor(Color.RED);
        }
    }


}
