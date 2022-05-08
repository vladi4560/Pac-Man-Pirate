package com.vladi_karasove.pac_man_anime.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.vladi_karasove.pac_man_anime.callBacks.CallBack_CheckTopTen;
import com.vladi_karasove.pac_man_anime.objects.Game;
import com.vladi_karasove.pac_man_anime.R;
import com.vladi_karasove.pac_man_anime.objects.Score;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;


public class ActivityGame extends AppCompatActivity {

    private MaterialButton buttons[];
    private ImageView background;
    private ImageView mat[][];
    private ImageView lives[];
    private MaterialTextView score;
    private int choice;
    private Game game;
    private final int ROWS = 8, COLS = 5, LIVES = 3;
    private SensorManager sensorManager;
    private Sensor gyroSensor;
    private Bundle bundle;
    private boolean gameMode;
    private String playerName;

    private MediaPlayer theme_music;
    private MediaPlayer crash_music;
    private MediaPlayer rum_music;
    private static CallBack_CheckTopTen callBack_checkTopTen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        InitViews();
        initMusic();
        initBundle();
        gameMode();
        game = new Game();
    }

    private void gameMode() {
        if (gameMode) {
            hideBtn();
            initSensor();
        }
    }
    private void initMusic() {
        theme_music= MediaPlayer.create(ActivityGame.this,R.raw.the_black_pearl);
        crash_music= MediaPlayer.create(ActivityGame.this,R.raw.crash);
        rum_music=MediaPlayer.create(ActivityGame.this,R.raw.laugh);

    }

    private void initBundle() {
        if (getIntent().getBundleExtra("Bundle") != null) {
            this.bundle = getIntent().getBundleExtra("Bundle");
            gameMode = bundle.getBoolean("gameMode");
            playerName = bundle.getString("playerName");
        } else {
            this.bundle = new Bundle();
        }
    }

    // Every tick of the clock the game will "play"
    private void play() {
        game.nextMove(this.choice);
        if (game.getHitCheck()) {
            updateLivesUI();
            crash_music.start();
            if (game.isGameOver()) {
                finishGame();
                Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
            }
        }
        if(game.getHitBonus()){
            game.setHitBonus(false);
            rum_music.start();
        }
        restartBoard();
        updateScoreUI();
    }

    private void finishGame() {
        callBack_checkTopTen.CheckTopTen(playerName,game.getScore());
        finish();
    }

    // Updates the score of the game in the UI
    private void updateScoreUI() {
        score.setText("" + game.getScore());
    }

    private void restartBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                mat[i][j].setImageResource(R.drawable.ic_square_24);
            }
        }
        mat[game.getPlayer().getX()][game.getPlayer().getY()].setImageResource(R.drawable.img_jack);
        mat[game.getEnemy().getX()][game.getEnemy().getY()].setImageResource(R.drawable.img_navy);
        mat[game.getBonus().getX()][game.getBonus().getY()].setImageResource(R.drawable.img_rum);
    }

    // Updates The UI if there any loses in life
    private void updateLivesUI() {
        for (int i = 2; game.getLives() < i + 1; i--)
            lives[i].setVisibility(View.INVISIBLE);

    }

    // initialize All the
    private void InitViews() {
        mat = new ImageView[ROWS][COLS];
        background = findViewById(R.id.background);
        score = findViewById(R.id.game_LBL_score);
        Glide.with(this).load("https://wallpaperaccess.com/full/884549.jpg").into(background);
        InitMat();
        InitBtn();
        InitLives();
    }

    // initialize All the Image View in Mat
    private void InitMat() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
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

    private void hideBtn() {
        for (int i = 0; i < 4; i++) {
            buttons[i].setVisibility(View.INVISIBLE);
        }
    }

    // set on click method that decide the direction of the Player
    private boolean oneTime = true;

    private void moveChoice(int num) {
        this.choice = num;
        if (oneTime) {
            oneTime = false;
            startTimer();
        }
    }


    ////////////////////////////////////////////////////////////////
    private Timer timer;
    private final int PERIOD = 1100;

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
            theme_music.pause();
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
    @Override
    protected void onStart() {
        super.onStart();
       theme_music.start();
       theme_music.setLooping(true);
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

    ///////////////////// Sensor///////////////////////////////////
    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(gyroSensorEventListener, gyroSensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener gyroSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            NumberFormat formatter = new DecimalFormat("#0");
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            toMove(z, x);
            if (oneTime) {
                oneTime = false;
                startTimer();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private void toMove(float z, float x) {
        if (x > 3.5) {
            this.choice = Game.LEFT;
        } else if (x < -3.5) {
            this.choice = Game.RIGHT;
        } else if (z > 6) {
            this.choice = Game.UP;
        } else if (z < 5) {
            this.choice = Game.DOWN;
        }
    }
 //////////////////////////////////////////Call Back//////////////////////////
    public static void setCallBack(CallBack_CheckTopTen callBack_CheckTopTen){
            callBack_checkTopTen=callBack_CheckTopTen;
        }

}

