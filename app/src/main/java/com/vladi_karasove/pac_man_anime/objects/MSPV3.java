package com.vladi_karasove.pac_man_anime.objects;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MSPV3 {
    private final String SP_FILE = "SP_FILE";
    private static MSPV3 me;
    private SharedPreferences sharedPreferences;

    public static MSPV3 getMe() {
        return me;
    }

    private MSPV3(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
    }

    public static MSPV3 initHelper(Context context) {
        if (me == null) {
            me = new MSPV3(context);
        }
        return me;
    }
    public <T> void putArray(String KEY, ArrayList<T> array) {
        String json = new Gson().toJson(array);
        sharedPreferences.edit().putString(KEY, json).apply();
    }
    public <T> ArrayList<T> getArray(String KEY, TypeToken typeToken) {
        // type token == new TypeToken<ArrayList<YOUR_CLASS>>() {}
        try {
            ArrayList<T> arr = new Gson().fromJson(sharedPreferences.getString(KEY, ""), typeToken.getType());
            if (arr == null) {
                return new ArrayList<>();
            }
            return arr;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

//    public void putDouble(String KEY, double defValue) {
//        putString(KEY, String.valueOf(defValue));
//    }
//
//    public double getDouble(String KEY, double defValue) {
//        return Double.parseDouble(getString(KEY, String.valueOf(defValue)));
//    }
//
//    public int getInt(String KEY, int defValue) {
//        return sharedPreferences.getInt(KEY, defValue);
//    }
//
//    public void putInt(String KEY, int value) {
//        sharedPreferences.edit().putInt(KEY, value).apply();
//    }
//
//    public String getString(String KEY, String defValue) {
//        return sharedPreferences.getString(KEY, defValue);
//    }
//
//    public void putString(String KEY, String value) {
//        sharedPreferences.edit().putString(KEY, value).apply();
//    }
}
