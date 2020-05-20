package com.javarush.games.snake;

import com.javarush.engine.cell.*;


public class SnakeGame extends Game{


    private int turnDelay;
    private Snake snake;
    public final static int WIDTH = 15;
    public final static int HEIGHT = 15;
    private Apple apple;
    private boolean isGameStopped;
    private final static int GOAL = 28;
    private int score;


    @Override
    public void onTurn(int x) {

        snake.move(apple);
        if (apple.isAlive == false)
        {
            createNewApple();
            score +=5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);

        }
        if(snake.isAlive == false)
        {
            gameOver();
        }

        if(snake.getLength() > GOAL)
        {
            win();
        }


        drawScene();
    }

    @Override
    public void initialize()
    {
      setScreenSize(WIDTH, HEIGHT); 
      createGame();
    }
    
    
    
    private void createGame()
    {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        turnDelay = 300;

        createNewApple();

     isGameStopped = false;
     drawScene();
     setTurnTimer(turnDelay);
     score = 0;
     setScore(score);



    }
    
    
    private void drawScene()
    {
     
     for(int x  = 0 ; x < WIDTH ; x++)
        {
            for(int y = 0 ; y < HEIGHT ; y++)
            {

                setCellValueEx(x, y ,Color.DARKCYAN, "");
            }
        }

     snake.draw(this);
     apple.draw(this);

    }


    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case SPACE:
                if (isGameStopped)
                    createGame();
        }

    }

    private void createNewApple()
    {

        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));

    }

    private void gameOver()
    {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.FIREBRICK, "YOU LOSE !", Color.ORANGE, 55);


    }

    private void win()
    {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKCYAN,"CONGRATULATIONS !!!", Color.CADETBLUE, 60);

    }
}









