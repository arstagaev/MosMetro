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


    public static void setTreeMap1(String hash, Context context){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("hash", hash);
        editor.apply();
    }
    public static String getTreeMap1(Context context){
        return preferences.
                getString("hash", "zero");
    }

    public static void setTreeMap2(String hash, Context context){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("hash2", hash);
        editor.apply();
    }
    public static String getTreeMap2(Context context){
        return preferences.
                getString("hash2", "zero");
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

    public static void setCheckData(Boolean success, Context context){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("success", success);
        editor.apply();
    }
    public static Boolean getCheckData(Context context){
        return preferences.
                getBoolean("success", false);
    }





}
