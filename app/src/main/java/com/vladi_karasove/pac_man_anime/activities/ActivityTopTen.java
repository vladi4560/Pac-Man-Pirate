package com.vladi_karasove.pac_man_anime.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.vladi_karasove.pac_man_anime.R;
import com.vladi_karasove.pac_man_anime.callBacks.CallBack_MyData;
import com.vladi_karasove.pac_man_anime.callBacks.CallBack_Score;
import com.vladi_karasove.pac_man_anime.fragments.FragmentMap;
import com.vladi_karasove.pac_man_anime.fragments.FragmentScore;

public class ActivityTopTen extends AppCompatActivity {
    private FragmentScore fragmentScores;
    private FragmentMap fragmentMap;
    private MaterialButton back;
    private ImageView backGround;
    private MediaPlayer theme_music;
    private static CallBack_MyData callBack_myData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        theme_music= MediaPlayer.create(ActivityTopTen.this,R.raw.fog_bound);
        initViews();
        initBTNClick();
        //fragment_Scores
        fragmentScores = new FragmentScore();
        fragmentScores.setActivity(this);
        fragmentScores.setScores(callBack_myData.getMyData());
        fragmentScores.setCallBackScore(callBack_score);
        getSupportFragmentManager().beginTransaction().add(R.id.top_ten_LAY_list, fragmentScores).commit();

        //fragment_Map
        fragmentMap = new FragmentMap();
        fragmentMap.setActivity(this);
        getSupportFragmentManager().beginTransaction().add(R.id.top_ten_LAY_map, fragmentMap).commit();


    }

    private void initViews() {
        back = findViewById(R.id.top_ten_BTN_back);
        backGround=findViewById(R.id.top_ten_img_background);
        Glide.with(this).load("https://static.wikia.nocookie.net/piratesdescaraibes/images/2/29/Pirates-of-the-caribbeanlogo.jpg/revision/latest?cb=20150228153400&path-prefix=fr").into(backGround);
    }

    private void initBTNClick(){
        back.setOnClickListener(view->finish());
    }
    public static void setCallBack_myData(CallBack_MyData callBack_MyData) {
        callBack_myData = callBack_MyData;
    }

    CallBack_Score callBack_score = new CallBack_Score() {
        @Override
        public void zoomOnMap(double lat, double lon) {
            zoomPlayer(lat,lon);
        }
    };

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    @Override
    protected void onStart() {
        super.onStart();
        theme_music.start();
        theme_music.setLooping(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        theme_music.pause();
    }

    private void zoomPlayer(double lat, double lon) {
        GoogleMap gm = fragmentMap.getMap();
        LatLng point = new LatLng(lat, lon);
        gm.addMarker(new MarkerOptions()
                .position(point));
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13.0f));
    }

}
