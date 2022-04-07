package com.vladi_karasove.pac_man_anime;

public class Player {
    private int x;
    private int y;
    private int lastx, lasty;
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3,START_HERO_X=4,START_HERO_Y=1,START_ENEMY_X=0,START_ENEMY_Y=1;

    public Player() { }

    public void hero(){
        this.x = START_HERO_X;
        this.y = START_HERO_Y;
        this.lastx=START_HERO_X;
        this.lasty = START_HERO_Y;
    }
    public void enemy(){
        this.x = START_ENEMY_X;
        this.y = START_ENEMY_Y;
        this.lastx=START_ENEMY_X;
        this.lasty = START_ENEMY_Y;
    }

    public Player setLastX(int x) {
        this.lastx = x;
        return this;
    }

    public Player setLastY(int y) {
        this.lasty = y;
        return this;
    }


    public int getLastX() {
        return lastx;
    }

    public int getLastY() {
        return lasty;
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


}