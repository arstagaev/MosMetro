package com.revolve44.mosmetro2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.revolve44.mosmetro2.network.Api;
import com.revolve44.mosmetro2.storage.SharedPref;

public class SplashActivity extends AppCompatActivity {
    /*
    Here we make request to server, and save answer from him in HashMap

    First (Splash) Screen
    //Main request URL: https://apidata.mos.ru/v1/datasets/1488/rows/?api_key=79bba0ab1c9f025ca4136af28229c43a
     */
    public static boolean offline = false;
    public static boolean dataexist = false;

    TextView textView;
    Button refresh;
    ProgressBar progressBar;
    Api api = new Api();
    CodeTrick ct = new CodeTrick();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.initPreferences(this); // send context to method in class SharedPref
        setContentView(R.layout.activity_splash);

        textView = findViewById(R.id.maintv);
        refresh = findViewById(R.id.refresh);
        progressBar = findViewById(R.id.progressBar);
        refresh.setVisibility(View.GONE);
        preparations();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void preparations(){
        progressBar.setVisibility(View.VISIBLE);
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
        dataexist = SharedPref.getCheckData(getApplicationContext());

        if (!offline | dataexist){
            Intent goaway = new Intent(SplashActivity.this, LinesActivity.class);
            startActivity(goaway);
            finish();
        }else{
            textView.setText("No Internet \n Check Connection");
            textView.setTextColor(Color.RED);
            refresh.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);


            //refresh.setVisibility(View.VISIBLE);
            refresh.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    //when play is clicked show stop button and hide play button
                     preparations();
                    refresh.setVisibility(View.GONE);


                }
            });

        }

    }

    @Override
    protected void onDestroy() {
        SharedPref.setCount(0,getApplicationContext());
        super.onDestroy();
    }
}