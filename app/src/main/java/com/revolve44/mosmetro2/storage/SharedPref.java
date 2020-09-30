package com.revolve44.mosmetro2.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.List;

public class SharedPref {
//    private String NAME = "SpinKotlin";
//    private int MODE = Context.MODE_PRIVATE;
//
    private static SharedPreferences preferences;

    private SharedPref(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void initPreferences(Context context) {
        new SharedPref(context);
    }

    public static Float getTemp(Context context) {
        return preferences.
                getFloat("temp", 0);
    }

    public static void setOne(List<String> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Московское центральное кольцо", json);
        editor.apply();
    }


    public static void setHash(String hash, Context context){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("hash", hash);
        editor.apply();
    }
    public static String getHash(Context context){
        return preferences.
                getString("hash", "zero");
    }

    public static int getCount(Context context) {
        return preferences.
                getInt("c", 0);
    }

    public static void setCount(int c, Context context) {
        SharedPreferences.Editor editor = preferences.edit();
        c++;
        editor.putInt("c", c).apply();
        editor.apply();
    }





}