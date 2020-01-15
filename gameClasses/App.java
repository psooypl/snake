package marcin.gameClasses;

import javax.swing.*;
import java.awt.*;

public class App {
    public static final int FRAME_WIDTH = 600;
    public static final int FRAME_HEIGHT = 600;

    public static void main(String[] args) {
        Snake snake = new Snake();

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame gameWindow = new JFrame("Snake");
                snake.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
                gameWindow.add(snake);
                gameWindow.pack();
                gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameWindow.setLocationRelativeTo(null);
                gameWindow.setVisible(true);
            }
        });
    }
}
