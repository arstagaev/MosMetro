package com.revolve44.mosmetro2;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revolve44.mosmetro2.adapters.StationAdapter;
import com.revolve44.mosmetro2.network.Api;
import com.revolve44.mosmetro2.storage.SharedPref;


import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class StationsActivity extends AppCompatActivity {

    /*
    Here we are may see list of stations of Moscow Subway

    Third Screen
     */

    private RecyclerView recyclerView;
    private StationAdapter adapter;

    private ArrayList<String> listOfStations = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPref.initPreferences(this); // send context to method in class SharedPref
        setContentView(R.layout.activity_stations);

        preparestart();
        initViews();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                buildcards();
            }
        }, 1000);
    }

    private void preparestart() {
        String name = getIntent().getStringExtra(Api.EXTRA_NAME);
        listOfStations = getIntent().getStringArrayListExtra(Api.EXTRA_ST);
        Log.d("order3",""+listOfStations);
        //Toast.makeText(getApplicationContext(), ""+ name, Toast.LENGTH_LONG).show();
        setTitle(""+name);

        assert name != null;
        if (name.equals("Московское центральное кольцо")){
            Snackbar.make(findViewById(android.R.id.content),"Выбрано "+name,Snackbar.LENGTH_SHORT).show();
        }else {
            Snackbar.make(findViewById(android.R.id.content),"Выбрана "+name,Snackbar.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initViews() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_st);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void buildcards() {

        adapter = new StationAdapter(listOfStations);
        recyclerView.setAdapter(adapter);

    }
}
