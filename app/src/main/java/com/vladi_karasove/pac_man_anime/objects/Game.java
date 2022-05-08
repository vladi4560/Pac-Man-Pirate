package com.vladi_karasove.pac_man_anime.objects;

import java.util.Random;

public class Game {
    private Player enemy;
    private Player player;
    private Bonus bonus;
    private boolean gameOver = false;
    private boolean hitCheck = false;
    private boolean hitBonus =false;
    private int lives = 3;
    private int score = 0;
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, MAXCOL = 5, MAXROW = 8;


    public Game() {
        player = new Player();
        player.hero();
        enemy = new Player();
        enemy.enemy();
        bonus = new Bonus();
    }

    public void nextMove(int playerDirection) {
        Random random = new Random();
        //int randomDirection = random.nextInt(4);
        moveOnBoard(this.player, playerDirection);
        moveOnBoard(this.enemy, 2);
        //hitCheck();
        hitCheck1();
        hitBonusCheck();
        setScore();
    }


    // restart locations of the two players on the board after a hit
    public void restartLocation() {
        player.hero();
        enemy.enemy();
    }
    // check if there was any hit between the player and the bonus
    private void hitBonusCheck() {
        if((player.getX()==bonus.getX()) && (player.getY()==bonus.getY())){
            score+=50;
            bonus.restartLocation();
            hitBonus=true;
        }
    }

    public boolean getHitCheck() {
        return hitCheck;
    }

    // check if there was any hit between the player and the enemy
    public void hitCheck() {
        if (((player.getLastX() == enemy.getX() && player.getLastY() == enemy.getY()) && (enemy.getLastX() == player.getX() && enemy.getLastY() == player.getY()))
                || (player.getX() == enemy.getX() && enemy.getY() == player.getY())) {
            hitCheck = true;
            lives--;
            restartLocation();
            if (lives == 0) {
                gameOver = true;
            }
            return;
        }
        hitCheck = false;
    }
    public void hitCheck1() {
        if (player.getX() == enemy.getX() && enemy.getY() == player.getY()) {
            hitCheck = true;
            lives--;
            restartLocation();
            if (lives == 0) {
                gameOver = true;
            }
            return;
        }
        else if((player.getLastX() == enemy.getX() && player.getLastY() == enemy.getY()) && (enemy.getLastX() == player.getX() && enemy.getLastY() == player.getY())){
            hitCheck = true;
            lives--;
            restartLocation();
            if (lives == 0) {
                gameOver = true;
            }
            return;
        }

        hitCheck = false;
    }

    public void moveOnBoard(Player player, int direction) {
        if (direction == UP) {
            if (player.getX() == 0) {
                player.setLastX(player.getX());
            } else {
                player.setLastX(player.getX());
                player.setX(player.getX() - 1);

            }
        }
        if (direction == RIGHT) {
            if (player.getY() == MAXCOL - 1) {
                player.setLastY(player.getY());
            } else {
                player.setLastY(player.getY());
                player.setY(player.getY() + 1);
            }
        }
        if (direction == DOWN) {
            if (player.getX() == MAXROW - 1) {
                if (player.getIsEnemy()) {
                    player.setLastX(player.getX());
                    player.setX(0);
                } else
                    player.setLastX(player.getX());
            } else {
                player.setLastX(player.getX());
                player.setX(player.getX() + 1);
            }

        }
        if (direction == LEFT) {
            if (player.getY() == 0) {
                player.setLastY(player.getY());
            } else {
                player.setLastY(player.getY());
                player.setY(player.getY() - 1);
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
    public Bonus getBonus() {
        return bonus;
    }
    public boolean getHitBonus() {
        return hitBonus;
    }
    public void setHitBonus(boolean bonus) {
        this.hitBonus=bonus;
    }
}



