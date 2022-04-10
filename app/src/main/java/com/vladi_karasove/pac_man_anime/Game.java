package com.vladi_karasove.pac_man_anime;

import java.util.Random;

public class Game {
    private Player enemy;
    private Player player;
    private boolean gameOver = false;
    private boolean hitcheck=false;
    private int lives = 3;
    private int score=0;
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, MAXCOL = 3, MAXROW = 5;

    public Game() {
        player = new Player();
        player.hero();
        enemy = new Player();
        enemy.enemy();
    }

    public void nextMove(int playerDirection) {
        Random random = new Random();
        int randomDirection = random.nextInt(4);
        movePlayer(playerDirection);
        moveEnemy(randomDirection);
        hitCheck();
        setScore();
    }
    // restart locations of the two players on the board after a hit
    public void restartLocation(){
       player.hero();
       enemy.enemy();
    }

    public boolean getHitcheck() {
        return hitcheck;
    }
    // check if there was any hit between the player and the enemy
    public void hitCheck() {
        if(((player.getLastX()==enemy.getX() && player.getLastY()==enemy.getY()) && (enemy.getLastX()==player.getX() && enemy.getLastY()==player.getY()))
                || (player.getX() == enemy.getX() && enemy.getY() == player.getY())) {
            hitcheck=true;
            lives--;
            restartLocation();
            if (lives == 0) {
                gameOver = true;
            }
            return;
        }
        hitcheck=false;
    }
    // Next move of the player by his choice
    public void movePlayer(int direction) {
        if (direction == UP) {
            if (player.getX() == 0) {
                player.setLastX(player.getX());

            }
            else {
                player.setLastX(player.getX());
                player.setX(player.getX() - 1);

            }
        }
        if (direction == RIGHT) {
            if (player.getY() == MAXCOL -1){
                player.setLastY(player.getY());
                }
            else {
                player.setLastY(player.getY());
                player.setY(player.getY() + 1);
            }
        }
        if (direction == DOWN) {
            if (player.getX() == MAXROW-1 ){
                player.setLastX(player.getX());
            }

            else {
                player.setLastX(player.getX());
                player.setX(player.getX() + 1);
            }

        }
        if (direction == LEFT) {
            if (player.getY() == 0){
                player.setLastY(player.getY());}
            else {
                player.setLastY(player.getY());
                player.setY(player.getY() - 1);
            }

        }

    }
    // Next move of the enemy by random
    public void moveEnemy(int direction) {
        if (direction == UP) {
            if (enemy.getX() == 0){
                enemy.setLastX(enemy.getX());}
            else {
                enemy.setLastX(enemy.getX());
                enemy.setX(enemy.getX() - 1);
            }

        }
        if (direction == RIGHT) {
            if (enemy.getY() == MAXCOL-1){
                enemy.setLastY(enemy.getY());}
            else {
                enemy.setLastY(enemy.getY());
                enemy.setY(enemy.getY() + 1);
            }

        }
        if (direction == DOWN) {
            if (enemy.getX() == MAXROW-1){
                enemy.setLastX(enemy.getX());
                enemy.setX(0);
            }
            else {
                enemy.setLastX(enemy.getX());
                enemy.setX(enemy.getX() + 1);
            }

        }
        if (direction == LEFT) {
            if (enemy.getY() == 0){
                enemy.setLastY(enemy.getY());}
            else {
                enemy.setLastY(enemy.getY());
                enemy.setY(enemy.getY() - 1);
            }

        }
    }
    public boolean isGameOver() {
        return gameOver;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }
    public void setScore() {
        score++;
    }

    public Player getEnemy() {
        return enemy;
    }

    public Player getPlayer() {
        return player;
    }
}



