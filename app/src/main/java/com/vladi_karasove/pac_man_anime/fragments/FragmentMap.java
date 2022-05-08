package com.vladi_karasove.pac_man_anime.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vladi_karasove.pac_man_anime.R;
import com.vladi_karasove.pac_man_anime.callBacks.CallBack_Map;

public class FragmentMap extends Fragment implements OnMapReadyCallback {
    private AppCompatActivity activity;
    private SupportMapFragment supportMapFragment;
    private GoogleMap gMap;
    private CallBack_Map callBack_map;



    public FragmentMap() {
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap=googleMap;
        LatLng sydney = new LatLng(-34, 151);
        gMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));
    }

    public GoogleMap getMap() {
        return gMap;
    }

    public void setCallBack_map(CallBack_Map callBack_map) {
        this.callBack_map = callBack_map;
    }
}
