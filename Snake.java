package com.javarush.games.snake;

import com.javarush.engine.cell.*;


import java.util.ArrayList;
import java.util.List;

public class Snake extends GameObject {

    public boolean isAlive = true;



    private Direction direction = Direction.LEFT;
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";


    private List<GameObject> snakeParts = new ArrayList<GameObject>();


    public Snake(int x, int y) {

        super(x, y);

        GameObject firstPart = new GameObject(x, y);
        GameObject secondPart = new GameObject(x + 1, y);
        GameObject thirdPart = new GameObject(x + 2, y);


        snakeParts.add(0, firstPart);
        snakeParts.add(1, secondPart);
        snakeParts.add(2, thirdPart);

    }

    public void setDirection(Direction direction) {
        switch (this.direction) {
            case LEFT:
            case RIGHT:
                if (snakeParts.get(0).x == snakeParts.get(1).x) return;
                break;
            case UP:
            case DOWN:
                if (snakeParts.get(0).y == snakeParts.get(1).y) return;
                break;
        }
        this.direction = direction;
    }


    public void draw(Game game) {
        for (int i = 0; i < snakeParts.size(); i++) {
            if (i == 0) {
                game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            } else {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
            }
        }
    }


    public GameObject createNewHead() {
        GameObject gameObject = null;
        if (direction == Direction.UP)
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        if (direction == Direction.DOWN)
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        if (direction == Direction.RIGHT)
            gameObject = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        if (direction == Direction.LEFT)
            gameObject = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        return gameObject;
    }


    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH
                || newHead.x < 0
                || newHead.y >= SnakeGame.HEIGHT
                || newHead.y < 0) {
            isAlive = false;
            return;
        }

        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        } else snakeParts.add(0, newHead);

        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
    }
    public boolean checkCollision(GameObject object){
        for (int i = 0; i < snakeParts.size(); i++) {
            if (object.x == snakeParts.get(i).x && object.y == snakeParts.get(i).y) {
                return true;
            }
        }
        return false;
    }


    public int getLength()
    {
        return snakeParts.size();
    }



}

