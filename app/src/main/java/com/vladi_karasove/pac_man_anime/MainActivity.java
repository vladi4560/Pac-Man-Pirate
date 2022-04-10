package com.vladi_karasove.pac_man_anime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
    }

    // Every tick of the clock the game will "play"
    private void play() {
        game.nextMove(this.choice);
        if (game.getHitcheck()) {
            updateLivesUI();
            if (game.isGameOver()){
                finish();
            Toast.makeText(getApplicationContext(),"Game Over",Toast.LENGTH_SHORT).show();}
        }
        restartBoard();
        updateScoreUI();
    }
    // Updates the score of the game in the UI
    private void updateScoreUI() {
       score.setText(""+game.getScore());
    }

    private void restartBoard() {
        for (int i = 0; i < Game.MAXROW; i++) {
            for (int j = 0; j < Game.MAXCOL; j++) {
                mat[i][j].setImageResource(R.drawable.ic_square_24);
            }
        }
        mat[game.getPlayer().getX()][game.getPlayer().getY()].setImageResource(R.drawable.img_jack);
        mat[game.getEnemy().getX()][game.getEnemy().getY()].setImageResource(R.drawable.img_navy);
    }

    // Updates The UI if there any loses in life
    private void updateLivesUI() {
        for (int i = 2; game.getLives() < i + 1; i--)
            lives[i].setVisibility(View.INVISIBLE);

    }
    // initialize All the
    private void InitViews() {
        mat = new ImageView[5][3];
        background = findViewById(R.id.background);
        score= findViewById(R.id.game_LBL_score);
        Glide.with(this).load("https://wallpaperaccess.com/full/884549.jpg").into(background);
        InitMat();
        InitBtn();
        InitLives();
    }
    // initialize All the Image View in Mat
    private void InitMat() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                String imgView = "game_img_" + i + j;
                int id = getResources().getIdentifier(imgView, "id", getPackageName());
                mat[i][j] = (ImageView) findViewById(id);
            }

        }
    }
    // initialize the Image View with the lives
    private void InitLives() {
        lives = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            String live = "game_IMG_heart" + i;
            int id = getResources().getIdentifier(live, "id", getPackageName());
            lives[i] = (ImageView) findViewById(id);
        }
    }
    // initialize the moving buttons
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

    // set on click method that decide the direction of the Player
    private boolean oneTime = true;
    private void moveChoice(int num) {
        this.choice = num;
        if (oneTime) {
            oneTime=false;
            startTimer();
        }
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

