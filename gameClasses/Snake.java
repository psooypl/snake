package marcin.gameClasses;

import marcin.enums.GameState;
import marcin.enums.Ort;
import marcin.squares.FoodSquare;
import marcin.squares.SnakeSquare;
import marcin.squares.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Snake extends JPanel {

    public static final int MS_200 = 200;
    public static final int MS_100 = 100;

    private Timer timer;
    private int stepTime;

    private FoodSquare foodSquare;
    private ArrayList<SnakeSquare> posList = new ArrayList<SnakeSquare>();

    private Ort actualOrt;
    private Ort previousOrt;
    private GameState gameState;


    public Snake() {
        init();
        game();//metoda gry
    }

    private void init() {
        setFocusable(true);
        requestFocusInWindow(); //do odczytu strzałek

        gameState = GameState.GAME_ON; //jezeli gra trwa = true
        stepTime = MS_200;

        posList.add(new SnakeSquare(10, 10)); //dodanie 1 square

        foodSquare = new FoodSquare(10, 110);

        previousOrt = Ort.UP;
        actualOrt = Ort.RIGHT; // orientacja 1 square w dół

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                previousOrt = actualOrt;
                if (e.getKeyCode() == KeyEvent.VK_UP && previousOrt != Ort.DOWN) {
                    actualOrt = Ort.UP;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && previousOrt != Ort.UP) {
                    actualOrt = Ort.DOWN;
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT && previousOrt != Ort.RIGHT) {
                    actualOrt = Ort.LEFT;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && previousOrt != Ort.LEFT) {
                    actualOrt = Ort.RIGHT;
                }
            }
        });

        timer = new Timer(stepTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState == GameState.GAME_ON) {
                    game();
                }
            }
        });

        timer.start();
    }


    public void game() {

        doMove();
        if (posList.get(0).compSqrPos(foodSquare)) {
            genFoodSquare();
            if (FoodSquare.foodNum % 5 == 1) {
                timer.setDelay(MS_100);
            } else {
                timer.setDelay(MS_200);
            }
            addSquareToEnd();
        }
        checkingColision();
        revalidate();
        repaint();
    }


    private void doMove() {

        for (int i = posList.size() - 1; i >= 1; i--) {
            posList.get(i).setPosition(posList.get(i - 1).getPosition());
        }
        switch (actualOrt) {
            case UP:
                posList.get(0).setPosition(new Point(posList.get(0).getCol(), posList.get(0).getRow() - 20));
                break;
            case DOWN:
                posList.get(0).setPosition(new Point(posList.get(0).getCol(), posList.get(0).getRow() + 20));
                break;
            case LEFT:
                posList.get(0).setPosition(new Point(posList.get(0).getCol() - 20, posList.get(0).getRow()));
                break;
            case RIGHT:
                posList.get(0).setPosition(new Point(posList.get(0).getCol() + 20, posList.get(0).getRow()));
                break;
        }
    }

    private void addSquareToEnd() {

        switch (actualOrt) {
            case UP:
                posList.add(new SnakeSquare(posList.get(posList.size() - 1).getRow() + 20, posList.get(posList.size() - 1).getCol()));
                break;
            case DOWN:
                posList.add(new SnakeSquare(posList.get(posList.size() - 1).getRow() - 20, posList.get(posList.size() - 1).getCol()));
                break;
            case LEFT:
                posList.add(new SnakeSquare(posList.get(posList.size() - 1).getRow(), posList.get(posList.size() - 1).getCol() + 20));
                break;
            case RIGHT:
                posList.add(new SnakeSquare(posList.get(posList.size() - 1).getRow(), posList.get(posList.size() - 1).getCol() - 20));
                break;
        }
    }

    private void checkingColision() {
        for (int i = 1; i < posList.size(); i++) {
            for (int j = 0; j < posList.size(); j++) {
                if (posList.get(i).compSqrPos(posList.get(j)) && i != j) {
                    gameState = GameState.GAME_OFF;
                }
            }
        }
        if ((posList.get(0).getCol() < 0 || posList.get(0).getCol() > 600) || (posList.get(0).getRow() < 0 || posList.get(0).getRow() > 600)) {
            gameState = GameState.GAME_OFF;
        }
    }

    public void genFoodSquare() {
        Random rd = new Random();
        int[] tab = {rd.nextInt(30) * 20 + 10, rd.nextInt(30) * 20 + 10}; //tab[] = {row,col}
        foodSquare = new FoodSquare(tab[0], tab[1]);
    }

    private void drawFoodSquare(Graphics2D g2D) {
        g2D.setColor(foodSquare.getSqrColor());
        g2D.fillRect(foodSquare.getCol() - 10, foodSquare.getRow() - 10, Square.SQUARE_SIZE, Square.SQUARE_SIZE);
        g2D.setColor(Color.BLACK);
        g2D.drawRect(foodSquare.getCol() - 10, foodSquare.getRow() - 10, Square.SQUARE_SIZE, Square.SQUARE_SIZE);
    }

    private void drawSummaryScreen(Graphics2D g2D) {
        cleanWindow(g2D);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2D.setFont(new Font("Calibri", Font.CENTER_BASELINE, 32));
        g2D.setColor(Color.BLACK);
        g2D.drawString("Twój wynik: " + Integer.toString(SnakeSquare.numOfSqr), 200, 300);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        if (gameState == GameState.GAME_ON) {
            cleanWindow(g2D);
            drawSnake(g2D);
            drawFoodSquare(g2D);
        } else if (gameState == GameState.GAME_OFF) {
            drawSummaryScreen(g2D);
        }
    }

    private void cleanWindow(Graphics2D g2D) {
        g2D.setColor(getBackground());
        g2D.fillRect(0, 0, App.FRAME_WIDTH, App.FRAME_HEIGHT);
    }

    private void drawSnake(Graphics2D g2D) {
        g2D.setColor(posList.get(0).getSqrColor());

        for (int i = 0; i < posList.size(); i++) {
            g2D.setColor(posList.get(0).getSqrColor());
            g2D.fillRect(posList.get(i).getCol() - 10, posList.get(i).getRow() - 10, Square.SQUARE_SIZE, Square.SQUARE_SIZE);
            g2D.setColor(Color.BLACK);
            g2D.drawRect(posList.get(i).getCol() - 10, posList.get(i).getRow() - 10, Square.SQUARE_SIZE, Square.SQUARE_SIZE);
        }
    }
}
