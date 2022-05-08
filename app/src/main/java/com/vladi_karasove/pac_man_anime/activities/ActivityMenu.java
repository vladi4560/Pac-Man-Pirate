package com.vladi_karasove.pac_man_anime.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.reflect.TypeToken;
import com.vladi_karasove.pac_man_anime.R;
import com.vladi_karasove.pac_man_anime.callBacks.CallBack_CheckTopTen;
import com.vladi_karasove.pac_man_anime.callBacks.CallBack_MyData;
import com.vladi_karasove.pac_man_anime.objects.LocationPlayerTrack;
import com.vladi_karasove.pac_man_anime.objects.MSPV3;
import com.vladi_karasove.pac_man_anime.objects.Score;

import java.util.ArrayList;
import java.util.Collections;


public class ActivityMenu extends AppCompatActivity {
    private ImageView background, icon;
    private MaterialButton sensor, buttons, topTen;
    private boolean gameMode;
    private TextInputLayout playerNameLayout;
    private MaterialButton checkName;
    private MaterialTextView guideName;

    private MediaPlayer theme_music;

    private String playerName;
    private ArrayList<Score> scores= new ArrayList<Score>();
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MSPV3.initHelper(this);
        InitViews();
        initMusic();
        initBtnClick();
        getData();

    }
    private void initMusic() {
        theme_music= MediaPlayer.create(ActivityMenu.this,R.raw.fog_bound);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
        theme_music.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        theme_music.start();
        theme_music.setLooping(true);
    }

    private void getData() {
        TypeToken token = new TypeToken<ArrayList<Score>>() {
        };
        scores = MSPV3.getMe().getArray("MY DATA", token);
        if (scores == null)
            scores = new ArrayList<Score>();
    }
    private void saveData(){
        MSPV3.getMe().putArray("MY DATA",scores);
    }

    private void InitViews() {
        background = findViewById(R.id.menu_IMG_background);
        icon = findViewById(R.id.menu_img_icon);
        sensor = findViewById(R.id.menu_BTN_sensors);
        buttons = findViewById(R.id.menu_BTN_buttons);
        topTen = findViewById(R.id.menu_BTN_top_ten);
        playerNameLayout = findViewById(R.id.menu_EDT_name);
        checkName = findViewById(R.id.menu_BTN_checkName);
        playerNameLayout = findViewById(R.id.menu_EDT_name);
        guideName = findViewById(R.id.menu_TXT_problemLabel);
        Glide.with(this).load("https://wallpaperaccess.com/full/884549.jpg").into(background);
    }

    private void initBtnClick() {
        sensor.setOnClickListener(view -> sensorGame());
        buttons.setOnClickListener(view -> btnGame());
        checkName.setOnClickListener((view -> CheckPlayerName()));
        topTen.setOnClickListener(view -> switchTopTen());
    }


    private void CheckPlayerName() {
        String name = playerNameLayout.getEditText().getText().toString();
        if (name.length() < 3) {
            guideName.setText("You need at least 4 characters");
            return;
        }
        if (!isAlpha(name)) {
            guideName.setText("Each character should be a letters");
            return;
        }
        guideName.setText("Now choose Game Mode");
        playerName = playerNameLayout.getEditText().getText().toString();
        buttons.setVisibility(View.VISIBLE);
        sensor.setVisibility(View.VISIBLE);

    }

    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    private void switchToGame() {
        Intent gameActivity = new Intent(this, ActivityGame.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("gameMode", gameMode);
        bundle.putString("playerName", playerName);
        gameActivity.putExtra("Bundle", bundle);
        ActivityGame.setCallBack(callBack_checkTopTen);
        buttons.setVisibility(View.INVISIBLE);
        sensor.setVisibility(View.INVISIBLE);
        guideName.setText("");
        playerNameLayout.getEditText().getText().clear();
        theme_music.pause();
        startActivity(gameActivity);
    }

    private void sensorGame() {
        gameMode = true;
        switchToGame();
    }

    private void btnGame() {
        gameMode = false;
        switchToGame();
    }

    private void switchTopTen() {
        Intent topTenActivity = new Intent(this, ActivityTopTen.class);
        Bundle bundle = new Bundle();
        topTenActivity.putExtra("Bundle", bundle);
        ActivityTopTen.setCallBack_myData(callBack_myData);
        startActivity(topTenActivity);

    }

    //////////////Call Backs////////////////////////////////////
    CallBack_MyData callBack_myData = new CallBack_MyData() {

        @Override
        public ArrayList<Score> getMyData() {
            return getScores();
        }
    };

    private ArrayList<Score> getScores() {
        return scores;
    }

    CallBack_CheckTopTen callBack_checkTopTen = new CallBack_CheckTopTen() {

        @Override
        public void CheckTopTen(String name, int playerScore) {
            if (playerAlreadyExist(name, playerScore)) {
                return;
            }
            addScoreToData(name, playerScore);
        }

    };


    private void addScoreToData(String name, int playerScore) {
        Score score;
        getLocation();
        if (scores.size() == 10) {
            scores.remove(9);
        }
        scores.add(new Score(playerScore, name, latitude, longitude));
        Collections.sort(scores);
    }

    private boolean playerAlreadyExist(String name, int score) {
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i).getName().equals(name)) {
                if (scores.get(i).getScore() < score) {
                    scores.get(i).setScore(score);
                    getLocation();
                    scores.get(i).setLatitude(latitude);
                    scores.get(i).setLongitude(longitude);
                    return true;
                }

            }
        }
        return false;
    }


    private void getLocation() {
        LocationPlayerTrack locationServis = new LocationPlayerTrack(ActivityMenu.this);
        if (locationServis.canGetLocation()) {
            latitude = locationServis.getLatitude();
            longitude = locationServis.getLongitude();
            //Log.d("taffff", "updateScoreGame: "+latitude);
            //Log.d("taffff", "updateScoreGame: "+longitude);

            Toast.makeText(getApplicationContext(), "THE PLAYER SAVED", Toast.LENGTH_LONG).show();
        } else {
            locationServis.showSettingsAlert();
            latitude = locationServis.getLatitude();
            longitude = locationServis.getLongitude();

        }
    }


    private void initGPS() {
        new LocationPlayerTrack(ActivityMenu.this).showSettingsAlert();
    }


}