package com.vladi_karasove.pac_man_anime.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textview.MaterialTextView;
import com.vladi_karasove.pac_man_anime.R;
import com.vladi_karasove.pac_man_anime.activities.ActivityTopTen;
import com.vladi_karasove.pac_man_anime.callBacks.CallBack_Score;
import com.vladi_karasove.pac_man_anime.objects.Score;

import java.util.ArrayList;

public class FragmentScore extends Fragment {

    private MaterialTextView[] listTable;
    private ArrayList<Score> scores;
    private AppCompatActivity activity;


    private CallBack_Score callBack_score;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        findViews(view);
        TableAction();
        addPlayerToBoard();
        return view;
    }

    private void findViews(View view) {
        listTable = new MaterialTextView[]{view.findViewById(R.id.top_ten_TXT_num1),
                view.findViewById(R.id.top_ten_TXT_num2),
                view.findViewById(R.id.top_ten_TXT_num3),
                view.findViewById(R.id.top_ten_TXT_num4),
                view.findViewById(R.id.top_ten_TXT_num5),
                view.findViewById(R.id.top_ten_TXT_num6),
                view.findViewById(R.id.top_ten_TXT_num7),
                view.findViewById(R.id.top_ten_TXT_num8),
                view.findViewById(R.id.top_ten_TXT_num9),
                view.findViewById(R.id.top_ten_TXT_num10)

        };
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }

    private void addPlayerToBoard() {
        for (int i = 0; i < scores.size(); i++)
            listTable[i].setText((i+1)+". "+scores.get(i).getName() + " " + scores.get(i).getScore());
    }


    private void TableAction(){
        for (int i=0; i<scores.size(); i++){
            Score tempScore=scores.get(i);
            listTable[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                callBack_score.zoomOnMap(tempScore.getLatitude(),tempScore.getLongitude());
                }
            });
        }
    }

    public void setActivity(ActivityTopTen activityTopTen) {
        activity=activityTopTen;
    }
    public void setCallBackScore(CallBack_Score callBack_score) {
        this.callBack_score = callBack_score;
    }


}
