package com.vladi_karasove.pac_man_anime.objects;

public class Player {
    private int x;
    private int y;
    private int lastX, lastY;
    private boolean isEnemy;
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3,START_HERO_X=7,START_HERO_Y=2,START_ENEMY_X=0,START_ENEMY_Y=2;

    public Player() { }

    public void hero(){
        this.x = START_HERO_X;
        this.y = START_HERO_Y;
        this.lastX =START_HERO_X;
        this.lastY = START_HERO_Y;
        this.isEnemy =false;
    }
    public void enemy(){
        this.x = START_ENEMY_X;
        this.y = START_ENEMY_Y;
        this.lastX =START_ENEMY_X;
        this.lastY = START_ENEMY_Y;
        this.isEnemy =true;
    }

    public Player setLastX(int x) {
        this.lastX = x;
        return this;
    }

    public Player setLastY(int y) {
        this.lastY = y;
        return this;
    }


    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Player setX(int x) {
        this.x = x;
        return this;
    }

    public Player setY(int y) {
        this.y = y;
        return this;
    }

    public boolean getIsEnemy(){
        return this.isEnemy;

    }
}