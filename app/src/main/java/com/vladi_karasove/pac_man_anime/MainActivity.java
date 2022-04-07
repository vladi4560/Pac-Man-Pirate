package com.vladi_karasove.pac_man_anime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private MaterialButton buttons[];
    private ImageView background;
    private ImageView mat[][];
    private ImageView lives[];
    private MaterialTextView score;
    private int choice;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitViews();
        game = new Game();
        startTimer();
    }


    private void play() {
        game.nextMove(this.choice);
        if (game.getHitcheck()) {
            updateLivesUI();
            if (game.isGameOver())
                finish();
        }
        restartBoard();
        updateScoreUI();
    }

    private void updateScoreUI() {
       score.setText(""+game.getScore());
    }

    private void restartBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                mat[i][j].setImageResource(R.drawable.ic_square_24);
            }
        }
        mat[game.getPlayer().getX()][game.getPlayer().getY()].setImageResource(R.drawable.img_jack);
        mat[game.getEnemy().getX()][game.getEnemy().getY()].setImageResource(R.drawable.img_navy);
       // mat[game.getPlayer().getX()][game.getPlayer().getY()].setVisibility(View.VISIBLE);
       // mat[game.getEnemy().getX()][game.getEnemy().getY()].setVisibility(View.VISIBLE);
    }

    private void updateBoard() {
        if (game.getPlayer().getX() == game.getPlayer().getLastX() && game.getPlayer().getY() == game.getPlayer().getLastY()) {
        } else {
            mat[game.getPlayer().getLastX()][game.getPlayer().getLastY()].setVisibility(View.INVISIBLE);
            mat[game.getPlayer().getX()][game.getPlayer().getY()].setImageResource(R.drawable.img_jack);
            mat[game.getPlayer().getX()][game.getPlayer().getY()].setVisibility(View.VISIBLE);
        }
        if (game.getEnemy().getX() == game.getEnemy().getLastX() && game.getEnemy().getY() == game.getEnemy().getLastY()) {
        } else {

            mat[game.getEnemy().getLastX()][game.getEnemy().getLastY()].setVisibility(View.INVISIBLE);
            mat[game.getEnemy().getX()][game.getEnemy().getY()].setImageResource(R.drawable.img_navy);
            mat[game.getEnemy().getX()][game.getEnemy().getY()].setVisibility(View.VISIBLE);
        }
    }

    private void updateLivesUI() {
        for (int i = 2; game.getLives() < i + 1; i--)
            lives[i].setVisibility(View.INVISIBLE);

    }

    private void InitViews() {
        mat = new ImageView[5][3];
        background = findViewById(R.id.background);
        score= findViewById(R.id.game_LBL_score);
        Glide.with(this).load("https://wallpaperaccess.com/full/884549.jpg").into(background);
        InitMat();
        InitBtn();
        InitLives();
    }

    private void InitMat() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                String imgView = "game_img_" + i + j;
                int id = getResources().getIdentifier(imgView, "id", getPackageName());
                mat[i][j] = (ImageView) findViewById(id);
            }

        }
    }

    private void InitLives() {
        lives = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            String live = "game_IMG_heart" + i;
            int id = getResources().getIdentifier(live, "id", getPackageName());
            lives[i] = (ImageView) findViewById(id);
        }
    }

    private void InitBtn() {
        buttons = new MaterialButton[4];
        for (int i = 0; i < 4; i++) {
            String btn = "game_BTN_" + i;
            int id = getResources().getIdentifier(btn, "id", getPackageName());
            buttons[i] = (MaterialButton) findViewById(id);
        }
        buttons[0].setOnClickListener(view -> moveChoice(Game.UP));
        buttons[1].setOnClickListener(view -> moveChoice(Game.RIGHT));
        buttons[2].setOnClickListener(view -> moveChoice(Game.DOWN));
        buttons[3].setOnClickListener(view -> moveChoice(Game.LEFT));
    }

    private boolean oneTime = true;

    private void moveChoice(int num) {
        this.choice = num;
//        if (oneTime) {
//            oneTime=false;
//            startTimer();
//        }
    }


    ////////////////////////////////////////////////////////////////
    private Timer timer;
    private final int PERIOD = 1000;

    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }

    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;
//
//    private void startTimer() {
//        timerStatus = TIMER_STATUS.RUNNING;
//
//        timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                tick();
//            }
//        }, 0, DELAY);
//
//    }

    private void tick() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                play();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timerStatus == TIMER_STATUS.PAUSE) {
            startTimer();
            timerStatus = TIMER_STATUS.RUNNING;
        }
    }

    private void stopTimer() {
        //updateTimerControls();
        timer.cancel();
    }

    private void startTimer() {
        timerStatus = TIMER_STATUS.RUNNING;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        play();

                    }
                });

            }
        }, 0, PERIOD);
    }
}

