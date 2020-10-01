package com.revolve44.mosmetro2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.revolve44.mosmetro2.network.Api;
import com.revolve44.mosmetro2.network.CellsResult;
import com.revolve44.mosmetro2.network.MosService;
import com.revolve44.mosmetro2.storage.SharedPref;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    /*
    Here we make request to server, and save answer from him in HashMap

    First (Splash) Screen
    //Main request URL: https://apidata.mos.ru/v1/datasets/1488/rows/?api_key=79bba0ab1c9f025ca4136af28229c43a
     */
    public static boolean offline = false;

    TextView textView;
    Api api = new Api();
    CodeTrick ct = new CodeTrick();

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.initPreferences(this); // send context to method in class SharedPref
        setContentView(R.layout.activity_load);


        ct.fillArrays(); // fill arrays of stations
        api.getCurrentData(); // make request to server
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something
                Engine();

            }
        }, 3000);

    }

    public void Engine(){
        Intent goaway = new Intent(SplashActivity.this, LinesActivity.class);
        startActivity(goaway);
        finish();
    }

    @Override
    protected void onDestroy() {
        SharedPref.setCount(0,getApplicationContext());
        super.onDestroy();
    }
}