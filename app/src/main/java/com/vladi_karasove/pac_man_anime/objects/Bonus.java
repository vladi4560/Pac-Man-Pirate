package com.vladi_karasove.pac_man_anime.objects;

import java.util.Random;

public class Bonus {
    private int x;
    private int y;
    private Random random;

    public Bonus() {
        random= new Random();
        restartLocation();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void restartLocation(){
        this.x= random.nextInt(Game.MAXROW);
        this.y=random.nextInt(Game.MAXCOL);
    }
}
